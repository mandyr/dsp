package application;

public class Configuration {
	
	public static final String localFolderPathDefault = "H:\\DistributedSysProg\\dsp\\folderLocal";
	//public static final String remoteFolderPath = "/home/mandy/eclipse-workspace/DSP/folder2";
	public static final int checkIntervalDefault = 5000; // Check local folder every five seconds
	
	private static Configuration instance;
	private String localFolderPath;
	private int checkInterval;

	public static Configuration getInstance() {
		if(instance==null)
			instance = new Configuration();
		return instance;
	}
	
	private Configuration() {
		localFolderPath = localFolderPathDefault;
		checkInterval = checkIntervalDefault;
	}

	public String getLocalFolderPath() {
		return this.localFolderPath;
	}
	
	public int getCheckInterval() {
		return this.checkInterval;
	}
	
	public void setLocalFolderPath(String newFolderPath) {
		this.localFolderPath = newFolderPath;
	}
	
	public void setCheckInterval(int newInterval) {
		this.checkInterval = newInterval;
	}
}
