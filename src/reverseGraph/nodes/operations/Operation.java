package reverseGraph.nodes.operations;

import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public abstract class Operation extends Derivable {
	public Operation(int outSize) {
		super(outSize);
	}

	public abstract void compute();

	public abstract Node[] getDependencies();

	public abstract void computeDependenciesDerivatives();
}
