package ua.karazin.hrynevych.radar.network.memory;

import ua.karazin.hrynevych.radar.network.exceptions.DifferentDimensionsException;

// Клас, що відповідає за блок обчислень мережі
public class MemoryPart {
	
	// Матриця LSTM-модулів блоку
	private MemoryNeuron[][] neurons;
	
	// Розмірність простору
	private int coord;
	
	// Кількість точок у пам'яті
	private int nodes;
	
	// Конструктор, що створює блок пам'яті
	// вказаного розміру
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
	
	// Стандартний конструктор, що створює
	// блок пам'яті з дев'яти нейронів (3х3)
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
	
	// Метод, що дозволяє додати нову точку трьохвимірного простору
	// у блок пам'яті
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
	
	// Метод, що доволяє додати нову точку у блок пам'яті
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
	
	// Перетворення трьох окремих координат точки у масив
	private static double[] toArray(double x, double y, double z) {
		double[] result = new double[3];
		
		result[0] = x;
		result[1] = y;
		result[2] = z;
		return result;
	}
	
	// Метод, що перевіряє відповідність розмірності
	// нової точки структурі блоку пам'яті
	private void checkDimensions(int pointCoords) throws DifferentDimensionsException {
		if (pointCoords != coord) {
			throw new DifferentDimensionsException();
		}
	}
	
	// Метод, що дозволяє отримати значення з пам'яті
	// LSTM-модулів у вигляді матриці
	public Double[][] getMatrix() {
		Double[][] result = new Double[coord][nodes];
		
		for (int i = 0; i < coord; i++) {
			for (int j = 0; j < nodes; j++) {
				result[i][j] = neurons[i][j].outputGate();
			}
		}
		return result;
	}
	
	// Метод, що дозволяє отримати розмірність простору
	public int getCoordinates() {
		return coord;
	}
	
	// Метод, що дозволяє отримати об'єм блока пам'яті
	// (максимальну кількість точок, які він може зберігати)
	public int getNodes() {
		return nodes;
	}
	
	// Метод, що додає нову трьохвимірну точку у блок пам'яті
	// і повертає матрицю значеннь, що зберігаються у блоці
	public Double[][] process(double x, double y, double z) throws DifferentDimensionsException {
		push(x, y, z);
		return getMatrix();
	}
	
	// Метод, що додає нову точку у блок пам'яті
	// і повертає матрицю значеннь, що зберігаються у блоці
	public Double[][] process(double[] newPoint) throws DifferentDimensionsException {
		push(newPoint);
		return getMatrix();
	}
}
