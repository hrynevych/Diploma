package ua.karazin.hrynevych.radar.network.calculation;

// ������, �� ������� ���� ���� ������� �������
public class Adder {
	
	// ����������� ������� �������
	public double process(double ... input) {
		double result = 0;
		
		for (int i = 0; i < input.length; i++) {
			result += input[i];
		}
		return result;
	}
}
