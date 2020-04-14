package reverseGraph.nodes;

import reverseGraph.data.Dimensions;
import reverseGraph.data.Tensor;

public final class Param extends Derivable {
	public Param(Dimensions dimensions) {
		super(dimensions);
	}

	public Param(Tensor initialValues) {
		super(initialValues);
	}
}
