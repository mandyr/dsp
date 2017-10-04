package lab1;

import java.io.File;
import java.io.FileOutputStream;

import javafx.concurrent.Task;

public class FileCopier <Void> extends Task<Void>{
	
	private String newFilePath = null;
	private String fileName;
	private MonitorFolder sourceFolder;
	
	
	public FileCopier(MonitorFolder monitorFolder, String newFilePath, String fileName) {
		this.sourceFolder = monitorFolder;
		this.newFilePath = newFilePath;
		this.fileName = fileName;
	}
	
	  @Override
	    protected void succeeded() {
	        super.succeeded();
	        // e.g. show "copy finished" dialog
	    }

	    @Override
	    protected void running() {
	        super.running();
	        // e.g. change mouse courser
	    }

	    @Override
	    protected void failed() {
	        super.failed();
	        // do stuff if call threw an excpetion
	    }

	@Override
	protected Void call() throws Exception {
		try {
			sourceFolder.openFile(fileName);
			FileOutputStream fos = new FileOutputStream(newFilePath);

			int c = 0;
			while(true) {
				if(sourceFolder.checkBool()) break;
				c = sourceFolder.getB();
				fos.write(c);
			}
			
			sourceFolder.closeFile(fileName);
			fos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} 	
		return null;
	}

}
