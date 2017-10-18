package uiComponents;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import application.MonitorFolder;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.BackgroundService;
import services.FolderManager;

public class MyMediaPlayerController implements Initializable {

	@FXML
	private Label folderName;
	
	@FXML
	private Button buttonChooseFolder;
	
	@FXML
	private ListView listViewFiles;
	
	private Stage stage;
	private FolderManager folderManager;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stage = new Stage();
		folderManager = FolderManager.getInstance();
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	public void onChooseFolderClick(ActionEvent event) {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            folderName.setText("Selected Folder: " + selectedDirectory.getName());
            folderManager.setMonitorFolderLocal(new MonitorFolder(selectedDirectory.getAbsolutePath()));
            updateListView();
            
         // From now on start observing the folder
			Thread observeMonitor = new Thread(() -> { 
				while(true) {
					if(folderManager.getMonitorFolderLocal().checkForChange()) {
						Platform.runLater(new Runnable() {
				            @Override
				            public void run() {
				                // Update UI here.
				            	updateListView();
				            }
				        });
					}	
				}
			});
			
			BackgroundService.getExecuterService().submit(observeMonitor);
            
         }	
	}
	
	@SuppressWarnings("unchecked")
	public void updateListView() {
		listViewFiles.setItems(folderManager.getLocalFileNames());
        listViewFiles.setCellFactory(new Callback<ListView<String>,javafx.scene.control.ListCell<String>>(){
        	
        	@Override
        	public ListCell<String> call(ListView<String> listView)
            {
                return new ListViewCell();
            }
        });
	}

}
