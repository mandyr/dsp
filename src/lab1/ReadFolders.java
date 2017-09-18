package lab1;

public interface ReadFolders {

	/**
	 Boolean ckeckBool ()// returns a bool - 
     String [] getNames()// returns the names of all the files in folder1
     bool openFile(string name)// opens a file called name
     Byte getB()//Gets a byte from the currently open file 
     bool closeFile(string name)// closes the open file 
     bool ckeckForChange ()// sets a bool if a file has been added
	 */
	
	boolean checkBool();
	String[] getNames();
	boolean openFile(String name);
	byte getB();
	boolean closeFile(String name);
	boolean checkForChange();
	
}
