package reverseGraph.nodes.operations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Substraction extends Operation {
	private final Node minuend;
	private final Node substraend;

	public Substraction(Node minuend, Node substraend) {
		this.minuend = minuend;
		this.substraend = substraend;
	}

	@Override
	public void compute() {
		this.output = minuend.getValue() - substraend.getValue();
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { minuend, substraend };
	}

	@Override
	public void computeDependenciesDerivative() {
		if (minuend instanceof Derivable) {
			((Derivable) minuend).addToDerivative(derivative);
		}

		if (substraend instanceof Derivable) {
			((Derivable) substraend).addToDerivative(-derivative);
		}
	}
}
