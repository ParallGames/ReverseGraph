package reverseGraph.nodes;

public final class Param extends Derivable {
	public Param(int outSize) {
		super(outSize);
	}

	public Param(double[] initialValues) {
		super(initialValues);
	}

	public void setValues(double[] values) {
		for (int i = 0; i < getSize(); i++) {
			outputs[i] = values[i];
		}
	}

	public void update(double[] updates) {
		for (int i = 0; i < getSize(); i++) {
			outputs[i] += updates[i];
		}
	}
}
