package com.example.noise2.filters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WienerFilter implements Filtering {

    @Override
    public BufferedImage filter(BufferedImage image, Double value) {
        return applyWienerFilter(image, 100, 5);
    }

    public static BufferedImage applyWienerFilter(BufferedImage inputImage, double noiseVariance, int windowSize) {
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int halfWindow = windowSize / 2;
        double inverseWindowSize = 1.0 / (windowSize * windowSize);

        // Convert the input image to a 2D array of grayscale values
        int[][] inputArray = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = inputImage.getRGB(i, j);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                int grayscale = (red + green + blue) / 3;
                inputArray[i][j] = grayscale;
            }
        }

        // Apply the Wiener filter
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                double sum = 0.0;
                double weightSum = 0.0;

                for (int m = i - halfWindow; m <= i + halfWindow; m++) {
                    for (int n = j - halfWindow; n <= j + halfWindow; n++) {
                        if (m >= 0 && m < width && n >= 0 && n < height) {
                            double distance = (i - m) * (i - m) + (j - n) * (j - n);
                            double weight = Math.exp(-distance / (2 * noiseVariance));

                            sum += weight * inputArray[m][n];
                            weightSum += weight;
                        }
                    }
                }

                double filteredValue = (weightSum > 0) ? sum / weightSum : inputArray[i][j];
                int filteredIntensity = (int) Math.round(filteredValue);
                Color filteredColor = new Color(filteredIntensity, filteredIntensity, filteredIntensity);
                outputImage.setRGB(i, j, filteredColor.getRGB());
            }
        }

        return outputImage;
    }

}

