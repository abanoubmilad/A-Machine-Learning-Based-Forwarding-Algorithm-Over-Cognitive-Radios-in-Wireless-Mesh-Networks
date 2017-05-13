import java.util.ArrayList;
import java.util.HashMap;

public class Node {
	private GPS gps;
	private String ip;

	private ArrayList<Mesh> meshes;
	private ArrayList<AbstractNode> neighbours;
	private HashMap<String, BandwidthState> ipStateMap;
	private HashMap<String, Integer> seqStateMap;

	private void initialize() {
		meshes = new ArrayList<>();
		neighbours = new ArrayList<>();
		ipStateMap = new HashMap<>();
		seqStateMap = new HashMap<>();
	}

	public Node(String ip, GPS gps) {
		this.ip = ip;
		this.gps = gps;
		initialize();
	}

	public void addMesh(Mesh mesh) {
		meshes.add(mesh);
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	public ArrayList<AbstractNode> getNeighbours() {
		return neighbours;
	}

	public void setNeighbours(ArrayList<AbstractNode> neighbours) {
		this.neighbours = neighbours;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void floodMessage(Message msg) {
		for (Mesh mesh : meshes) {
			mesh.flood(msg);
		}
	}

	public void floodMessageWithGPS(Message msg) {
		for (Mesh mesh : meshes) {
			mesh.floodWithGPS(msg);
		}
	}

	public void sendHello() {
		floodMessage(new Message(Message.TYPE_HELLO, 0, ip, gps, Timer.getInstance().getTime(), null, null, null));
	}

	public void sendMessageWithGPS(Message msg) {
		floodMessageWithGPS(msg);
	}

	public void receive(Message msg, float bandwidth, float time) {
		switch (msg.getType()) {
		case Message.TYPE_HELLO:
			boolean flag = false;
			for (AbstractNode abstractNode : neighbours) {
				if (abstractNode.getIp().equals(msg.getCarrierIP())) {
					flag = true;
					break;
				}
			}
			if (!flag)
				neighbours.add(new AbstractNode(msg.getCarrierIP(), msg.getCarrierGPS()));
			BandwidthState state = ipStateMap.get(msg.getCarrierIP());
			if (state == null) {
				state = new BandwidthState();
				ipStateMap.put(msg.getCarrierIP(), state);
			}
			state.update(bandwidth, time);

			SimLog.print("node ip:" + ip + " received hello from node ip:" + msg.getCarrierIP() + " , gps:(x="
					+ msg.getCarrierGPS().getX() + ",y=" + msg.getCarrierGPS().getY() + ") bandwidth:" + bandwidth
					+ " time:" + time);
			break;
		case Message.TYPE_DATA:
			state = ipStateMap.get(msg.getCarrierIP());
			if (state == null) {
				state = new BandwidthState();
				ipStateMap.put(msg.getCarrierIP(), state);
			}
			state.update(bandwidth, time);

			Integer lastSeqNum = seqStateMap.get(msg.getCarrierIP());
			if (lastSeqNum != null && msg.getSeqNum() <= lastSeqNum)
				return;
			seqStateMap.put(msg.getCarrierIP(), msg.getSeqNum());

			if (msg.getDestinationIP().equals(ip)) {
				deliverMsg(msg);
			} else {
				msg.setCarrierTime(time);
				if (msg.getDestinationGPS() != null)
					floodMessageWithGPS(msg);

				else

					floodMessage(msg);
			}
			break;

		default:
			break;
		}
	}

	private void deliverMsg(Message msg) {
		SimLog.print(" ");
		SimLog.print("-----------------------------------------------------------------------------");
		SimLog.print(
				"node ip:" + ip + " , gps:(x=" + msg.getDestinationGPS().getX() + ",y=" + msg.getDestinationGPS().getY()
						+ ")" + " received a data msg '" + msg.getPayLoad() + "' from node ip:" + msg.getSourceIP()
						+ " , gps:(x=" + msg.getSourceGPS().getX() + ",y=" + msg.getSourceGPS().getY() + ")");
		SimLog.print("-----------------------------------------------------------------------------");
		SimLog.print(" ");

	}

}
