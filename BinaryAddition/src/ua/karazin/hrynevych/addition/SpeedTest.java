package ua.karazin.hrynevych.addition;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class SpeedTest {
	
	private static final int ITERATIONS = 100;
	
	private static final int NUMBER_LENGTH = 8;
	
	private static final InputStream STD_IN = System.in;
	
	private static final PrintStream STD_OUT = System.out;
	
	private static final String ENCODING = "Cp1251";
	
	private static ua.karazin.hrynevych.addition.feedforward.Network ff;
	
	private static ua.karazin.hrynevych.addition.recurrent.Network rec;

	public static void main(String[] args) throws UnsupportedEncodingException {
		long ffTime;
		long recTime;
		long ffMemory;
		long recMemory;
		String input = getInput(ITERATIONS, NUMBER_LENGTH);
		
		System.setIn(new ByteArrayInputStream(input.getBytes(ENCODING)));
		System.setOut(new PrintStream(new ByteArrayOutputStream()));
		
		ffMemory = Runtime.getRuntime().freeMemory();
		ffTime = System.nanoTime();
		ff = new ua.karazin.hrynevych.addition.feedforward.Network();
		for (int i = 0; i < ITERATIONS; i++) {
			ff.process();
		}
		ffTime = System.nanoTime() - ffTime;
		ffMemory = ffMemory - Runtime.getRuntime().freeMemory();
		
		System.setIn(new ByteArrayInputStream(input.getBytes(ENCODING)));
		
		recMemory = Runtime.getRuntime().freeMemory();
		recTime = System.nanoTime();
		rec = new ua.karazin.hrynevych.addition.recurrent.Network();
		for (int i = 0; i < ITERATIONS; i++) {
			rec.process();
		}
		recTime = System.nanoTime() - recTime;
		recMemory = recMemory - Runtime.getRuntime().freeMemory();
		
		System.setIn(STD_IN);
		System.setOut(STD_OUT);
		
		System.out.println("TIME");
		System.out.println("feedforward: " + ffTime);
		System.out.println("recurrent:   " + recTime);
		System.out.println("MEMORY");
		System.out.println("feedforward: " + ffMemory);
		System.out.println("recurrent:   " + recMemory);
	}
	
	public static String getInput(int iterations, int numberLength) {
		StringBuffer number;
		StringBuffer result = new StringBuffer();
		
		for (int i = 0; i < 2 * iterations; i++) {
			number = new StringBuffer();
			for (int j = 0; j < numberLength; j++) {
				if (Math.random() > 0.5) {
					number.append(1);
				} else {
					number.append(0);
				}
			}
			result.append(number);
			result.append(System.lineSeparator());
		}
		return result.toString().trim();
	}
}
