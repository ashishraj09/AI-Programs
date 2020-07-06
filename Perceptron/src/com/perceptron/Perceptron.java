package com.perceptron;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

public class Perceptron {

	static final Logger logger = Logger.getLogger(Perceptron.class);
	double[] weights;
	double threshold;
	double bias;

	public void train(double[][] inputs, int[] outputs, double threshold, double lrate, int epoch) {

		this.threshold = threshold;

		weights = new double[inputs[0].length];
		for (int i = 0; i < inputs[0].length; i++) {
			weights[i] = generateRandom(0, 1);
		}
		for (int i = 0; i <= epoch; i++) {

			double totalError = 0;
			for (int j = 0; j < outputs.length; j++) {
				int output = calculateOutput(inputs[j]);
				int error = outputs[j] - output;

				totalError += error * error;

				for (int k = 0; k < inputs[0].length; k++) {
					double delta = lrate * inputs[j][k] * error;
					weights[k] += delta;
					bias += error * lrate;
				}
			}
			logger.info("train() :: epoch : " + i + " RMSE: " + Math.sqrt(totalError / outputs.length));

		}
	}

	public int calculateOutput(double[] input) {
		double sum = 0.0;
		for (int i = 0; i < input.length; i++) {
			sum += weights[i] * input[i] + bias;
		}
		if (sum > threshold)
			return 1;
		else
			return 0;
	}

	public static double generateRandom(double min, double max) {
		return Double.parseDouble(new DecimalFormat("#.####").format(min + Math.random() * (max - min)));
	}

}