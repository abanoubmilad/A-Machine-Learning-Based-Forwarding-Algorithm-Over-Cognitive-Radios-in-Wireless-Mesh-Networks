public class AbstractNode {
	private GPS gps;
	private String ip;

	
	public AbstractNode(String ip, GPS gps) {
		this.ip = ip;
		this.gps = gps;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
