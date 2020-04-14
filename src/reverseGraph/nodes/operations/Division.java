package reverseGraph.nodes.operations;

import reverseGraph.exceptions.Assert;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Division extends Operation {
	private final Node dividend;
	private final Node divisor;

	public Division(Node dividend, Node divisor) {
		super(dividend.values.dimensions);

		Assert.sameDimensions(dividend.values.dimensions, divisor.values.dimensions);

		this.dividend = dividend;
		this.divisor = divisor;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.flat.length; i++) {
			values.flat[i] = dividend.values.flat[i] / divisor.values.flat[i];
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
				((Derivable) dividend).derivatives.flat[i] += derivatives.flat[i] / divisor.values.flat[i];
			}
		}

		if (divisor instanceof Derivable) {
			for (int i = 0; i < derivatives.flat.length; i++) {
				((Derivable) divisor).derivatives.flat[i] -= derivatives.flat[i] * dividend.values.flat[i]
						/ (divisor.values.flat[i] * divisor.values.flat[i]);
			}
		}
	}
}
