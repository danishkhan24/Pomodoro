package sample;
import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GUILoader extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(Event::consume);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Radio Sync Panel");
        primaryStage.show();

    }

    void execute(String[] args){
        //System.out.println("starting program now!");
        launch(args);
    }
}
