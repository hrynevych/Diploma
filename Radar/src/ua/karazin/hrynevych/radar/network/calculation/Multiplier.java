package ua.karazin.hrynevych.radar.network.calculation;

// ������, �� ������� ������� ���� ������� �������
public class Multiplier {

	// ����������� ������� �������
	public double process(double ... input) {
		double result = 1;
		
		for (int i = 0; i < input.length; i++) {
			result *= input[i];
		}
		return result;
	}
}
