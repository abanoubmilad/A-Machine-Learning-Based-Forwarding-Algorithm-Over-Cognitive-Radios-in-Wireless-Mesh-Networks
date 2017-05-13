import java.util.Random;

public class Timer {

	private float passed;
	private Random generator;

	private static Timer timer;

	private Timer(){
		generator = new Random();
		passed = 0;
	}
	public static Timer getInstance() {
		if (timer == null)
			timer = new Timer();
		return timer;
	}

	public float getTime() {
		float x = generator.nextFloat();
		while (x == 0) {
			x = generator.nextFloat();
		}
		passed += x * 100;
		return passed;
	}

	// public static void calculateAverage() {
	// // float temp;
	// float sum = 0;
	//
	// for (int i = 0; i < 100000; i++) {
	// sum += getNextBandWidth();
	//
	// }
	// System.out.println(sum / 100000);
	// }
	//
	// public static void main(String[] args) {
	// calculateAverage();
	// for (int i = 0; i < 20; i++) {
	// System.out.println(getNextBandWidth());
	// }
	// }

}
