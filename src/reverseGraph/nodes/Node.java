package reverseGraph.nodes;

public abstract class Node {
	protected double output;

	public final double getValue() {
		return output;
	}

	@Override
	public String toString() {
		System.out.println(output);
		return String.valueOf(output);
	}
}
