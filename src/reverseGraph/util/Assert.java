package reverseGraph.util;

import reverseGraph.data.Dimensions;

public class Assert {
	public static void equals(Dimensions dimA, Dimensions dimB) {
		if(!(dimA.equals(dimB))) {
			throw new RuntimeException("The two dimensions aren't equals : " + dimA + " != " + dimB);
		}
	}
	
	public static void equals(int valueA, int valueB) {
		if (valueA != valueB) {
			throw new RuntimeException("The two value should be equals : " + valueA + " != " + valueB);
		}
	}
}
