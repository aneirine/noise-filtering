package com.example.noise2.filters;

import java.awt.image.BufferedImage;

public class GaussianSmoothing implements Filtering {

    private static final double DEFAULT_SIGMA = 1.5;

    @Override
    public BufferedImage filter(BufferedImage image, Double value) {
        double sigma = DEFAULT_SIGMA;
        if (value != null) sigma = value;
        return applyGaussianSmoothing(image, sigma);
    }

    public BufferedImage applyGaussianSmoothing(BufferedImage image, Double sigma) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Create a new image for the smoothed result
        BufferedImage smoothedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Create the Gaussian kernel
        int kernelSize = (int) Math.ceil(sigma * 6);
        if (kernelSize % 2 == 0) {
            kernelSize++; // Ensure an odd kernel size
        }
        double[][] kernel = createGaussianKernel(kernelSize, sigma);

        // Apply the Gaussian filter to each pixel in the image
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                double redSum = 0;
                double greenSum = 0;
                double blueSum = 0;
                double weightSum = 0;

                // Convolve the kernel with the neighborhood of the pixel
                for (int j = -kernelSize / 2; j <= kernelSize / 2; j++) {
                    for (int i = -kernelSize / 2; i <= kernelSize / 2; i++) {
                        int neighborX = Math.min(Math.max(x + i, 0), width - 1);
                        int neighborY = Math.min(Math.max(y + j, 0), height - 1);

                        int rgb = image.getRGB(neighborX, neighborY);
                        double weight = kernel[j + kernelSize / 2][i + kernelSize / 2];

                        redSum += ((rgb >> 16) & 0xFF) * weight;
                        greenSum += ((rgb >> 8) & 0xFF) * weight;
                        blueSum += (rgb & 0xFF) * weight;
                        weightSum += weight;
                    }
                }

                // Normalize the weighted sum and set the smoothed pixel in the new image
                int smoothedRed = (int) (redSum / weightSum);
                int smoothedGreen = (int) (greenSum / weightSum);
                int smoothedBlue = (int) (blueSum / weightSum);
                int smoothedRgb = (smoothedRed << 16) | (smoothedGreen << 8) | smoothedBlue;
                smoothedImage.setRGB(x, y, smoothedRgb);
            }
        }

        return smoothedImage;
    }

    private double[][] createGaussianKernel(int size, double sigma) {
        double[][] kernel = new double[size][size];
        double twoSigmaSquared = 2 * sigma * sigma;
        double normalizationFactor = 0;

        // Calculate the Gaussian kernel values
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                int x = i - size / 2;
                int y = j - size / 2;
                double exponent = (x * x + y * y) / twoSigmaSquared;
                kernel[j][i] = Math.exp(-exponent);
                normalizationFactor += kernel[j][i];
            }
        }

        // Normalize the kernel
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                kernel[j][i] /= normalizationFactor;
            }
        }

        return kernel;
    }


}
