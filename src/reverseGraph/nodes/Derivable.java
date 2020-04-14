package reverseGraph.nodes;

import reverseGraph.data.Dimensions;
import reverseGraph.data.Tensor;

public abstract class Derivable extends Node {
	public final Tensor derivatives;

	public Derivable(Dimensions dimensions) {
		super(dimensions);
		derivatives = new Tensor(dimensions);
	}

	public Derivable(Tensor initialValues) {
		super(initialValues);
		derivatives = new Tensor(initialValues.dimensions);
	}
}
