package com.example.noise2.filters;

import com.example.noise2.mvc.ImageModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;


public class FilterService {

    private MedianFiltering medianFiltering = new MedianFiltering();
    private GaussianSmoothing gaussianSmoothing = new GaussianSmoothing();
    private PeriodicNoiseFilter periodicNoiseFilter = new PeriodicNoiseFilter();
    private WienerFilter wienerFilter = new WienerFilter();


    public void filter(ImageModel model) {
        Image image = model.getOriginalImage();
        if (image != null) {
            Filtering filtering = this.defineFilter(model.getImplementedFilter());
            BufferedImage inputImage = SwingFXUtils.fromFXImage(image, null);
            var filtered = filtering.filter(inputImage, getValue(model.getFilterValue()));
            model.setEditedImage(SwingFXUtils.toFXImage(filtered, null));
        } else {
            System.out.println("No image");
        }
    }

    private Double getValue(String fieldValue) {
        Double value = null;
        if (fieldValue != null && fieldValue.trim().length() > 0) {
            value = Double.valueOf(fieldValue);
        }
        return value;
    }

    private Filtering defineFilter(ImageModel.FilterType filterType) {
        switch (filterType) {
            case MEDIAN:
                System.out.println("median");
                return medianFiltering;
            case GAUSSIAN:
                System.out.println("gaussian");
                return gaussianSmoothing;
            case PERIODIC:
                System.out.println("periodic");
                return periodicNoiseFilter;
            case WIENER:
                System.out.println("wiener");
                return wienerFilter;
            default:
                System.out.println("none");
                return null;
        }
    }
}
