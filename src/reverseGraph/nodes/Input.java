package reverseGraph.nodes;

import reverseGraph.WrongSizeException;

public final class Input extends Node {
	public Input(int outSize) {
		super(outSize);
	}

	public Input(double[] initialValues) {
		super(initialValues);
	}

	public void setValues(double[] values) {
		if (values.length != this.values.length) {
			throw new WrongSizeException(values.length, this.values.length);
		}

		System.arraycopy(values, 0, this.values, 0, values.length);
	}
}
