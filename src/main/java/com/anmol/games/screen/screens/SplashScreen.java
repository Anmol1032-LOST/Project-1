package com.anmol.games.screen.screens;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.LOST;
import com.anmol.games.screen.Screen;
import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.CenterQuad;

public class SplashScreen extends Screen {
    Throwable error;
    float extraTime = Constants.IS_DEVELOPMENT ? .1f : 2;

    @Override
    protected void init() {
        Geometry geometry = new Geometry("", new CenterQuad(LOST.height - 16, LOST.height - 16));
        geometry.setMaterial(new Material(screenController.app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md"));
        geometry.getMaterial().setTexture("ColorMap", screenController.app.getAssetManager().loadTexture("Interface/Icon/Icon4096.png"));
        geometry.setLocalTranslation(LOST.width / 2, LOST.height / 2, 0);
        guiNode.attachChild(geometry);
    }

    @Override
    protected void show() {
        Thread thread = new Thread(() -> {
            Assets.loadInterface(screenController.app.getAssetManager());
            screenController.loadingScreen.init(screenController);
        }, "SplashScreenThread");
        thread.setUncaughtExceptionHandler((t, e) -> error = e);
        thread.start();
    }

    @Override
    protected void hide() {

    }

    @Override
    public void update(float tpf) {
        if (error != null) {
            throw new RuntimeException(error);
        }
        if (screenController.loadingScreen.loaded) {
            if (extraTime < 0) {
                switchScreen(screenController.loadingScreen);
            } else {
                extraTime -= tpf;
            }
        }
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {

    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
