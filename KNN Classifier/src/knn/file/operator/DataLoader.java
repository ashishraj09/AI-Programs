package knn.file.operator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import knn.bean.KNNData;

public class DataLoader {
	
	public static KNNData loadTrainingData(String trainFile,KNNData data, Scanner sc) {
		
		String currentLine;
		try {
				List<String> attributeName = new ArrayList<>();
				List<double[]> trainFeatures = new ArrayList<>();
				List<String> trainLabels = new ArrayList<>();
				trainFile = trainFile.replace("\"", "");
			 	FileInputStream fileStream = new FileInputStream(trainFile);
			 	DataInputStream inputStream = new DataInputStream(fileStream);
				BufferedReader fileContent = new BufferedReader(new InputStreamReader(inputStream));
				currentLine = fileContent.readLine();
				String[] token = currentLine.split(getFileDelimter(currentLine));
				 for (int i = 1; i < token.length; i++)
				 {
					 attributeName.add(token[i]);
				 }
				 
				 data.setAttributeName(attributeName);
				 currentLine = fileContent.readLine();
				
				while (currentLine != null)   {
					if( currentLine.trim().isEmpty() ) {
				          break;
				     }
					
					 String[] tokens = currentLine.split(getFileDelimter(currentLine));
					 double[] featureList = new double[tokens.length - 1];
					   for (int i = 0; i < tokens.length; i++)
					   {
							if(i+1 == tokens.length)
					   		{
					   			trainLabels.add(tokens[i]);
					   		}
					   		else
					   		{
					   			featureList[i] = Double.parseDouble(tokens[i]);
					   		}
					   }
					   trainFeatures.add(featureList);
					   currentLine = fileContent.readLine();
				}
				data.setTrainFeatures(trainFeatures);
				data.setTrainLabel(trainLabels);
				inputStream.close();
				
		}catch (FileNotFoundException e) {
			System.err.println("File Error: " + e.getMessage());
			RetryOperator.retry(sc);
		}catch (IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
		}
		
		return data;
		
	}
	
	public static KNNData loadTestData(String testFile,KNNData data,Scanner sc) {
		
		String currentLine;
		try {
				List<double[]> testFeatures = new ArrayList<>();
				List<String> testLabels = new ArrayList<>();
				List<String> attributeName = new ArrayList<>();
				testFile = testFile.replace("\"", "");
			 	FileInputStream fileStream = new FileInputStream(testFile);
			 	DataInputStream inputStream = new DataInputStream(fileStream);
				BufferedReader fileContent = new BufferedReader(new InputStreamReader(inputStream));
				
				currentLine = fileContent.readLine();
				String[] token = currentLine.split(getFileDelimter(currentLine));
				 for (int i = 1; i < token.length; i++)
				 {
					 attributeName.add(token[i]);
				 }
				 
				 data.setAttributeName(attributeName);
				 currentLine = fileContent.readLine();
				
				while (currentLine != null)   {
					if( currentLine.trim().isEmpty() ) {
				          break;
				     }
					
					   String[] tokens = currentLine.split(getFileDelimter(currentLine));
					   double[] featureList = new double[tokens.length - 1];
					   for (int i = 0; i < tokens.length; i++)
					   {   
						   if(i+1 == tokens.length)
					   		{
					   			testLabels.add(tokens[i]);
					   		}
					   		else
					   		{
					   			featureList[i] = Double.parseDouble(tokens[i]);
					   		}
					   }
					   testFeatures.add(featureList);
					   currentLine = fileContent.readLine();
					   }
				data.setTestFeatures(testFeatures);
				data.setTestLabel(testLabels);
				inputStream.close();
				
		}catch (FileNotFoundException e) {
			System.err.println("File Error: " + e.getMessage());
			RetryOperator.retry(sc);
		}catch (IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
		}
		
		return data;
		
	}
	
	private static String getFileDelimter(String line)
	{
		String cvsSplitBy = " ";
		if (line.contains(",")) {
            cvsSplitBy = ",";
        } else if (line.contains(";")) {
           cvsSplitBy = ";";
        }else if (line.contains("  ")) {
            cvsSplitBy = "  ";
         }
        else if (line.contains(" ")) {
            cvsSplitBy = " ";
         }else {
            System.out.println("Wrong separator!!! Please check the file.");
        }
		  return cvsSplitBy;
	}

}
