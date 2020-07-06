package data.operator;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import data.bean.Feature;

public class Dataloader {

	Dataloader() {

	}

	public static List<Feature> loadTrainData(String fileName) {
		FileInputStream fileStream = null;
		DataInputStream inputStream = null;
		BufferedReader fileContent = null;
		try {
			List<Feature> FeatureList = new ArrayList<>();
			
			fileStream = new FileInputStream(fileName.replaceAll("\"", ""));
			inputStream = new DataInputStream(fileStream);
			fileContent = new BufferedReader(new InputStreamReader(inputStream));

			String currentLine = fileContent.readLine();
			while (null != currentLine) {
				if (currentLine.trim().isEmpty()) {
					break;
				}

				String[] tokens = currentLine.split(getFileDelimter(currentLine));
				List<Integer> fList = new ArrayList<Integer>();
				for (int i = 0; i < tokens.length - 1; i++) {
					if(isNumber(tokens[i]))
						fList.add(Integer.parseInt(tokens[i]));

				}
				int[] intArray = new int[fList.size()];
			    for (int i=0; i < intArray.length; i++)
			    {
			    	intArray[i] = fList.get(i).intValue();
			    }
				FeatureList.add(new Feature(intArray, Integer.parseInt(tokens[tokens.length - 1])));

				currentLine = fileContent.readLine();
			}
			fileContent.close();
			inputStream.close();
			fileStream.close();

			return FeatureList;

		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
			System.exit(0);
		}
		return null;
	}
	
	public static List<Feature> loadTestData(String fileName) {
		FileInputStream fileStream = null;
		DataInputStream inputStream = null;
		BufferedReader fileContent = null;
		try {
			List<Feature> FeatureList = new ArrayList<>();
			fileStream = new FileInputStream(fileName.replaceAll("\"", ""));
			inputStream = new DataInputStream(fileStream);
			fileContent = new BufferedReader(new InputStreamReader(inputStream));

			String currentLine = fileContent.readLine();
			while (null != currentLine) {
				if (currentLine.trim().isEmpty()) {
					break;
				}

				String[] tokens = currentLine.split(getFileDelimter(currentLine));
				List<Integer> fList = new ArrayList<Integer>();
				for (int i = 0; i < tokens.length; i++) {
					if(isNumber(tokens[i]))
						fList.add(Integer.parseInt(tokens[i]));

				}
				int[] intArray = new int[fList.size()];
			    for (int i=0; i < intArray.length; i++)
			    {
			    	intArray[i] = fList.get(i).intValue();
			    }
				FeatureList.add(new Feature(intArray, Integer.parseInt(tokens[tokens.length - 1])));

				currentLine = fileContent.readLine();
			}
			fileContent.close();
			inputStream.close();
			fileStream.close();

			return FeatureList;

		} catch (IOException e) {
			System.out.println("IOException " + e.getMessage());
			System.exit(0);
		}
		return null;
	}

	private static String getFileDelimter(String line) {
		String cvsSplitBy = " ";
		if (line.contains(",")) {
			cvsSplitBy = ",";
		} else if (line.contains(";")) {
			cvsSplitBy = ";";
		} else if (line.contains("     ")) {
			cvsSplitBy = "     ";
		} else if (line.contains("  ")) {
			cvsSplitBy = "  ";
		} else if (line.contains(" ")) {
			cvsSplitBy = " ";
		} else {
			System.out.println("getFileDelimter() :: Wrong separator!!! Please check the file. ");
		}
		return cvsSplitBy;
	}

	private static boolean isNumber(String s) {
		try {

			Integer.parseInt(s);
			return true;

		} catch (Exception e) {
			return false;
		}

	}
}
