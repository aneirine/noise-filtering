package com.example.noise2.filters;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MedianFiltering implements Filtering {

    private static final int DEFAULT_FILTER_VALUE = 3;

    @Override
    public BufferedImage filter(BufferedImage image, Double value) {
        int filterValue = DEFAULT_FILTER_VALUE;
        if (value != null) filterValue = value.intValue();

        return applyMedianFilter(image, filterValue);
    }

    public static BufferedImage applyMedianFilter(BufferedImage image, int filterSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage filteredImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] neighborhoodPixels = getNeighborhoodPixels(image, x, y, filterSize);
                int medianValue = calculateMedian(neighborhoodPixels);
                filteredImage.setRGB(x, y, medianValue);
            }
        }

        return filteredImage;
    }

    private static int[] getNeighborhoodPixels(BufferedImage image, int x, int y, int filterSize) {
        int halfFilterSize = filterSize / 2;
        int[] neighborhoodPixels = new int[filterSize * filterSize];

        int index = 0;
        for (int j = -halfFilterSize; j <= halfFilterSize; j++) {
            for (int i = -halfFilterSize; i <= halfFilterSize; i++) {
                int newX = x + i;
                int newY = y + j;

                // Handle edge cases
                if (newX < 0 || newX >= image.getWidth() || newY < 0 || newY >= image.getHeight()) {
                    neighborhoodPixels[index] = image.getRGB(x, y);
                } else {
                    neighborhoodPixels[index] = image.getRGB(newX, newY);
                }

                index++;
            }
        }

        return neighborhoodPixels;
    }

    private static int calculateMedian(int[] pixels) {
        int[] sortedPixels = pixels.clone();
        Arrays.sort(sortedPixels);
        return sortedPixels[pixels.length / 2];
    }
}




