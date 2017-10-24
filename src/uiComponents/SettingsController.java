package uiComponents;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import application.Configuration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import services.FolderManager;
import services.MonitorFolder;


public class SettingsController implements Initializable {

	@FXML
	private Button buttonChangeFolder;
	
	@FXML
	private Button buttonCancel;
	
	@FXML
	private Button buttonSave;
	
	@FXML
	private TextField textFieldPath;
	
	private Stage stage;
	private String newFolderPath;	
	
	@FXML 
	public void onChangeFolderClick(ActionEvent actionEvent) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(this.stage);
        if (selectedDirectory != null) {
        	newFolderPath =selectedDirectory.getPath();
        	textFieldPath.setText(newFolderPath);
        }
	} 
	
	@FXML
	public void onCancelButtonClick(ActionEvent actionEvent) {
		closeWindow();
	}
	
	@FXML
	public void onSaveButtonClick(ActionEvent actionEvent) {
		// Change the remote folder path
		Configuration.getInstance().setLocalFolderPath(newFolderPath);
		FolderManager.getInstance().changeLocalFolder(new MonitorFolder(newFolderPath));
		closeWindow();
	}
		

	public void closeWindow() {
		// Close the window
		Stage stage = (Stage) buttonSave.getScene().getWindow();
	    stage.close();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		textFieldPath.setText(Configuration.getInstance().getLocalFolderPath());
		textFieldPath.setDisable(true);
		stage = new Stage();
	} 
	
}
