package ua.karazin.hrynevych.radar.network.exceptions;

// ����������, ��� ���������� ��� ��������
// ���������� � ����� ���'�� �� ���� ���������,
// ���� ���� ���'�� �� ���������� �� 100%
// (������ null-��������)
public class EmptyMatrixException extends Exception{

	// ��������� id ����� ����������
	private static final long serialVersionUID = -2755851924220566110L;
	
	// �����������, �� �������� ��������
	// ����������� �� ����� ��� ��������
	// ����������
	public EmptyMatrixException() {
		super("Matrix is empty");
	}
}
