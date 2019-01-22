package reverseGraph.nodes;

public abstract class Derivable extends Node {
	protected double derivative = 0;

	public void resetDerivative() {
		derivative = 0;
	}

	public void addToDerivative(double value) {
		derivative += value;
	}

	public double getDerivative() {
		return derivative;
	}
}
