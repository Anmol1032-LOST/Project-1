package com.anmol.games.screen.screens;

import com.anmol.games.Assets;
import com.anmol.games.LOST;
import com.anmol.games.screen.Screen;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.CenterQuad;

public class IntroScreen extends Screen {
    Geometry screen;

    @Override
    protected void init() {
        screen = new Geometry("", new CenterQuad(LOST.width, LOST.height));
        screen.setMaterial(Assets.mat.clone());
        screen.getMaterial().setColor("Color", ColorRGBA.Red);
        screen.setLocalTranslation(LOST.width / 2f, LOST.height / 2f, 0);
        guiNode.attachChild(screen);
    }

    @Override
    protected void show() {

    }

    @Override
    protected void hide() {

    }

    @Override
    public void update(float tpf) {

    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {

    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }

}
