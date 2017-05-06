import java.util.Random;

public class Rayleigh {

	public static float sigma = 4;
	private static Random generator = new Random();

	public static float getNextBandWidth() {
		float x = generator.nextFloat();
		while (x == 0) {
			x = generator.nextFloat();
		}

		return (float) (sigma * Math.sqrt(-2 * Math.log(1-x)));
	}

	public static void calculateAverage() {
		// float temp;
		float sum = 0;

		for (int i = 0; i < 100000; i++) {
			sum += getNextBandWidth();

		}
		System.out.println(sum / 100000);
	}

	public static void main(String[] args) {
		calculateAverage();
		for (int i = 0; i < 20; i++) {
			System.out.println(getNextBandWidth());
		}
	}

}
