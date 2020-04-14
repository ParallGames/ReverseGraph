package reverseGraph.nodes;

import reverseGraph.data.Dimensions;
import reverseGraph.data.Tensor;

public final class Input extends Node {
	public Input(Dimensions dimensions) {
		super(dimensions);
	}

	public Input(Tensor initialValues) {
		super(initialValues);
	}

	public void setValues(Tensor values) {
		this.values.set(values);
	}
}
