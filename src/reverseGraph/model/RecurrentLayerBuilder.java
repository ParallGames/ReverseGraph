package reverseGraph.model;

import reverseGraph.data.Dimensions;
import reverseGraph.nodes.Node;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Layer;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.nodes.operations.RecurrentLayer;
import reverseGraph.util.Util;

public class RecurrentLayerBuilder {
	final Param weights;
	final Param biases;
	final Param recurrencesWeights;

	final Operation[] outputs;

	public RecurrentLayerBuilder(Node[] inputs, LayerModel model) {
		this.weights = Util.createNormalizedTanhWeights(inputs.length, model.outputSize);
		this.biases = new Param(new Dimensions(model.outputSize));
		this.recurrencesWeights = new Param(new Dimensions(model.outputSize));

		outputs = new Operation[inputs.length];

		outputs[0] = model.activation.apply(new Layer(inputs[0], weights, biases));

		for (int i = 1; i < inputs.length; i++) {
			outputs[i] = model.activation
					.apply(new RecurrentLayer(inputs[i], weights, biases, outputs[i - 1], recurrencesWeights));
		}
	}

	public RecurrentLayerBuilder(Node[] inputs, Param weights, Param biases, Param recurrencesWeights, Activation act) {
		this.weights = weights;
		this.biases = biases;
		this.recurrencesWeights = recurrencesWeights;

		outputs = new Operation[inputs.length];

		outputs[0] = act.apply(new Layer(inputs[0], weights, biases));

		for (int i = 1; i < inputs.length; i++) {
			outputs[i] = act.apply(new RecurrentLayer(inputs[i], weights, biases, outputs[i - 1], recurrencesWeights));
		}
	}

	public RecurrentLayerBuilder(Node[] inputs, Param weights, Param biases, Node previousRecurrences,
			Param recurrencesWeights, Activation act) {
		this.weights = weights;
		this.biases = biases;
		this.recurrencesWeights = recurrencesWeights;

		outputs = new Operation[inputs.length];

		outputs[0] = act.apply(new RecurrentLayer(inputs[0], weights, biases, previousRecurrences, recurrencesWeights));

		for (int i = 1; i < inputs.length; i++) {
			outputs[i] = act.apply(new RecurrentLayer(inputs[i], weights, biases, outputs[i - 1], recurrencesWeights));
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

	public Param getRecurrencesWeights() {
		return recurrencesWeights;
	}

	public Operation getLastValues() {
		return outputs[outputs.length - 1];
	}
}
