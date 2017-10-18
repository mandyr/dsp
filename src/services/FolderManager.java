package services;

import java.util.ArrayList;
import java.util.Arrays;

import application.Configuration;
import application.MonitorFolder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FolderManager {
		
	private MonitorFolder monitorFolderLocal;
	private MonitorFolder monitorFolderRemote;
	
	private ObservableList<String> localFolderFileNames;
	private ArrayList<String> remoteFolderFileNames;
	
	private static FolderManager folderManagerInstance;
	
	public static FolderManager getInstance() {
		
		if(folderManagerInstance==null)
			folderManagerInstance = new FolderManager();
		
		return folderManagerInstance;
	}
	
	private FolderManager() {
		monitorFolderRemote = new MonitorFolder(Configuration.remoteFolderPath);
	}
	
	public MonitorFolder getMonitorFolderLocal() {
		return monitorFolderLocal;
	}
	public void setMonitorFolderLocal(MonitorFolder monitorFolder) {
		this.monitorFolderLocal = monitorFolder;
	}
	
	public ObservableList<String> getLocalFileNames(){
		return localFolderFileNames = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(monitorFolderLocal.getNames())));	
	}
	
	public String getLocalFolderPath() {
		return monitorFolderLocal.getFolderPath();
	}
	
	public boolean remoteFileExists(String fileName) {
		boolean isLocatedInRemoteFolder = false;
		
		// Get all files for folder locations
		remoteFolderFileNames = new ArrayList<String>(Arrays.asList(monitorFolderRemote.getNames()));
					
		// Check if File is present in folder 2
			for (String song : remoteFolderFileNames) {
				if(song == null)
					continue;				
				if(song.equals(fileName)) {
					isLocatedInRemoteFolder = true;
					break;
				}
			}
		return isLocatedInRemoteFolder;
	}

}
