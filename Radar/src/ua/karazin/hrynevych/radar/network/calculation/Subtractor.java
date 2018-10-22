package ua.karazin.hrynevych.radar.network.calculation;

// ������, �� ������� ������ ������� ������,
// �� ����� ���� ����� �� ����
public class Subtractor {
	
	// ����������� ������� �������
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
