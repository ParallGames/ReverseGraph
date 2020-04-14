package reverseGraph.model;

import reverseGraph.data.Dimensions;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Layer;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.util.Util;

public class LayerBuilder {
	final Param weights;
	final Param biases;

	final Operation[] outputs;

	public LayerBuilder(Node[] inputs, LayerModel model) {
		this.weights = Util.createXavierWeights(inputs.length, model.outputSize);
		this.biases = new Param(new Dimensions(model.outputSize));

		outputs = new Operation[inputs.length];

		for (int i = 0; i < inputs.length; i++) {
			outputs[i] = model.activation.apply(new Layer(inputs[i], weights, biases));
		}
	}

	public LayerBuilder(Node[] inputs, Param weights, Param biases, Activation act) {
		this.weights = weights;
		this.biases = biases;

		outputs = new Operation[inputs.length];

		for (int i = 0; i < inputs.length; i++) {
			outputs[i] = act.apply(new Layer(inputs[i], weights, biases));
		}
	}

	public Operation[] getOutputs() {
		return outputs;
	}

	public Param getWeights() {
		return weights;
	}

	public Param getBiases() {
		return biases;
	}
}
