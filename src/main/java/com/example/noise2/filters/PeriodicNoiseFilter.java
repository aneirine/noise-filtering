package com.example.noise2.filters;

import java.awt.image.BufferedImage;

public class PeriodicNoiseFilter implements Filtering {

    private static final int DEFAULT_FILTER_VALUE = 5;

    @Override
    public BufferedImage filter(BufferedImage image, Double value) {
        int filterValue = DEFAULT_FILTER_VALUE;
        if (value != null) filterValue = value.intValue();
        return removePeriodicNoise(image, filterValue);
    }

    public BufferedImage removePeriodicNoise(BufferedImage image, int filterSize) {
        double threshold = 20;

        int width = image.getWidth();
        int height = image.getHeight();

        // Create a new image for the filtered result
        BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        // Apply the filter to each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Calculate the average of the pixel's neighbors within the filter size
                double average = calculateAverage(image, x, y, filterSize);

                // Calculate the absolute difference between the pixel value and the average
                int pixelValue = image.getRGB(x, y) & 0xFF;
                double difference = Math.abs(pixelValue - average);

                // If the difference exceeds the threshold, update the pixel value with the average
                if (difference > threshold) {
                    filteredImage.setRGB(x, y, (int) average << 16 | (int) average << 8 | (int) average);
                } else {
                    filteredImage.setRGB(x, y, image.getRGB(x, y));
                }
            }
        }

        return this.makeBrighter(filteredImage);
    }

    private double calculateAverage(BufferedImage image, int x, int y, int filterSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        int halfFilterSize = filterSize / 2;
        int startX = Math.max(0, x - halfFilterSize);
        int startY = Math.max(0, y - halfFilterSize);
        int endX = Math.min(width - 1, x + halfFilterSize);
        int endY = Math.min(height - 1, y + halfFilterSize);

        int sum = 0;
        int count = 0;

        for (int j = startY; j <= endY; j++) {
            for (int i = startX; i <= endX; i++) {
                int pixelValue = image.getRGB(i, j) & 0xFF;
                sum += pixelValue;
                count++;
            }
        }

        return (double) sum / count;
    }

    private BufferedImage makeBrighter(BufferedImage image) {
        int brightness = 10;
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage adjustedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Adjust the brightness of each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                // Extract the red, green, and blue components of the pixel
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                // Adjust the brightness of the pixel components
                red = clamp(red + brightness);
                green = clamp(green + brightness);
                blue = clamp(blue + brightness);

                // Combine the adjusted components into a new RGB value
                int adjustedRgb = (red << 16) | (green << 8) | blue;

                // Set the adjusted pixel in the new image
                adjustedImage.setRGB(x, y, adjustedRgb);
            }
        }

        return adjustedImage;
    }


    private static int clamp(int value) {
        return Math.min(Math.max(value, 0), 255);
    }


}
