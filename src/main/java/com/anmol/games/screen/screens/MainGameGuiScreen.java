package com.anmol.games.screen.screens;

import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.Screen;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;

public class MainGameGuiScreen extends Screen {
    @Override
    protected void init() {
        screenController.app.getInputManager().addMapping("MainGameGuiScreen.interact", new KeyTrigger(KeyInput.KEY_G));
        screenController.app.getInputManager().addListener(this, "MainGameGuiScreen.interact");

        screenController.app.getInputManager().addMapping("MainGameGuiScreen.screen0", new KeyTrigger(KeyInput.KEY_ESCAPE));
        screenController.app.getInputManager().addListener(this, "MainGameGuiScreen.screen0");
    }

    @Override
    protected void show() {
        onScreen = false;
    }

    @Override
    protected void hide() {

    }

    @Override
    public void update(float tpf) {

    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
        if (name.equals("MainGameGuiScreen.interact") && !isPressed && screenController.playerAppState.enabled && GlobalVariables.lostMap) {
            if (vec.set(GlobalVariables.data.player_pos).setY(0).lengthSquared() < 256  && !onScreen) {
               switchScreen(screenController.switchToLoopMapAppState1);
               onScreen = true;
            }
        }

        if (!isPressed && name.equals("MainGameGuiScreen.screen0") && !onScreen) {
            switchScreen(screenController.screen0);
            onScreen = true;
        }
    }

    boolean onScreen = false;
    Vector3f vec = new Vector3f();

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
