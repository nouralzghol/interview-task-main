package com.progressoft.tools;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;


public class NormalizerImplementation implements Normalizer {
    private final ArrayList<String[]> dataArrayList = new ArrayList<>();
    int indexOfRequiredColumn;


    @Override
    public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize) {
        ScoringSummaryImp summary=new ScoringSummaryImp();
        try{
            readCSV(csvPath);
            indexOfRequiredColumn = getTheRequiredIndex(colToStandardize);

            ArrayList<BigDecimal> nonStandardizeData = storeInArray(indexOfRequiredColumn,csvPath);
            summary.setData(nonStandardizeData);

            ArrayList<BigDecimal> standardizedData=Standardize(nonStandardizeData,summary);

                  String normalizedCol=colToStandardize+"_z";
                  addCalculatedCol(destPath,normalizedCol,standardizedData);

              }catch (IOException e) {
                  e.printStackTrace();
              }

        return summary;
    }

    @Override
    public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) {
        ScoringSummaryImp summary=new ScoringSummaryImp();
        try{
            readCSV(csvPath);
            indexOfRequiredColumn = getTheRequiredIndex(colToNormalize);

            ArrayList<BigDecimal> nonNormalizedData = storeInArray(indexOfRequiredColumn,csvPath);
            summary.setData(nonNormalizedData);

            ArrayList<BigDecimal> normalizedData=Normalize(nonNormalizedData,summary);

            String normalizedCol=colToNormalize+"_mm";
            addCalculatedCol(destPath,normalizedCol,normalizedData);

        }catch (IOException e) {
            e.printStackTrace();
        }

        return summary;
    }




    private int getTheRequiredIndex(String requiredColumn) {
        indexOfRequiredColumn = -1;
        String[] search = dataArrayList.get(0);

        for (int i = 0; i < search.length; i++) {
            if (search[i].equals(requiredColumn))
                indexOfRequiredColumn = i;
        }

            if (indexOfRequiredColumn == -1) {
                throw new IllegalArgumentException("column " + requiredColumn + " not found");
            }
        return indexOfRequiredColumn;
    }

    private ArrayList<BigDecimal> storeInArray(int indexOfRequiredColumn,Path csvPath) {
        ArrayList<BigDecimal> nonStandardizeData=new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(csvPath)));
            String line= reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                nonStandardizeData.add(BigDecimal.valueOf(Double.parseDouble(columns[indexOfRequiredColumn])));

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return nonStandardizeData;
    }

    private void readCSV(Path csvPath) throws IOException {
        try(BufferedReader csvReader = new BufferedReader(new FileReader(String.valueOf(csvPath)))) {
            String row;
            while ((row = csvReader.readLine()) != null) {
                String[] rowData = row.split(",");
                dataArrayList.add(rowData);
            }
        }
        catch (FileNotFoundException e){
            throw new IllegalArgumentException("source file not found");
        }
    }

    private void addCalculatedCol(Path destPath, String newCol, ArrayList<BigDecimal> calculatedData) throws FileNotFoundException {
        try (PrintWriter writer = new PrintWriter(String.valueOf(destPath))) {
            StringBuilder csvHeader = new StringBuilder();
            for(int headerColIndex=0;headerColIndex<=indexOfRequiredColumn;headerColIndex++) {
                csvHeader.append(dataArrayList.get(0)[headerColIndex]);
                csvHeader.append(",");
            }
            csvHeader.append(newCol);
            if(dataArrayList.get(0).length-1!=indexOfRequiredColumn) {
                csvHeader.append(",");
                for(int headerColIndex=indexOfRequiredColumn+1;headerColIndex<dataArrayList.get(0).length;headerColIndex++) {
                    csvHeader.append(dataArrayList.get(0)[headerColIndex]);
                    if(headerColIndex!=dataArrayList.get(0).length-1)
                        csvHeader.append(",");
                }
            }
            csvHeader.append('\n');
            //adding the headline after editing it
            writer.write(csvHeader.toString());
            StringBuilder csvData = new StringBuilder();
            for(int dataRowIndex=1;dataRowIndex< dataArrayList.size();dataRowIndex++){
                for(int dataColIndex=0;dataColIndex<=indexOfRequiredColumn;dataColIndex++) {
                    csvData.append(dataArrayList.get(dataRowIndex)[dataColIndex]);
                    csvData.append(",");
                }
                csvData.append(calculatedData.get(dataRowIndex-1));
                if(dataArrayList.get(0).length-1!=indexOfRequiredColumn) {
                    csvData.append(",");
                    for(int dataColIndex=indexOfRequiredColumn+1;dataColIndex<dataArrayList.get(dataRowIndex).length;dataColIndex++) {
                        csvData.append(dataArrayList.get(dataRowIndex)[dataColIndex]);
                        if(dataColIndex!=dataArrayList.get(dataRowIndex).length-1)
                            csvData.append(",");
                    }
                }
                csvData.append('\n');
            }

            writer.write(csvData.toString());

        }
    }

    private ArrayList<BigDecimal> Standardize(ArrayList<BigDecimal> columnToStandardize,ScoringSummaryImp summary) {
        ArrayList<BigDecimal> standardizedData = new ArrayList<>();
        BigDecimal mean=summary.mean();
        BigDecimal standardDeviation=summary.standardDeviation();

        for(BigDecimal elementToStandardize: columnToStandardize){
            standardizedData.add(elementToStandardize.subtract(mean).divide(standardDeviation, RoundingMode.HALF_EVEN));
        }
        return standardizedData;

    }

    private ArrayList<BigDecimal> Normalize(ArrayList<BigDecimal> nonNormalizedData, ScoringSummaryImp summary) {
        summary.setData(nonNormalizedData);
        ArrayList<BigDecimal> normalizedData = new ArrayList<>();
        BigDecimal max=summary.max();
        BigDecimal min=summary.min();

        for(BigDecimal elementToNormalize: nonNormalizedData){
            normalizedData.add((elementToNormalize.subtract(min)).divide(max.subtract(min), RoundingMode.HALF_EVEN));
        }
        return normalizedData;
    }


}