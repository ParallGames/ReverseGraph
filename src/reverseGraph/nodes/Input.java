package reverseGraph.nodes;

public class Input extends Node {
	public Input(double initialValue) {
		this.output = initialValue;
	}

	public void setValue(double value) {
		output = value;
	}
}
