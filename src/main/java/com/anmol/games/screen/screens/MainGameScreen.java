package com.anmol.games.screen.screens;

import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.Screen;
import com.jme3.bullet.BulletAppState;

public class MainGameScreen extends Screen {
    float t = 0;
    @Override
    protected void init() {
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        screenController.mapAppState.setEnabled(enabled);
        screenController.playerAppState.setEnabled(enabled);
    }

    @Override
    protected void show() {
        t = 0;
    }

    @Override
    protected void hide() {

    }

    @Override
    public void update(float tpf) {
        t += tpf;
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {

    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
