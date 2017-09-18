package lab1;

import java.io.File;

/*
 * Write a class (called monitorFolder) that implements this interface. 
 * The class is to monitor one subfolder (Folder1) for additions. 
 * When asked by a client class the Monitorfolder returns true if the contents of folder1 
 * changes etc for the other methods. MonitorFolder uses a filechooser object to “see” the folder. 
 */


import java.io.IOException;


public class MonitorFolder implements ReadFolders {

	private String folderName = null;
	private int numberOfFiles = 0;
	
	public MonitorFolder(String folderName) {
		this.folderName = folderName;
	}
	
	@Override
	public boolean checkBool() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getNames() {
		// returns the names of all the files in folder1
		File[] files = new File(folderName).listFiles();
		String[] result = new String[files.length];
		
		int counter = 0;
		for(int i=0;i< files.length;i++) {
			File file = files[i];
			if(file.isFile()) {
				result[counter] = file.getName();
				counter++;
			}	
		}
			
		numberOfFiles = result.length;
		return result;
	}

	@Override
	public boolean openFile(String name) {
		// opens a file called name
		return false;
	}

	@Override
	public byte getB() {
		//Gets a byte from the currently open file 
		return 0;
	}

	@Override
	public boolean closeFile(String name) {
		// closes the open file 
		return false;
	}

	@Override
	public boolean checkForChange() {
		// sets a bool if a file has been added
		return false;
	}

}
