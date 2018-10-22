package ua.karazin.hrynevych.radar.network;

import ua.karazin.hrynevych.radar.network.calculation.CalculationPart;
import ua.karazin.hrynevych.radar.network.exceptions.DifferentDimensionsException;
import ua.karazin.hrynevych.radar.network.exceptions.EmptyMatrixException;
import ua.karazin.hrynevych.radar.network.memory.MemoryPart;

// Клас, що представляє штучну нейронну мережу
// і синхронізує блок пам'яті із блоком обчислень
public class Network {
	
	// Затримка оновлення даних (вважається відомою)
	private long delay = 20;
	
	// Час, що відповідає моменту запуску мережі
	private final long zeroTime;
	
	// Масив моментів часу, у які були занесені точки
	// у блок пам'яті
	private long[] times;
	
	// Блок пам'яті
	private MemoryPart mp;
	
	// блок обчислень
	private CalculationPart cp;
	
	// Стандартний конструктор нейронної мережі
	public Network() {
		zeroTime = System.currentTimeMillis();
		times = new long[3];
		mp = new MemoryPart();
		cp = new CalculationPart();
	}
	
	// Конструктор нейронної мережі, що дозволяє встановити
	// розмірність простору і об'єм блоку пам'яті
	public Network(int coordinates, int nodes) {
		zeroTime = System.currentTimeMillis();
		times = new long[nodes];
		mp = new MemoryPart(coordinates, nodes);
		cp = new CalculationPart(nodes);
	}
	
	// Метод оновлення даних - додає нову точку у блок пам'яті
	public void updateData(double ... point) throws DifferentDimensionsException {
		mp.push(point);
		addTime(System.currentTimeMillis() - zeroTime);
	}
	
	// Демонстраційний метод оновлення даних - додає нову точку
	// у блок пам'яті і ставить їй у відповідність вказаний момент часу
	public void updateDataDemo(long time, double ... point) throws DifferentDimensionsException {
		mp.push(point);
		addTime(time);
	}
	
	// Додає новий момент часу у масив моментів часу, 
	// зміщуючи при цьому значення, які в ньому зберігаються
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
	
	// Повертає імовірне положення об'єкта через
	// вказаний час після запису в пам'ять останньої точки
	public double[] getNext(long time) throws DifferentDimensionsException, EmptyMatrixException {
		double[] result = new double[mp.getCoordinates()];
		long[] extendedTimes = extendTimes(times[0] + time);
		double[][] func = convertMatrix();
		
		for (int i = 0; i < result.length; i++) {
			result[i] = cp.process(extendedTimes, func[i]);
		}
		return result;
	}
	
	// Повертає імовірне положення об'єкта у наступний момент часу,
	// який визначається затримкою оновлення даних
	public double[] getNext() throws DifferentDimensionsException, EmptyMatrixException {
		return getNext(delay);
	}
	
	// Повертає імовірне положення об'єкта у вказаний момент часу
	public double[] getNextDemo(long time) throws DifferentDimensionsException, EmptyMatrixException {
		double[] result = new double[mp.getCoordinates()];
		long[] extendedTimes = extendTimes(time);
		double[][] func = convertMatrix();
		
		for (int i = 0; i < result.length; i++) {
			result[i] = cp.process(extendedTimes, func[i]);
		}
		return result;
	}
	
	// Розширює масив моментів часу ще одним (наступним) моментом,
	// положення об'єкта в якому визначатиме нейронна мережа.
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
	
	// Метод, що створює матрицю дійсних чисел і заповнює
	// її значеннями з блока пам'яті
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
	
	// Метод, що виконує робочий цикл мережі, додаючи у пам'ять 
	// задану точку і повертаючи імовірне положення об'єкта через
	// вказаний проміжок часу
	public double[] process(long time, double ... point) throws DifferentDimensionsException, EmptyMatrixException {
		updateData(point);
		return getNext(time);
	}
	
	// Метод, що виконує робочий цикл мережі, додаючи у пам'ять
	// задану точку і повертаючи імовірне положення об'єкта у
	// момент часу, що визначається затримкою оновлення даних
	public double[] processDefault(double ... point) throws DifferentDimensionsException, EmptyMatrixException {
		updateData(point);
		return getNext();
	}
	
	// Демонстраційний метод, що виконує робочий цикл мережі, додаючи
	// у пам'ять вказану точку, прив'язану до вказаного моменту часу.
	// Повертає імовірне положення об'єкта у вказаний момент часу.
	public double[] processDemo(long absoluteTime, long pointTime, double ... point) throws DifferentDimensionsException, EmptyMatrixException {
		updateDataDemo(pointTime, point);
		return getNextDemo(absoluteTime);
	}
}
