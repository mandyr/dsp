package fxml_implementation;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class CellController {
	
	@FXML
	private Label songName;
	private String folderPath;
	private String songNameString;
	
	@FXML
	private Button buttonPlay;
	
	@FXML
	private Button buttonMove;
	
	@FXML
	private HBox hBox;
	
	FolderManager folderManager;
	
	public CellController() {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXML_SongListItem.fxml"));
		fxmlLoader.setController(this);
		try {
			fxmlLoader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		folderManager = FolderManager.getInstance();
		folderPath = folderManager.getLocalFolderPath();
		
	}
	
	public void setSong(String song) {
		songName.setText(song);
		songNameString = songName.getText();
	}	
	
	public HBox getBox() {
		return this.hBox;
	}
	
	@FXML 
	public void onPlayClick() {
		//Construct MediaPath
		File f = new File(folderPath + File.separator + songName.getText());
		
		Media pick = new Media(f.toURI().toASCIIString());
	    new MediaPlayer(pick).play();
	}
	
	@FXML 
	public void onMoveClick() {
		// open file and copy it to other folder	
		String newFilePath = Configuration.remoteFolderPath + File.separator + songName.getText();
		FileCopier<Void> fileCopier = new FileCopier<>(folderManager.getMonitorFolderLocal(), newFilePath, songName.getText(), this);
		try {
			BackgroundService.getExecuterService().submit(fileCopier);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		songName.setText(songNameString + " - Copying");
		buttonPlay.setDisable(true);
		buttonMove.setDisable(true);
		
	}
	
	public void checkFileLocation() {
		if(folderManager.remoteFileExists(songName.getText())) {
			buttonPlay.setDisable(false);
			buttonMove.setDisable(true);
		} else {
			buttonPlay.setDisable(true);
			buttonMove.setDisable(false);
		}
	}

}

