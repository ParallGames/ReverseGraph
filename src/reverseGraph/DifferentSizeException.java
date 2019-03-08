package reverseGraph;

public class DifferentSizeException extends RuntimeException {
	public DifferentSizeException(int size1, int size2) {
		super("The arrays should have the sime size : " + size1 + ", " + size2 + ".");
	}
}
