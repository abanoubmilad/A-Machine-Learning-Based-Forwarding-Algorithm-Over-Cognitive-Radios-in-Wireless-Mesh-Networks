
public class Message {
	public static final int TYPE_HELLO = 0;
	public static final int TYPE_DATA = 1;
	public static final float MSG_PAYLOAD_SIZE = 20;

	private int type;
	private int seqNum;
	private String sourceIP;
	private String destinationIP;
	private String carrierIP;
	private float carrierTime;
	private float createdTime;

	private GPS sourceGPS;
	private GPS destinationGPS;
	private GPS carrierGPS;

	private String payLoad;

	// private int timeStamp;

	//
	// public int getTimeStamp() {
	// return timeStamp;
	// }
	//
	// public void setTimeStamp(int timeStamp) {
	// this.timeStamp = timeStamp;
	// }

	public String getCarrierIP() {
		return carrierIP;
	}

	public float getCarrierTime() {
		return carrierTime;
	}

	public void setCarrierTime(float carrierTime) {
		this.carrierTime = carrierTime;
	}

	public float getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(float createdTime) {
		this.createdTime = createdTime;
	}

	public void setCarrierIP(String carrierIP) {
		this.carrierIP = carrierIP;
	}

	public GPS getCarrierGPS() {
		return carrierGPS;
	}

	public void setCarrierGPS(GPS carrierGPS) {
		this.carrierGPS = carrierGPS;
	}

	public String getPayLoad() {
		return payLoad;
	}

	public void setPayLoad(String payLoad) {
		this.payLoad = payLoad;
	}

	public Message(int type, int seqNum, String sourceIP, GPS sourceGPS, float createdTime, String destinationIP,
			GPS destinationGPS, String payLoad) {
		this.type = type;
		this.seqNum = seqNum;
		this.sourceIP = sourceIP;
		this.sourceGPS = sourceGPS;
		this.createdTime = createdTime;
		this.destinationIP = destinationIP;
		this.destinationGPS = destinationGPS;
		this.carrierIP = sourceIP;
		this.carrierGPS = sourceGPS;
		this.carrierTime = createdTime;
		this.payLoad = payLoad;
	}
	public Message(Message msg) {
		this.type = msg.getType();
		this.seqNum = msg.getSeqNum();
		this.sourceIP = msg.getSourceIP();
		this.sourceGPS = msg.getSourceGPS();
		this.createdTime = msg.getCreatedTime();
		this.destinationIP = msg.getDestinationIP();
		this.destinationGPS = msg.getDestinationGPS();
		this.carrierIP = msg.getCarrierIP();
		this.carrierGPS = new GPS(msg.getCarrierGPS().getX(), msg.getCarrierGPS().getY());
		this.carrierTime = msg.getCarrierTime();
		this.payLoad = msg.getPayLoad();
	}

	// public Message(int type, int seqNum, String sourceIP, GPS sourceGPS,
	// String destinationIP, GPS destinationGPS,
	// String carrierIP, GPS carrierGPS, String payLoad, int timeStamp) {
	// this.type = type;
	// this.seqNum = seqNum;
	// this.sourceIP = sourceIP;
	// this.sourceGPS = sourceGPS;
	// this.destinationIP = destinationIP;
	// this.destinationGPS = destinationGPS;
	// this.carrierIP = carrierIP;
	// this.carrierGPS = carrierGPS;
	// this.payLoad = payLoad;
	// this.timeStamp=timeStamp;
	// }

	public int getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(int seqNum) {
		this.seqNum = seqNum;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public String getDestinationIP() {
		return destinationIP;
	}

	public void setDestinationIP(String destinationIP) {
		this.destinationIP = destinationIP;
	}

	public GPS getSourceGPS() {
		return sourceGPS;
	}

	public void setSourceGPS(GPS sourceGPS) {
		this.sourceGPS = sourceGPS;
	}

	public static int getTypeHello() {
		return TYPE_HELLO;
	}

	public static int getTypeData() {
		return TYPE_DATA;
	}

	public GPS getDestinationGPS() {
		return destinationGPS;
	}

	public void setDestinationGPS(GPS destinationGPS) {
		this.destinationGPS = destinationGPS;
	}

	public Message getCloned(Message msg) {

		return new Message(msg.getType(), msg.getSeqNum(), msg.getSourceIP(), msg.getSourceGPS(), msg.getCreatedTime(),
				msg.getDestinationIP(), msg.getDestinationGPS(), msg.getPayLoad());

	}
}
