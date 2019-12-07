package reverseGraph.node.vector;

import reverseGraph.node.Node;

public abstract class VectorNode extends Node {
	public final int width;

	public VectorNode(int width) {
		super(width);

		this.width = width;
	}

	public abstract double get(int index);

	@Override
	public final double getFlat(int index) {
		return get(index);
	}
}
