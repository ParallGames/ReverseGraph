package reverseGraph.node;

import reverseGraph.data.Dimensions;

public class Input extends Node {
	public final String label;

	public Input(Dimensions dimensions, String label) {
		super(dimensions);
		this.label = label;
	}
}
