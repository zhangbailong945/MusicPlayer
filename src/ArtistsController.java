package musicplayer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import java.util.Collections;
import javafx.geometry.Insets;
import javafx.scene.control.OverrunStyle;

public class ArtistsController implements Initializable {

    @FXML private ScrollPane scroll;
    @FXML private FlowPane grid;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Artist> artists = FXCollections.observableArrayList(Library.getArtists());
        Collections.sort(artists);

        for (Artist artist : artists) {

            VBox cell = new VBox();
            Label title = new Label(artist.getTitle());
            ImageView image = new ImageView(artist.getArtistImage());

            title.setTextOverrun(OverrunStyle.CLIP);
            title.setWrapText(true);
            title.setPrefHeight(50);
            title.prefWidthProperty().bind(grid.widthProperty().divide(4).subtract(52));

            image.fitWidthProperty().bind(grid.widthProperty().divide(4).subtract(52));
            image.fitHeightProperty().bind(grid.widthProperty().divide(4).subtract(52));
            image.setPreserveRatio(true);
            image.setSmooth(true);
            image.setCache(true);

            cell.getChildren().addAll(image, title);
            cell.setPadding(new Insets(10, 10, 0, 10));
            cell.getStyleClass().add("artist-cell");
            cell.setOnMouseClicked(event -> {

                MainController mainController = MusicPlayer.getMainController();
                ArtistsMainController artistsMainController = (ArtistsMainController) mainController.loadView("artistsMain");

                VBox artistCell = (VBox) event.getSource();
                String artistTitle = ((Label) artistCell.getChildren().get(1)).getText();
                Artist a = Library.getArtists().stream().filter(x -> artistTitle.equals(x.getTitle())).findFirst().get();
                artistsMainController.selectArtist(a);
            });

            grid.getChildren().add(cell);
            grid.setMargin(cell, new Insets(25, 25, 0, 0));
        }

        scroll.getStyleClass().add("scroll-pane");
        grid.getStyleClass().add("flow-pane");

        int rows = (artists.size() % 4 == 0) ? artists.size() / 4 : artists.size() / 4 + 1;
        grid.prefHeightProperty().bind(grid.widthProperty().divide(4).add(18).multiply(rows));
    }
}