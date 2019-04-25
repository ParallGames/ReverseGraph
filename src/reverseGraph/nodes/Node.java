package reverseGraph.nodes;

public abstract class Node {
	private static int count = 0;

	public final int index = count++;

	public final double[] values;

	protected Node(int outSize) {
		values = new double[outSize];
	}

	protected Node(double[] values) {
		this.values = values;
	}

	@Override
	public String toString() {
		String str = "{";

		for (int i = 0; i < values.length - 1; i++) {
			str += values[i] + ", ";
		}

		str += values[values.length - 1] + "}";

		return String.valueOf(str);
	}
}
