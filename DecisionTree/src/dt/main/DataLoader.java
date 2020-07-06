package dt.main;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dt.bean.DataSet;
import dt.bean.Instance;

public class DataLoader {
	
	DataLoader(){}
	
	public static DataSet readData(String trainFile, String testFile, Scanner sc) {
		
		DataSet data = new DataSet();
		try {
				BufferedReader trainContent = readFile(trainFile.replaceAll("\"", ""),sc);
				BufferedReader testContent = readFile(testFile.replaceAll("\"", ""),sc);
				data = processFileContent(data, trainContent, true, false);
				data = processFileContent(data, testContent, true, true);
				trainContent.close();
				testContent.close();
			}
			catch(Exception e)
			{
				System.out.println("Error reading data "+e.getLocalizedMessage());
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
	
	private static BufferedReader readFile(String file, Scanner sc)
	{
		
		BufferedReader fileContent = null ;
		try {
			FileInputStream fileStream = new FileInputStream(file);
			DataInputStream inputStream = new DataInputStream(fileStream);
			fileContent = new BufferedReader(new InputStreamReader(inputStream));
			
		} catch (FileNotFoundException e) {
			
			System.err.println("File Error: " + e.getMessage());
			RetryOperator.retry(sc);
			
		}
		return fileContent;

	}
	
	private static DataSet processFileContent(DataSet data, BufferedReader fileContent,boolean readCategory,boolean test)
	{
		String currentLine;
		List<String> attributeNames = new ArrayList<String>();
		List<Instance> attributeSet = new ArrayList <Instance> ();
		try {
			
			currentLine = fileContent.readLine();
			if(readCategory)
			{
				String[] tokens = currentLine.split(getFileDelimter(currentLine));
				 for (int i = 1; i < tokens.length; i++)
				 {
					 attributeNames.add(tokens[i]);
				 }
				 
				 data.setAttributeNames(attributeNames);
			}
			
			currentLine = fileContent.readLine();
			
			while (currentLine != null)   {
				if( currentLine.trim().isEmpty() ) {
			          break;
			     }
				
					String[] tokens = currentLine.split(getFileDelimter(currentLine));
					boolean[] attributes = new boolean[tokens.length];
					
					
					
					for (int i = 1; i < tokens.length; i++)
					{
						
						attributes[i-1] = Boolean.parseBoolean(tokens[i]);
					}
					attributeSet.add(new Instance(attributes, new boolean[tokens.length]));
					currentLine = fileContent.readLine();
					
					if(tokens[0].contentEquals("live"))
					{
						attributes[tokens.length-1] = true;
					}
					else
					{
						attributes[tokens.length-1] = false;
					}
				}
		
			if(test)
			{
				data.setTestSet(attributeSet);
			}
			else
			{
				data.setTrainingSet(attributeSet);
			}
			
		
		} catch (IOException e) {
			System.err.println("I/O Error: " + e.getMessage());
		}
		
		return data;
	}
	
}
