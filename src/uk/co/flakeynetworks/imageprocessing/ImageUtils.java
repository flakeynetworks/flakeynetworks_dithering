package uk.co.flakeynetworks.imageprocessing;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class ImageUtils {

    public static BufferedImage deepCopy(BufferedImage bi) {

        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    } // end of deepCopy


    public BufferedImage convertToGreyScale(BufferedImage image) {

        for(int x = 0; x < image.getWidth(); x++) {

            for(int y = 0; y < image.getHeight(); y++) {

                int pixel = image.getRGB(x, y);
                Color color = new Color(pixel);

                color = convertToGreyScale(color);
                image.setRGB(x, y, color.getRGB());
            } // end of for
        } // end of for

        return image;
    } // end of convertToGreyScale


    public Color convertToGreyScale(Color color) {

        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();

        int total = red + green + blue;
        int average = (int) Math.round(total / 3);

        return new Color(average, average, average);
    } // end of convertToGreyScale
} // end of ImageUtils
