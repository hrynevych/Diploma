package ua.karazin.hrynevych.radar.network.memory;

import ua.karazin.hrynevych.radar.network.exceptions.DifferentDimensionsException;

// ����, �� ������� �� ���� ��������� �����
public class MemoryPart {
	
	// ������� LSTM-������ �����
	private MemoryNeuron[][] neurons;
	
	// ��������� ��������
	private int coord;
	
	// ʳ������ ����� � ���'��
	private int nodes;
	
	// �����������, �� ������� ���� ���'��
	// ��������� ������
	public MemoryPart(int coordinates, int amount) {
		coord = coordinates;
		nodes = amount;
		neurons = new MemoryNeuron[coordinates][amount];
		for (int i = 0; i < coordinates; i++) {
			for (int j = 0; j < amount; j++) {
				neurons[i][j] = new MemoryNeuron();
			}
		}
	}
	
	// ����������� �����������, �� �������
	// ���� ���'�� � ���'��� ������� (3�3)
	public MemoryPart() {
		int dimension = 3;
		
		coord = dimension;
		nodes = dimension;
		neurons = new MemoryNeuron[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				neurons[i][j] = new MemoryNeuron();
			}
		}
	}
	
	// �����, �� �������� ������ ���� ����� ������������� ��������
	// � ���� ���'��
	public void push(double x, double y, double z) throws DifferentDimensionsException {
		double[] newPoint = toArray(x, y, z);
		
		checkDimensions(newPoint.length);
		for (int i = 0; i < coord; i++) {
			for (int j = nodes - 1; j > 0; j--) {
				neurons[i][j].forgetGate();
				neurons[i][j].inputGate(neurons[i][j-1].outputGate());
			}
			neurons[i][0].forgetGate();
			neurons[i][0].inputGate(newPoint[i]);
		}
	}
	
	// �����, �� ������� ������ ���� ����� � ���� ���'��
	public void push(double[] newPoint) throws DifferentDimensionsException {
		checkDimensions(newPoint.length);
		for (int i = 0; i < coord; i++) {
			for (int j = nodes - 1; j > 0; j--) {
				neurons[i][j].forgetGate();
				neurons[i][j].inputGate(neurons[i][j-1].outputGate());
			}
			neurons[i][0].forgetGate();
			neurons[i][0].inputGate(newPoint[i]);
		}
	}
	
	// ������������ ����� ������� ��������� ����� � �����
	private static double[] toArray(double x, double y, double z) {
		double[] result = new double[3];
		
		result[0] = x;
		result[1] = y;
		result[2] = z;
		return result;
	}
	
	// �����, �� �������� ���������� ���������
	// ���� ����� �������� ����� ���'��
	private void checkDimensions(int pointCoords) throws DifferentDimensionsException {
		if (pointCoords != coord) {
			throw new DifferentDimensionsException();
		}
	}
	
	// �����, �� �������� �������� �������� � ���'��
	// LSTM-������ � ������ �������
	public Double[][] getMatrix() {
		Double[][] result = new Double[coord][nodes];
		
		for (int i = 0; i < coord; i++) {
			for (int j = 0; j < nodes; j++) {
				result[i][j] = neurons[i][j].outputGate();
			}
		}
		return result;
	}
	
	// �����, �� �������� �������� ��������� ��������
	public int getCoordinates() {
		return coord;
	}
	
	// �����, �� �������� �������� ��'�� ����� ���'��
	// (����������� ������� �����, �� �� ���� ��������)
	public int getNodes() {
		return nodes;
	}
	
	// �����, �� ���� ���� ����������� ����� � ���� ���'��
	// � ������� ������� ��������, �� ����������� � �����
	public Double[][] process(double x, double y, double z) throws DifferentDimensionsException {
		push(x, y, z);
		return getMatrix();
	}
	
	// �����, �� ���� ���� ����� � ���� ���'��
	// � ������� ������� ��������, �� ����������� � �����
	public Double[][] process(double[] newPoint) throws DifferentDimensionsException {
		push(newPoint);
		return getMatrix();
	}
}
