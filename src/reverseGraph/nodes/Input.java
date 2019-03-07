package reverseGraph.nodes;

public final class Input extends Node {
	public Input(int outSize) {
		super(outSize);
	}

	public Input(double[] initialValues) {
		super(initialValues);
	}

	public void setValues(double[] values) {
		for (int i = 0; i < getSize(); i++) {
			outputs[i] = values[i];
		}
	}
}
