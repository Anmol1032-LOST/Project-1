package com.anmol.games.screen.appstates;

import com.anmol.games.screen.Screen;

public class SwitchToLoopMapAppState extends Screen {
    @Override
    protected void init() {

    }

    @Override
    protected void show() {

    }

    @Override
    protected void hide() {
        screenController.lostMapAppState.setEnabled(false);
        screenController.loopMapAppState.setEnabled(true);
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
