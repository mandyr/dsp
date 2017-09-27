package lab1;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * Write a class called myMediaPlayer (the client) which uses the class monitorFolder (containment not inheritance).
 *  It has a javaFX GUI. It displays the filesnames in folder1, followed by a play button and lastly a move button.
 *  
 *  The class myMediaPlayer can only play the files in folder2 (not so but pretend) so in order to play 
 *  a file it must move the file to folder2. Button play is then enabled after a move and button move is disabled.  
 */

public class MyMediaPlayer extends Application {

		//private final String folder2 = "/home/mandy/eclipe-workspace/DSP_Lab1/folder2";
		private final String folder2 = "H:\\DistributedSysProg\\dsp\\folder2";
	
		private MonitorFolder monitorFolder1;
		private MonitorFolder monitorFolder2;
		
		private MediaPlayer mediaPlayer;
		
		private String folderPath = null;
		
		private ArrayList<String> listOfFilesFolder1 = new ArrayList<String>();
		private ArrayList<String> listOfFilesFolder2 = new ArrayList<String>();
		
		// UI Elements
		private Group root;
		private Scene scene;
		private DirectoryChooser directoryChooser;
		private Stage stage;
		private ListView<String> listView;
		private Text chooseAFolder;
		private Button btnPlay;
		private Button btnMove;
		
		
		@Override
		public void start(Stage primaryStage) throws Exception {
			try {
				
				// Define Monitor folder for folder 2
				monitorFolder2 = new MonitorFolder(folder2);
				
				this.stage = primaryStage;
						    
			    // Add grid pane to window
			    GridPane grid = new GridPane();
			    grid.setAlignment(Pos.CENTER);
			    grid.setHgap(10);
			    grid.setVgap(10);
			    grid.setPadding(new Insets(25, 25, 25, 25));

			   
			    Text scenetitle = new Text("My Media Player");
			    scenetitle.setFont(Font.font("Roboto", FontWeight.NORMAL, 20));
			    grid.add(scenetitle, 0, 0, 2, 1);
			    
			    chooseAFolder = new Text("Please choose a folder");
			    chooseAFolder.setFont(Font.font("Roboto", FontWeight.NORMAL, 16));
			    grid.add(chooseAFolder, 0,1,1,1);
			    
			    Button btn = new Button("Choose folder");
			    HBox hbBtn = new HBox(10);
			    hbBtn.getChildren().add(btn);
			    grid.add(hbBtn, 0,2,1,1);
			    
			    btn.setOnAction(new EventHandler<ActionEvent>() {
			    	 
			        @Override
			        public void handle(ActionEvent e) {
			            openDirectoryChooser();
			        }
			    });
			    
			    listView = new ListView<String>();
			    listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

					@Override
					public void changed(ObservableValue<? extends String> observable, String oldValue,
							String newValue) {
						 
						checkForFileLocation(newValue);
					}
			    	
			    });
			    grid.add(listView, 0,3);
			    
			    // Add buttons beneath list of files
			    btnPlay = new Button("Play");
			    HBox hbBtnPlay = new HBox(10);
			    hbBtn.getChildren().add(btnPlay);
			    grid.add(hbBtnPlay, 0,4);
			    		    
			    btnPlay.setDisable(true);
			    btnPlay.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						String path = folderPath + "/" + listView.getSelectionModel().getSelectedItem();
						
						File file = new File(path);
						String song = file.toURI().toASCIIString();
						playSong(song);
											
					}
				});
			    
			    btnMove = new Button("Move");
			    HBox hbBtnMove = new HBox(10);
			    hbBtn.getChildren().add(btnMove);
			    grid.add(hbBtnMove, 1,4);
			    
			    btnMove.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						// open file and copy it to other folder
						String originalFileName = listView.getSelectionModel().getSelectedItem();
						String newFilePath = folder2 + File.separator + originalFileName;
						File newFile = new File(newFilePath);
						
						try {
							monitorFolder1.openFile(originalFileName);
							//DataOutputStream dout = new DataOutputStream(new FileOutputStream(newFile));
							FileOutputStream fos = new FileOutputStream(newFilePath);

							int c = 0;
							while(true) {
								if(monitorFolder1.checkBool()) break;
								c = monitorFolder1.getB();
								fos.write(c);
							}
							
							monitorFolder1.closeFile(originalFileName);
							fos.close();
							
							checkForFileLocation(originalFileName);
						} catch (Exception e) {
							e.printStackTrace();
						} 						
					}
				});
			    
				btnMove.setDisable(true);
			    
			    // Set the scene
			    scene = new Scene(grid, 400, 500);
			    
			    // Show the stage
				stage.setScene(scene);
				stage.setTitle("My Media Player");
				stage.show();
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void main(String[] args) {
			launch(args);
		}
		
		public void playSong(String fileName) {
			Media pick = new Media(fileName);
		    MediaPlayer player = new MediaPlayer(pick);
		    player.play();			
		}
		
		private void openDirectoryChooser() {
			directoryChooser = new DirectoryChooser();
			File selectedDirectory = directoryChooser.showDialog(stage);
            if (selectedDirectory != null) {
                chooseAFolder.setText("Selected Folder: " + selectedDirectory.getName());
                folderPath = selectedDirectory.getAbsolutePath();
                monitorFolder1 = new MonitorFolder(folderPath);
                
                listOfFilesFolder1.clear();
                listOfFilesFolder1 = new ArrayList<>(Arrays.asList(monitorFolder1.getNames()));
                populateUIList();
                
                btnMove.setDisable(true);
                btnPlay.setDisable(true);
             }	
		}
		
		private void populateUIList() {
			
			ObservableList<String> items = FXCollections.observableArrayList(listOfFilesFolder1);
		    listView.setItems(items);
		}
		
		private void checkForFileLocation(String filename) {
			// Get all files for folder locations
			listOfFilesFolder2 = new ArrayList<String>(Arrays.asList(monitorFolder2.getNames()));
			
			// Check if File is present in folder 2
			boolean isLocatedInFolder2 = false;
			for (String song : listOfFilesFolder2) {
				if(song == null)
					continue;				
				if(song.equals(filename)) {
					isLocatedInFolder2 = true;
					break;
				}
			}
			
			if(isLocatedInFolder2){
				btnPlay.setDisable(false);
				btnMove.setDisable(true);
			} else {
				btnPlay.setDisable(true);
				btnMove.setDisable(false);
			}
		}
}
