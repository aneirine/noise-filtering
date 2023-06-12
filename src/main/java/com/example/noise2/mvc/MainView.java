package com.example.noise2.mvc;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;

public class MainView {

    @FXML
    public javafx.scene.image.ImageView imageView;
    @FXML
    public HBox initialButtonsBox;
    @FXML
    public HBox additionalButtonsBox;
    @FXML
    public TextField numberField;

    public String getFilterValue() {
        return numberField.getText();
    }

    public void displayImage(Image image) {
        imageView.setImage(image);
        imageView.setImage(image);
        imageView.setVisible(true);

        additionalButtonsBox.setVisible(true);
        this.hideInitials();
    }

    public void deleteImage() {
        imageView.setImage(null);
        additionalButtonsBox.setVisible(false);
        this.initialButtons();
    }

    private void initialButtons() {
        initialButtonsBox.setVisible(true);
        initialButtonsBox.setManaged(true);
    }

    private void hideInitials() {
        initialButtonsBox.setVisible(false);
        initialButtonsBox.setManaged(false);
    }
}
