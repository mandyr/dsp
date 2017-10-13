package fxml_implementation;

import javafx.scene.control.ListCell;

public class ListViewCell extends ListCell<String>{
	
	 @Override
	    public void updateItem(String string, boolean empty)
	    {
	        super.updateItem(string,empty);
	        if(string != null)
	        {
	            CellController data = new CellController();
	            data.setSong(string);
	            setGraphic(data.getBox());
	            data.checkFileLocation();
	        }
	    }

}
