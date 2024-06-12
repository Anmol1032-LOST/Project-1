package com.anmol.games.screen;

import com.anmol.games.LOST;
import com.anmol.games.Sounds;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.math.FastMath;
import com.jme3.scene.Node;

public abstract class Screen implements ActionListener, AnalogListener {
    protected final Node rootNode = new Node();
    protected final Node guiNode = new Node();
    protected boolean enabled = false;
    public boolean loaded = false;
    protected ScreenController screenController;

    public void init(ScreenController screenController) {
        this.screenController = screenController;
        init();
        loaded = true;
    }

    protected abstract void init();

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            if (Sounds.clickSound != null) {
                Sounds.clickSound.play();
            }
            screenController.app.getGuiNode().attachChild(guiNode);
            screenController.app.getRootNode().attachChild(rootNode);
            show();
        } else {
            screenController.app.getGuiNode().detachChild(guiNode);
            screenController.app.getRootNode().detachChild(rootNode);
            hide();
        }
    }

    protected abstract void show();

    protected abstract void hide();


    public abstract void update(final float tpf);

    public void simpleUpdate(final  float tpf) {
        if (enabled && loaded) update(Math.min(tpf, 1/35f));
    }

    @Override
    public void onAction(final String name, final boolean isPressed, final float tpf) {
        if (enabled && loaded) action(name, isPressed, tpf);
    }

    protected abstract void action(final String name, final boolean isPressed, final float tpf);

    @Override
    public void onAnalog(final String name, final float value, final float tpf) {
        if (enabled && loaded) analog(name, value, tpf);
    }

    protected abstract void analog(final String name, final float value, final float tpf);

    protected void switchScreen(Screen screen) {
        this.setEnabled(false);
        screen.setEnabled(true);
    }
}
