package reverseGraph.nodes.operations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public class Sum extends Operation {
	private final Node[] addends;

	public Sum(Node... addends) {
		this.addends = addends;
	}

	@Override
	public void compute() {
		double output = 0;

		for (Node node : addends) {
			output += node.getValue();
		}

		this.output = output;
	}

	@Override
	public Node[] getDependencies() {
		return addends;
	}

	@Override
	public void computeDependenciesDerivative() {
		for (Node node : addends) {
			if (node instanceof Derivable) {
				((Derivable) node).addToDerivative(derivative);
			}
		}
	}
}
