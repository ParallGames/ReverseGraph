package examples;

import reverseGraph.OptimizationGraph;
import reverseGraph.data.Dimensions;
import reverseGraph.data.Tensor;
import reverseGraph.model.Activation;
import reverseGraph.model.LayerModel;
import reverseGraph.model.NeuralNetwork;
import reverseGraph.nodes.Input;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.optimizers.Adam;
import reverseGraph.util.Util;

/*
 * Neural network learning XOR logical gate
 */
public class XOR {
	public static void main(String[] args) {
		// Net inputs
		Input inputs = new Input(new Dimensions(2));

		// Create net model
		LayerModel[] layers = new LayerModel[2];
		layers[0] = new LayerModel(10, Activation.LEAKYRELU);
		layers[1] = new LayerModel(1, Activation.TANH);

		NeuralNetwork net = new NeuralNetwork(inputs, layers);
		// Create net
		Operation output = net.getOutput();

		// Net label (the desired output)
		Input label = new Input(new Dimensions(1));

		// Add operation to compute how far the output is from the label
		Operation error = Util.createMeanSquareError(output, label);

		// Create a graph to compute all operations
		OptimizationGraph graph = new OptimizationGraph(error, new Adam(0.01, 0.9, 0.999));

		double errorSum = 1;

		while (errorSum > 0.1) {
			errorSum = 0;

			// Set input for 0 XOR 0 = 0
			inputs.setValues(Tensor.createVector(new double[] { -1, -1 }));
			label.setValues(Tensor.createVector(new double[] { -1 }));

			// Compute net output
			graph.compute();
			errorSum += error.values.get();

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Set input for 1 XOR 0 = 1
			inputs.setValues(Tensor.createVector(new double[] { 1, -1 }));
			label.setValues(Tensor.createVector(new double[] { 1 }));

			// Compute net output
			graph.compute();
			errorSum += error.values.get();

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Set input for 0 XOR 1 = 1
			inputs.setValues(Tensor.createVector(new double[] { -1, 1 }));
			label.setValues(Tensor.createVector(new double[] { 1 }));

			// Compute net output
			graph.compute();
			errorSum += error.values.get();

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Set input for 1 XOR 1 = 0
			inputs.setValues(Tensor.createVector(new double[] { 1, 1 }));
			label.setValues(Tensor.createVector(new double[] { -1 }));

			// Compute net output
			graph.compute();
			errorSum += error.values.get();

			// Learn the current input
			graph.computeDerivatives();
			graph.minimize();

			// Print the error of the net
			System.out.println(errorSum);
		}
	}
}
