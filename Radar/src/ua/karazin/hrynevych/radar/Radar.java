package ua.karazin.hrynevych.radar;

import java.lang.reflect.Method;

// Клас, об'єкти якого імітують діяльність
// радарів з різними характеристиками
public class Radar {
	
	// Час, який пройшов з моменту запуску радара
	private long passed = 0;
	
	// Затримка між відправленими сигналами
	private int delay = 20;
	
	// Траєкторія руху об'єкта
	private String trajectory = "traj0";
	
	// Стандартний конструктор
	public Radar() { }
	
	// Конструктор з можливістю встановити затримку
	public Radar(int delayMillis) {
		delay = delayMillis;
	}
	
	// Конструктор з можливістю встановити траєкторію
	public Radar(String trajectoryName) {
		trajectory = trajectoryName;
	}
	
	// Конструктор з можливістю встановити затримку і траєкторію
	public Radar(int delayMillis, String trajectoryName) {
		delay = delayMillis;
		trajectory = trajectoryName;
	}
	
	// Метод, що виконує один цикл діяльності радара.
	// Після вказаної затримки метод повертає поточне
	// положення об'єкта.
	public double[] process() {
		Class<? extends Radar> c = this.getClass();
		double[] result = new double[3];
		
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			System.err.println("Delay Failed");
		}
		try {
			Method m = c.getDeclaredMethod(trajectory, long.class);
			result = (double[]) m.invoke(this, passed);
		} catch (Exception e) {
			result = traj0(passed);
		}
		passed += delay;
		return result;
	}
	
	// Метод, що повертає положення об'єкта у вказаний
	// момент часу
	public double[] processDemo(long time) {
		Class<? extends Radar> c = this.getClass();
		double[] result = new double[3];
		
		try {
			Method m = c.getDeclaredMethod(trajectory, long.class);
			result = (double[]) m.invoke(this, time);
		} catch (Exception e) {
			result = traj0(time);
		}
		return result;
	}
	
	// Метод, що повертає поточну затримку радара
	public int getDelay() {
		return delay;
	}
	
	// Блок методів, що представляють траєкторії руху
	// (може бути розширений)
	//
	// Нульова константа (об'єкт знаходиться у точці (0; 0; 0)
	public static double[] traj0(long time) {
		double[] result = new double[3];
		
		result[0] = 0;
		result[1] = 0;
		result[2] = 0;
		return result;
	}
	
	// Константа (об'єкт знаходиться у певній точці простору)
	public static double[] traj1(long time) {
		double[] result = new double[3];
		
		result[0] = 1;
		result[1] = 0;
		result[2] = 2;
		return result;
	}
	
	// Рух по прямій вздовж однієї з осей координат зі
	// сталою швидкістю
	public static double[] traj2(long time) {
		double[] result = new double[3];
		
		result[0] = 0.5 * time;
		result[1] = 1;
		result[2] = 2;
		return result;
	}
	
	// Рух по довільній прямій зі сталою швидкістю
	public static double[] traj3(long time) {
		double[] result = new double[3];
		
		result[0] = 8 + 0.4 * time;
		result[1] = 0.3 * time;
		result[2] = time;
		return result;
	}
	
	// Рух по прямій вздовж однієї з осей координат
	// зі сталим прискоренням
	public static double[] traj4(long time) {
		double[] result = new double[3];
		
		result[0] = 1.25E-5 * time * time;
		result[1] = 0;
		result[2] = 0;
		return result;
	}
	
	// Рух по балістичній траєкторії (параболі)
	// з прискоренням
	public static double[] traj5(long time) {
		double[] result = new double[3];
		
		result[0] = 0.1 * time;
		result[1] = 0;
		result[2] = 1.25E-5 * time * time;
		return result;
	}
	
	// Демонстраційна траєкторія
	// Поліноміальна траєкторія, на якій об'єкт досягає
	// надмірно високої швидкості (284 км/с).
	public static double[] traj6(long time) {
		double[] result = new double[3];
		
		result[0] = time;
		result[1] = Math.pow(time, 2);
		result[2] = Math.pow(time, 3) / 1000;
		return result;
	}
	
	// Поліноміальна траєкторія, що імітує зліт
	// реактивного літака
	public static double[] traj7(long time) {
		double[] result = new double[3];
		
		result[0] = 0.25 * time;
		result[1] = 1.25E-5 * Math.pow(time, 2);
		result[2] = 2E-11 * Math.pow(time, 3);
		return result;
	}
	
	// Рух по балістичній траєкторії (параболі)
	// з уповільненням
	public static double[] traj8(long time) {
		double[] result = new double[3];
		
		result[0] = 0.1 * time;
		if (time < 0) {
			result[1] = -Math.pow(-time, 0.5);
		} else {
			result[1] = Math.pow(time, 0.5);
		}
		result[2] = 1;
		return result;
	}
	
	// Рух по синусоїді
	public static double[] traj9(long time) {
		double[] result = new double[3];
		
		result[0] = 0.1 * time;
		result[1] = 0;
		result[2] = 100 * Math.sin(time / 200.0);
		return result;
	}
	
	// Рух по косинусоїді
	public static double[] traj10(long time) {
		double[] result = new double[3];
		
		result[0] = 0.1 * time;
		result[1] = 0;
		result[2] = 100 * Math.cos(time / 200.0);
		return result;
	}
	
	// Рух по колу (три оберти, після чого - вихід
	// на пряму)
	public static double[] traj11(long time) {
		double[] result = new double[3];
		
		if (time < 1000 * 6 * Math.PI) {
			result[0] = 500 * Math.sin(time / 1000.0);
			result[1] = 500 * Math.cos(time / 1000.0);
		} else {
			result[0] = 0.5 * time - 500 * 6 * Math.PI;
			result[1] = 500;
		}
		result[2] = 0;
		return result;
	}
	
	// Рух по спіралі від початку коодинат
	public static double[] traj12(long time) {
		double[] result = new double[3];
		
		result[0] = 0.25 * time * Math.cos(time / 1000.0);
		result[1] = 0.25 * time * Math.sin(time / 1000.0);
		result[2] = 0;
		return result;
	}
	
	// Гвинтовий рух
	public static double[] traj13(long time) {
		double[] result = new double[3];
		
		result[0] = 500 * Math.sin(time / 1000.0);
		result[1] = 500 * Math.cos(time / 1000.0);
		result[2] = 0.5 * time;
		return result;
	}
	
	// Гіперболічна траєкторія, що імітує посадку літака
	public static double[] traj14(long time) {
		double[] result = new double[3];
		
		result[0] = 0.5 * time;
		if (time < 0) {
			result[1] = -0.5 * time + 10;
		} else {
			result[1] = 5E-3 / (time + 0.1);
		}
		result[2] = 0;
		return result;
	}
	
	// Рух по експоненціальній траєкторії з прискоренням
	public static double[] traj15(long time) {
		double[] result = new double[3];
		
		result[0] = 0.5 * time;
		result[1] = 0;
		result[2] = Math.exp(2E-4 * time);
		return result;
	}
	
	// Рух по експоненціальній траєкторії з уповільненням
	public static double[] traj16(long time) {
		double[] result = new double[3];
			
		result[0] = 0.5 * time;
		if (time < 0) {
			result[1] = 0.5 * time + Math.log(0.1) / 20;
		} else {
			result[1] = Math.log(time + 0.1) / 20;
		}
		result[2] = 0;
		return result;
	}
	
	// Рух по тангенсоїді з прискоренням
	public static double[] traj17(long time) {
		double[] result = new double[3];
		
		result[0] = 0.5 * time;
		if (time <= -15000 * Math.PI) {
			result[1] = Double.NEGATIVE_INFINITY;
		} else if (time >= 15000 * Math.PI) {
			result[1] = Double.POSITIVE_INFINITY;
		} else {
			result[1] = Math.tan(time / 30000.0);
		}
		result[2] = 0;
		return result;
	}
	
	// Рух по тангенсоїді з уповільненням
	public static double[] traj18(long time) {
		double[] result = new double[3];
		
		result[0] = 0.5 * time;
		result[1] = 0;
		result[2] = 50 * Math.atan(time / 100.0);
		return result;
	}
	
	// Рух по сигмоїді (логістична функція)
	public static double[] traj19(long time) {
		double[] result = new double[3];
		
		result[0] = 0.5 * time;
		result[1] = 0;
		result[2] = 100 / (1 + Math.exp(-time / 200.0));
		return result;
	}
	
	// Рух по пороговій траєкторії: три ділянки
	// з прямолінійним рухом з двома змінами траєкторії
	public static double[] traj20(long time) {
		double[] result = new double[3];
		
		if (time < 100) {
			result[0] = 0.5 * time;
			result[2] = 0;
		} else if (time > 1000) {
			result[0] = 0.5 * time - 450;
			result[2] = 50;
		} else {
			result[0] = 50;
			result[2] = 5 * time / 90.0 - 50 / 9.0;
		}
		result[1] = 100;
		return result;
	}
	
	// Зубчаста траєкторія, що імітує маневри вертольота
	public static double[] traj21(long time) {
		double[] result = new double[3];
		double shift = ((time % 20000) + 20000) % 20000;

		if (shift < 10000) {
			result[0] = 0.03 * time - 300 * Math.floor(time / 20000.0);
			result[2] = 0.02 * time - 400 * Math.floor(time / 20000.0);
		} else {
			result[0] = 300 * Math.ceil(time / 20000.0);
			result[2] = -0.02 * time + 400 * Math.ceil(time / 20000.0);
		}
		result[1] = 0;
		return result;
	}
	
	// Траєкторія, що складається з декількох поліноміальних
	// ділянок та точки, у якій об'єкт зупиняється на деякий час
	public static double[] traj22(long time) {
		double[] result = new double[3];
		
		if (time < 5000) {
			result[0] = 0.01 * time;
			result[2] = 0;
		} else if (time < 7000) {
			result[0] = 0.01 * time;
			result[2] = 1E-5 * Math.pow(time, 2) - 0.1 * time + 250;
		} else if (time < 12000) {
			result[0] = 70;
			result[2] = 40;
		} else {
			result[0] = - 0.03 * time + 430;
			result[2] = 0.02 * time - 200;
		}
		result[1] = 0;
		return result;
	}
	
	// Зміна однієї поліноміальної траєкторії на іншу
	// на великій швидкості
	public static double[] traj23(long time) {
		double[] result = new double[3];
		
		result[0] = 0.1 * time;
		if (time < 10000) {
			result[1] = (-63.6 * time + 737124) / 182;
		} else {
			result[1] = Math.pow(0.1 * time - 1318, 2) / 182;
		}
		result[2] = 0;
		return result;
	}
	
	// Дві зміни траєкторії з функціональної (експоненціальна) на 
	// поліноміальну (параболічна) і навпаки
	public static double[] traj24(long time) {
		double[] result = new double[3];
		
		result[0] = 0.1 * time;
		result[1] = 0;
		if (time < 2000) {
			result[2] = 50 * Math.exp(0.01 * time - 20);
		} else if (time < 4000){
			result[2] = -2.5E-4 * Math.pow(time, 2) + 1.5 * time - 1950;
		} else {
			result[2] = 50 * Math.exp(-0.01 * time + 40);
		}
		return result;
	}
	
	// Рух по дугам кіл різних радіусів зі сталою швидкістю
	public static double[] traj25(long time) {
		double[] result = new double[3];
		
		if (time < 0) {
			result[0] = 0.1 * time;
			result[1] = 500;
		} else if (time < 2000) {
			result[0] = 500 * Math.sin(Math.PI * time / 4000);
			result[1] = 500 * Math.cos(Math.PI * time / 4000);
		} else if (time < 4000) {
			result[0] = 250 * Math.cos(Math.PI * time * 5E-4) + 750;
			result[1] = 250 * Math.sin(Math.PI * time * 5E-4);
		} else if (time < 8000) {
			result[0] = -1000 * Math.sin(Math.PI * time * 1.25E-4) + 2000;
			result[1] = -1000 * Math.cos(Math.PI * time * 1.25E-4);
		} else if (time < 10000) {
			result[0] = 250 * Math.sin(Math.PI * time * 5E-4) + 2000;
			result[1] = 250 * Math.cos(Math.PI * time * 5E-4) + 750;
		} else if (time < 11500) {
			result[0] = 250 * Math.sin(Math.PI * time * 5E-4) + 2000;
			result[1] = -250 * Math.cos(Math.PI * time * 5E-4) + 250;
		} else {
			result[0] = 6.25E-2 * Math.pow(2, 0.5) * Math.PI * time + 2000 - 718.75 * Math.pow(2, 0.5) * Math.PI - 125 * Math.pow(2, 0.5);
			result[1] = -6.25E-2 * Math.pow(2, 0.5) * Math.PI * time + 250 + 718.75 * Math.pow(2, 0.5) * Math.PI - 125 * Math.pow(2, 0.5);
		}
		result[2] = 0;
		return result;
	}
}
