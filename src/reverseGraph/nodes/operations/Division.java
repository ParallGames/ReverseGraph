package reverseGraph.nodes.operations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Division extends Operation {
	private final Node dividend;
	private final Node divisor;

	public Division(Node dividend, Node divisor) {
		super(dividend.getSize());

		this.dividend = dividend;
		this.divisor = divisor;
	}

	@Override
	public void compute() {
		for (int i = 0; i < getSize(); i++) {
			outputs[i] = dividend.getValues()[i] / divisor.getValues()[i];
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { dividend, divisor };
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (dividend instanceof Derivable) {
			double[] derivatives = new double[getSize()];

			for (int i = 0; i < getSize(); i++) {
				derivatives[i] = this.getDerivatives()[i] / divisor.getValues()[i];
			}

			((Derivable) dividend).addToDerivatives(derivatives);
		}

		if (divisor instanceof Derivable) {
			double[] derivatives = new double[getSize()];

			for (int i = 0; i < getSize(); i++) {
				derivatives[i] = -this.getDerivatives()[i] * dividend.getValues()[i]
						/ (divisor.getValues()[i] * divisor.getValues()[i]);
			}

			((Derivable) divisor).addToDerivatives(derivatives);
		}
	}
}
