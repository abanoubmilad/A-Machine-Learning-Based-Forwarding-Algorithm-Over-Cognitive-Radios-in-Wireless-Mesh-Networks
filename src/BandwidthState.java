
public class BandwidthState {

	private float b0, b1, b2;
	private float t0, t1, t2;

	public BandwidthState() {
		b0 = 0;
		t0 = 0;
		b1 = 0;
		t1 = 0;
		b2 = 0;
		t2 = 0;
	}

	
	public BandwidthState(float lastBandwith, float lastTime) {
		b0 = 0;
		t0 = 0;
		b1 = 0;
		t1 = 0;
		this.b2 = lastBandwith;
		this.t2 = lastTime;
	}

	public void update(float newBandwidth, float time) {
		b0 = b1;
		t0 = t1;

		b1 = b2;
		t1 = t2;

		b2 = newBandwidth;
		t2 = time;

	}

	public float getExpectedBandwidth(float tp) {
		float alpha0 = b0 / t0;
		float alpha1 = alpha0 + b1 / (t1 - t0);
		float alpha2 = alpha1 + b2 / ((t2 - t1) * (t2 - t0));

		return alpha0 + (alpha1 + alpha2 * (tp - t1)) * (tp - t0);
	}

}
