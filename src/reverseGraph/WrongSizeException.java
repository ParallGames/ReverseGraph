package reverseGraph;

public class WrongSizeException extends RuntimeException {
	public WrongSizeException(int size, int desiredSize) {
		super("Wrong array size : " + size + " it should be : " + desiredSize + ".");
	}
}
