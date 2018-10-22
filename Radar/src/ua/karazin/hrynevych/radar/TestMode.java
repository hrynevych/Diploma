package ua.karazin.hrynevych.radar;

import ua.karazin.hrynevych.radar.network.Network;
import ua.karazin.hrynevych.radar.network.exceptions.DifferentDimensionsException;
import ua.karazin.hrynevych.radar.network.exceptions.EmptyMatrixException;

// Тестовий режим
// Дозволяє установити траєкторію руху об'єкта та кількість
// робочих циклів, відслідкувати виконання мережею відповідної
// кількості ітерацій, визначити похибку між дійсними і
// передбаченими координатами
public class TestMode {
	
	// Траєкторія руху об'єкта
	public static final String TRAJECTORY = "traj2";
	
	// Розмірність простору
	public static final int DIMENSIONS = 3;
	
	// Кількість точок, що зберігаються в пам'яті
	public static final int MEMORY_CAPACITY = 3;
	
	// Кількість робочих циклів, які повинна відпрацювати мережа
	public static final int ITERATIONS = 10;
	
	// Метод, що відповідає за інтерфейс і реалізацію тестового режиму
	public static void main(String[] args) throws DifferentDimensionsException, EmptyMatrixException {
		Network n = new Network();
		Radar r = new Radar(TRAJECTORY);
		
		double[][] actual = new double[ITERATIONS][DIMENSIONS];
		double[][] predicted = new double[ITERATIONS][DIMENSIONS];
		
		for (int i = 0; i < MEMORY_CAPACITY; i++) {
			n.updateData(r.process());
		}
		for (int i = 0; i < ITERATIONS; i++) {
			if (i == 0) {
				predicted[i] = n.getNext(r.getDelay());
			} else {
				actual[i - 1] = r.process();
				predicted[i] = n.process(r.getDelay(), actual[i-1]);
			}
			if (i == ITERATIONS - 1) {
				actual[i] = r.process();
			}
		}
		
		for (int i = 0; i < ITERATIONS; i++) {
			System.out.println("Iteration " + (i + 1) + ": ");
			System.out.print("Actual:    (");
			for (int j = 0; j < DIMENSIONS; j++) {
				if (j == DIMENSIONS - 1) {
					System.out.println(actual[i][j] + ")");
				} else {
					System.out.print(actual[i][j] + "; ");
				}
			}
			System.out.print("Predicted: (");
			for (int j = 0; j < DIMENSIONS; j++) {
				if (j == DIMENSIONS - 1) {
					System.out.println(predicted[i][j] + ")");
				} else {
					System.out.print(predicted[i][j] + "; ");
				}
			}
			System.out.print("Error, %:  (");
			for (int j = 0; j < DIMENSIONS; j++) {
				double error;
				
				if (actual[i][j] != 0) {
					error = Math.abs(predicted[i][j] - actual[i][j]) / actual[i][j] * 100;
				} else {
					error = Math.abs(predicted[i][j] - actual[i][j]);
				}
				
				if (j == DIMENSIONS - 1) {
					System.out.println(error + ")\n");
				} else {
					System.out.print(error + "; ");
				}
			}
		}
	}
}
