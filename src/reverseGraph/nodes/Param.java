package reverseGraph.nodes;

public class Param extends Derivable {
	public Param() {
		this(0);
	}

	public Param(double initialValue) {
		output = initialValue;
	}

	public void setValue(double value) {
		output = value;
	}

	public void update(double value) {
		output += value;
	}
}
