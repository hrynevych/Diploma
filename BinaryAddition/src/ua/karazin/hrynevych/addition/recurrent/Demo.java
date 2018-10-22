package ua.karazin.hrynevych.addition.recurrent;

import java.util.Scanner;

public class Demo {

	public static void main(String[] args) {
		Network n = new Network();
		Scanner in = new Scanner(System.in);

		do {
			n.process();
			System.out.println("\nWanna continue? (Enter - yes, \"stop\" - no)");
			if (in.hasNextLine()) {
				if (in.nextLine().equals("stop")) {
					break;
				}
			}
		} while (true);
		in.close();
	}
}
