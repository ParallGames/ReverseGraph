package reverseGraph.model;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Layer;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.util.Util;

public class NeuralNetwork {
	private final Node inputs;
	private final Operation[] layers;
	private final Node[] weights;
	private final Node[] biases;

	public NeuralNetwork(Node inputs, Node[] weights, Node[] biases, Activation[] activations) {
		if (weights.length != biases.length) {
			throw new DifferentSizeException(weights.length, biases.length);
		}

		this.inputs = inputs;
		layers = new Operation[weights.length];
		this.weights = weights;
		this.biases = biases;

		layers[0] = activations[0].apply(new Layer(inputs, weights[0], biases[0]));

		for (int i = 1; i < weights.length; i++) {
			layers[i] = activations[i].apply(new Layer(layers[i - 1], weights[i], biases[i]));
		}
	}

	public NeuralNetwork(Node inputs, LayerModel[] layers) {
		this.inputs = inputs;
		this.layers = new Operation[layers.length];
		weights = new Param[layers.length];
		biases = new Param[layers.length];

		weights[0] = Util.createXavierWeights(inputs.values.length, layers[0].outputSize);
		biases[0] = new Param(layers[0].outputSize);
		this.layers[0] = layers[0].activation.apply(new Layer(inputs, weights[0], biases[0]));

		for (int i = 1; i < layers.length; i++) {
			weights[i] = Util.createXavierWeights(layers[i - 1].outputSize, layers[i].outputSize);
			biases[i] = new Param(layers[i].outputSize);
			this.layers[i] = layers[i].activation.apply(new Layer(this.layers[i - 1], weights[i], biases[i]));
		}
	}

	public Node getInputs() {
		return inputs;
	}

	public Operation getLayer(int layer) {
		return layers[layer];
	}

	public Node getWeights(int layer) {
		return weights[layer];
	}

	public Node getBiases(int layer) {
		return biases[layer];
	}

	public Operation getOutput() {
		return layers[layers.length - 1];
	}
}
