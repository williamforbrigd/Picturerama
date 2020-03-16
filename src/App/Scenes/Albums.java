package Scenes;

import Css.Css;
import Database.DBConnection;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Albums extends SceneBuilder {

    private ArrayList<Button> albumButtons = new ArrayList<>();

    private ScrollPane scrollPane = new ScrollPane();
    private VBox scrollPaneVbox = new VBox();

    private Button newAlbumButton = new Button("New Album");

    private TextField nameAlbumInput = new TextField();

    private Stage dialogWindow;
    private VBox dialogVbox;
    private HBox dialogHBox;
    private Text dialogText;


    public Albums() {
        super();
        this.setLayout();
    }

    @Override
    public void setLayout() {
        super.setLayout();
        super.setGridPane();
        super.setPageTitle("Albums");

        this.addScrollPane();
        this.addAlbumsScrollPane();
        this.addButtonsToBorderPane();
        this.styleButtons();
    }


    private void addAlbumsScrollPane() {
        try {
            DBConnection.getAlbums().forEach(album -> {
                Button albumButton = new Button(album.getAlbumName());
                albumButtons.add(albumButton);
                Css.setAlbumButtons(albumButton);
                scrollPaneVbox.getChildren().add(albumButton);
            });
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void addScrollPane() {
        scrollPaneVbox.setPadding(new Insets(10,10,10,10));
        scrollPaneVbox.setSpacing(10);
        scrollPane.setContent(scrollPaneVbox);
        scrollPane.setPrefSize(700, 700);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        Css.setAlbumScrollPaneBorder(scrollPane);
        super.getGridPane().add(scrollPane, 0, 0);
    }
    
    private void styleButtons() {
        albumButtons.forEach(Css::setAlbumButtons);
        Css.setNewAlbumButton(newAlbumButton);
    }

    private void addButtonsToBorderPane() {
        HBox hBox = new HBox();
        hBox.getChildren().addAll(newAlbumButton);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setSpacing(20);
        hBox.setPadding(new Insets(10,10,10,10));
        super.getBorderPane().setBottom(hBox);
        BorderPane.setAlignment(hBox, Pos.CENTER);

        newAlbumButton.setOnAction(e -> createNewAlbumButtonPressed());
    }

    private void createNewAlbumButtonPressed() {
        createPopupDialog();

        dialogWindow.getIcons().add(new Image("file:src/App/Images/Logo.png"));
        dialogWindow.setTitle("Add Album");

        dialogText.setText("Please enter the name of the album to be added: ");
        Css.setTextAlbums(dialogText);

        nameAlbumInput.setPromptText("Album name");
        Css.setTextFieldAlbums(nameAlbumInput);

        Button addAlbum = new Button("Add album");
        Css.setAddAlbumButton(addAlbum);
        addAlbum.setOnAction(e -> {
            addAlbumButtonPressed();
            nameAlbumInput.clear();
            dialogWindow.close();
        });

        dialogHBox.getChildren().addAll(nameAlbumInput, addAlbum);
        dialogVbox.getChildren().addAll(dialogText, dialogHBox);
    }

    private void createPopupDialog() {
        dialogWindow = new Stage();
        dialogWindow.initModality(Modality.APPLICATION_MODAL);

        dialogVbox = new VBox();
        dialogVbox.setAlignment(Pos.CENTER);

        dialogText = new Text();

        dialogHBox = new HBox();
        dialogHBox.setPadding(new Insets(10,10,10,10));
        dialogHBox.setSpacing(10);

        Scene dialogScene = new Scene(dialogVbox, 500, 100);
        dialogWindow.setScene(dialogScene);
        dialogWindow.show();
    }

    private void addAlbumButtonPressed() {
        DBConnection.registerAlbum(nameAlbumInput.getText());
        Button albumButton = new Button(nameAlbumInput.getText());
        albumButtons.add(albumButton);
        Css.setAlbumButtons(albumButton);
        scrollPaneVbox.getChildren().add(albumButton);
    }
}
