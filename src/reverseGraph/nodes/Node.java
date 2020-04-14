package reverseGraph.nodes;

import reverseGraph.data.Dimensions;
import reverseGraph.data.Tensor;

public abstract class Node {
	public final Tensor values;

	protected Node(Dimensions dimensions) {
		values = new Tensor(dimensions);
	}

	protected Node(Tensor values) {
		this.values = values.clone();
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("{");

		for (int i = 0; i < values.flat.length - 1; i++) {
			str.append(values.flat[i]);
			str.append(", ");
		}

		str.append(values.flat[values.flat.length - 1]);
		str.append("}");

		return String.valueOf(str);
	}
}
