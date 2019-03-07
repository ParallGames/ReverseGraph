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

		for (int i = 0; i < getSize() - 1; i++) {
			str += outputs[i] + ", ";
		}

		str += outputs[getSize() - 1] + "}";

		return String.valueOf(str);
	}
}
