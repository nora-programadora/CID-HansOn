/*
Logistic Regression
Nora Rebeca Aguirre Sánchez
213400059
*/

package examples.LogisticRegression;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import jade.core.Agent;
import java.util.ArrayList;
import java.util.List;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class LRAgent extends Agent {

	protected void setup() {
	    System.out.println("Agent "+getLocalName()+" started.");
	} 

	private class LRA extends OneShotBehaviour{

		/** the learning rate */
		private double rate;

		/** the weight to learn */
		private double[] weights;

		/** the number of iterations */
		private int ITERATIONS = 100;

		public LRA(int n) {
			this.rate = 0.0001;
			weights = new double[n];
		}

		private static double sigmoid(double z) {
			return 1.0 / (1.0 + Math.exp(-z));
		}

		public void train(List<Instance> instances) {
			for (int n=0; n<ITERATIONS; n++) {
				double lik = 0.0;
				for (int i=0; i<instances.size(); i++) {
					int[] x = instances.get(i).x;
					double predicted = classify(x);
					int label = instances.get(i).label;
					for (int j=0; j<weights.length; j++) {
						weights[j] = weights[j] + rate * (label - predicted) * x[j];
					}
					// not necessary for learning
					lik += label * Math.log(classify(x)) + (1-label) * Math.log(1- classify(x));
				}
				System.out.println("iteration: " + n + " " + Arrays.toString(weights) + " mle: " + lik);
			}
		}

		private double classify(int[] x) {
			double logit = .0;
			for (int i=0; i<weights.length;i++)  {
				logit += weights[i] * x[i];
			}
			return sigmoid(logit);
		}

		public static class Instance {
			public int label;
			public int[] x;

			public Instance(int label, int[] x) {
				this.label = label;
				this.x = x;
			}
		}

		public static List<Instance> readDataSet(String file) {
			List<Instance> dataset = new ArrayList<Instance>();
			Scanner scanner = null;
			try {
				scanner = new Scanner(new File(file));
				while(scanner.hasNextLine()) {
					String line = scanner.nextLine();
					if (line.startsWith("#")) {
						continue;
					}
					String[] columns = line.split("\\s+");

					// skip first column and last column is the label
					int i = 1;
					int[] data = new int[columns.length-2];
					for (i=1; i<columns.length-1; i++) {
						data[i-1] = Integer.parseInt(columns[i]);
					}
					int label = Integer.parseInt(columns[i]);
					Instance instance = new Instance(label, data);
					dataset.add(instance);
				}
			} finally {
				if (scanner != null)
					scanner.close();
			}
			return dataset;
		}


		public void action() {
			List<Instance> instances = readDataSet("dataset.txt");
			LRA logistic = new LRA(5);
			logistic.train(instances);
			int[] x = {2, 1, 1, 0, 1};
			System.out.println("prob(1|x) = " + logistic.classify(x));

			int[] x2 = {1, 0, 1, 0, 0};
			System.out.println("prob(1|x2) = " + logistic.classify(x2));

		}
	}

}