package classifier;

import java.util.ArrayList;
import java.util.List;

import data.bean.Feature;

public class Classifier {

	private double trueProbabilty;
	private double falseProbabilty;
	private static List<Double> trueProbilities = new ArrayList<Double>();
	private static List<Double> falseProbilities = new ArrayList<Double>();
	private StringBuilder output = new  StringBuilder("Naive Bayes Classifier\n"); 
	private static String lineBreak = "--------------------------------------------------------------------------------------------------------\n";
	
	public StringBuilder getOutput() {
		return output;
	}
	
	public void appendtoOutput(String s) {
		this.output.append(s);
	}

	public void getNaiveBayesClassifier(List<Feature> trainFeature) {

		List<Feature> trueFeatures = new ArrayList<Feature>();
		List<Feature> falseFeatures = new ArrayList<Feature>();

		for (Feature feature : trainFeature) {
			if (feature.getClassification() == 1) {
				trueFeatures.add(feature);
			} else {
				falseFeatures.add(feature);
			}
		}

		trueProbabilty = (double) trueFeatures.size() / trainFeature.size();
		falseProbabilty = (double) falseFeatures.size() / trainFeature.size();
		trueProbilities = getfeatureProbability(trueFeatures);
		falseProbilities = getfeatureProbability(falseFeatures);
		
		output.append(lineBreak);
		output.append("Training P(Fi|c)\n"); 
		output.append(lineBreak);
		for (int i = 0; i < falseProbilities.size(); i++) {
			output.append("P(c = non-spam | Feature" + (i + 1) + " = 0) = " + (1 - falseProbilities.get(i)
					+ ", P (c = Spam | Feature" + (i + 1) + " = 0) = " + (1 - trueProbilities.get(i)))+ "\n");
			output.append("P(c = non-spam | Feature" + (i + 1) + " = 1) = " + falseProbilities.get(i)
					+ ", P (c = spam | Feature" + (i + 1) + " = 1) = " + trueProbilities.get(i)+"\n");
		}
		output.append(lineBreak);
		
		output.append("\n");
		output.append(lineBreak);
		output.append("Test Classification Output:\n");
		output.append(lineBreak);
	}

	private List<Double> getfeatureProbability(List<Feature> features) {

		int[] count = new int[features.get(0).getInputs().length];
		List<Double> probabilityList = new ArrayList<Double>();

		for (Feature feature : features) {
			for (int i = 0; i < feature.getInputs().length; i++) {
				if(feature.getInputs()[i] == 1)
					count[i] ++;
			}
		}

		for (int i = 0; i < count.length; i++) {

			probabilityList.add((double) count[i] / ((double) features.size()));
		}

		return probabilityList;
	}

	public void getPredictions(Feature testFeature) {
		double spamProbabilty = this.trueProbabilty;
		double nonSpamProbabilty = this.falseProbabilty;
		for (int i = 0; i < testFeature.getInputs().length; i++) {
			
			output.append(testFeature.getInputs()[i]+ " ");
			
			if (1 == testFeature.getInputs()[i]) {
				spamProbabilty *= trueProbilities.get(i);
				nonSpamProbabilty *= falseProbilities.get(i);
				} else {
				spamProbabilty *= (1 - trueProbilities.get(i));
				nonSpamProbabilty *=  (1 - falseProbilities.get(i));
			}
		}
		
		if (spamProbabilty > nonSpamProbabilty) {
			output.append("\nClass: spam");
		} else {
			output.append("\nClass: non-spam");
		}
		output.append("\n");
		
		output.append("P(spam) = " + String.format("%.16f", spamProbabilty));
		output.append(" P(non-spam) = " + String.format("%.16f",nonSpamProbabilty)+"\n\n");
	
	}

}
