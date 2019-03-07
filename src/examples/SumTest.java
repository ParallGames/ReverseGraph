package examples;

import reverseGraph.Graph;
import reverseGraph.nodes.Param;
import reverseGraph.nodes.operations.Division;
import reverseGraph.nodes.operations.Sum;
import reverseGraph.optimizers.SGD;

public class SumTest {
	public static void main(String[] args) {
		Param c1 = new Param(new double[] { 2, 3, 4, 5 });
		Param c2 = new Param(new double[] { 5, 5, 5, 5 });

		Param p = new Param(10);

		Division mul = new Division(c1, c2);

		Sum sum = new Sum(mul, p);

		Graph g = new Graph(sum, new SGD(100));

		g.compute();

		g.computeDerivatives();

		g.minimize();

		System.out.println(mul);
		System.out.println(c1);
		System.out.println(c2);
	}
}
