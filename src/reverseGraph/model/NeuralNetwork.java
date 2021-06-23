package reverseGraph.model;

import reverseGraph.data.Dimensions;
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

	public NeuralNetwork(Node inputs, LayerModel[] layerModels) {
		this.inputs = inputs;
		this.layers = new Operation[layerModels.length];
		weights = new Param[layers.length];
		biases = new Param[layers.length];

		weights[0] = Util.createNormalizedTanhWeights(inputs.values.flat.length, layerModels[0].outputSize);
		biases[0] = new Param(new Dimensions(layerModels[0].outputSize));
		this.layers[0] = layerModels[0].activation.apply(new Layer(inputs, weights[0], biases[0]));

		for (int i = 1; i < layers.length; i++) {
			weights[i] = Util.createNormalizedTanhWeights(layerModels[i - 1].outputSize, layerModels[i].outputSize);
			biases[i] = new Param(new Dimensions(layerModels[i].outputSize));
			this.layers[i] = layerModels[i].activation.apply(new Layer(this.layers[i - 1], weights[i], biases[i]));
		}
	}

	public Node getInput() {
		return inputs;
	}

	public Operation getLayer(int layer) {
		return layers[layer];
	}

	public Node getWeight(int layer) {
		return weights[layer];
	}

	public Node getBias(int layer) {
		return biases[layer];
	}

	public Operation getOutput() {
		return layers[layers.length - 1];
	}
}
