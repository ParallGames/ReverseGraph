package reverseGraph.nodes;

public abstract class Node {
	protected final double[] outputs;

	protected Node(int outSize) {
		outputs = new double[outSize];
	}

	protected Node(double[] values) {
		this.outputs = values;
	}

	public final double[] getValues() {
		return outputs;
	}

	public final int getSize() {
		return outputs.length;
	}

	@Override
	public String toString() {
		String str = "{";

		final int size = getSize();
		for (int i = 0; i < size - 1; i++) {
			str += outputs[i] + ", ";
		}

		str += outputs[size - 1] + "}";

		return String.valueOf(str);
	}
}
