package ua.karazin.hrynevych.radar;

import java.util.Scanner;

import ua.karazin.hrynevych.radar.network.Network;
import ua.karazin.hrynevych.radar.network.exceptions.DifferentDimensionsException;
import ua.karazin.hrynevych.radar.network.exceptions.EmptyMatrixException;

// Демонстраційний режим
// Дозволяє обрати траєкторію руху об'єкта, додавати у пам'ять
// довільні точки і отримувати імовірне положення об'єкта у
// довільний момент часу.
public class DemonstrationMode {
	
	// Розмірність простору
	public static final int DIMENSIONS = 3;
	
	// Кількість точок, що зберігаються у пам'яті
	public static final int MEMORY_CAPACITY = 3;
	
	// Ключове слово, що зупиняє роботу мережі
	public static final String STOP_WORD = "stop";
	
	// Ключове слово, що дозволяє пропустити операцію
	// додавання нової точки у пам'ять
	public static final String SKIP_WORD = "skip";
	
	// Сканер для зчитування інформації з консолі
	private static Scanner in = new Scanner(System.in);
	
	// Метод, що відповідає за інтерфейс і реалізацію демонстраційного режиму
	public static void main(String[] args) throws DifferentDimensionsException, EmptyMatrixException {
		Network n = new Network();
		Radar r = null;
		String trajectory = null;
		
		System.out.println("Welcome to Radar Demo!\n");
		System.out.print("Enter the trajectory (\"traj\" + number, e.g. \"traj0\"): ");
		if (in.hasNextLine()) {
			trajectory = in.nextLine();
		}
		System.out.print("Enter the delay (in millis or press Enter for default): ");
		if (in.hasNextLine()) {
			String input = in.nextLine();
			if (input.isEmpty()) {
				r = new Radar(trajectory);
			} else {
				r = new Radar(Integer.parseInt(input), trajectory);
			}
		}
		System.out.println();
		
		for (int i = 0; i < MEMORY_CAPACITY; i++) {
			double[] point;

			System.out.println("Adding point by the time");
			System.out.print("Enter the time (in millis): ");
			if (in.hasNextLine()) {
				long time = Long.parseLong(in.nextLine());
				point = r.processDemo(time);
				n.updateDataDemo(time, point);
				System.out.println("Point " + pointToString(point) + " added successfully\n");
			}
		}

		while (true) {
			System.out.println("Getting predicted point");
			System.out.print("Enter the time (in millis, \"" + STOP_WORD + "\" to finish the work): ");
			if (in.hasNextLine()) {
				String input = in.nextLine();
				if (input.isEmpty() || input.equals(STOP_WORD)) {
					break;
				}
				long time = Long.parseLong(input);
				double[] point = n.getNextDemo(time);
				System.out.println("At time " + time + " estimated position of the target is " + pointToString(point));
				System.out.println("At time " + time + " actual position of the target is    " + pointToString(r.processDemo(time)));
				System.out.println("Error, %: " + pointToString(countError(point, r.processDemo(time))) + "\n");
			}
			
			System.out.println("Adding point by the time");
			System.out.print("Enter the time (in millis, \"" + SKIP_WORD + "\" to predict new point): ");
			if (in.hasNextLine()) {
				String input = in.nextLine();
				if (input.isEmpty() || input.equals(SKIP_WORD)) {
					System.out.println();
					continue;
				}
				long time = Long.parseLong(input);
				double[] point = r.processDemo(time);
				n.updateDataDemo(time, point);
				System.out.println("Point " + pointToString(point) + " added successfully\n");
			}
		}
	}
	
	// Метод, що представляє масив координат точки у
	// зручному строковому форматі
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
	
	// Метод, що підраховує похибку по кожній
	// координаті у відсотках
	public static double[] countError(double[] estimated, double[] actual) throws DifferentDimensionsException {
		if (estimated.length != actual.length) {
			throw new DifferentDimensionsException();
		}
		double[] error = new double[estimated.length];
		
		for (int i = 0; i < error.length; i++) {
			if (actual[i] != 0) {
				error[i] = Math.abs(estimated[i] - actual[i]) / actual[i] * 100;
			} else {
				error[i] = Math.abs(estimated[i] - actual[i]);
			}
		}
		return error;
	}
}
