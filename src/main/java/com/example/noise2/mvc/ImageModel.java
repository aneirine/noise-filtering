package com.example.noise2.mvc;

import javafx.scene.image.Image;

public class ImageModel {

    public enum FilterType {
        MEDIAN, GAUSSIAN, PERIODIC, WIENER
    }

    private FilterType implementedFilter;
    private Image originalImage;
    private Image editedImage;
    private String filterValue;

    public void setImplementedFilter(FilterType implementedFilter) {
        this.implementedFilter = implementedFilter;
    }

    public ImageModel setOriginalImage(Image originalImage) {
        this.originalImage = originalImage;
        return this;
    }

    public ImageModel setEditedImage(Image editedImage) {
        this.editedImage = editedImage;
        return this;
    }

    public FilterType getImplementedFilter() {
        return implementedFilter;
    }

    public Image getOriginalImage() {
        return originalImage;
    }

    public Image getEditedImage() {
        return editedImage;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public ImageModel setFilterValue(String filterValue) {
        this.filterValue = filterValue;
        return this;
    }
}


