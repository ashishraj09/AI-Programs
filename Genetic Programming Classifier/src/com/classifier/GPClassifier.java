package com.classifier;

import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.jgap.gp.CommandGene;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Variable;

import com.data.splitter.DataOperator;

public class GPClassifier {

	static final Logger logger = Logger.getLogger(GPClassifier.class);

	static List<String> variableNames;
	static Variable[] vxs;
	static float[][] train_x;
	static float[][] test_x;
	static float[] train_y;
	static float[] test_y;

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		try {

			System.out.println("Genetic Programming for Classification");
			System.out.println("Do you wish to enter on entire dataset file and split it into test and train ? - Y/N");
			System.out.println("Enter 'No' To use an already split test and train file - No");
			String option = sc.nextLine();
			List<Float[]> testData = null;
			List<Float[]> trainData = null;
			if (option.equalsIgnoreCase("Y") || option.equalsIgnoreCase("YES")) {
				System.out.println("Enter the complete dataset file name: ");
				String dataSetFile = sc.nextLine().replaceAll("\"", "");
				System.out.println("Enter the test split ratio : (0.0 - 0.9) ");
				String ratioString = sc.nextLine();
				double ratio = Double.parseDouble(ratioString);
				if (ratio > 1) {
					System.out.println("Invalid input : " + ratio + " Expected value between (0.0 - 1).");
				} else {
					DataOperator.loadData(dataSetFile,ratio);
					testData = DataOperator.loadData2("split_test_data");
					trainData = DataOperator.loadData2("split_train_data");
					variableNames = DataOperator.loadAttributeNames(dataSetFile);
				}

			} else {
				System.out.println("Enter the train dataset file name: ");
				String trainSet = sc.nextLine().replaceAll("\"", "");
				System.out.println("Enter the test dataset file name: ");
				String testSet = sc.nextLine().replaceAll("\"", "");
				testData = DataOperator.loadData2(testSet);
				trainData = DataOperator.loadData2(trainSet);
				variableNames = DataOperator.loadAttributeNames(trainSet);
			}

			setData(trainData, testData);

			if (logger.isDebugEnabled()) {
				debugDataPrint();
			}

			startClassifier();
		} catch (Exception e) {
			logger.error("main() Error occured ", e);
		} finally {
			sc.close();
		}

	}

	private static void startClassifier() {
		try {
			logger.info("startClassifier() :: Classifier started.");
			GPConfiguration config = new GPConfiguration();
			config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
			config.setMaxInitDepth(4);
			config.setPopulationSize(1000);
			config.setMaxCrossoverDepth(4);
			for (int i = 0; i < vxs.length; i++) {
				vxs[i] = Variable.create(config, variableNames.get(i), CommandGene.FloatClass);
			}
			config.setFitnessFunction(new FitnessFunction(vxs, train_x, train_y));
			config.setStrictProgramCreation(true);
			config.setCrossoverProb(75.0f);
			config.setMutationProb(25.0f);
			config.setReproductionProb(25.0f);
			GenoType type = new GenoType(config, vxs);
			GPGenotype gp = type.create();
			gp.setVerboseOutput(true);
			gp.evolve(800);
			gp.outputSolution(gp.getAllTimeBest());

			IGPProgram program = gp.getAllTimeBest();

			FitnessFunction trainFitness = new FitnessFunction(vxs, train_x, train_y);
			double trainError = trainFitness.evaluate(program);
			System.out.println("The Train Error :" + trainError);
			System.out.println("Train Accuracy : " + (100 - trainError) + "%");
			FitnessFunction testFitness = new FitnessFunction(vxs, test_x, test_y);
			double testError = testFitness.evaluate(program);
			System.out.println("The Test Error :" + testError + "%");
			System.out.println("Test Accuracy : " + (100 - testError) + "%");

		} catch (Exception e) {
			logger.error("startRegression() :: Exception: ", e);
		}

	}

	private static void setData(List<Float[]> trainData, List<Float[]> testData) {
		try {
			logger.info("setData() :: test and train data values set. ");
			logger.info("setData() :: variableNames.size() " + variableNames.size());
			vxs = new Variable[variableNames.size() - 1];
			train_x = new float[trainData.size()][variableNames.size() - 1];
			train_y = new float[trainData.size()];

			test_x = new float[testData.size()][variableNames.size() - 1];
			test_y = new float[testData.size()];

			for (int i = 0; i < trainData.size(); i++) {
				Float[] att = trainData.get(i);
				for (int j = 0; j < att.length - 1; j++) {
					train_x[i][j] = att[j];
				}
				train_y[i] = att[att.length - 1];
			}

			for (int i = 0; i < testData.size(); i++) {
				Float[] att = testData.get(i);
				for (int j = 0; j < att.length - 1; j++) {
					test_x[i][j] = att[j];
				}
				test_y[i] = att[att.length - 1];
			}

		} catch (Exception e) {
			logger.error("setData() :: Exception: " + e.getMessage());
		}
	}

	private static void debugDataPrint() {
		int count_1 = 0;
		int count_2 = 0;

		System.out.println("Train DataSet:");
		for (int i = 0; i < train_x.length; i++) {
			for (int j = 0; j < train_x[0].length; j++) {
				System.out.print((int) train_x[i][j] + " ");
			}
			System.out.println((int) train_y[i]);
			if (train_y[i] == 1.0) {
				count_1++;
			} else {
				count_2++;
			}
		}
		System.out.println("Train DataSet :: Total Records " + train_y.length + " Normal count : " + count_1
				+ " Anomly Count: " + count_2);
		count_1 = 0;
		count_2 = 0;
		System.out.println("Test Dataset:");
		for (int i = 0; i < test_x.length; i++) {
			for (int j = 0; j < test_x[0].length; j++) {
				System.out.print((int) test_x[i][j] + " ");
			}
			System.out.println((int) test_y[i]);
			if (test_y[i] == 1.0) {
				count_1++;
			} else {
				count_2++;
			}
		}
		System.out.println("Test Dataset:: Total Records " + test_y.length + " Normal count : " + count_1
				+ " Anomly Count: " + count_2);
	}

}
