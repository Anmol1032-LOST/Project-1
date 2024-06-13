package com.anmol.games;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class OnError {
    public static void error(Thread t, Throwable e) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException error) {
            throw new RuntimeException(error);
        }

        JFrame frame = new JFrame("Creator's Message");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dimension = new Dimension(d.width * 3 / 4, d.height * 3 / 4);
        frame.setSize(dimension);
        frame.setMinimumSize(dimension);
        frame.setMaximumSize(dimension);
        frame.setPreferredSize(dimension);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);

        try {
            Class<?> clazz = LOST.class;
            frame.setIconImages(List.of(new BufferedImage[]{
                    ImageIO.read(Objects.requireNonNull(clazz.getResourceAsStream("/Interface/Icon/Icon0001.png"))),
                    ImageIO.read(Objects.requireNonNull(clazz.getResourceAsStream("/Interface/Icon/Icon0002.png"))),
                    ImageIO.read(Objects.requireNonNull(clazz.getResourceAsStream("/Interface/Icon/Icon0004.png"))),
                    ImageIO.read(Objects.requireNonNull(clazz.getResourceAsStream("/Interface/Icon/Icon0008.png"))),
                    ImageIO.read(Objects.requireNonNull(clazz.getResourceAsStream("/Interface/Icon/Icon0016.png"))),
                    ImageIO.read(Objects.requireNonNull(clazz.getResourceAsStream("/Interface/Icon/Icon0032.png"))),
                    ImageIO.read(Objects.requireNonNull(clazz.getResourceAsStream("/Interface/Icon/Icon0064.png"))),
                    ImageIO.read(Objects.requireNonNull(clazz.getResourceAsStream("/Interface/Icon/Icon0128.png"))),
                    ImageIO.read(Objects.requireNonNull(clazz.getResourceAsStream("/Interface/Icon/Icon0256.png"))),
                    ImageIO.read(Objects.requireNonNull(clazz.getResourceAsStream("/Interface/Icon/Icon4096.png"))),
            }));
        } catch (IOException error) {
            throw new RuntimeException(error);
        }

        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(Color.BLACK);

        JLabel label = getjLabel(dimension, e);

        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static JLabel getjLabel(Dimension dimension, Throwable e) {
        String text = "<html><center>Sorry for that some error occurred<br><br>" + e.getMessage() + "</center></html>";
        JLabel label = new JLabel(text);
        label.setOpaque(true);
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBackground(Color.BLACK);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.PLAIN, 37));
        label.setPreferredSize(dimension);
        return label;
    }
}
