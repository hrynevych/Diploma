package ua.karazin.hrynevych.radar.network.memory;

// Клас, що представляє LSTM-модулі,
// з яких складається блок пам'яті
public class MemoryNeuron {
	
	// Значення, що зберігається у
	// внутрішній пам'яті модуля
	private Double storedValue;
	
	// Вхідний вентиль
	public void inputGate(Double value) {
		storedValue = value;
	}
	
	// Вентиль забування
	public void forgetGate() {
		storedValue = null;
	}
	
	// Вихідний вентиль
	public Double outputGate() {
		return storedValue;
	}
}
