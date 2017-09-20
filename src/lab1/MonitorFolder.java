package lab1;

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


public class MonitorFolder implements ReadFolders {

	private String folderPath = null;
	private int numberOfFiles = 0;
	private File openedFile;
	private DataInputStream dataInputStream; 
	
	public MonitorFolder(String folderName) {
		this.folderPath = folderName;
	}
	
	@Override
	public boolean checkBool() {
		// TODO Auto-generated method stub
		return false;
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
				result[counter] = file.getName();
				counter++;
			}	
		}
			
		numberOfFiles = result.length;
		return result;
	}

	@Override
	public boolean openFile(String name) throws Exception {
		// opens a file called name
		
		String filePath = folderPath + File.separator + name;
		openedFile = new File(filePath);

		try {			
			dataInputStream = new DataInputStream(new FileInputStream(openedFile));
			return true;
		} catch (FileNotFoundException e) {
			throw e;
		}
	}

	@Override
	public byte getB() throws IOException {
		//Gets a byte from the currently open file
		byte c = 0;
		try {
			c = dataInputStream.readByte();
			return c;
		} catch (IOException e) {
			throw e;
		}	
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
