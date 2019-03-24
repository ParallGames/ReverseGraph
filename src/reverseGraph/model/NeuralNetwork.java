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
	private final Param[] weights;
	private final Param[] biases;

	public NeuralNetwork(Node inputs, Param[] weights, Param[] biases, Activation[] activations) {
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

		weights[0] = new Param(Util.createXavierWeights(inputs.getSize(), layers[0].outputSize));
		biases[0] = new Param(layers[0].outputSize);
		this.layers[0] = layers[0].activation.apply(new Layer(inputs, weights[0], biases[0]));

		for (int i = 1; i < layers.length; i++) {
			weights[i] = new Param(Util.createXavierWeights(layers[i - 1].outputSize, layers[i].outputSize));
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

	public Param getWeights(int layer) {
		return weights[layer];
	}

	public Param getBiases(int layer) {
		return biases[layer];
	}

	public Operation getOutput() {
		return layers[layers.length - 1];
	}
}
