package reverseGraph.data;

public final class Tensor implements Cloneable {
	public final Dimensions dimensions;
	public final double[] values;

	/**
	 * Create a tensor by copying an other
	 * 
	 * @param tensor the tensor to copy
	 */
	public Tensor(Tensor tensor) {
		dimensions = tensor.dimensions;
		values = new double[tensor.values.length];
		System.arraycopy(tensor.values, 0, values, 0, tensor.values.length);
	}

	/**
	 * Create a new tensor with the given dimensions
	 * 
	 * @param dimensions the dimensions for this tensor
	 */
	public Tensor(Dimensions dimensions) {
		this.dimensions = dimensions;

		values = new double[dimensions.valuesCount];
	}

	/**
	 * Create a new tensor with the given dimensions
	 * 
	 * @param dimensions the dimensions for this tensor
	 */
	public Tensor(int... dimensions) {
		this(new Dimensions(dimensions));
	}

	/**
	 * Create a copy of this tensor
	 * 
	 * @return the new tensor
	 */
	public Tensor clone() {
		return new Tensor(this);
	}

	/**
	 * Get the value at the given coordinates
	 * 
	 * @param coordinate position of the value in this tensor
	 * @return the value at this coordinate
	 */
	public double get(int... coordinate) {
		int index = coordinateToIndex(coordinate);

		return values[index];
	}

	/**
	 * Set the value at the given coordinates
	 * 
	 * @param value      the value to set
	 * @param coordinate the position to set the value
	 */
	public void set(double value, int... coordinate) {
		int index = coordinateToIndex(coordinate);

		values[index] = value;
	}

	/**
	 * Add to the value at the given coordinates
	 * 
	 * @param value      the value to add
	 * @param coordinate to position to add the value
	 */
	public void add(double value, int... coordinate) {
		int index = coordinateToIndex(coordinate);

		values[index] += value;
	}
	
	private int coordinateToIndex(int... coordinate) {
		if (coordinate.length != dimensions.count()) {
			throw new RuntimeException("Coordinate should have the same dimension count as this tensor:");
		}

		int multiplier = 1;

		int index = 0;

		for (int i = coordinate.length - 1; i >= 0; i--) {
			if (coordinate[i] >= dimensions.get(i)) {
				throw new RuntimeException("Coordinate out of bounds");
			}

			index += coordinate[i] * multiplier;
			multiplier *= dimensions.get(i);
		}

		return index;
	}
}
