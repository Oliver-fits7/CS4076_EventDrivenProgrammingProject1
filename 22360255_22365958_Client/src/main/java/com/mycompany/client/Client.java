package com.mycompany.client;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Rectangle2D;

/**
 * Our entry point to the client gui application.
 * This creates the main scene and switches between the start screen and main screen.
 */
public class Client extends Application{
    private final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    private ReactiveButton addBtn = new ReactiveButton("Add Class");
    private ReactiveButton removeBtn = new ReactiveButton("Remove Class");
    private Button displayBtn = new Button("Display Class Information");
    private Button terminateBtn = new Button("Terminate Connection");
    /**
     * This label is included in order to give the user responsiveness.
     * Things like "connection successful" and "added class succesfully"
     */
    private Label serverResponseLbl = new Label("My Label");


    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene commandScene = createCommandScene();
        primaryStage.setScene(commandScene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch();
    }

    /**
     * Creates the main screen scene. It initializes components and sets width and height.
     * @return Scene containing the children for the main menu. Those being HBox's and VBox's as well as the relevant buttons.
     */
    public Scene createCommandScene(){
        //Here we create the basic layout structure of the scene.
        HBox btnBox = new HBox(addBtn, removeBtn, displayBtn);
        VBox menuBox = new VBox(btnBox, terminateBtn, serverResponseLbl);
        Scene commandScene = new Scene(menuBox, screenBounds.getWidth()/3, screenBounds.getHeight()/3);

        //As I've learned spacing should be done in the java not the css
        //due to bindings(can't have percentages in javafx css, as opposed to normal)
        //You may ask yourself why I picked these values?
        //Very precise and arduous research
        menuBox.prefWidthProperty().bind(commandScene.widthProperty());
        menuBox.prefHeightProperty().bind(commandScene.heightProperty());
        menuBox.spacingProperty().bind(Bindings.divide(menuBox.prefHeightProperty(), 8));

        btnBox.prefWidthProperty().bind(Bindings.divide(menuBox.prefWidthProperty(), 1.5));
        btnBox.prefHeightProperty().bind(Bindings.divide(menuBox.prefHeightProperty(), 3));

        btnBox.spacingProperty().bind(Bindings.divide(btnBox.prefWidthProperty(), 8));

        //Adding a stylesheet to the scene
        commandScene.getStylesheets().add(String.valueOf(Client.class.getResource("commandSceneStyleSheet.css")));

        return commandScene;
    }
}