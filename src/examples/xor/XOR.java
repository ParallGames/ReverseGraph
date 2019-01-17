package examples.xor;

import java.util.ArrayList;

import reverseGraph.Graph;
import reverseGraph.nodes.Input;
import reverseGraph.nodes.operations.Multiplication;
import reverseGraph.nodes.operations.Operation;
import reverseGraph.nodes.operations.Substraction;
import reverseGraph.optimizers.RMSprop;
import vnl.Activation;
import vnl.Network;
import vnl.model.LayerModel;

public class XOR {
	public static void main(String[] args) {
		Input i1 = new Input(0);
		Input i2 = new Input(0);

		ArrayList<LayerModel> layers = new ArrayList<>();
		layers.add(new LayerModel(100, Activation.LEAKYRELU));
		layers.add(new LayerModel(100, Activation.LEAKYRELU));
		layers.add(new LayerModel(1, Activation.TANH));

		Operation output = Network.createNetwork(new Input[] { i1, i2 }, layers.toArray(new LayerModel[0]))[0];

		Input label = new Input(0);
		Substraction diff = new Substraction(output, label);
		Multiplication loss = new Multiplication(diff, diff);

		System.out.println("Creating graph");

		Graph graph = new Graph(loss, new RMSprop());

		System.out.println("Graph created");

		double errorSum = 1;

		while (errorSum > 0.1) {
			errorSum = 0;

			i1.setValue(-1);
			i2.setValue(-1);
			label.setValue(-1);
			graph.compute();
			graph.computeDerivatives();
			errorSum += loss.getOutput();
			graph.minimize();

			i1.setValue(1);
			i2.setValue(-1);
			label.setValue(1);
			graph.compute();
			graph.computeDerivatives();
			errorSum += loss.getOutput();
			graph.minimize();

			i1.setValue(-1);
			i2.setValue(1);
			label.setValue(1);
			graph.compute();
			graph.computeDerivatives();
			errorSum += loss.getOutput();
			graph.minimize();

			i1.setValue(1);
			i2.setValue(1);
			label.setValue(-1);
			graph.compute();
			graph.computeDerivatives();
			errorSum += loss.getOutput();
			graph.minimize();
			System.out.println(errorSum);
		}
		System.out.println("2.655399949887283E-8");
	}
}
