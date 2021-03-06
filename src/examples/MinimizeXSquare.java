package examples;

import reverseGraph.OptimizationGraph;
import reverseGraph.data.Tensor;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Multiplication;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.optimizers.Optimizer;
import reverseGraph.optimizers.SGD;

/**
 * Example that optimizes the x^2 function
 */
public class MinimizeXSquare {
	public static void main(String[] args) {

		// Create a parameter and set its value to 5
		Param x = new Param(Tensor.createScalar(5));

		// Create an operation that squares that parameter
		Operation square = new Multiplication(x, x);

		// Create an optimizer to update the parameter by 0.2 time the derivative
		Optimizer optimizer = new SGD(0.2);

		// Create a graph to compute and optimize the function x^2
		OptimizationGraph graph = new OptimizationGraph(square, optimizer);

		for (int i = 0; i < 10; i++) {
			// Compute the value of x^2
			graph.compute();

			// Print the parameter and function output values
			System.out.println("X value : " + x);
			System.out.println("Function output : " + square);
			System.out.println();

			// Compute the derivatives of the function
			graph.computeDerivatives();

			// Update the parameter by using the optimizer
			graph.minimize();
		}
	}
}
