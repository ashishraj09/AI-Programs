package knn.implementation;

public class EuclideanDistanceCalculator {

	public static double calculateEuclideanDistance( double[] trainFeatures,  double[]  testFeatures) {
        double cal = 0;
        for (int i = 0; i < trainFeatures.length; i++)
        {  
        	cal += Math.pow(trainFeatures[i] - testFeatures[i], 2);
        }
        return Math.sqrt(cal);
    }
	
}
