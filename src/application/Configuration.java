package application;

public class Configuration {
	
	public static final String remoteFolderPathDefault = "H:\\DistributedSysProg\\dsp\\folder2";
	//public static final String remoteFolderPath = "/home/mandy/eclipse-workspace/DSP/folder2";
	public static final int checkIntervalDefault = 5000; // Check local folder every five seconds
	
	private static Configuration instance;
	private String remoteFolderPath;
	private int checkInterval;

	public static Configuration getInstance() {
		if(instance==null)
			instance = new Configuration();
		return instance;
	}
	
	private Configuration() {
		remoteFolderPath = remoteFolderPathDefault;
		checkInterval = checkIntervalDefault;
	}

	public String getRemoteFolderPath() {
		return this.remoteFolderPath;
	}
	
	public int getCheckInterval() {
		return this.checkInterval;
	}
	
	public void setRemoteFolderPath(String newFolderPath) {
		this.remoteFolderPath = newFolderPath;
	}
	
	public void setCheckInterval(int newInterval) {
		this.checkInterval = newInterval;
	}
}
