package reverseGraph.nodes.operations;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Division extends Operation {
	private final Node dividend;
	private final Node divisor;

	public Division(Node dividend, Node divisor) {
		super(dividend.values.length);

		if (dividend.values.length != divisor.values.length) {
			throw new DifferentSizeException(dividend.values.length, divisor.values.length);
		}

		this.dividend = dividend;
		this.divisor = divisor;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.length; i++) {
			values[i] = dividend.values[i] / divisor.values[i];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { dividend, divisor };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (dividend instanceof Derivable) {
			for (int i = 0; i < derivatives.length; i++) {
				((Derivable) dividend).derivatives[i] += derivatives[i] / divisor.values[i];
			}
		}

		if (divisor instanceof Derivable) {
			for (int i = 0; i < derivatives.length; i++) {
				((Derivable) divisor).derivatives[i] -= derivatives[i] * dividend.values[i]
						/ (divisor.values[i] * divisor.values[i]);
			}
		}
	}
}
