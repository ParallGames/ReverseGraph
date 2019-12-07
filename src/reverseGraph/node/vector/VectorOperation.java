package reverseGraph.node.vector;

import reverseGraph.node.Computable;

public abstract class VectorOperation extends VectorDerivable implements Computable {
	public VectorOperation(int width) {
		super(width);
	}
}
