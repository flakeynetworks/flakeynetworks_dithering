package uk.co.flakeynetworks.imageprocessing.dithering.algorithms;

import uk.co.flakeynetworks.imageprocessing.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author Richard Stokes - Flakeynetworks.co.uk
 */
public class DitheringFloydSteinberg implements DitheringAlgorithm {

    public static final int DEFAULT_QUANTIZED_LEVELS = 3;
    protected int quantizedLevels = DEFAULT_QUANTIZED_LEVELS;


    public DitheringFloydSteinberg() { } // end of constructor


    @Override
    public BufferedImage processImage(BufferedImage originalImage) {

        BufferedImage newImage = ImageUtils.deepCopy(originalImage);

        for(int x = 0; x < newImage.getWidth(); x++) {
            for(int y = 0; y < newImage.getHeight(); y++) {

                // Get the pixel from the original image to work with.
                int originalPixel = newImage.getRGB(x, y);
                Color originalColor = new Color(originalPixel);

                // Quantize the pixel.
                Color newColor = quantizeColor(originalColor);
                newImage.setRGB(x, y, newColor.getRGB());

                // Calculate the error
                int errorRed = originalColor.getRed() - newColor.getRed();
                int errorGreen = originalColor.getGreen() - newColor.getGreen();
                int errorBlue = originalColor.getBlue() - newColor.getBlue();
                int[] errorColors = {errorRed, errorGreen, errorBlue};

                // Set the error to the other pixels
                Color errorPixel;
                double errorCorrection = 7.00 / 16.00;
                Color errorCorrectedColor;

                try {
                    errorPixel = new Color(newImage.getRGB(x + 1, y));
                    errorCorrectedColor = calcaulateError(errorPixel, errorCorrection, errorColors);
                    newImage.setRGB(x + 1, y, errorCorrectedColor.getRGB());
                } catch (ArrayIndexOutOfBoundsException e) { } // end of catch

                try {
                    errorPixel = new Color(newImage.getRGB(x - 1, y + 1));
                    errorCorrection = 3.00 / 16.00;
                    errorCorrectedColor = calcaulateError(errorPixel, errorCorrection, errorColors);
                    newImage.setRGB(x - 1, y + 1, errorCorrectedColor.getRGB());
                } catch (ArrayIndexOutOfBoundsException e) { } // end of catch

                try {
                    errorPixel = new Color(newImage.getRGB(x , y + 1));
                    errorCorrection = 5.00 / 16.00;
                    errorCorrectedColor = calcaulateError(errorPixel, errorCorrection, errorColors);
                    newImage.setRGB(x, y + 1, errorCorrectedColor.getRGB());
                } catch (ArrayIndexOutOfBoundsException e) { } // end of catch

                try {
                    errorPixel = new Color(newImage.getRGB(x + 1, y + 1));
                    errorCorrection = 1.00 / 16.00;
                    errorCorrectedColor = calcaulateError(errorPixel, errorCorrection, errorColors);
                    newImage.setRGB(x + 1, y + 1, errorCorrectedColor.getRGB());
                } catch (ArrayIndexOutOfBoundsException e) { } // end of catch
            } // end of for
        } // end of for

        return newImage;
    } // end of processImage


    private Color calcaulateError(Color errorPixel, double errorCorrection, int[] errorColors) {

        int quantErrorRed = errorPixel.getRed() + (int) (errorColors[0] * errorCorrection);
        if(quantErrorRed < 0) quantErrorRed = 0;
        if(quantErrorRed > 255) quantErrorRed = 255;

        int quantErrorGreen = errorPixel.getGreen() + (int) (errorColors[1] * errorCorrection);
        if(quantErrorGreen < 0) quantErrorGreen = 0;
        if(quantErrorGreen > 255) quantErrorGreen = 255;

        int quantErrorBlue = errorPixel.getBlue() + (int) (errorColors[2] * errorCorrection);
        if(quantErrorBlue < 0) quantErrorBlue = 0;
        if(quantErrorBlue > 255) quantErrorBlue = 255;

        return new Color(quantErrorRed, quantErrorGreen, quantErrorBlue);
    } // end of calculateError


    public Color quantizeColor(Color color) {

        if(color == null) return null;

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getGreen();

        double levelsFactor = 255 / (double) quantizedLevels;

        int newRed = (int) (Math.round(quantizedLevels * red / 255.00) * levelsFactor);
        int newGreen = (int) (Math.round(quantizedLevels * green / 255.00) * levelsFactor);
        int newBlue = (int) (Math.round(quantizedLevels * blue / 255.00) * levelsFactor);

        return new Color(newRed, newGreen, newBlue);
    } // end of quantizeColor


    public void setQuantizeLevels(int levels) {

        if(levels < 0) levels = 0;
        if(levels > 255) levels = 255;

        quantizedLevels = levels;
    } // end of setQuantizeLevels
} // end of DitheringFloydSteinberg
