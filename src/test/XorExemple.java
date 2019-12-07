package test;

import reverseGraph.Graph;
import reverseGraph.model.Parameters;
import reverseGraph.node.matrix.MatrixParam;
import reverseGraph.node.operations.Subtraction;
import reverseGraph.node.operations.Sum;
import reverseGraph.node.vector.VectorInput;
import reverseGraph.node.vector.VectorParam;
import reverseGraph.node.vector.operation.Layer;
import reverseGraph.node.vector.operation.ReLU;
import reverseGraph.node.vector.operation.Square;
import reverseGraph.node.vector.operation.Tanh;
import reverseGraph.optimizers.Adam;

public class XorExemple {
	public static void main(String[] args) {
		VectorInput input = new VectorInput(2);

		MatrixParam weights1 = Parameters.createXavierWeights(2, 10);
		VectorParam biases1 = new VectorParam(10);

		Layer layer1 = new Layer(input, weights1, biases1);

		ReLU activation1 = new ReLU(layer1);

		MatrixParam weights2 = Parameters.createXavierWeights(10, 1);
		VectorParam biases2 = new VectorParam(1);

		Layer layer2 = new Layer(activation1, weights2, biases2);

		Tanh activation2 = new Tanh(layer2);

		VectorInput label = new VectorInput(1);

		Subtraction diff = new Subtraction(activation2, label);

		Square square = new Square(diff);

		Sum sum = new Sum(square);

		Graph graph = new Graph(sum, new Adam(0.01, 0.9, 0.999));

		double error = 1;
		while (error > 0.1) {
			error = 0;

			input.set(0, -1);
			input.set(1, -1);
			label.set(0, -1);
			graph.compute();
			graph.computeDerivatives();
			graph.minimize();
			error += sum.get();

			input.set(0, 1);
			input.set(1, -1);
			label.set(0, 1);
			graph.compute();
			graph.computeDerivatives();
			graph.minimize();
			error += sum.get();

			input.set(0, -1);
			input.set(1, 1);
			label.set(0, 1);
			graph.compute();
			graph.computeDerivatives();
			graph.minimize();
			error += sum.get();

			input.set(0, 1);
			input.set(1, 1);
			label.set(0, -1);
			graph.compute();
			graph.computeDerivatives();
			graph.minimize();
			error += sum.get();

			System.out.println(error);
		}
	}
}
