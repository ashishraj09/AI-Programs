package knn.implementation;

public class ManhanttanDistanceCalculator {

	public static double calculateManhanttanDistance( double[] trainFeatures,  double[]  testFeatures) {
        double cal = 0;
        for (int i = 0; i < trainFeatures.length; i++)
        {  
        	cal = cal + Math.abs(trainFeatures[i] - testFeatures[i]);
        }
        return Math.sqrt(cal);
    }
}
