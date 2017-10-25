package services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import application.Configuration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FolderManager {
		
	private MonitorFolder monitorFolderLocal;
	private MonitorFolder monitorFolderRemote;
	
	private ObservableList<String> localFolderFileNamesObs;
	private ObservableList<String> remoteFolderFileNamesObs;
	private ArrayList<String> remoteFolderFileNames;
	private ArrayList<String> localFolderFileNames;
	
	private static FolderManager folderManagerInstance;
	private Configuration configInstance;
	
	public static FolderManager getInstance() {
		
		if(folderManagerInstance==null)
			folderManagerInstance = new FolderManager();
		
		return folderManagerInstance;
	}
	
	private FolderManager() {
		configInstance = Configuration.getInstance();
		monitorFolderLocal = new MonitorFolder(configInstance.getLocalFolderPath());	
	}
	
	public MonitorFolder getMonitorFolderLocal() {
		return monitorFolderLocal;
	}
	
	public void setMonitorFolderLocal(MonitorFolder monitorFolder) {
		this.monitorFolderLocal = monitorFolder;
	}
	
	public MonitorFolder getMonitorFolderRemote() {
		return this.monitorFolderRemote;
	}
	
	public void setMonitorFolderRemote(MonitorFolder monitorFolder) {
		this.monitorFolderRemote = monitorFolder;
	}
	
	public ObservableList<String> getLocalFileNames(){
		localFolderFileNamesObs = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(monitorFolderLocal.getNames())));
		return localFolderFileNamesObs;
	}
	
	public ObservableList<String> getRemoteFileNames(){
		remoteFolderFileNamesObs = FXCollections.observableArrayList(new ArrayList<>(Arrays.asList(monitorFolderRemote.getNames())));
		return remoteFolderFileNamesObs;
	}
	
	public String getLocalFolderPath() {
		return monitorFolderLocal.getFolderPath();
	}
	
	public String getRemoteFolderPath() {
		return monitorFolderRemote.getFolderPath();
	}
	
	public boolean localFileExists(String fileName) {
		boolean isLocatedInLocalFolder = false;
		
		// Get all files for folder locations
		localFolderFileNames = new ArrayList<String>(Arrays.asList(monitorFolderLocal.getNames()));
					
		// Check if File is present in folder 2
			for (String song : localFolderFileNames) {
				if(song == null)
					continue;				
				if(song.equals(fileName)) {
					isLocatedInLocalFolder = true;
					break;
				}
			}
		return isLocatedInLocalFolder;
	}

	public void changeLocalFolder(MonitorFolder newMonitorFolder) {
		this.monitorFolderLocal = newMonitorFolder;
	}

}
