package ua.karazin.hrynevych.comparing.feedforward;

public class Grades {
	
	private static String[] grades = {"utterly disgusting", "disgusting", "very bad", "bad", "not that bad", "undesirable",
			"tolerable", "neutral", "okay", "fine", "not that good", "good", "very good", "cool", "pretty cool"};
	
	public static int getNumber(String grade) {
		for (int i = 0; i < grades.length; i++) {
			if (grade.equals(grades[i])) {
				return i;
			}
		}
		return -1;
	}
	
	public static String getGrade(int number) {
		return grades[number];
	}
}
