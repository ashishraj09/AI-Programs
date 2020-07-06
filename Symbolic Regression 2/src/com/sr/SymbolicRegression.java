package com.sr;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Variable;

public class SymbolicRegression {

	static final Logger logger = Logger.getLogger(SymbolicRegression.class);

	static int[][] x;
	static int[] y;
	static Variable[] vx;
	static List<String> attributeName = new ArrayList<>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the file name: ");
		String inputFileName = sc.nextLine().replaceAll("\"", "");
		logger.info("main() : File name entred : " + inputFileName);
		loadData(inputFileName);
		sc.close();
		startSR();
	}

	private static void startSR() {

		logger.error("startSR() Regression started!!");
		try {

			GPConfiguration config = new GPConfiguration();
			config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
			config.setMaxInitDepth(4);
			config.setMaxCrossoverDepth(4);
			config.setPopulationSize(500);
			for (int i = 0; i < vx.length; i++) {
				vx[i] = Variable.create(config, attributeName.get(i), CommandGene.IntegerClass);
			}
			config.setFitnessFunction(new FitnessFunction(vx, x, y));
			config.setStrictProgramCreation(true);
			config.setCrossoverProb(75.0f);
			config.setMutationProb(25.0f);
			config.setReproductionProb(25.f);
			GenoType type = new GenoType(config, vx);
			GPGenotype gp = type.create();
			gp.setVerboseOutput(true);
			gp.evolve(500);
			gp.outputSolution(gp.getAllTimeBest());
			type.showTree(gp.getAllTimeBest(), "regression.png");
			System.out.printf("Mean Square Error : %.6f", gp.getAllTimeBest().getFitnessValue() / x.length);
		} catch (Exception e) {
			logger.error("startSR()", e);
		}

	}

	private static void loadData(String inputFileName) {
		logger.info("loadData() :: reading file : " + inputFileName);
		FileInputStream fileStream = null;
		DataInputStream inputStream = null;
		BufferedReader fileContent = null;
		try {
			// Temporary store to read any file dynamically
			List<int[]> xtrain = new ArrayList<>();
			List<Integer> ytrain = new ArrayList<>();

			fileStream = new FileInputStream(inputFileName.replaceAll("\"", ""));
			inputStream = new DataInputStream(fileStream);
			fileContent = new BufferedReader(new InputStreamReader(inputStream));

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
				int[] att = new int[attributeName.size() - 1];
				for (int i = 0; i < attributeName.size() - 1; i++) {

					att[i] = Integer.parseInt(tokens[i]);

				}
				xtrain.add(att);
				ytrain.add(Integer.parseInt(tokens[tokens.length - 1]));
				currentLine = fileContent.readLine();
			}
			// Dynamically declare variable size
			int columnSize = xtrain.get(0).length;
			x = new int[xtrain.size()][columnSize];
			y = new int[ytrain.size()];
			vx = new Variable[columnSize];

			for (int i = 0; i < xtrain.size(); i++) {
				for (int j = 0; j < columnSize; j++) {
					int[] att = xtrain.get(i);
					x[i][j] = att[j];
				}
				y[i] = ytrain.get(i);
			}
			fileContent.close();
			inputStream.close();
			fileStream.close();
			logger.info("loadData() :: File read!!");
		} catch (IOException e) {
			logger.error("loadData() :: IOException " + e.getMessage());
			System.exit(0);
		}

	}

	private static String getFileDelimter(String line) {
		String cvsSplitBy = " ";
		if (line.contains(",")) {
			cvsSplitBy = ",";
		} else if (line.contains(";")) {
			cvsSplitBy = ";";
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
