package reverseGraph.node;

import reverseGraph.data.Dimensions;

public abstract class Node {
	private static int count = 0;

	public final Dimensions dimensions;

	public final int index;

	public Node(Dimensions dimensions) {
		this.dimensions = dimensions;
		index = count++;
	}
}
