package ua.karazin.hrynevych.radar.network;

import ua.karazin.hrynevych.radar.network.calculation.CalculationPart;
import ua.karazin.hrynevych.radar.network.exceptions.DifferentDimensionsException;
import ua.karazin.hrynevych.radar.network.exceptions.EmptyMatrixException;
import ua.karazin.hrynevych.radar.network.memory.MemoryPart;

// ����, �� ����������� ������ �������� ������
// � ��������� ���� ���'�� �� ������ ���������
public class Network {
	
	// �������� ��������� ����� (��������� ������)
	private long delay = 20;
	
	// ���, �� ������� ������� ������� �����
	private final long zeroTime;
	
	// ����� ������� ����, � �� ���� ������� �����
	// � ���� ���'��
	private long[] times;
	
	// ���� ���'��
	private MemoryPart mp;
	
	// ���� ���������
	private CalculationPart cp;
	
	// ����������� ����������� �������� �����
	public Network() {
		zeroTime = System.currentTimeMillis();
		times = new long[3];
		mp = new MemoryPart();
		cp = new CalculationPart();
	}
	
	// ����������� �������� �����, �� �������� ����������
	// ��������� �������� � ��'�� ����� ���'��
	public Network(int coordinates, int nodes) {
		zeroTime = System.currentTimeMillis();
		times = new long[nodes];
		mp = new MemoryPart(coordinates, nodes);
		cp = new CalculationPart(nodes);
	}
	
	// ����� ��������� ����� - ���� ���� ����� � ���� ���'��
	public void updateData(double ... point) throws DifferentDimensionsException {
		mp.push(point);
		addTime(System.currentTimeMillis() - zeroTime);
	}
	
	// ��������������� ����� ��������� ����� - ���� ���� �����
	// � ���� ���'�� � ������� �� � ���������� �������� ������ ����
	public void updateDataDemo(long time, double ... point) throws DifferentDimensionsException {
		mp.push(point);
		addTime(time);
	}
	
	// ���� ����� ������ ���� � ����� ������� ����, 
	// ������� ��� ����� ��������, �� � ����� �����������
	private void addTime(long newTime) {
		for (int i = times.length - 1; i > -1; i--) {
			if (i == 0) {
				if (newTime - times[i] < delay) {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						System.err.println("Delay Failed");
					}
				}
				times[i] = newTime;
			} else {
				times[i] = times[i - 1];
			}
		}
	}
	
	// ������� ������� ��������� ��'���� �����
	// �������� ��� ���� ������ � ���'��� �������� �����
	public double[] getNext(long time) throws DifferentDimensionsException, EmptyMatrixException {
		double[] result = new double[mp.getCoordinates()];
		long[] extendedTimes = extendTimes(times[0] + time);
		double[][] func = convertMatrix();
		
		for (int i = 0; i < result.length; i++) {
			result[i] = cp.process(extendedTimes, func[i]);
		}
		return result;
	}
	
	// ������� ������� ��������� ��'���� � ��������� ������ ����,
	// ���� ����������� ��������� ��������� �����
	public double[] getNext() throws DifferentDimensionsException, EmptyMatrixException {
		return getNext(delay);
	}
	
	// ������� ������� ��������� ��'���� � �������� ������ ����
	public double[] getNextDemo(long time) throws DifferentDimensionsException, EmptyMatrixException {
		double[] result = new double[mp.getCoordinates()];
		long[] extendedTimes = extendTimes(time);
		double[][] func = convertMatrix();
		
		for (int i = 0; i < result.length; i++) {
			result[i] = cp.process(extendedTimes, func[i]);
		}
		return result;
	}
	
	// �������� ����� ������� ���� �� ����� (���������) ��������,
	// ��������� ��'���� � ����� ����������� �������� ������.
	private long[] extendTimes(long time) {
		long[] extendedTimes = new long[times.length + 1];
		
		for (int i = 0; i < extendedTimes.length; i++) {
			if (i == times.length) {
				extendedTimes[i] = time;
			} else {
				extendedTimes[i] = times[i];
			}
		}
		return extendedTimes;
	}
	
	// �����, �� ������� ������� ������ ����� � ��������
	// �� ���������� � ����� ���'��
	private double[][] convertMatrix() throws EmptyMatrixException {
		double[][] result = new double[mp.getCoordinates()][mp.getNodes()];
		
		for (int i = 0; i < mp.getCoordinates(); i++) {
			for (int j = 0; j < mp.getNodes(); j++) {
				try {
					result[i][j] = mp.getMatrix()[i][j];
				} catch (NullPointerException e) {
					throw new EmptyMatrixException();
				}
			}
		}
		return result;
	}
	
	// �����, �� ������ ������� ���� �����, ������� � ���'��� 
	// ������ ����� � ���������� ������� ��������� ��'���� �����
	// �������� ������� ����
	public double[] process(long time, double ... point) throws DifferentDimensionsException, EmptyMatrixException {
		updateData(point);
		return getNext(time);
	}
	
	// �����, �� ������ ������� ���� �����, ������� � ���'���
	// ������ ����� � ���������� ������� ��������� ��'���� �
	// ������ ����, �� ����������� ��������� ��������� �����
	public double[] processDefault(double ... point) throws DifferentDimensionsException, EmptyMatrixException {
		updateData(point);
		return getNext();
	}
	
	// ��������������� �����, �� ������ ������� ���� �����, �������
	// � ���'��� ������� �����, ����'����� �� ��������� ������� ����.
	// ������� ������� ��������� ��'���� � �������� ������ ����.
	public double[] processDemo(long absoluteTime, long pointTime, double ... point) throws DifferentDimensionsException, EmptyMatrixException {
		updateDataDemo(pointTime, point);
		return getNextDemo(absoluteTime);
	}
}
