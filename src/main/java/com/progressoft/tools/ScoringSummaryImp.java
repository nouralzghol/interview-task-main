package com.progressoft.tools;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ScoringSummaryImp implements ScoringSummary{
    private ArrayList<BigDecimal> dataToCalculate = new ArrayList<>();

    public void setData(ArrayList<BigDecimal> nonStandardizeData) {
        dataToCalculate = nonStandardizeData;
    }


    @Override
    public BigDecimal mean() {
        double sum=0;
        double count=dataToCalculate.size();

        for(BigDecimal i:dataToCalculate){
            sum=sum+Double.parseDouble(String.valueOf(i));
        }

        double result=Math.ceil(sum/count);
        BigDecimal mean=BigDecimal.valueOf(result);

        return mean.setScale(2,RoundingMode.CEILING);
    }

    @Override
    public BigDecimal standardDeviation() {
        BigDecimal size = new BigDecimal(dataToCalculate.size());
        BigDecimal standardDeviation =new BigDecimal(0);
        BigDecimal mean=mean();
        for (int i=0;i<dataToCalculate.size();i++) {
            standardDeviation = standardDeviation.add((dataToCalculate.get(i)).subtract(mean).pow(2));
        }
        MathContext mContext = new MathContext(10,RoundingMode.UP);
        return standardDeviation.divide(size,RoundingMode.UP).sqrt(mContext).setScale(2, RoundingMode.UP);

    }

    @Override
    public BigDecimal variance() {
        BigDecimal variance=standardDeviation().pow(2).setScale(0,RoundingMode.HALF_EVEN);
        return variance.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal median() {
        BigDecimal median;
        BigDecimal[] array=dataToCalculate.toArray(new BigDecimal[0]);
        Arrays.sort(array);
        int length=array.length;
        if(length%2==0){
            int m1=length/2;
            int m2=(length/2)+1;
            median=(array[m1].add(array[m2])).divide(BigDecimal.valueOf(2),RoundingMode.HALF_EVEN);
        }else{
            int m=length/2;
            median=(array[m]);
        }
        return median.setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal min() {
        return Collections.min(dataToCalculate).setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal max() {
        return Collections.max(dataToCalculate).setScale(2, RoundingMode.HALF_EVEN);
    }
}
