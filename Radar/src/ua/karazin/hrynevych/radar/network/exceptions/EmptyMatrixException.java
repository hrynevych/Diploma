package ua.karazin.hrynevych.radar.network.exceptions;

// Виключення, яке викидається при передачі
// інформації з блока пам'яті на блок обчислень,
// якщо блок пам'яті не заповнений на 100%
// (містить null-значення)
public class EmptyMatrixException extends Exception{

	// Унікальний id номер виключення
	private static final long serialVersionUID = -2755851924220566110L;
	
	// Конструктор, що виводить відповідне
	// повідомлення на екран при викиданні
	// виключення
	public EmptyMatrixException() {
		super("Matrix is empty");
	}
}
