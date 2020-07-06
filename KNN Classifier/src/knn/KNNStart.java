package knn;

import java.util.ArrayList;
import java.util.Scanner;

import knn.bean.KNNData;
import knn.bean.KNNResultData;
import knn.file.operator.DataLoader;
import knn.implementation.AccuracyCalculator;
import knn.implementation.KNNProcessor;

public class KNNStart {

	public static void main(String[] args) {
		menu();
	}
	
	public static void menu()
	{
		System.out.println("KNN algorthim implementation for AI Assignment 1");
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter training dataset file name : ");
		String trainFile=sc.nextLine();
		System.out.println("Enter test dataset file name : ");
		String testFile=sc.nextLine();
		KNNData data = new KNNData();
		data = DataLoader.loadTrainingData(trainFile,data,sc);
		data = DataLoader.loadTestData(testFile,data,sc);
		ArrayList<KNNResultData> result = KNNProcessor.Process(data,KConfigMenu.getFormulaChoice(sc),KConfigMenu.getKValue(sc, data));
		AccuracyCalculator.getAccuracy(data, result);
		sc.close();
	}
	
	

}
