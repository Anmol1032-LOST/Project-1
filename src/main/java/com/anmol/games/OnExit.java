package com.anmol.games;

import com.jme3.math.FastMath;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class OnExit {
    public static void exit() {
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        JFrame frame = new JFrame("Paranoma's Message");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    Robot robot = new Robot();
                    robot.keyPress(KeyEvent.VK_ALT);
                    robot.keyPress(KeyEvent.VK_F4);
                    robot.keyRelease(KeyEvent.VK_F4);
                    robot.keyRelease(KeyEvent.VK_ALT);
                    robot.delay(700);
                    robot.keyPress(KeyEvent.VK_ENTER);
                    robot.keyRelease(KeyEvent.VK_ENTER);
                    robot.delay(100);
                } catch (AWTException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.getContentPane().setBackground(Color.BLACK);

        JLabel label = getjLabel(dimension);

        JButton jButton1 = new JButton("Ok, I won't play it");
        jButton1.setBackground(Color.GREEN);
        jButton1.setForeground(Color.WHITE);
        jButton1.setFont(new Font("Arial", Font.PLAIN, 32));
        jButton1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        JButton jButton2 = new JButton("No I will play it later");
        jButton2.setBackground(Color.BLACK);
        jButton2.setForeground(Color.WHITE);
        jButton2.setFont(new Font("Arial", Font.PLAIN, 32));
        jButton2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jButton2.getParent().remove(jButton2);
                label.setText("<html><center>Currently I don't have privileges but<br/>...<br/>I can destroy that Button<br/>And Lock Your Cursor<br/>What you will do Now.</center></html>");
                new Thread(() -> {
                    try {
                        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
                        Robot robot = new Robot();
                        robot.setAutoDelay(16);
                        for (int i = 0; i < 2000; i++) {
                            robot.mouseMove((int) (d.width/2 + FastMath.sin(i/100f)*d.height/2.4f), (int) (d.height/2 + FastMath.cos(i/100f)*d.height/2.4f));
                        }
                    } catch (AWTException ex) {
                        throw new RuntimeException(ex);
                    }
                }).start();
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(jButton2);
        buttonPanel.add(jButton1);

        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private static JLabel getjLabel(Dimension dimension) {
        String text = "<html><center>Hi! I am Paranoma<br/><br/>Thanks for exiting the Game<br/><br/>and Don't Play The Game Again<br/>Else I will Restrict the World<br/>If I get the privileges</center></html>";
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
