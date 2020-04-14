package reverseGraph.exceptions;

import reverseGraph.data.Dimensions;

public class Assert {
	public static void sameDimensions(Dimensions d1, Dimensions d2) {
		if (!d1.equals(d2)) {
			throw new DifferentDimensionsException(d1, d2);
		}
	}

	public static void desiredDimensions(Dimensions d, Dimensions desiredDimensions) {
		if (!d.equals(desiredDimensions)) {
			throw new WrongDimensionsException(d, desiredDimensions);
		}
	}
}
