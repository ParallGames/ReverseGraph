package reverseGraph.nodes.operations;

import reverseGraph.WrongSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class DivideAll extends Operation {
	private final Node dividend;
	private final Node divisor;

	public DivideAll(Node dividend, Node divisor) {
		super(dividend.values.length);

		if (divisor.values.length != 1) {
			throw new WrongSizeException(divisor.values.length, 1);
		}

		this.dividend = dividend;
		this.divisor = divisor;
	}

	@Override
	public void compute() {
		for (int i = 0; i < values.length; i++) {
			values[i] = dividend.values[i] / divisor.values[0];
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
				((Derivable) dividend).derivatives[i] += derivatives[i] / divisor.values[0];
			}
		}

		if (divisor instanceof Derivable) {
			for (int i = 0; i < derivatives.length; i++) {
				((Derivable) divisor).derivatives[0] -= derivatives[i] * dividend.values[i]
						/ (divisor.values[0] * divisor.values[0]);
			}
		}
	}
}