package ua.karazin.hrynevych.radar.network.calculation;

// Нейрон, що повертає перший вхідний сигнал,
// від якого були відняті всі інші
public class Subtractor {
	
	// Активаційна функція нейрона
	public double process(double ... input) {
		double result = 0;
		
		for (int i = 0; i < input.length; i++) {
			if (i == 0) {
				result = input[i];
			} else {
				result -= input[i];
			}
		}
		return result;
	}
}
