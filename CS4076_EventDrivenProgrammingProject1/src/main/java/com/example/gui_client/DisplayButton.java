package com.example.gui_client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class DisplayButton extends GridPane {
    private String[] suggestions = new String[24 * 4];
    private String[] classList = {"Computer Systems(lm051)","Philosophy (mf041)","Mechanical Engineering (lm060)","Arts (mf042)"};
    private String[] rooms =  {"Kemmy Business School G01","Kemmy Business School G02","Computer Science G001","Computer Science G002","Foundation Building 042",} ;
    private String userInput ;
    @FXML
    private Label heading = new Label("Book class time") ;
    @FXML
    private Label enterClass = new Label("Class:") ;
    @FXML
    private Label enterRoom = new Label("Room:") ;
    @FXML
    private Label enterFrom = new Label("From:") ;
    @FXML
    private Label enterTo = new Label("To:") ;
    @FXML
    private Label enterDate = new Label("Date:") ;
    @FXML
    private DatePicker datePicker = new DatePicker() ;
    @FXML
    private TextField getClass = new TextField() ;
    @FXML
    private TextField getRoom = new TextField() ;
    @FXML
    private ComboBox<String> getToTime = new ComboBox<String>() ;
    @FXML
    private ComboBox<String> getFromTime = new ComboBox<String>() ;
    @FXML
    private Button submitButton = new Button("Book") ;
    @FXML
    private HBox date = new HBox(enterDate,datePicker) ;
    @FXML
    private HBox classCode = new HBox(enterClass,getClass) ;
    @FXML
    private HBox roomCode = new HBox(enterRoom,getRoom) ;
    @FXML
    private HBox codes = new HBox(classCode,roomCode) ;
    @FXML
    private HBox startTime = new HBox(enterFrom,getToTime) ;
    @FXML
    private HBox endTime = new HBox(enterTo,getFromTime) ;
    @FXML
    private HBox time = new HBox(startTime,endTime) ;
    @FXML
    private VBox details = new VBox(date,codes,time,submitButton) ;
    @FXML
    private Rectangle border = new Rectangle();
    public DisplayButton(){

        //Heading
        heading.setFont(new Font(heading.getFont().getName(),20));
        GridPane.setMargin(heading,new Insets(10));
        GridPane.setHalignment(heading, HPos.CENTER);
        GridPane.setHalignment(details,HPos.CENTER);

        //Prompt labels
        getClass.setPromptText("lm051");
        getRoom.setPromptText("KBG10");
        HBox.setMargin(enterClass,new Insets(0,5,0,0));
        HBox.setMargin(enterDate,new Insets(0,5,0,0));
        HBox.setMargin(enterRoom,new Insets(0,5,0,0));
        HBox.setMargin(enterTo,new Insets(0,5,0,0));
        HBox.setMargin(enterFrom,new Insets(0,5,0,0));


        //time autofill
        suggestions = fillTimeArray(suggestions) ;
        getToTime.setPromptText("13:00");
        getToTime.setEditable(true);
        getToTime.getEditor().textProperty().addListener((observable,oldValue,newValue)-> {
            autoFillBox(getToTime ,newValue,suggestions);
        });
        getFromTime.setPromptText("13:00");
        getFromTime.setEditable(true);
        getFromTime.getEditor().textProperty().addListener((observable,oldValue,newValue)-> {
            autoFillBox(getFromTime ,newValue,suggestions);
        });


        //DATE
        date.setPadding(new Insets(10));
        date.setAlignment(Pos.CENTER);
        //CODES
        classCode.setPadding(new Insets(10));

        roomCode.setPadding(new Insets(10));
        //TIME
        startTime.setPadding(new Insets(10));
        endTime.setPadding(new Insets(10));

        //DETAILS
        this.widthProperty().addListener((observable,oldValue,newValue) -> {
            getClass.setPrefWidth(newValue.doubleValue() / 4);
            getRoom.setPrefWidth(newValue.doubleValue() / 4);
            getToTime.setPrefWidth(newValue.doubleValue() / 4);
            getFromTime.setPrefWidth(newValue.doubleValue() / 4);
        });
        details.setAlignment(Pos.CENTER);

        //adding to parent node
        this.add(details,0,1);
        this.add(heading,0,0);

        //Border
        Rectangle border = new Rectangle();
        border.widthProperty().bind(this.widthProperty());
        border.heightProperty().bind(this.heightProperty());
        border.setStroke(Color.BLACK);
        border.setFill(null);
        border.setStrokeWidth(5);

        //Book button
        userInput = "" ;
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                userInput += "Date:" + datePicker.getValue().toString() ;
                userInput += ",ClassCode:" + getClass.getText();
                userInput += ",RoomCode:" + getRoom.getText();
                userInput += ",From:" + getFromTime.getValue();
                userInput += ",To:" + getToTime.getValue() ;

                //
                System.out.println("user input = " + userInput);
                //
            }
        });


   }

    private static void autoFillBox(ComboBox box,String newValue ,String[] suggestions){
        box.getItems().clear();
        String input = newValue ;

        for (String suggestion:suggestions ){
             if (suggestion.substring(0, input.length()).equals(input))
                box.getItems().add(suggestion);
        }
    }
    /**
     *Fills the suggestions array with times incrementing by 15mins
     * @Param suggestions String[]
    */
    private static String[] fillTimeArray(String[] suggestions){
        int number = 0;

        for (int hour = 0; hour < 24; hour++) {
            for (int minute = 0; minute < 4; minute++) {
                String time = String.format("%02d:%02d", hour, minute * 15);
                suggestions[minute + (hour * 4)] = time;
            }
        }
        return suggestions ;
    }
}
