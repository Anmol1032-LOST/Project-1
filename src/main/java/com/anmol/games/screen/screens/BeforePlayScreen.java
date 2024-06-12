package com.anmol.games.screen.screens;

import com.anmol.games.Assets;
import com.anmol.games.GuiUtils;
import com.anmol.games.LOST;
import com.anmol.games.screen.Screen;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.CenterQuad;

public class BeforePlayScreen extends Screen {
    final Node cornerNode = new Node();
    final ColorRGBA blankColor = new ColorRGBA(0, 0, 0, 0);
    Geometry icon;
    Geometry blank;
    @Override
    protected void init() {
        guiNode.attachChild(cornerNode);
        GuiUtils.makeScreen(guiNode, cornerNode);

        {
            icon = new Geometry("", new CenterQuad(LOST.height*3/4 - 64, LOST.height*3/4 - 64));
            icon.setMaterial(Assets.mat.clone());
            icon.getMaterial().setTexture("ColorMap", Assets.textures.get("Interface/Icon/Icon4096.png"));
            icon.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            icon.setLocalTranslation(LOST.width / 2, LOST.height / 2, 99);
            guiNode.attachChild(icon);
        }

        {
            blank = new Geometry("", new CenterQuad(LOST.width, LOST.height));
            blank.setMaterial(Assets.mat.clone());
            blank.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            blank.getMaterial().setColor("Color", blankColor);
            blank.setLocalTranslation(LOST.width / 2, LOST.height / 2, 98);
            guiNode.attachChild(blank);
        }
    }

    @Override
    protected void show() {
        t = 0;
        icon.setLocalScale(1);
    }

    @Override
    protected void hide() {

    }

    float t;
    @Override
    public void update(float tpf) {
        t += tpf;
        if (t < 7) {
            blankColor.setAlpha(t/7);
            icon.rotate(0, 0, (t - 1) * tpf);
            GuiUtils.updateScreen(cornerNode);
        } else if (t < 14) {
            float c = (t-7)/7;
            icon.rotate(0, 0, (t - 1) * tpf);
            icon.setLocalScale(FastMath.pow((1+c), 15));
        } else {
            switchScreen(screenController.startScreen);
        }
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
