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
		Input input1 = new Input(0);
		Input input2 = new Input(0);

		// Create net model
		LayerModel[] layers = new LayerModel[2];
		layers[0] = new LayerModel(10, Activation.LEAKYRELU);
		layers[1] = new LayerModel(1, Activation.TANH);

		// Create net
		Operation output = Network.createNetwork(new Input[] { input1, input2 }, layers)[0];

		// Net label (the desired output)
		Input label = new Input(0);

		// Add operation to compute the difference between the current and the desired
		// output
		Substraction diff = new Substraction(output, label);
		Multiplication loss = new Multiplication(diff, diff);

		// Create a graph to compute all operations
		Graph graph = new Graph(loss, new Adam(0.01, 0.9, 0.999));

		double errorSum = 1;

		while (errorSum > 0.1) {
			errorSum = 0;

			// Set input for 0 XOR 0 = 0
			input1.setValue(-1);
			input2.setValue(-1);
			label.setValue(-1);

			// Compute net output
			graph.compute();
			errorSum += loss.getValue();

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Set input for 1 XOR 0 = 1
			input1.setValue(1);
			input2.setValue(-1);
			label.setValue(1);

			// Compute net output
			graph.compute();
			errorSum += loss.getValue();

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Set input for 0 XOR 1 = 1
			input1.setValue(-1);
			input2.setValue(1);
			label.setValue(1);

			// Compute net output
			graph.compute();
			errorSum += loss.getValue();

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Set input for 1 XOR 1 = 0
			input1.setValue(1);
			input2.setValue(1);
			label.setValue(0);

			// Compute net output
			graph.compute();
			errorSum += loss.getValue();

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Print the error of the net
			System.out.println(errorSum);
		}
	}
}
