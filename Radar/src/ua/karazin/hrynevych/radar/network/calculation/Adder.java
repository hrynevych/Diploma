package ua.karazin.hrynevych.radar.network.calculation;

// Нейрон, що повертає суму своїх вхідних сигналів
public class Adder {
	
	// Активаційна функція нейрона
	public double process(double ... input) {
		double result = 0;
		
		for (int i = 0; i < input.length; i++) {
			result += input[i];
		}
		return result;
	}
}
