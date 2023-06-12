package com.example.noise2.filters;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HampelIdentifier {

    // Function to calculate the median of a list of numbers
    private static double calculateMedian(List<Double> data) {
        Collections.sort(data);
        int size = data.size();
        if (size % 2 == 0) {
            int mid = size / 2;
            return (data.get(mid - 1) + data.get(mid)) / 2.0;
        } else {
            return data.get(size / 2);
        }
    }

    // Function to calculate the median absolute deviation (MAD) of a list of numbers
    private static double calculateMAD(List<Double> data, double median) {
        List<Double> absoluteDeviations = new ArrayList<>();
        for (double value : data) {
            absoluteDeviations.add(Math.abs(value - median));
        }
        return calculateMedian(absoluteDeviations);
    }

    // Function to detect outliers using the Hampel identifier
    public static List<Double> detectOutliers(List<Double> data, double threshold) {
        List<Double> outliers = new ArrayList<>();
        double median = calculateMedian(data);
        double mad = calculateMAD(data, median);
        for (double value : data) {
            double deviation = Math.abs(value - median) / mad;
            if (deviation > threshold) {
                outliers.add(value);
            }
        }
        return outliers;
    }

    static int windowSize = 3;
    static double threshold = 3.0;

    public BufferedImage detectOutliers(BufferedImage image /*int windowSize, double threshold*/) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        int halfWindow = windowSize / 2;

        for (int y = halfWindow; y < height - halfWindow; y++) {
            for (int x = halfWindow; x < width - halfWindow; x++) {
                double[] pixelValues = new double[windowSize * windowSize];
                int count = 0;

                for (int j = -halfWindow; j <= halfWindow; j++) {
                    for (int i = -halfWindow; i <= halfWindow; i++) {
                        int pixel = image.getRGB(x + i, y + j);
                        Color color = new Color(pixel);
                        int grayLevel = color.getRed(); // Assuming grayscale image

                        pixelValues[count++] = grayLevel;
                    }
                }

                var pixelList = Arrays.asList(Arrays.stream(pixelValues).boxed().toArray(Double[]::new));
                double median = calculateMedian(pixelList);
                double mad = calculateMAD(pixelList, median);
                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel);
                int grayLevel = color.getRed(); // Assuming grayscale image

                double deviation = Math.abs(grayLevel - median) / mad;
                if (deviation > threshold) {
                    result.setRGB(x, y, Color.RED.getRGB()); // Mark as outlier in the result image
                } else {
                    result.setRGB(x, y, pixel); // Copy original pixel value
                }
            }
        }

        return result;
    }

}
