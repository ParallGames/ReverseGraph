package test;

import reverseGraph.data.Tensor;

public class SimpleGraph {
	public static void main(String[] args) {
		Tensor tens = new Tensor(5, 5);

		tens.set(2, 4, 4);

		tens.get(2, 5, 6);
	}
}
