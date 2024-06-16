package com.anmol.games.screen.screens;

import com.anmol.games.screen.Screen;

public class MainGameScreen extends Screen {
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
        screenController.mainGameGuiScreen.setEnabled(enabled);
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
