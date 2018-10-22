package ua.karazin.hrynevych.comparing.recurrent;

import java.util.Scanner;

public class Network {
	
	public static final String STOP_WORD = "stop";

	private static Neuron n1 = new Neuron();
	
	public static void main(String[] args) {
		String input;
		String output;
		Scanner in = new Scanner(System.in);

		System.out.print("U: ");
		while (in.hasNextLine()) {
			input = in.nextLine();
			if (input.equals(STOP_WORD)) {
				break;
			}
			output = n1.process(input);
			n1.backConnection(output);
			System.out.println("N: " + output);
			System.out.print("U: ");
		}
		in.close();
	}
}





