package classifier;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import data.bean.Feature;
import data.operator.Dataloader;

public class Start {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner( System.in ); 
		System.out.println("Naive Bayes Classifier\n");
		System.out.println("Enter the training set file Name:");
		String trainingSet = sc.nextLine();
		
		if(trainingSet.isBlank() || trainingSet.isEmpty())
		{
			trainingSet = "spamLabelled.dat";
		}
		
		System.out.println("Enter the test set file Name:");
		String testSet = sc.nextLine();
		if(testSet.isBlank() || testSet.isEmpty())
		{
			testSet = "spamUnlabelled.dat";
		}

		List<Feature> trainData = Dataloader.loadTrainData(trainingSet);
		List<Feature> testData = Dataloader.loadTestData(testSet);

		Classifier c = new Classifier();
		c.getNaiveBayesClassifier(trainData);
		int index = 1;
		for (Feature feature : testData) {
			c.appendtoOutput("Feature " + index + ": ");
			index++;
			c.getPredictions(feature);
		}
		c.appendtoOutput(
				"--------------------------------------------------------------------------------------------------------\n");
		System.out.println("\n"+c.getOutput().toString());
		outputToFile(c.getOutput().toString());
		sc.close();

	}

	public static void outputToFile(String fileContent) {
		   try {
		      FileWriter myWriter = new FileWriter("sampleoutput.txt");
		      myWriter.write(fileContent);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file : sampleoutput.txt.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
     }

}
