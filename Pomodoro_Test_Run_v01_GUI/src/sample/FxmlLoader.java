package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.net.URL;

public class FxmlLoader {
    private Pane view;


    public Pane getPage(String filename){
        try {
            URL fileURL = getClass().getResource(filename);
            if (fileURL == null){
                throw new java.io.FileNotFoundException("FXML file not found");
            }

            view = new FXMLLoader().load(fileURL);

        } catch (Exception e){
            System.out.println("No page " + filename + ", please check FxmlLoader");
        }
        return view;
    }
}
