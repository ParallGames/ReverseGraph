package reverseGraph.data;

public final class Tensor implements Cloneable {
	public final Dimensions dimensions;
	public final double[] flat;

	/**
	 * Create a tensor by copying an other
	 * 
	 * @param tensor the tensor to copy
	 */
	public Tensor(Tensor tensor) {
		dimensions = tensor.dimensions;
		flat = new double[tensor.flat.length];
		System.arraycopy(tensor.flat, 0, flat, 0, tensor.flat.length);
	}

	/**
	 * Create a new tensor with the given dimensions
	 * 
	 * @param dimensions the dimensions for this tensor
	 */
	public Tensor(Dimensions dimensions) {
		this.dimensions = dimensions;

		flat = new double[dimensions.valuesCount];
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
	@Override
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

		return flat[index];
	}

	/**
	 * Set the value at the given coordinates
	 * 
	 * @param value      the value to set
	 * @param coordinate the position to set the value
	 */
	public void set(double value, int... coordinate) {
		int index = coordinateToIndex(coordinate);

		flat[index] = value;
	}

	/**
	 * Copy values of the given tensor to this one
	 * 
	 * @param values the values to copy
	 */
	public void set(Tensor values) {
		set(values.flat);
	}

	/**
	 * Copy values of the given array to the internal tensor array
	 * 
	 * @param values the values to copy
	 */
	public void set(double[] values) {
		if (values.length != this.flat.length) {
			throw new RuntimeException("Tensors should have the same values count");
		}

		System.arraycopy(values, 0, this.flat, 0, this.flat.length);
	}

	/**
	 * Add to the value at the given coordinates
	 * 
	 * @param value      the value to add
	 * @param coordinate to position to add the value
	 */
	public void add(double value, int... coordinate) {
		int index = coordinateToIndex(coordinate);

		flat[index] += value;
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

	public static Tensor createVector(double[] values) {
		Tensor t = new Tensor(values.length);

		t.set(values);

		return t;
	}

	public static Tensor createScalar(double value) {
		Tensor t = new Tensor(Dimensions.SCALAR);

		t.flat[0] = value;

		return t;
	}
}
