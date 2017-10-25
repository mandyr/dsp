package services;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/*
 * Write a class (called monitorFolder) that implements this interface. 
 * The class is to monitor one subfolder (Folder1) for additions. 
 * When asked by a client class the Monitorfolder returns true if the contents of folder1 
 * changes etc for the other methods. MonitorFolder uses a filechooser object to â€œseeâ€� the folder. 
 */


import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import com.sun.javafx.collections.ObservableSequentialListWrapper;

import application.Configuration;
import application.ReadFolders;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;


public class MonitorFolder extends Observable implements ReadFolders  {
		
	private String folderPath = null;
	private File openedFile;
	private DataInputStream dataInputStream; 
	private boolean endOfFile = false;
	
	private ObservableList<File> observableListOfFiles = null;
	
	
	public MonitorFolder(String folderPath) {
		this.folderPath = folderPath;
		observableListOfFiles = FXCollections.observableList(new ArrayList<File>());
	}
	
	@Override
	public boolean checkBool() {
		return endOfFile;
	}

	@Override
	public String[] getNames() {
		// returns the names of all the files in folder1
		File[] files = new File(folderPath).listFiles();
		String[] result = new String[files.length];
		
		int counter = 0;
		for(int i=0;i< files.length;i++) {
			File file = files[i];
			if(file.isFile()) {
				observableListOfFiles.add(file);
				result[counter] = file.getName();
				counter++;
			}	
		}
			
		return result;
	}

	@Override
	public boolean openFile(String name) {
		// opens a file called name
		
		String filePath = folderPath + File.separator + name;
		openedFile = new File(filePath);
	
			try {
				dataInputStream = new DataInputStream(new FileInputStream(openedFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return true;
		
	}

	@Override
	public byte getB(){
		//Gets a byte from the currently open file
		int c = 0;
		try {
			c = dataInputStream.read();
			if (c== -1) endOfFile = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (byte)c;
	}

	@Override
	public boolean closeFile(String name) {
		// closes the open file 
		try {
			dataInputStream.close();
			System.out.println("Original file closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkForChange() {
		// sets a bool if a file has been added
		boolean result = false;

		File[] listOfFiles = new File(folderPath).listFiles();
	    for (File fileEntry : listOfFiles) {
	        if (!fileContainedInOldList(fileEntry)) {
	    		observableListOfFiles.add(fileEntry);
	    		result = true;
	    		setChanged();
	    		notifyObservers();
	        }
	    }
	    
	    try {
			Thread.sleep(Configuration.getInstance().getCheckInterval());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	    
		return result;
	}
	
	private boolean fileContainedInOldList(File file) {
		boolean result = false;
		for(File f : observableListOfFiles) {
			if(f.getName().equals(file.getName())) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public ObservableList<File> getObservableList(){
		return this.observableListOfFiles;
	}
	
	
	public String getFolderPath() {
		return this.folderPath;
	}

}
