
public class SimLog {
	public static boolean enableLogging = true;

	public static void print(String msg) {
		if (enableLogging)
			System.out.println(msg);
	}

	public static void setEnableLogging(boolean enableLogging) {
		SimLog.enableLogging = enableLogging;
	}
	
	
}
