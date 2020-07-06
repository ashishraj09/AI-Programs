package com.data.splitter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class DataOperator {
	
	static List<String> attributeName = new ArrayList<>();
	static List<Float[]> normalattributes = new ArrayList<>();
	static List<Float[]> anomalyAttributes = new ArrayList<>();
	static final Logger logger = Logger.getLogger(DataOperator.class);
	

public static void loadData(String file, double splitRatio) {
		
		String currentLine;
		try {
				logger.info("loadData() :: reading file " + file.replaceAll("\"", "")+".");
			 	FileInputStream fileStream = new FileInputStream(file.replaceAll("\"", ""));
			 	DataInputStream inputStream = new DataInputStream(fileStream);
				BufferedReader fileContent = new BufferedReader(new InputStreamReader(inputStream));
				
				currentLine = fileContent.readLine();
				
				String[] token = currentLine.split(getFileDelimter(currentLine));
				 for (int i = 0; i < token.length; i++)
				 {
					 attributeName.add(token[i]);
				 }
				 
				 int colLength =  attributeName.size();
				 
				 currentLine = fileContent.readLine();
				while (null != currentLine)   {
					if( currentLine.trim().isEmpty() ) {
				          break;
				     }
					
					String[] tokens = currentLine.split(getFileDelimter(currentLine));
					
					if(tokens[colLength -1].replaceAll("'", "").contentEquals("Normal"))
					{
						Float attribute[] = new Float[colLength];
						for (int i = 0; i < tokens.length -1; i++)
						{
							attribute[i] = Float.parseFloat(tokens[i]);
						}
						attribute[tokens.length - 1] = 1.0f;
						normalattributes.add(attribute);
					}
					else
					{
						Float attribute[] = new Float[colLength];
						for (int i = 0; i < tokens.length -1; i++)
						{
							attribute[i] = Float.parseFloat(tokens[i]);
						}
						attribute[tokens.length - 1] = 2.0f;
						anomalyAttributes.add(attribute);
					}
	
					currentLine = fileContent.readLine();
					
				}
				logger.info("loadData() :: read file complete.");
				fileContent.close();
				inputStream.close();
				fileStream.close();
				logger.debug("Debug file read :");
				if(logger.isDebugEnabled())
				{
					getPrintFile();
				}
				calculateSplitRatio(splitRatio);
				
		}catch (FileNotFoundException e) {
			logger.error("loadData() :: File Error: " + e.getMessage());
			System.exit(0);
			
		}catch (IOException e) {
			logger.error("loadData() :: I/O Error: " + e.getMessage());
			System.exit(0);
		}
		
		
	}

	private static void getPrintFile()
	{
		
		for(int i =0; i < attributeName.size(); i++)
		{
			System.out.print(attributeName.get(i)+ " ");
			
		}
		System.out.println();
		for(int i =0; i < anomalyAttributes.size(); i++)
		{
			Float[]attribute = anomalyAttributes.get(i);
			for(Float att : attribute)
			{
				System.out.print(att+" ");
				
			}
			System.out.println();
		}
		for(int i =0; i < normalattributes.size(); i++)
		{
			Float[]attribute = normalattributes.get(i);
			for(Float att : attribute)
			{
				System.out.print(att+" ");
				
			}
			System.out.println();
		}
		
		System.out.println("anomalyAttributes Size : "+anomalyAttributes.size());
		System.out.println("normalattributes Size : "+normalattributes.size());
		
		
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
        else if (line.contains("	")) {
            cvsSplitBy = "	";
         }
        else if (line.contains(" ")) {
            cvsSplitBy = " ";
         }else {
            logger.error("getFileDelimter() :: Wrong separator!!! Please check the file. " );
            
        }
		  return cvsSplitBy;
	}
	
	private static void calculateSplitRatio(double ratio)
	{
		int normalSize = normalattributes.size();
		int anomalSize = anomalyAttributes.size();
		
		int normalSplitSize = (int) (normalSize * ratio);
		int anomalSplitSize = (int) (anomalSize * ratio);
		logger.debug("Normal Total Size : " + normalSize + " normalSplitSize : "+normalSplitSize );
		logger.debug("Anomal Total Size : " + anomalSize + " normalSplitSize : "+anomalSplitSize );
		
		checkOrCreateFile("test_data");
		checkOrCreateFile("train_data");
		
		int normalStart = 0;
		int normalEnd = normalSplitSize + 1;
		int anomalStart = 0;
		int anomalEnd = anomalSplitSize + 1;
		logger.debug("Test Data  - normalStart: " + normalStart + " normalEnd : "+normalEnd + " anomalStart : "+ anomalStart+ " anomalEnd : "+anomalEnd);
		writeToFile("split_test_data",normalStart,normalEnd,anomalStart,anomalEnd);
		

		
		normalStart = normalSplitSize + 1;
		normalEnd = normalSize;
		anomalStart = anomalSplitSize + 1;
		anomalEnd = anomalSize;
		logger.debug("Train Data  - normalStart: " + normalStart + " normalEnd : "+normalEnd + " anomalStart : "+ anomalStart+ " anomalEnd : "+anomalEnd);
		writeToFile("split_train_data",normalStart,normalEnd,anomalStart,anomalEnd);
		
		
	}

	private static void writeToFile(String filename, int normalStart, int normalEnd,int anomalStart, int anomalEnd) {
		try {
		      FileWriter writer = new FileWriter(filename);
		      
		      for(int i =0; i < attributeName.size()-1; i++)
				{
		    	  writer.write(attributeName.get(i)+ " ");
					
				}
		      writer.write(attributeName.get(attributeName.size()-1)+ "\n");
		      
		      for(int i= anomalStart; i< anomalEnd; i++)
		      {
		    	  Float[]attribute = anomalyAttributes.get(i);
					for(int j =0; j< attribute.length - 1;  j++ )
					{
						writer.write(attribute[j]+ " ");
						
					}
					writer.write(attribute[attribute.length - 1]+"\n");
		    	  
		      }
		      for(int i= normalStart; i< normalEnd; i++)
		      {
		    	  Float[]attribute = normalattributes.get(i);
		    	  for(int j =0; j< attribute.length - 1;  j++ )
		    	  {
						writer.write(attribute[j]+ " ");
						
					}
					writer.write(attribute[attribute.length - 1]+"\n");
		      }
		      
		      writer.close();
		      logger.info("writeToFile() :: Successfully wrote to the file "+ filename+".");
		      
		    } catch (IOException e) {
		    	logger.error("writeToFile() :: IOException ",e);
		    }
		
	}


	private static void checkOrCreateFile(String string) {
		try {
		      File fileObject = new File(string);
		      if (fileObject.createNewFile()) {
		        logger.info("checkOrCreateFile() :: File created: "+ fileObject.getName());
		      } else {
		        logger.info("checkOrCreateFile() :: File "+ fileObject.getName() +" already exists. " );
		      }
		    } catch (IOException e) {
		    	logger.error("checkOrCreateFile() :: IOException ",e);
		    }
		
	}

	public static List<Float[]> loadData2(String fileName) {
		String currentLine;
		List<Float[]> attributes = new ArrayList<>();
		List<String> attributeName = new ArrayList<>();
		try {
		logger.info("loadData2) :: reading file " + fileName.replaceAll("\"", "")+".");
	 	FileInputStream fileStream = new FileInputStream(fileName.replaceAll("\"", ""));
	 	DataInputStream inputStream = new DataInputStream(fileStream);
		BufferedReader fileContent = new BufferedReader(new InputStreamReader(inputStream));
		
		currentLine = fileContent.readLine();
		
		String[] token = currentLine.split(getFileDelimter(currentLine));
		 for (int i = 0; i < token.length; i++)
		 {
			 attributeName.add(token[i]);
		 }
		 
		 int colLength =  attributeName.size();
		 
		 currentLine = fileContent.readLine();
		while (null != currentLine)   {
			if( currentLine.trim().isEmpty() ) {
		          break;
		     }
			
			String[] tokens = currentLine.split(getFileDelimter(currentLine));
			boolean flag = tokens[colLength -1].replaceAll("'", "").contentEquals("Normal") || tokens[colLength -1].replaceAll("'", "").contentEquals("Anomaly");
			
			if(flag)
			{	
				if(tokens[colLength -1].replaceAll("'", "").contentEquals("Normal"))
				{
					Float attribute[] = new Float[colLength];
					for (int i = 0; i < tokens.length -1; i++)
					{
						attribute[i] = Float.parseFloat(tokens[i]);
					}
					attribute[tokens.length - 1] = 1.0f;
					attributes.add(attribute);
				}
				else
				{
					Float attribute[] = new Float[colLength];
					for (int i = 0; i < tokens.length -1; i++)
					{
						attribute[i] = Float.parseFloat(tokens[i]);
					}
					attribute[tokens.length - 1] = 2.0f;
					attributes.add(attribute);
				}
			}
			else
			{
				Float attribute[] = new Float[colLength];
				for (int i = 0; i < tokens.length; i++)
				{
					attribute[i] = Float.parseFloat(tokens[i]);
				}
				attributes.add(attribute);
			}
			currentLine = fileContent.readLine();
			
		}
		logger.info("loadData2() :: read file complete.");
		fileContent.close();
		inputStream.close();
		fileStream.close();

		}catch (FileNotFoundException e) {
			logger.error("loadData2() :: File Error: " + e.getMessage());
			System.exit(0);
		}catch (IOException e) {
			logger.error("loadData2() :: I/O Error: " + e.getMessage());
			System.exit(0);
		}
		return attributes;

		
	}
	
	public static List<String> loadAttributeNames(String fileName) {

		String currentLine;
		List<String> attributeName = new ArrayList<>();
		try {
		logger.info("loadData2) :: reading file " + fileName.replaceAll("\"", "")+".");
	 	FileInputStream fileStream = new FileInputStream(fileName.replaceAll("\"", ""));
	 	DataInputStream inputStream = new DataInputStream(fileStream);
		BufferedReader fileContent = new BufferedReader(new InputStreamReader(inputStream));
		
		currentLine = fileContent.readLine();
		
		String[] token = currentLine.split(getFileDelimter(currentLine));
		 for (int i = 0; i < token.length; i++)
		 {
			 attributeName.add(token[i]);
		 }
		fileContent.close();
		inputStream.close();
		fileStream.close();
		}catch (FileNotFoundException e) {
			logger.error("loadData2() :: File Error: " + e.getMessage());
	
		}catch (IOException e) {
			logger.error("loadData2() :: I/O Error: " + e.getMessage());
		}
		return attributeName;
	}
}