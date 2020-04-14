package reverseGraph.data;

public final class Dimensions {
	public static final Dimensions SCALAR = new Dimensions();

	private final int[] dimensions;

	public final int valuesCount;

	public Dimensions(int... dimensions) {
		valuesCount = valuesCount(dimensions);

		// new Array to prevent references
		this.dimensions = new int[dimensions.length];

		System.arraycopy(dimensions, 0, this.dimensions, 0, dimensions.length);
	}

	public int count() {
		return dimensions.length;
	}

	public int get(int index) {
		return dimensions[index];
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");

		for (int i = 0; i < dimensions.length; i++) {
			if (i > 0) {
				builder.append(",");
			}

			builder.append(dimensions[i]);
		}

		builder.append("]");

		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Dimensions)) {
			return false;
		}

		Dimensions dim = (Dimensions) obj;

		if (dim.dimensions.length != dimensions.length) {
			return false;
		}

		for (int i = 0; i < dimensions.length; i++) {
			if (dim.dimensions[i] != dimensions[i]) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int hashCode() {
		return valuesCount;
	}

	private static int valuesCount(int... dimensions) {
		int valuesCount = 1;

		for (int i = 0; i < dimensions.length; i++) {
			if (dimensions[i] <= 0) {
				throw new RuntimeException("A dimension size cannot be smaller than 0");
			}

			valuesCount *= dimensions[i];
		}

		return valuesCount;
	}
}
