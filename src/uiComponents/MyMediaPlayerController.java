package uiComponents;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import services.BackgroundService;
import services.FolderManager;
import services.MonitorFolder;

public class MyMediaPlayerController implements Initializable {

	@FXML
	private Label folderName;
	
	@FXML
	private Button buttonChooseFolder;
	
	@FXML
	private Button buttonSettings;
	
	@FXML
	private Button buttonRefresh;
	
	@FXML
	private ListView listViewFiles;
	
	private Stage stage;
	private FolderManager folderManager;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		stage = new Stage();
		folderManager = FolderManager.getInstance();
		//buttonSettings.setDisable(true);
		
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
	
	@FXML
	public void onSettingsClick(ActionEvent event) {
		
		try{
			
			 Stage stage = new Stage();
		     Parent root = FXMLLoader.load(getClass().getResource("FXML_Settings.fxml"));
		     stage.setTitle("Settings");
		     stage.setScene(new Scene(root, 350, 300));
		     stage.show();
		     
		        
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void onRefreshClick(ActionEvent actionEvent) {
		updateListView();
	}

}
