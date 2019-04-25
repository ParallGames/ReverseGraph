package reverseGraph.nodes;

public abstract class Derivable extends Node {
	public final double[] derivatives;

	public Derivable(int outSize) {
		super(outSize);
		derivatives = new double[outSize];
	}

	public Derivable(double[] initialValues) {
		super(initialValues);
		derivatives = new double[initialValues.length];
	}
}
