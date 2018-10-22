package ua.karazin.hrynevych.comparing.feedforward;

public class Neuron {
	
	/*
	 * 0 - сеть выводит то чему обучена
	 * 0.5 - среднее между значением в памяти и новым
	 * 1 - сеть выводит новые данные
	 */
	private final double CORRECTION_STEP = 1;

	public String process(String input) {
		String[] inputParts;
		String output = null;
		
		if (input.endsWith(" is")) {
			inputParts = input.split(" ");
			output = input + " " + MapContainer.getValue(inputParts[0].toLowerCase());
		} else {
			int oldGrade;
			int newGrade;
			int outputGrade;
			
			inputParts = input.split(" is ");
			oldGrade = Grades.getNumber(MapContainer.getValue(inputParts[0].toLowerCase()));
			newGrade = Grades.getNumber(inputParts[1].toLowerCase());
			outputGrade = calculateGrade(oldGrade, newGrade);
			output = inputParts[0] + " is " + Grades.getGrade(outputGrade);
		}
		return output;
	}
	
	public int calculateGrade(int oldGrade, int newGrade) {
		double correction = CORRECTION_STEP * (oldGrade - newGrade);
		int grade = oldGrade - (int) (correction);
		
		if ((grade == oldGrade) && (correction != 0)) {
			grade = newGrade;
		}
		return grade;
	}
}
