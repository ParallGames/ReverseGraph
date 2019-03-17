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
		if (values.length != getSize()) {
			throw new WrongSizeException(values.length, getSize());
		}

		final int size = getSize();
		for (int i = 0; i < size; i++) {
			this.values[i] = values[i];
		}
	}
}
