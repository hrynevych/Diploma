package ua.karazin.hrynevych.radar.network.calculation;

import ua.karazin.hrynevych.radar.network.exceptions.DifferentDimensionsException;

// Клас, що відповідає за блок обчислень мережі
public class CalculationPart {

	// Кількість точок у пам'яті мережі
	int n;
	
	// Масив нейронів першого шару
	private Subtractor[] s;
	
	// Масив нейронів другого шару
	private Multiplier[] m;
	
	// Нейрон третього шару
	private Adder a;
	
	// Масив вагових коефіцієнтів для вхідних сигналів
	private double[] sWeights;
	
	// Масив вагових коефіцієнтів для зв'язків між першим
	// і другим шаром нейронів
	private double[] mWeights;
	
	// Масив вагових коефіцієнтів для зв'язків між другим
	// і третім шаром нейронів
	private double[] aWeights;
	
	// Конструктор, що створює блок обчислень за
	// заданою кількістю точок
	public CalculationPart(int nodes) {
		n = nodes;
		
		sWeights = new double[2 * n * (n - 1)];
		mWeights = new double[n * (n - 1)];
		aWeights = new double[n];
		s = new Subtractor[n * (n - 1)];
		m = new Multiplier[n];
		a = new Adder();
		
		for (int i = 0; i < sWeights.length; i++) {
			sWeights[i] = 1;
		}
		for (int i = 0; i < mWeights.length; i++) {
			mWeights[i] = 1;
		}
		for (int i = 0; i < s.length; i++) {
			s[i] = new Subtractor();
		}
		for (int i = 0; i < m.length; i++) {
			m[i] = new Multiplier();
		}
	}
	
	// Стандартний конструктор, що створює блок
	// обчислень для оброблення трьох точок
	public CalculationPart() {
		this(3);
	}
	
	// Метод, що заповнює масив вагових коефіцієнтів для
	// зв'язків між другим і третім шаром початковими даними,
	// які залежать від масиву моментів часу і значень певної
	// координати у ці моменти часу
	private void fillAWeights(long[] time, double[] func) throws DifferentDimensionsException {
		checkDimensions(time.length, func.length);
		for (int i = 0; i < func.length; i++) {
			double numerator = func[i];
			double denominator = 1;

			for (int j = 0; j < func.length; j++) {
				if (i == j) {
					continue;
				} else {
					denominator *= time[i] - time[j];
				}
			}
			aWeights[i] = numerator / denominator;
		}
	}
	
	// Метод, що відповідає за роботу першого шару нейронів
	private double[] layer1(long[] time) throws DifferentDimensionsException {
		double[] result = new double[s.length];
		long[] input = formInput(time);
		
		for (int i = 0; i < s.length; i++) {
			result[i] = s[i].process(input[2 * i] * sWeights[2 * i], input[2 * i + 1] * sWeights[2 * i + 1]);
		}
		return result;
	}
	
	// Метод, що відповідає за роботу другого шару нейронів
	private double[] layer2(double[] prev) {
		double[] result = new double[m.length];
		
		for (int i = 0; i < m.length; i++) {
			double[] input = new double[s.length / m.length];
			
			for (int j = 0; j < input.length; j++) {
				input[j] = prev[i * input.length + j] * mWeights[i * input.length + j];
			}
			result[i] = m[i].process(input);
		}
		return result;
	}
	
	// Метод, що відповідає за роботу третього шару нейронів
	private double layer3(double[] prev) {
		double[] input = new double[m.length];
		
		for (int i = 0; i < input.length; i++) {
			input[i] = prev[i] * aWeights[i];
		}
		return a.process(input);
	}
	
	// Метод, що перетворює масив моментів часу у форму, яку
	// може інтерпретувати блок обчислень
	private long[] formInput(long[] time) throws DifferentDimensionsException {
		int counter = 0;
		long[] result = new long[2 * s.length];
		
		checkDimensions(time.length);
		for (int i = 0; i < time.length - 1; i++) {
			for (int j = 0; j < time.length - 1; j++) {
				if (i == j) {
					continue;
				} else {
					result[counter++] = time[time.length - 1];
					result[counter++] = time[j];
				}
			}
		}
		return result;
	}
	
	// Метод, що перевіряє відповідність вхідних даних 
	// (кількість моментів часу) структурі блоку обчислень
	private void checkDimensions(int timeNodes) throws DifferentDimensionsException {
		if (timeNodes != (n + 1)) {
			throw new DifferentDimensionsException();
		}
	}
	
	// Метод, що перевіряє відповідність вхідних даних
	// (кількість моментів часу і кількість точок у пам'яті)
	// структурі блоку обчислень
	private void checkDimensions(int timeNodes, int funcNodes) throws DifferentDimensionsException {
		if ((funcNodes != timeNodes - 1) || (funcNodes != n)) {
			throw new DifferentDimensionsException();
		}
	}
	
	// Метод, що виконує робочий цикл блоку обчислень і повертає
	// імовірне значення певної координати у наступний момент часу
	public double process(long[] time, double[] func) throws DifferentDimensionsException {
		fillAWeights(time, func);
		return layer3(layer2(layer1(time)));
	}
}
