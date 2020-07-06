package dt.main;

import java.util.Scanner;

import dt.bean.DataSet;
import dt.bean.DecisionTree;
import dt.operator.Helper;
import dt.operator.TestDataProcessor;
import dt.operator.TreeProcessor;
public class DecisionTreeMain {

	public static void main (String [] args) {
		
		menu();
		/*for(int i =0;i<10;i++)
		{
			Scanner sc=new Scanner(System.in);
			System.out.println("i : "+i);
			classifier("hepatitis-training-run-"+i,"hepatitis-test-run-"+i,sc);
		}*/
	}
	
	public static void menu()
	{
		System.out.println("Decision Tree implementation for AI Assignment 1");
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter training dataset file name : ");
		String trainFile=sc.nextLine();
		System.out.println("Enter test dataset file name : ");
		String testFile=sc.nextLine();
		classifier(trainFile,testFile,sc);
		sc.close();
	}
	
	public static void classifier(String trainFile, String testFile, Scanner sc)
	{
		DataSet data = DataLoader.readData(trainFile,testFile,sc);
		DecisionTree dt = TreeProcessor.treeBuilder(data.getTrainingSet(),data.getAttributeNames());
		System.out.println("\nDecision Tree:");
		Helper.displayFinalTree("",dt);
		Helper.getBaseLineClassifier(data);
		Helper.getAccuracy(TestDataProcessor.processTesData(data, dt),data.getTestSet());
	}

}
