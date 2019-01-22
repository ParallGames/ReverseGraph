package reverseGraph.nodes.operations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Multiplication extends Operation {
	private final Node factor1;
	private final Node factor2;

	public Multiplication(Node factor1, Node factor2) {
		this.factor1 = factor1;
		this.factor2 = factor2;
	}

	@Override
	public void compute() {
		this.output = factor1.getValue() * factor2.getValue();
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { factor1, factor2 };
	}

	@Override
	public void computeDependenciesDerivative() {
		if (factor1 instanceof Derivable) {
			((Derivable) factor1).addToDerivative(derivative * factor2.getValue());
		}

		if (factor2 instanceof Derivable) {
			((Derivable) factor2).addToDerivative(derivative * factor1.getValue());
		}
	}
}
