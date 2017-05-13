
public class Main {

	public static void main(String[] args) {

		SimLog.setEnableLogging(true);
		SimLog.print(" ");
		SimLog.print("-----------------------------------------------------------------------------");
		SimLog.print("starting logging ... ");
		SimLog.print("-----------------------------------------------------------------------------");
		SimLog.print(" ");
		SimLog.print("-----------------------------------------------------------------------------");
		SimLog.print("intitializing network meshes and nodes .... ");
		SimLog.print("-----------------------------------------------------------------------------");
		SimLog.print(" ");

		Mesh[] meshes = new Mesh[4];
		for (int i = 0; i < meshes.length; i++) {
			meshes[i] = new Mesh();
		}

		String mesh1_ip_prefix = "1.";
		for (int j = 0; j < 5; j++) {
			meshes[0].addNode(new Node(mesh1_ip_prefix + j, new GPS(j + 1, j + 1)));
		}

		String mesh12_ip_prefix = "12.";
		for (int j = 0; j < 2; j++) {
			Node node = new Node(mesh12_ip_prefix + j, new GPS(0, j));
			meshes[0].addNode(node);
			meshes[1].addNode(node);
		}

		String mesh2_ip_prefix = "2.";
		for (int j = 0; j < 2; j++) {
			meshes[1].addNode(new Node(mesh2_ip_prefix + j, new GPS(-j - 1, j + 1)));
		}

		String mesh23_ip_prefix = "23.";
		for (int j = 0; j < 3; j++) {
			Node node = new Node(mesh23_ip_prefix + j, new GPS(-j, 0));
			meshes[1].addNode(node);
			meshes[2].addNode(node);

		}

		String mesh3_ip_prefix = "3.";
		for (int j = 0; j < 4; j++) {
			meshes[2].addNode(new Node(mesh3_ip_prefix + j, new GPS(-j - 1, -j - 1)));
		}

		String mesh34_ip_prefix = "34.";
		for (int j = 0; j < 2; j++) {
			Node node = new Node(mesh34_ip_prefix + j, new GPS(-j, 0));
			meshes[2].addNode(node);
			meshes[3].addNode(node);

		}

		String mesh4_ip_prefix = "4.";
		for (int j = 0; j < 6; j++) {
			meshes[3].addNode(new Node(mesh4_ip_prefix + j, new GPS(j + 1, -j - 1)));

		}

		for (int i = 0; i < meshes.length; i++) {
			meshes[i].sayHelllo();

		}
		SimLog.print(" ");
		SimLog.print("-----------------------------------------------------------------------------");
		SimLog.print("sending a data msg .... ");
		SimLog.print("-----------------------------------------------------------------------------");
		SimLog.print(" ");

		Node sender = meshes[0].getNodes().get(0);
		Node receiver = meshes[3].getNodes().get(0);
		String payLoad = "hello world!";

		SimLog.print(" ");
		SimLog.print("-----------------------------------------------------------------------------");
		SimLog.print("sender node ip:" + sender.getIp() + " , gps:(x=" + sender.getGps().getX() + ",y="
				+ sender.getGps().getY() + ")");
		SimLog.print("data msg :" + payLoad);
		SimLog.print("receiver node ip:" + receiver.getIp() + " , gps:(x=" + receiver.getGps().getX() + ",y="
				+ receiver.getGps().getY() + ")");
		SimLog.print("-----------------------------------------------------------------------------");
		SimLog.print(" ");

		sender.sendMessageWithGPS(new Message(Message.TYPE_DATA, 1, sender.getIp(), sender.getGps(),
				Timer.getInstance().getTime(), receiver.getIp(), receiver.getGps(), payLoad));

	}

}
