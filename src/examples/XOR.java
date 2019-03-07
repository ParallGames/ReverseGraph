package examples;

import reverseGraph.Graph;
import reverseGraph.nodes.Input;
import reverseGraph.nodes.operations.Multiplication;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.nodes.operations.Substraction;
import reverseGraph.optimizers.Adam;
import vnl.Activation;
import vnl.Network;
import vnl.model.LayerModel;

/*
 * Neural network learning XOR logical gate
 */
public class XOR {
	public static void main(String[] args) {
		// Net inputs
		Input inputs = new Input(2);

		// Create net model
		LayerModel[] layers = new LayerModel[2];
		layers[0] = new LayerModel(10, Activation.LEAKYRELU);
		layers[1] = new LayerModel(1, Activation.TANH);

		// Create net
		Operation output = Network.createNetwork(inputs, layers);

		// Net label (the desired output)
		Input label = new Input(1);

		// Add operation to compute the difference between the current and the desired
		// output
		Substraction diff = new Substraction(output, label);
		Multiplication loss = new Multiplication(diff, diff);

		// Create a graph to compute all operations
		Graph graph = new Graph(loss, new Adam(0.01, 0.9, 0.999));

		System.out.println(graph.paramsCount());
		System.out.println(graph.operationsCount());

		double errorSum = 1;

		while (errorSum > 0.1) {
			errorSum = 0;

			// Set input for 0 XOR 0 = 0
			inputs.setValues(new double[] { -1, -1 });
			label.setValues(new double[] { -1 });

			// Compute net output
			graph.compute();
			errorSum += loss.getValues()[0];

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Set input for 1 XOR 0 = 1
			inputs.setValues(new double[] { 1, -1 });
			label.setValues(new double[] { 1 });

			// Compute net output
			graph.compute();
			errorSum += loss.getValues()[0];

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Set input for 0 XOR 1 = 1
			inputs.setValues(new double[] { -1, 1 });
			label.setValues(new double[] { 1 });

			// Compute net output
			graph.compute();
			errorSum += loss.getValues()[0];

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Set input for 1 XOR 1 = 0
			inputs.setValues(new double[] { 1, 1 });
			label.setValues(new double[] { -1 });

			// Compute net output
			graph.compute();
			errorSum += loss.getValues()[0];

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Print the error of the net
			System.out.println(errorSum);
		}
	}
}
