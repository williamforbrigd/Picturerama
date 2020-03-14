package Scenes;

import Css.Css;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

import Components.Album;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Albums extends SceneBuilder {

    private static ArrayList<Button> albumButtons = new ArrayList<>();
    private static ArrayList<Album> albumList;

    private static ScrollPane scrollPane = new ScrollPane();
    private static VBox scrollPaneVbox = new VBox();

    private static Button newAlbumButton = new Button("New album");

    private static TextField nameAlbumInput = new TextField();

    private static Stage dialogWindow = new Stage();
    private static VBox dialogVbox = new VBox(20);


    public Albums() {
        super();

        albumList = new ArrayList<>();

        //this.updateAlbumList();
        this.setLayout();

    }

    @Override
    public void setLayout() {
        super.setLayout();
        super.setGridPane();
        super.setPageTitle("Albums");

        scrollPaneVbox.setPadding(new Insets(10,10,10,10));
        scrollPaneVbox.setSpacing(10);

        this.addScrollPane();
        //this.addAlbumsScrollPane();
        this.createNewAlbumButton();
        this.styleButtons();
    }

    /*
    private void updateAlbumList() {
        try {
            DBConnection.getAlbums().forEach(album -> {
                if(!albumList.contains(album)) {
                    albumList.add(album);
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

     */

    /*
    private void addAlbumsScrollPane() {
        DBConnection.getAlbums().forEach(album -> {
            if(!DBConnection.getAlbums().contains(album)) {
                Button albumButton = new Button(album.getAlbumName());
                albumButtons.add(albumButton);
                scrollPaneVbox.getChildren().add(albumButton);
            }
        });
    }

     */


    private void addScrollPane() {
        scrollPane.setContent(scrollPaneVbox);
        scrollPane.setPrefSize(500, 500);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        super.getGridPane().add(scrollPane, 0, 0);
    }


    private void styleButtons() {
        albumButtons.forEach(Css::setAlbumButton);
    }


    private void createNewAlbumButton() {
        super.getBorderPane().setBottom(newAlbumButton);
        BorderPane.setAlignment(newAlbumButton, Pos.CENTER);
        newAlbumButton.setOnAction(e -> createNewAlbumButtonPressed());
    }

    private void createNewAlbumButtonPressed() {
        this.createPopupDialog();
        Label text = new Label("Please enter the name of the album:");

        nameAlbumInput.setPromptText("Name for the album");
        nameAlbumInput.setMinWidth(100);

        Button addAlbum = new Button("Add album");
        addAlbum.setOnAction(e -> {
            addAlbumButtonPressed();
            nameAlbumInput.clear();
            dialogWindow.close();
        });

        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameAlbumInput, addAlbum);

        dialogVbox.getChildren().addAll(text,hBox);
    }

    private void createPopupDialog() {
        dialogWindow = new Stage();
        dialogWindow.initModality(Modality.APPLICATION_MODAL);
        dialogVbox = new VBox();
        dialogVbox.setAlignment(Pos.CENTER);
        Scene dialogScene = new Scene(dialogVbox, 300, 100);
        dialogWindow.setScene(dialogScene);
        dialogWindow.show();
    }



    private void addAlbumButtonPressed() {
        //DBConnection.registerAlbum(nameAlbumInput.getText());
        //addAlbumsScrollPane();

        Button albumButton = new Button(nameAlbumInput.getText());
        albumButtons.add(albumButton);
        Css.setAlbumButton(albumButton);

        scrollPaneVbox.getChildren().add(albumButton);
        scrollPaneVbox.setAlignment(Pos.BOTTOM_RIGHT);
        //TODO these three lines of codes can be done in the addAlbumsSrollPane()-method.
    }






}
