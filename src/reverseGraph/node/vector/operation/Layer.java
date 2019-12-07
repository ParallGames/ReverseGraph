package reverseGraph.node.vector.operation;

import reverseGraph.node.Derivable;
import reverseGraph.node.Node;
import reverseGraph.node.matrix.MatrixDerivable;
import reverseGraph.node.matrix.MatrixNode;
import reverseGraph.node.vector.VectorDerivable;
import reverseGraph.node.vector.VectorNode;
import reverseGraph.node.vector.VectorOperation;
import reverseGraph.util.Assert;

public class Layer extends VectorOperation {
	private final VectorNode inputs;

	private final MatrixNode weights;
	private final VectorNode biases;

	private final double[] values;
	private final double[] derivatives;

	/**
	 * Constructs a fully connected neural network layer The input size is defined
	 * by the width of the inputs. The output size is defined by the width of
	 * biases. The width of weights should be equals to the input width and the
	 * height equals to biases width.
	 * 
	 * @param inputs  inputs of the layer
	 * @param weights weights of the layer
	 * @param biases  biases of the layer
	 */
	public Layer(VectorNode inputs, MatrixNode weights, VectorNode biases) {
		super(biases.width);

		Assert.assertEquals(inputs.width, weights.width);
		Assert.assertEquals(weights.height, biases.width);

		this.inputs = inputs;
		this.weights = weights;
		this.biases = biases;

		values = new double[size];
		derivatives = new double[size];
	}

	@Override
	public void compute() {
		for (int y = 0; y < width; y++) {
			values[y] = biases.get(y);
			for (int x = 0; x < inputs.width; x++) {
				values[y] += inputs.get(x) * weights.get(x, y);
			}
		}
	}

	@Override
	public void computeDependenciesDerivatives() {
		if (inputs instanceof Derivable) {
			VectorDerivable derivable = (VectorDerivable) inputs;

			for (int x = 0; x < inputs.width; x++) {
				double derivative = 0;
				for (int y = 0; y < width; y++) {
					derivative += weights.get(x, y) * derivatives[y];
				}
				derivable.addDerivative(x, derivative);
			}
		}

		if (weights instanceof Derivable) {
			MatrixDerivable derivable = (MatrixDerivable) weights;

			for (int x = 0; x < inputs.width; x++) {
				for (int y = 0; y < width; y++) {
					derivable.addDerivative(x, y, inputs.get(x) * derivatives[y]);
				}
			}
		}

		if (biases instanceof Derivable) {
			VectorDerivable derivable = (VectorDerivable) biases;
			for (int i = 0; i < size; i++) {
				derivable.addDerivative(i, derivatives[i]);
			}
		}
	}

	@Override
	public Node[] getDependencies() {
		return new Node[] { inputs, weights, biases };
	}

	@Override
	public double getDerivative(int index) {
		return derivatives[index];
	}

	@Override
	public void addDerivative(int index, double value) {
		derivatives[index] += value;
	}

	@Override
	public void resetDerivatives() {
		for (int i = 0; i < derivatives.length; i++) {
			derivatives[i] = 0;
		}
	}

	@Override
	public double get(int index) {
		return values[index];
	}
}
