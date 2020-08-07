package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    private ArrayList<String> LoadedShow = new ArrayList<>();
    private int ArrPointer = 0;

    @FXML private ListView<String> ShowSegmentList;
    @FXML private ListView<String> MyShowList;
    @FXML private TextField TitleName;
    @FXML private DatePicker ShowDate;
    @FXML private TextField ShowSegmentName;
    @FXML private TextField SegmentStartTime;
    @FXML private TextField SegmentEndTime;
    @FXML private BorderPane mainPane;
    @FXML private Label DeleteText;
    @FXML private Button closeButton;
    @FXML private ListView<String> TempSegmentList;
    @FXML private TextField TempSegName;
    @FXML private TextField TemptStartTime;
    @FXML private TextField TempEndTime;
    @FXML private ChoiceBox<File> EditSavedShow;
    @FXML private TextField TempName;

    @FXML
    private void AddNewShow() {
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("AddNewShow.fxml");
        mainPane.setCenter(view);
    }

    @FXML
    private void SaveShow() throws IOException {
        File showFile = new File(TitleName.getText() + ".rsh");

        if (!showFile.exists() && !showFile.isDirectory()){
            List<String> segments = ShowSegmentList.getItems();
            if (segments.isEmpty()){
                DialogBoxError("EmptyFieldError");
                //ErrorText.setText("Segments can not be Empty!");
                return;
            }

            String content = String.join("\n", segments);
            BufferedWriter writer = new BufferedWriter(new FileWriter(showFile));

            if (ShowDate.getValue() == null){
                DialogBoxError("EmptyFieldError");
                //ErrorText.setText("Date can not be Empty!");
                return;
            }
            writer.write(ShowDate.getValue().toString() + "\n");

            writer.write(content);
            writer.close();
            ShowSegmentList.getItems().clear();
            TitleName.clear();
            ShowDate.getEditor().clear();
        }
        else{
            DialogBoxError("DialogBox");
            //ErrorText.setText("Show with same name already exists!");
        }
    }

    private void DialogBoxError(String str) throws IOException {
        Stage primaryStage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource(str + ".fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    @FXML
    private void AddSegment() throws IOException {
        if (ShowSegmentName.getText().trim().isEmpty() || SegmentStartTime .getText().trim().isEmpty()
                || SegmentEndTime.getText().trim().isEmpty()){
            DialogBoxError("EmptyFieldError");
            return;
        }

        ShowSegmentList.getItems().add(SegmentStartTime.getText() + " " +
                SegmentEndTime.getText() + " " + ShowSegmentName.getText());
        ShowSegmentName.clear();
        SegmentStartTime.clear();
        SegmentEndTime.clear();
    }

    @FXML
    private void MyShows(){
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("MyShows.fxml");
        mainPane.setCenter(view);
    }

    @FXML
    private void LoadShows(){
        MyShowList.getItems().clear();
        File dir = new File("./");
        File [] files = dir.listFiles((dir1, name) -> name.endsWith(".rsh"));
        if (files != null){
            for (File rshfile : files) {
                System.out.println(rshfile.getName());
                MyShowList.getItems().add(rshfile.getName());
            }
        }
    }

    @FXML
    private void DeleteAllShows(){
        boolean deleted = true;
        File dir = new File("./");
        File [] files = dir.listFiles((dir1, name) -> name.endsWith(".rsh"));
        if (files != null){
            //System.out.println(files);
            for (File rshfile : files) {
                System.out.println(rshfile.getName());
                deleted = rshfile.delete();
                System.out.println(deleted);
            }
            if (!deleted){
                DeleteText.setText("Unable to Delete some files close all the show files and try again");
            }
            else {
                DeleteText.setText("All shows Deleted successfully");}
        }
        else {
            DeleteText.setText("No Files found to Delete!");}
    }

    @FXML
    private void CreateTemplate(){
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("Template.fxml");
        mainPane.setCenter(view);
    }

    @FXML
    private void EditRemoveShows(){
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("EditRemove.fxml");
        mainPane.setCenter(view);
    }

    @FXML
    private void EditSavedShow(){
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("EditSavedShow.fxml");
        mainPane.setCenter(view);
    }

    @FXML
    private void EditSavedTemp(){
        FxmlLoader object = new FxmlLoader();
        Pane view = object.getPage("EditTemplate.fxml");
        mainPane.setCenter(view);
    }

    @FXML
    private void FillEditList(){
        File dir = new File("./");
        File [] files = dir.listFiles((dir1, name) -> name.endsWith(".rsh"));
        if (files != null){
            ObservableList<File> observableList = FXCollections.observableList(Arrays.asList(files));
            EditSavedShow.setItems(observableList);
            EditSavedShow.show();
        }
    }

    @FXML
    private void FillTempEditList(){
        File dir = new File("./");
        File [] files = dir.listFiles((dir1, name) -> name.endsWith(".tp"));
        if (files != null){
            ObservableList<File> observableList = FXCollections.observableList(Arrays.asList(files));
            EditSavedShow.setItems(observableList);
            EditSavedShow.show();
        }
    }

    @FXML
    private void LoadShowToEdit() throws IOException {

        File file = EditSavedShow.getSelectionModel().getSelectedItem();

        if (file == null){return;}
        // Open the file
        FileInputStream fileStream;
        fileStream = new FileInputStream(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(fileStream));

        String strLine;

        //Read File Line By Line
        strLine = br.readLine();

        if (!strLine.trim().isEmpty()) {
            ShowDate.setValue(LOCAL_DATE(strLine.trim()));
        }
        MyShowList.getItems().clear();
        LoadedShow.clear();

        while ((strLine = br.readLine()) != null) {
            LoadedShow.add(strLine);
            MyShowList.getItems().add(strLine);
        }

        String[] str = LoadedShow.get(0).split(" ");
        ShowSegmentName.setText(str[2]);
        SegmentStartTime.setText(str[0]);
        SegmentEndTime.setText(str[1]);
        //Close the input stream
        fileStream.close();
    }

    private LocalDate LOCAL_DATE (String dateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }

    @FXML
    private void NextSegment() {
        if (ArrPointer != (LoadedShow.size()-1)) {
            String[] str = LoadedShow.get(++ArrPointer).split(" ");
            ShowSegmentName.setText(str[2]);
            SegmentStartTime.setText(str[0]);
            SegmentEndTime.setText(str[1]);
        }
    }

    @FXML
    private void SaveEditSegment() throws IOException {
        if (ShowSegmentName.getText().trim().isEmpty() || SegmentStartTime.getText().trim().isEmpty() || SegmentEndTime.getText().trim().isEmpty()){
            DialogBoxError("EmptyFieldError");
        }
        else {
            String str = SegmentStartTime.getText() + " " + SegmentEndTime.getText() + " " + ShowSegmentName.getText();
            LoadedShow.set(ArrPointer, str);
            MyShowList.getItems().clear();
            for (String i : LoadedShow){
                MyShowList.getItems().add(i);
            }
        }
    }

    @FXML
    private void EditNewSegment() throws IOException {
        if (TempSegName.getText().trim().isEmpty() || TemptStartTime.getText().trim().isEmpty() || TempEndTime.getText().trim().isEmpty()){
            DialogBoxError("EmptyFieldError");
        }
        else {
            LoadedShow.add(ArrPointer+1, TemptStartTime.getText() + " " + TempEndTime.getText() + " " + TempSegName.getText());
            MyShowList.getItems().clear();
            for (String i : LoadedShow){
                MyShowList.getItems().add(i);
            }
            TempSegName.clear();
            TemptStartTime.clear();
            TempEndTime.clear();
        }
    }

    @FXML
    private void SaveEditedFile() throws IOException {

        if (!LoadedShow.isEmpty()){
            File file = EditSavedShow.getSelectionModel().getSelectedItem();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            writer.write(ShowDate.getValue().toString() + "\n");
            for (String i: LoadedShow){
                writer.write(i);
                writer.write("\n");
            }
            writer.close();
            MyShowList.getItems().clear();
            ShowSegmentName.clear();
            SegmentStartTime.clear();
            SegmentEndTime.clear();
            ShowDate.setValue(null);
        }
    }

    @FXML
    private void SaveEditedTemp() throws IOException {

        if (!LoadedShow.isEmpty()){
            File file = EditSavedShow.getSelectionModel().getSelectedItem();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write("\n");

            for (String i: LoadedShow){
                writer.write(i);
                writer.write("\n");
            }
            writer.close();
            MyShowList.getItems().clear();
            ShowSegmentName.clear();
            SegmentStartTime.clear();
            SegmentEndTime.clear();
        }
    }

    @FXML
    private void DeleteEdit(){
        LoadedShow.remove(MyShowList.getSelectionModel().getSelectedIndex());
        MyShowList.getItems().clear();
        for (String i : LoadedShow){
            MyShowList.getItems().add(i);
        }
        String[] str = LoadedShow.get(0).split(" ");
        ShowSegmentName.setText(str[2]);
        SegmentStartTime.setText(str[0]);
        SegmentEndTime.setText(str[1]);
    }

    @FXML
    private void PrevSegment(){
        if (ArrPointer != 0){
            String[] str = LoadedShow.get(--ArrPointer).split(" ");
            ShowSegmentName.setText(str[2]);
            SegmentStartTime.setText(str[0]);
            SegmentEndTime.setText(str[1]);
        }
    }

    @FXML
    private void closeButtonHandler(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void addSegTemp() throws IOException {

        if (TempSegName.getText().trim().isEmpty() || TemptStartTime .getText().trim().isEmpty()
            || TempEndTime.getText().trim().isEmpty()){
            DialogBoxError("EmptyFieldError");
            return;
        }

        TempSegmentList.getItems().add(TemptStartTime.getText() + " " +
                TempEndTime.getText() + " " + TempSegName.getText());
        TempSegName.clear();
        TemptStartTime.clear();
        TempEndTime.clear();
    }

    @FXML
    private void SaveTemp() throws IOException {
        File TempFile = new File(TempName.getText() + ".tp");
        List<String> segments = TempSegmentList.getItems();
        if (segments.isEmpty()){
            DialogBoxError("EmptyFieldError");
            return;
        }

        String content = String.join("\n", segments);
        BufferedWriter writer = new BufferedWriter(new FileWriter(TempFile));
        writer.write("\n");
        writer.write(content);
        writer.close();
        TempSegmentList.getItems().clear();
        TempName.clear();
    }

    @FXML
    private void LoadTemp() throws IOException {

        File TempFile = EditSavedShow.getSelectionModel().getSelectedItem();
        if (TempFile.exists()){
            BufferedReader reader = new BufferedReader(new FileReader(TempFile));

            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                ShowSegmentList.getItems().add(line);
            }
            reader.close();
        }
    }

    @FXML
    private void clearList(){
        ShowSegmentList.getItems().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //
    }
}
