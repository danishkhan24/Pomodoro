package sample;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScheduledShowGUI extends Application{


    @Override
    public void start(Stage primaryStage) {

        Clock timer = new Clock();
        Pane root = new Pane();
        root.getChildren().add(timer);
        Scene scene = new Scene(root, 850, 420);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        root.setStyle("-fx-background-color: black;");
        primaryStage.show();
    }


    public class Clock extends Pane{
        private Timeline time = new Timeline();
        private int i = 0;
        private long length = System.currentTimeMillis();


//        String showName = sample.Parameters.ShowName;
        ArrayList<Long> segmentTime = sample.Parameters.segments;
        //int totalDuration = sample.Parameters.ShowDuration;
        //Label Title = new Label("Show Name:");
        //Label NameofShow = new Label(showName);
        Label SystemTime = new Label();
        //Label TotalSegments = new Label("Total Segments:");
        //Label CurrentSegment = new Label("Current Segment:");
        //Label CountdownLabel = new Label("Countdown to Next Segment :");
        //Label TotalSegmentsPart = new Label(segmentTime.size() + "");
        //Label CurrentSegmentPart = new Label();
        Label CountdownLabelPart = new Label();

        private Clock() {
            //Title.setTranslateX(0);
            //Title.setTranslateY(0);
//            NameofShow.setTranslateX(168);
//            NameofShow.setTranslateY(23);
            //TotalSegments.setTranslateX(33);
            //TotalSegments.setTranslateY(53);
//            TotalSegmentsPart.setTranslateX(168);
//            TotalSegmentsPart.setTranslateY(54);
            //CurrentSegment.setTranslateX(33);
            //CurrentSegment.setTranslateY(86);
//            CurrentSegmentPart.setTranslateX(168);
//            CurrentSegmentPart.setTranslateY(87);
            //CountdownLabel.setTranslateX(33);
            //CountdownLabel.setTranslateY(115);
            CountdownLabelPart.setTranslateX(50);
            CountdownLabelPart.setTranslateY(10);
            CountdownLabelPart.setFont(new Font(140));
            CountdownLabelPart.setTextFill(Color.web("#FFFFFF"));

            SystemTime.setTranslateX(50);
            SystemTime.setTranslateY(170);
            SystemTime.setFont(new Font(80));
            SystemTime.setTextFill(Color.web("#FFFFFF"));

            //getChildren().add(Title);
            //getChildren().add(NameofShow);
            //getChildren().add(TotalSegments);
            //getChildren().add(TotalSegmentsPart);
            //getChildren().add(CurrentSegment);
            //getChildren().add(CurrentSegmentPart);
            //getChildren().add(CountdownLabel);
            getChildren().add(CountdownLabelPart);
            getChildren().add(SystemTime);
            //status = segmentTime.size();
            doTime(segmentTime);

        }


        private void doTime(ArrayList<Long> frames){

            time.setCycleCount(Animation.INDEFINITE);

            long[] endValue = {System.currentTimeMillis() + 2000};
            for (long frame: frames) {
                endValue[0] = endValue[0] + (frame * 1000);
            }

            long[] h = {0}, m = {0}, s = {0};
            long[] diffTime = {0};
            SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss aa");

            KeyFrame frame = new KeyFrame(Duration.millis(1000 - Calendar.getInstance().get(Calendar.MILLISECOND)), event -> {

                if (endValue[0] <= System.currentTimeMillis()){time.stop();}

                if (length <= System.currentTimeMillis() && i!=frames.size()){
                    length = System.currentTimeMillis() + (long)((frames.get(i++)).intValue() * 1000);
                }

                diffTime[0] = length - System.currentTimeMillis();

                h[0] =  (diffTime[0] / 3600000) % 24;
                m[0] =  (diffTime[0] / 60000) % 60;
                s[0] =  (diffTime[0] / 1000) % 60 + 1;

                SystemTime.setText(dateFormat.format(new Date()));
                String newTime = (h[0] + ":" + m[0] + ":" + s[0]);
                CountdownLabelPart.setText(newTime);

            });

            time.getKeyFrames().add(frame);
            time.playFromStart();
            time.setOnFinished(event -> {

                System.out.println("exiting platform");
                Platform.exit();
                Thread.currentThread().interrupt();
                System.exit(1);
            });
        }
    }


    void execute(String[] args){
        launch(args);
    }
}
