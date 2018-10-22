package ua.karazin.hrynevych.radar;

import ua.karazin.hrynevych.radar.network.Network;
import ua.karazin.hrynevych.radar.network.exceptions.DifferentDimensionsException;
import ua.karazin.hrynevych.radar.network.exceptions.EmptyMatrixException;

// ������� �����
// ���� ������ ����� ������������ �����.
// ��������� � �������� ����� ���������� �����������
// ��� ����� �����������.
public class WorkMode {
	
	// �������� ���� ��'����
	public static final String TRAJECTORY = "traj2";
	
	// ��������� ��������
	public static final int DIMENSIONS = 3;
	
	// ʳ������ �����, �� ����������� � ���'��
	public static final int MEMORY_CAPACITY = 3;
	
	// ����������� �������� 䳿 ������
	public static final int MAX_COORD = 20000;
	
	// ˳������� ������� ������� �����
	public static int counter = 0;
	
	// �����, �� ������� �� ��������� � ��������� �������� ������
	public static void main(String[] args) throws DifferentDimensionsException, EmptyMatrixException {
		Network n = new Network();
		Radar r = new Radar(TRAJECTORY);
		
		for (int i = 0; i < MEMORY_CAPACITY - 1; i++) {
			double[] point = r.process();
			n.updateData(point);
			System.err.println("Point " + (++counter) + " added: " + pointToString(point));
		}
		while (true) {
			double[] point = r.process();
			if (!isOnTheRadar(point)) {
				System.err.println("Target is lost");
				break;
			}
			
			if (counter >= MEMORY_CAPACITY) {
				System.err.println("Point " + (counter - MEMORY_CAPACITY + 1) + " is lost");
			}
			System.err.println("Point " + (++counter) + " added: " + pointToString(point));
			
			double[] next = n.process(r.getDelay(), point);
			
			System.out.println("Next: " + pointToString(next));
		}
	}
	
	// �����, �� ����������� ����� ��������� ����� �
	// �������� ���������� ������
	public static String pointToString(double[] point) {
		StringBuffer result = new StringBuffer("(");
		
		for (int i = 0; i < point.length; i++) {
			result.append(point[i]);
			if (i != point.length - 1) {
				result.append("; ");
			}
		}
		result.append(")");
		return result.toString();
	}
	
	// �����, �� ������� �� �� ������ ��'��� �� ���� 䳿 ������
	public static boolean isOnTheRadar(double[] point) {
		boolean result = true;
		
		for (int i = 0; i < point.length; i++) {
			if (point[i] > MAX_COORD) {
				result = false;
			}
		}
		return result;
	}
}
