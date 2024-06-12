package com.anmol.games;

import com.anmol.games.screen.ScreenController;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import com.jme3.bullet.BulletAppState;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class LOST extends SimpleApplication {
    public static float width;
    public static float height;
    public static float t = 0;
    ScreenController screenController;
    float emptyFrames = 35;

    public LOST() {
        super(new AppState[0]);
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

        setShowSettings(false);
        setSettings(settings);
        setPauseOnLostFocus(false);
    }

    public static void main(String[] args) {
        LOST app = new LOST();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        GlobalVariables.bulletAppState = new BulletAppState();
        stateManager.attach(GlobalVariables.bulletAppState);
        screenController = new ScreenController(this);
        Sounds.load(assetManager);
    }

    @Override
    public void simpleUpdate(float tpf) {
        t += tpf;
        if (emptyFrames > 0) {
            emptyFrames -= 1;
            return;
        }
        screenController.update(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    @Override
    public void destroy() {
        super.destroy();
        if (!Constants.IS_DEVELOPMENT) {
            Thread thread1 = new Thread(OnExit::exit);
            thread1.setDaemon(false);
            thread1.start();
        }
    }
}
