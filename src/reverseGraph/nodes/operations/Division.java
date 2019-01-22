package reverseGraph.nodes.operations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Division extends Operation {
	private final Node dividend;
	private final Node divisor;

	public Division(Node dividend, Node divisor) {
		this.dividend = dividend;
		this.divisor = divisor;
	}

	@Override
	public void compute() {
		this.output = dividend.getValue() / divisor.getValue();
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { dividend, divisor };
	}

	@Override
	public void computeDependenciesDerivative() {
		if (dividend instanceof Derivable) {
			((Derivable) dividend).addToDerivative(derivative / divisor.getValue());
		}

		if (divisor instanceof Derivable) {
			((Derivable) divisor)
					.addToDerivative(-derivative * dividend.getValue() / (divisor.getValue() * divisor.getValue()));
		}
	}
}
