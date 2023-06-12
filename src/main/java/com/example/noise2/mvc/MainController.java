package com.example.noise2.mvc;

import com.example.noise2.filters.FilterService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;

import java.io.File;

public class MainController extends MainView {

    private FilterService filterService = new FilterService();
    private ImageModel imageModel = new ImageModel();

    @FXML
    public void uploadButtonAction() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageModel.setOriginalImage(image);
            // display in view
            this.displayImage(image);
        }
    }

    @FXML
    public void deleteButtonAction() {
        this.deleteImage();
    }

    @FXML
    public void showOriginal() {
        this.displayImage(imageModel.getOriginalImage());
    }

    public void applyFilter(ActionEvent actionEvent) {
        Button clickedButton = (Button) actionEvent.getSource();
        String buttonValue = clickedButton.getUserData().toString();

        ImageModel.FilterType filterType = ImageModel.FilterType.valueOf(buttonValue);
        imageModel.setImplementedFilter(filterType);
        imageModel.setFilterValue(this.getFilterValue());
        filterService.filter(imageModel);
        displayImage(imageModel.getEditedImage());
    }


}