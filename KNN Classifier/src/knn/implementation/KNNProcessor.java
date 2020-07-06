package knn.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import knn.bean.KNNData;
import knn.bean.KNNResultData;
import knn.bean.KNNSortedData;

public class KNNProcessor {

	public static ArrayList<KNNResultData> Process(KNNData data,int choice, int k)
	{
		Iterator<double[]> testFeaturesItr = data.getTestFeatures().iterator();
		ArrayList<KNNResultData> resultDataList = new ArrayList<>();
		while(testFeaturesItr.hasNext()){
			double testFeatures[] = testFeaturesItr.next();
			Iterator<double[]> trainFeaturesItr = data.getTrainFeatures().iterator();
			int labelCounter = 0;
			ArrayList<KNNSortedData> sortedDataList = new ArrayList<>();
			while(trainFeaturesItr.hasNext()) {
				double distance = 0;
				double trainFeatures[] = trainFeaturesItr.next();
				
				if(1 == choice)
					{	
						distance = EuclideanDistanceCalculator.calculateEuclideanDistance(trainFeatures, testFeatures);
					}
				else if(2 == choice)
					{
						distance = ManhanttanDistanceCalculator.calculateManhanttanDistance(trainFeatures, testFeatures);
					}
				
				KNNSortedData sortedData = new KNNSortedData(testFeatures,distance,data.getTrainLabel().get(labelCounter));
				labelCounter++;
				sortedDataList.add(sortedData);
				Collections.sort(sortedDataList, new Comparator<KNNSortedData>(){
				    @Override
					public int compare(KNNSortedData data1, KNNSortedData data2) {
						return Double.compare(data1.getDistance(),data2.getDistance());
					}
				});
				
			}
			
			int counter = 0; int label1 = 0, label2 = 0, label3 = 0;
			
			while (counter < k) {
				KNNSortedData sortedData = sortedDataList.get(counter);
				String label = sortedData.getResultLabel();
				if (label.equals("1"))
					
					{
					label1++;
					}
				else if (label.equals("2"))
					{
					label2++;
					}
				else if (label.equals("3"))
					{
					label3++;
					}
				counter++;

			}
			
			if (label1 > label2 && label1 > label3) {
				KNNResultData resultData = new KNNResultData(testFeatures,"1");
				resultDataList.add(resultData);

			} else if (label2 > label1 && label2 > label3) {
				KNNResultData resultData = new KNNResultData(testFeatures,"2");
				resultDataList.add(resultData);
			}

			else if (label3 > label1 && label3 > label2) {
				KNNResultData resultData = new KNNResultData(testFeatures,"3");
				resultDataList.add(resultData);
			} 
			else
			{
				KNNResultData resultData = new KNNResultData(testFeatures,sortedDataList.get(0).getResultLabel());
				resultDataList.add(resultData);
			}
			
		}
		return resultDataList;
		
	}

}
