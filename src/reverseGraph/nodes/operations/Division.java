package reverseGraph.nodes.operations;

import reverseGraph.DifferentSizeException;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public final class Division extends Operation {
	private final Node dividend;
	private final Node divisor;

	public Division(Node dividend, Node divisor) {
		super(dividend.getSize());

		if (dividend.getSize() != divisor.getSize()) {
			throw new DifferentSizeException(dividend.getSize(), divisor.getSize());
		}

		this.dividend = dividend;
		this.divisor = divisor;
	}

	@Override
	public void compute() {
		final int size = getSize();
		for (int i = 0; i < size; i++) {
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
			final int size = getSize();
			for (int i = 0; i < size; i++) {
				((Derivable) divisor).derivatives[i] += derivatives[i] / divisor.values[i];
			}
		}

		if (divisor instanceof Derivable) {
			final int size = getSize();
			for (int i = 0; i < size; i++) {
				((Derivable) divisor).derivatives[i] -= derivatives[i] * dividend.values[i]
						/ (divisor.values[i] * divisor.values[i]);
			}
		}
	}
}
