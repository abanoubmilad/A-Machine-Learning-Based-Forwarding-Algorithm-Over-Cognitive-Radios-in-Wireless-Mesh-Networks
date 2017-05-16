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

	public ArrayList<Mesh> getMeshes() {
		return meshes;
	}

	public void setMeshes(ArrayList<Mesh> meshes) {
		this.meshes = meshes;
	}

	public HashMap<String, BandwidthState> getIpStateMap() {
		return ipStateMap;
	}

	public void setIpStateMap(HashMap<String, BandwidthState> ipStateMap) {
		this.ipStateMap = ipStateMap;
	}

	public HashMap<String, Integer> getSeqStateMap() {
		return seqStateMap;
	}

	public void setSeqStateMap(HashMap<String, Integer> seqStateMap) {
		this.seqStateMap = seqStateMap;
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
			mesh.floodWithGPSOptimal(msg);
		}
	}

	public void sendHello() {
		floodMessage(new Message(Message.TYPE_HELLO, 0, ip, gps, 0, null, null, null));
	}

	public void sendMessageWithGPS(Message msg) {
		floodMessageWithGPS(msg);
	}

	public void receive(Message msg, float bandwidth, float receiveTime) {
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
			state.update(bandwidth, receiveTime);

			SimLog.print("node ip:" + ip + " received hello from node ip:" + msg.getCarrierIP() + " , gps:(x="
					+ msg.getCarrierGPS().getX() + ",y=" + msg.getCarrierGPS().getY() + ") bandwidth:" + bandwidth
					+ " time:" + receiveTime);
			break;
		case Message.TYPE_DATA:
			state = ipStateMap.get(msg.getCarrierIP());
			if (state == null) {
				state = new BandwidthState();
				ipStateMap.put(msg.getCarrierIP(), state);
			}
			state.update(bandwidth, receiveTime);

			Integer lastSeqNum = seqStateMap.get(msg.getCarrierIP());
			if (lastSeqNum != null && msg.getSeqNum() <= lastSeqNum)
				return;
			seqStateMap.put(msg.getCarrierIP(), msg.getSeqNum());

			if (msg.getDestinationIP().equals(ip)) {
				deliverMsg(msg);
			} else {
				Message cloned = new Message(msg);
				cloned.setCarrierTime(receiveTime);
				cloned.setCarrierIP(ip);
				cloned.setCarrierGPS(gps);
				if (cloned.getDestinationGPS() != null)
					floodMessageWithGPS(cloned);

				else

					floodMessage(cloned);
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
