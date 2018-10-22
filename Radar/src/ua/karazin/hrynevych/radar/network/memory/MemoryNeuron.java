package ua.karazin.hrynevych.radar.network.memory;

// ����, �� ����������� LSTM-�����,
// � ���� ���������� ���� ���'��
public class MemoryNeuron {
	
	// ��������, �� ���������� �
	// �������� ���'�� ������
	private Double storedValue;
	
	// ������� �������
	public void inputGate(Double value) {
		storedValue = value;
	}
	
	// ������� ���������
	public void forgetGate() {
		storedValue = null;
	}
	
	// �������� �������
	public Double outputGate() {
		return storedValue;
	}
}
