package reverseGraph.nodes.operations;

import reverseGraph.data.Dimensions;
import reverseGraph.nodes.Derivable;
import reverseGraph.nodes.Node;

public abstract class Operation extends Derivable {
	private static int count = 0;
	public final int index;

	public Operation(Dimensions dimensions) {
		super(dimensions);
		index = count++;
	}

	public abstract void compute();

	public abstract Node[] getDependencies();

	public abstract void computeDependenciesDerivatives();
}
