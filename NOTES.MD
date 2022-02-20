- Describe in your own words what is z-score and Min-Max scaling and why would we need to use such normalization methods? describe by example, if possible.
```
The purpose of normalization is that in machine learning we try to find patterns in the data
 by comparing features of data points, but the issue is when 
 the features are on drastically different scales.
 so they invented normalization methods to handle these outliers and
 reduce the dominance of features on the other.

-Min-Max Scaling is one of the most common ways to normalize data.
 For every feature, the minimum value of that feature gets transformed into a 0,
  the maximum value gets transformed into a 1, and every other value gets
  transformed into a decimal between 0 and 1.
   Min-Max Scaling Guarantees all features will have the exact same scale but does not handle outliers well.

- example:
minValue=20, maxValue=40
valueToBeNormalized=30
20 -> 0   & 40 -> 1
30 -> 0.5


- Z-score Scaling is a strategy of normalizing data that avoids the outlier issue 
but it doesn't produce normalized data with the exact same scale.
Z-score Scaling needs 2 operations to be done on the data group:
 1. Mean value ( μ )
 2. standard deviation ( σ )
 
 -example:
 after calculating the μ and σ of the data using their equation
 we can apply the formula for each value in the data 
 so if we have :
 μ=65,000 
 σ=18,000
 value=85,800
 
 z=(value-μ)/σ
 z=(85,800-65,000)/18,000
 z=1.156
 
```

- We used Junit5 in our test cases, describe in your own words why would we use such library? 
what is unit testing and why would we use it?
```
JUnit testing is used to test the behaviour of methods inside the classes we have written in Java. it tests a method for the expected results and
 whether the method is able to handle the exceptions in the way we want.
 ```
- What is the IDE did you use?
```
IntelliJ IDEA 2021.2.1 (Community Edition)
x64

```

- Name the Java APIs you used?
```
Java SE API 
```

- Mention down any difficulties or concerns you faced while working on the task. Your feedback is appreciated :D.
```
  At first, I got stuck about how to implement the methods and I came up
  with an implementation that I did all the calculations in the 
  normalizerImplementaion class then passed them to the scoringSummaryImp
  class and that didn't work out either, then finally after doing a lot 
  of searching I implement each interface in a separate class and played with the objects.
  Also, I faced some issues when I was Rounding the results of the calculations, 
  it kept giving wrong answers although I was using the right rounding methods,
  after rounding it twice it worked.I guess it's related to the rounding.
```

- If you implement any of the bonus points, please mentioned it in this file
```
write your answer here
```

- Did this task enrich technical skills or knowledge? If yes, tell us how: 
```
Yes, I learned so many things while solving the task, starting from knowing about normalization and how it is an essential part of machine learning, 
I collected all the information and the equations that I'll need.
I worked with new things to me like BigDecimal and Paths,
also it enriched my skills and knowledge with interfaces 
and objects, it was a hard task but I really enjoyed it, Thank you!
```
