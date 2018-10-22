package ua.karazin.hrynevych.radar.network.exceptions;

// ����������, ��� ����������, ���� ���������
// ������� ����� �� ������� � ����������
// �������� �������� �����
public class DifferentDimensionsException extends Exception{

	// ��������� id ����� ����������
	private static final long serialVersionUID = 4782543172975658673L;

	// �����������, �� �������� ��������
	// ����������� �� ����� ��� ��������
	// ����������
	public DifferentDimensionsException() {
		super("Different dimensions");
	}
}
