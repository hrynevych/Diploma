package ua.karazin.hrynevych.radar.network.calculation;

// Нейрон, що повертає добуток своїх вхідних сигналів
public class Multiplier {

	// Активаційна функція нейрона
	public double process(double ... input) {
		double result = 1;
		
		for (int i = 0; i < input.length; i++) {
			result *= input[i];
		}
		return result;
	}
}
