package reverseGraph.node;

import reverseGraph.data.Dimensions;
import reverseGraph.data.Tensor;

public abstract class Operation extends Node {
	private final Node[] dependencies;
	
	public Operation(Dimensions dimensions, Node... dependencies) {
		super(dimensions);
		this.dependencies = dependencies;
	}

	public final Node[] getDependencies() {
		return dependencies;
	}

	public abstract void compute(Tensor output, Tensor[] inputs);

	public abstract void computeDerivatives(Tensor output, Tensor[] inputs, Tensor outputGradient, Tensor[] inputsGradient);
}
