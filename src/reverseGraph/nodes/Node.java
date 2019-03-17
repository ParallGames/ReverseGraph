package reverseGraph.nodes;

public abstract class Node {
	public final double[] values;

	protected Node(int outSize) {
		values = new double[outSize];
	}

	protected Node(double[] values) {
		this.values = values;
	}

	public final int getSize() {
		return values.length;
	}

	@Override
	public String toString() {
		String str = "{";

		final int size = getSize();
		for (int i = 0; i < size - 1; i++) {
			str += values[i] + ", ";
		}

		str += values[size - 1] + "}";

		return String.valueOf(str);
	}
}
