package ua.karazin.hrynevych.radar.network.exceptions;

// Виключення, яке викидається, якщо розмірність
// вхідних даних не співпадає зі структурою
// елементів нейронної мережі
public class DifferentDimensionsException extends Exception{

	// Унікальний id номер виключення
	private static final long serialVersionUID = 4782543172975658673L;

	// Конструктор, що виводить відповідне
	// повідомлення на екран при викиданні
	// виключення
	public DifferentDimensionsException() {
		super("Different dimensions");
	}
}
