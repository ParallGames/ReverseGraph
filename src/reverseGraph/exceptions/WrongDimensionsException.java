package reverseGraph.exceptions;

import reverseGraph.data.Dimensions;

public class WrongDimensionsException extends RuntimeException {
	public WrongDimensionsException(Dimensions dim, Dimensions desiredDim) {
		super("Wrong array size : " + dim + " it should be : " + desiredDim + ".");
	}
}
