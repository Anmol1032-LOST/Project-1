package com.anmol.games;

import com.anmol.games.screen.ScreenController;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class LOST extends SimpleApplication {
    public static float width;
    public static float height;
    ScreenController screenController;

    public LOST() {
        super(new AppState[0]);
    }

    public static void main(String[] args) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        if (Constants.IS_DEVELOPMENT) {
            width = dimension.width * 3 / 4f;
            height = dimension.height * 3 / 4f;
        } else {
            width = dimension.width;
            height = dimension.height;
        }

        AppSettings settings = new AppSettings(true);
        settings.setResolution((int) width, (int) height);
        settings.setTitle("L.O.S.T.");

        Class<?> clazz = LOST.class;
        try {
            settings.setIcons(new BufferedImage[]{
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
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (Constants.IS_DEVELOPMENT) {
            settings.setVSync(false);
        } else {
            settings.setVSync(true);
            settings.setFrameRate(35);
            settings.setFullscreen(true);
        }


        LOST app = new LOST();
        app.setShowSettings(false);
        app.setSettings(settings);
        app.setPauseOnLostFocus(false);
        app.start();
    }

    float emptyFrames = 35;

    @Override
    public void simpleInitApp() {
        screenController = new ScreenController(this);
    }

    @Override
    public void simpleUpdate(float tpf) {
        if (emptyFrames > 0) {
            emptyFrames -= 1;
            return;
        }
        screenController.update(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }
}
