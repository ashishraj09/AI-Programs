package com.sr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.jgap.gp.CommandGene;
import org.jgap.gp.impl.DeltaGPFitnessEvaluator;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Variable;

public class SymbolicRegression {

	static final Logger logger = Logger.getLogger(SymbolicRegression.class);

	static Float[] x = new Float[20];
	static Float[] y = new Float[20];
	static Variable vx;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the dataset file name: ");
		String inputFileName = sc.nextLine().replaceAll("\"", "");
		logger.info("main() : File name entred : " + inputFileName);
		loadData(inputFileName);
		sc.close();
		startSR();
	}

	private static void startSR() {

		try {
			GPConfiguration config = new GPConfiguration();
			config.setGPFitnessEvaluator(new DeltaGPFitnessEvaluator());
			config.setMaxInitDepth(10);
			config.setMaxCrossoverDepth(10);
			config.setPopulationSize(100);
			vx = Variable.create(config, "X", CommandGene.FloatClass);
			config.setFitnessFunction(new FitnessFunction(vx, x, y));
			config.setCrossoverProb(75.0f);
			config.setMutationProb(25.0f);
			config.setReproductionProb(25.0f);
			GenoType type = new GenoType(config, vx);
			GPGenotype gp = type.create();
			gp.setVerboseOutput(true);
			gp.evolve(800);
			gp.outputSolution(gp.getAllTimeBest());
			System.out.printf("Mean Square Error : %.6f", gp.getAllTimeBest().getFitnessValue() / x.length);
			type.showTree(gp.getAllTimeBest(), "regression.png");
		} catch (Exception e) {
			logger.error("startSR()", e);
		}

	}

	private static void loadData(String inputFileName) {

		try {
			File inputFile = new File(inputFileName.replaceAll("\"", ""));
			Scanner fileInput;
			fileInput = new Scanner(inputFile);
			fileInput.nextLine();
			
			int count = 0;
			while (fileInput.hasNext()) {
				Float xValue = fileInput.nextFloat();
				Float yValue = fileInput.nextFloat();
				x[count] = xValue;
				y[count] = yValue;
				count++;
			}
			fileInput.close();
			System.out.println("-------------" );
			System.out.println("X"+"\t"+"Y" );
			System.out.println("-------------" );
				for (int i = 0; i < count; i++)
					System.out.println(x[i] + "   " + y[i]);
			System.out.println("-------------" );
			logger.info("loadData() :: File loaded!!");
		} catch (FileNotFoundException e) {
			logger.error("loadData()", e);
		}

	}

}
