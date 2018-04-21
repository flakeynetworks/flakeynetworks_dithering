package uk.co.flakeynetworks.imageprocessing.dithering.test;

import uk.co.flakeynetworks.imageprocessing.dithering.algorithms.DitheringFloydSteinberg;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class DitheringTest {

    public static void main(String[] args) {

        try {

            DitheringFloydSteinberg algorithm = new DitheringFloydSteinberg();
            algorithm.setQuantizeLevels(1);

            //File image = new File("res\\Lenna.png");
            File image = new File("res\\Michelangelo's_David_-_63_grijswaarden.png");


            final BufferedImage normal = ImageIO.read(image);

            final BufferedImage dithered = algorithm.processImage(ImageIO.read(image));

            JFrame frame = new JFrame("Flakeynetworks Dithering Test");
            frame.setLayout(new GridLayout(1, 2));

            frame.add(new JComponent() {
                private static final long serialVersionUID = 2963702769416707676L;

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(normal, 0, 0, this);
                }
            });

            frame.add(new JComponent() {
                private static final long serialVersionUID = -6919658458441878769L;

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    g.drawImage(dithered, 0, 0, this);
                }
            });

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(normal.getWidth() * 2, normal.getHeight());

            Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
            int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
            frame.setLocation(x, y);

            frame.setVisible(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } // end of catch
    } // end of main
} // end of DitheringTest
