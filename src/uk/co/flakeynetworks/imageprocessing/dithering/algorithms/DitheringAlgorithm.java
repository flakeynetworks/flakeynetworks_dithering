package uk.co.flakeynetworks.imageprocessing.dithering.algorithms;

import java.awt.image.BufferedImage;

/**
 *
 * @author Richard Stokes - Flakeynetworks.co.uk
 */
public interface DitheringAlgorithm {

    BufferedImage processImage(BufferedImage originalImage);
} // end of DitheringAlgorithm
