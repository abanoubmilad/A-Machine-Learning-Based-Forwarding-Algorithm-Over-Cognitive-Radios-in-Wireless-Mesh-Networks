import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class BandwidthState {

	// private float b0, b1, b2;
	// private float t0, t1, t2;
	private ArrayList<State> states;

	public BandwidthState() {
		states = new ArrayList<>(3);
	}

	// public BandwidthState(float lastBandwith, float lastTime) {
	// if(states.size()>2)
	// states.remove(0);
	// states.add(new State(lastBandwith, lastTime));
	// }

	public void update(float bandwith, float time) {
		if (states.size() > 2)
			states.remove(0);
		states.add(new State(bandwith, time));

	}

	public Float getExpectedBandwidth(float tp) {
		if (states.size() != 3)
			return null;

		State[] temp = { states.get(0), states.get(1), states.get(2) };
		Arrays.sort(temp, new Comparator<State>() {
			@Override
			public int compare(State o1, State o2) {
				return (int) (o1.getTime() - o2.getTime());
			}
		});

		float b0 = temp[0].getBandwidth();
		float t0 = temp[0].getTime();

		float b1 = temp[1].getBandwidth();
		float t1 = temp[1].getTime();

		float b2 = temp[2].getBandwidth();
		float t2 = temp[2].getTime();

		SimLog.print("****************************************************************************");
		SimLog.print("calc expected bandwidth:");
		SimLog.print("				b0:" + b0 + " t0:" + t0);
		SimLog.print("				b1:" + b1 + " t1:" + t1);
		SimLog.print("				b2:" + b2 + " t2:" + t2);

		float alpha0 = b0 / t0;
		float alpha1 = alpha0 + b1 / (t1 - t0);
		float alpha2 = alpha1 + b2 / ((t2 - t1) * (t2 - t0));

		float expexted = alpha0 + (alpha1 + alpha2 * (tp - t1)) * (tp - t0);

		SimLog.print("expected -> b:" + expexted + " t:" + tp);
		SimLog.print("****************************************************************************");
		return expexted;
	}

	private class State {
		private float bandwidth;
		private float time;

		public float getBandwidth() {
			return bandwidth;
		}

		public void setBandwidth(float bandwidth) {
			this.bandwidth = bandwidth;
		}

		public float getTime() {
			return time;
		}

		public void setTime(float time) {
			this.time = time;
		}

		public State(float bandwidth, float time) {
			this.bandwidth = bandwidth;
			this.time = time;
		}

	}
}
