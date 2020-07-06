package knn.implementation;

import java.util.List;

import knn.bean.KNNData;
import knn.bean.KNNResultData;

public class AccuracyCalculator {

	public static void getAccuracy(KNNData originalData,List<KNNResultData> predictedData)
	{
		int count = 0;
		for(int i=0;i<predictedData.size();i++)
		{
			
			
			if(originalData.getTestLabel().get(i).toString().equals(predictedData.get(i).getResultLabel().toString()))
			{
				count++;
			}
		}
		System.out.println("\nCorrect Prediction : "+count);
		System.out.println("Total Dataset : "+originalData.getTestLabel().size());
		System.out.println("Test Accuracy : " + (((float) count / originalData.getTestLabel().size()) * 100) + "%");
		printResult(originalData, predictedData);
	}
	
	public static void printResult(KNNData originalData,List<KNNResultData> predictedData)
	{
		System.out.println("----------------------------------");
		List<String> names = originalData.getAttributeName();
		for(String name : names)
		{
			System.out.print(name+" ");
		}
		System.out.print("Predicted_Class");
		System.out.println("");
		System.out.println("----------------------------------");
		for(int i=0;i<predictedData.size();i++)
		{
			
			double[] testFeatures = originalData.getTestFeatures().get(i);
				for(double feature : testFeatures)
				{
					System.out.print(feature+",");
				}
				
				
					System.err.print(originalData.getTestLabel().get(i).toString()+","+predictedData.get(i).getResultLabel().toString());
				
				System.out.println("");
		}
		
	}
}
