package reverseGraph.nodes;

public class Param extends Derivable {
	public Param(double initialValue) {
		output = initialValue;
	}

	public void setValue(double value) {
		output = value;
	}
}
