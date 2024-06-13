package com.anmol.games.screen.screens;

import com.anmol.games.screen.Screen;
import com.anmol.games.screen.appstates.entity.entities.Slime;
import com.jme3.math.Vector3f;

public class MainGameScreen extends Screen {
    float t = 0;

    @Override
    protected void init() {
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        screenController.lostMapAppState.setEnabled(enabled);
        screenController.playerAppState.setEnabled(enabled);
        screenController.attackAppState.setEnabled(enabled);
        screenController.gameGui.setEnabled(enabled);
        screenController.entityAppState.setEnabled(enabled);
    }

    @Override
    protected void show() {
        t = 0;
        screenController.entityAppState.spawn(new Slime(new Vector3f(0, 376, 0), 1, 1));
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
