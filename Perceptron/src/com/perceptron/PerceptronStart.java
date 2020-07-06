package com.perceptron;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class PerceptronStart {

	static final Logger logger = Logger.getLogger(PerceptronStart.class);
	static List<String> attributeName = new ArrayList<>();
	public static double[][] train_x;
	public static int[] train_y;

	public static double[][] test_x;
	public static int[] test_y;

	public static void main(String[] args) {
		Perceptron perceptron = new Perceptron();
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the training set file :");
		String trainFile = sc.nextLine();
		System.out.println("Please enter the test set file :");
		String testFile = sc.nextLine();
		sc.close();
		loadTrain(trainFile);
		loadTest(testFile);
		int[] testOutput = new int[test_y.length];
		perceptron.train(train_x, train_y, 0, 0.1, 200);
		for (int i = 0; i < test_x.length; i++) {
			testOutput[i] = perceptron.calculateOutput(test_x[i]);
		}
		int count = 0;
		System.out.println("----------------------------------------------------------------------------");
		for (String name : attributeName)
			System.out.print(name + "\t");
		System.out.println(" Predicted Output");
		System.out.println("----------------------------------------------------------------------------");
		for (int i = 0; i < testOutput.length; i++) {
			for (int j = 0; j < test_x[0].length; j++) {
				System.out.print((int) test_x[i][j] + "\t\t  ");
			}
			System.out.println(" " + (int) test_y[i] + "\t\t  " + testOutput[i]);
			if (testOutput[i] == test_y[i]) {
				count++;
			}
		}
		System.out.println("----------------------------------------------------------------------------");
		logger.info("main() Accuracy : " + (((double) count / testOutput.length) * 100) + "%");

	}

	private static void loadTest(String inputFileName) {
		List<double[]> attributeInputs = new ArrayList<>();
		List<Integer> attributeOutputs = new ArrayList<>();

		try {

			System.out.print("loadData() :: reading file " + inputFileName);
			FileInputStream fileStream = new FileInputStream(inputFileName.replaceAll("\"", ""));
			DataInputStream inputStream = new DataInputStream(fileStream);
			BufferedReader fileContent = new BufferedReader(new InputStreamReader(inputStream));

			String labels = fileContent.readLine();

			String[] token = labels.split(getFileDelimter(labels));
			for (int i = 0; i < token.length; i++) {
				// attributeName.add(token[i]);
			}

			String currentLine = fileContent.readLine();
			while (null != currentLine) {
				if (currentLine.trim().isEmpty()) {
					break;
				}

				String[] tokens = currentLine.split(getFileDelimter(currentLine));
				double[] doubleTokens = new double[tokens.length - 1];
				for (int i = 0; i < tokens.length - 1; i++) {
					doubleTokens[i] = Double.parseDouble(tokens[i]);
				}
				attributeInputs.add(doubleTokens);
				attributeOutputs.add(Integer.parseInt(tokens[tokens.length - 1]));
				currentLine = fileContent.readLine();
			}

			fileStream.close();
			test_x = new double[attributeInputs.size()][attributeName.size() - 1];
			test_y = new int[attributeOutputs.size()];

			for (int i = 0; i < attributeInputs.size(); i++) {
				double[] att = attributeInputs.get(i);
				for (int j = 0; j < att.length; j++) {
					test_x[i][j] = att[j];
				}
				test_y[i] = attributeOutputs.get(i);
			}

			logger.info("loadData() :: File loaded!!");
		} catch (IOException e) {
			logger.error("loadData()" + e);
			System.exit(0);
		}

	}

	private static void loadTrain(String inputFileName) {
		List<double[]> attributeInputs = new ArrayList<>();
		List<Integer> attributeOutputs = new ArrayList<>();

		try {

			logger.info("loadData() :: reading file " + inputFileName);
			FileInputStream fileStream = new FileInputStream(inputFileName.replaceAll("\"", ""));
			DataInputStream inputStream = new DataInputStream(fileStream);
			BufferedReader fileContent = new BufferedReader(new InputStreamReader(inputStream));

			String labels = fileContent.readLine();

			String[] token = labels.split(getFileDelimter(labels));
			for (int i = 0; i < token.length; i++) {
				attributeName.add(token[i]);
			}

			String currentLine = fileContent.readLine();
			while (null != currentLine) {
				if (currentLine.trim().isEmpty()) {
					break;
				}

				String[] tokens = currentLine.split(getFileDelimter(currentLine));
				double[] doubleTokens = new double[tokens.length - 1];
				for (int i = 0; i < tokens.length - 1; i++) {
					doubleTokens[i] = Double.parseDouble(tokens[i]);
				}
				attributeInputs.add(doubleTokens);
				attributeOutputs.add(Integer.parseInt(tokens[tokens.length - 1]));
				currentLine = fileContent.readLine();
			}

			fileStream.close();
			train_x = new double[attributeInputs.size()][attributeName.size() - 1];
			train_y = new int[attributeOutputs.size()];

			for (int i = 0; i < attributeInputs.size(); i++) {
				double[] att = attributeInputs.get(i);
				for (int j = 0; j < att.length; j++) {
					train_x[i][j] = att[j];
				}
				train_y[i] = attributeOutputs.get(i);
			}

			logger.info("loadData() :: File loaded!!");
		} catch (IOException e) {
			logger.error("loadData()" + e);
			System.exit(0);
		}

	}

	private static String getFileDelimter(String line) {
		String cvsSplitBy = " ";
		if (line.contains(",")) {
			cvsSplitBy = ",";
		} else if (line.contains(";")) {
			cvsSplitBy = ";";
		} else if (line.contains(",")) {
			cvsSplitBy = ",";
		} else if (line.contains("  ")) {
			cvsSplitBy = "  ";
		} else if (line.contains(" ")) {
			cvsSplitBy = " ";
		} else {
			logger.error("getFileDelimter() :: Wrong separator!!! Please check the file. ");
		}
		return cvsSplitBy;
	}

}
