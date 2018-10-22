package ua.karazin.hrynevych.addition.recurrent;

import java.util.Scanner;

public class Network {
	
	Scanner in = new Scanner(System.in);
	
	private byte[] num1;
	
	private byte[] num2;
	
	private Neuron neuron;
	
	private String output;
	
	public Network() {
		output = "";
		neuron = new Neuron();
	}
	
	public void process() {
		byte bit1 = 0;
		byte bit2 = 0;
		byte prev = 0;
		int maxLength;
		
		prepareNetwork();
		maxLength = Math.max(num1.length, num2.length);
		for (int i = 0; i < maxLength; i++) {
			bit1 = 0;
			bit2 = 0;
			if (i < num1.length) {
				bit1 = num1[i];
			}
			if (i < num2.length) {
				bit2 = num2[i];
			}
			prev = neuron.process(bit1, bit2, prev);
		}
		if (prev == 1) {
			add(prev);
		}
		System.out.println("Sum: " + output);
		output = "";
	}
	
	private void prepareNetwork() {
		getNumbers();
	}
	
	private void getNumbers() {
		String input = null;

		System.out.print("Number 1: ");
		if (in.hasNextLine()) {
			input = in.nextLine();
			num1 = convert(input);
		}
		System.out.print("Number 2: ");
		if (in.hasNextLine()) {
			input = in.nextLine();
			num2 = convert(input);
		}
	}
	
	private byte[] convert(String input) {
		byte[] result = new byte[input.length()];
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '1') {
				result[result.length - 1 - i] = 1;
			} else {
				result[result.length - 1 - i] = 0;
			}
		}
		return result;
	}
	
	private void add(int bit) {
		output = bit + output;
	}
	
	
	private class Neuron {
		
		public byte process(byte bit1, byte bit2, byte prev) {
			byte next = 0;
			int result = bit1 + bit2 + prev;

			if (result % 2 == 0) {
				add(0);
				if (result == 2) {
					next++;
				}
			} else {
				add(1);
				if (result == 3) {
					next++;
				}
			}
			return next;
		}
	}
}
