package services;

import java.io.File;
import java.io.FileOutputStream;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.concurrent.Task;
import uiComponents.CellController;

public class FileCopier <Void> extends Task<Void> {
	
	private String newFilePath = null;
	private String fileName;
	private MonitorFolder sourceFolder;
	private CellController cellController;
	
	private boolean running = false;
	private boolean finished = false;
	
	public FileCopier(MonitorFolder monitorFolder, String newFilePath, String fileName, CellController cellController) {
		this.sourceFolder = monitorFolder;
		this.newFilePath = newFilePath;
		this.fileName = fileName;
		this.cellController = cellController;
	}
	
	  @Override
	    protected void succeeded() {
	        super.succeeded();
	        // e.g. show "copy finished" dialog
	        System.out.println("FileCopier finished");
	        finished = true;     
	        Platform.runLater(new Runnable() {
	            @Override
	            public void run() {
	                // Update UI here.
	            	cellController.setSong(fileName);
	            	cellController.checkFileLocation();
	            }
	        });
	    }

	    @Override
	    protected void running() {
	        super.running();
	        // e.g. change mouse courser
	        System.out.println("FileCopier running");
	        running = true;
	    }

	    @Override
	    protected void failed() {
	        super.failed();
	        // do stuff if call threw an excpetion
	        System.out.println("FileCopier failed");
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
			succeeded();
			
		} catch (Exception e) {
			failed();
			e.printStackTrace();
		} 	
		return null;
	}


}
