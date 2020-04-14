package reverseGraph.exceptions;

import reverseGraph.data.Dimensions;

public class DifferentDimensionsException extends RuntimeException {
	public DifferentDimensionsException(Dimensions dim1, Dimensions dim2) {
		super("The arrays should have the sime size : " + dim1 + " != " + dim2 + ".");
	}
}
