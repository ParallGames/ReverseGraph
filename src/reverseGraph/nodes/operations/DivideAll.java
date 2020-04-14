package reverseGraph.nodes.operations;

import reverseGraph.data.Dimensions;
import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class DivideAll extends Operation {
	private final Node dividend;
	private final Node divisor;

	public DivideAll(Node dividend, Node divisor) {
		super(dividend.values.dimensions);

		Assert.desiredDimensions(divisor.values.dimensions, Dimensions.SCALAR);

		this.dividend = dividend;
		this.divisor = divisor;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.flat.length; i++) {
			values.flat[i] = dividend.values.flat[i] / divisor.values.flat[0];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { dividend, divisor };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (dividend instanceof Derivable) {
			for (int i = 0; i < derivatives.flat.length; i++) {
				((Derivable) dividend).derivatives.flat[i] += derivatives.flat[i] / divisor.values.flat[0];
			}
		}

		if (divisor instanceof Derivable) {
			for (int i = 0; i < derivatives.flat.length; i++) {
				((Derivable) divisor).derivatives.flat[0] -= derivatives.flat[i] * dividend.values.flat[i]
						/ (divisor.values.flat[0] * divisor.values.flat[0]);
			}
		}
	}
}