package reverseGraph.nodes.operations;

import reverseGraph.data.Dimensions;
import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Expander extends Operation {
	private final Node input;

	public Expander(Node input, Dimensions outDimensions) {
		super(outDimensions);

		Assert.desiredDimensions(input.values.dimensions, Dimensions.SCALAR);

		this.input = input;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.flat.length; i++) {
			values.flat[i] = input.values.flat[0];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { input };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (input instanceof Derivable) {
			double sum = 0;

			for (int i = 0; i < derivatives.flat.length; i++) {
				sum += derivatives.flat[i];
			}

			((Derivable) input).derivatives.flat[0] += sum;
		}
	}
}
