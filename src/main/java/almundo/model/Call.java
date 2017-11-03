package almundo.model;

public class Call {

	private int number;
	private int min;
	private int max;
	
	public Call(int min, int max, int number) {
		this.number = number;
		this.min = min;
		this.max = max;
	}
	
	public int getCallDuration() {
		return (int)(Math.random() * ((max - min) + 1)) + min;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
}
