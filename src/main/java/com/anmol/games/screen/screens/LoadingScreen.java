package com.anmol.games.screen.screens;

import com.anmol.games.*;
import com.anmol.games.screen.Screen;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.CenterQuad;

public class LoadingScreen extends Screen {
    final Vector3f vector3f = new Vector3f();
    final Node cornerNode = new Node();
    Geometry selectedBox;
    Geometry loadingBar;
    Geometry loadingBar_;
    BitmapText loadingText;
    Throwable error;
    float c;
    float t = 0;

    @Override
    protected void init() {
        guiNode.attachChild(cornerNode);
        {
            selectedBox = new Geometry("", new CenterQuad(128, 128));
            selectedBox.setMaterial(Assets.mat.clone());
            selectedBox.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/GUI/SelectedBox.png"));
            selectedBox.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            cornerNode.attachChild(selectedBox);
        }
        GuiUtils.makeScreen(guiNode, cornerNode);

        {
            final Material mat = Assets.mat.clone();
            mat.setTexture("ColorMap", Assets.textures.get("Textures/GUI/Corner.png"));
            mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            int i = 0;
            for (Vector3f v : new Vector3f[]{new Vector3f(LOST.width / 8, LOST.height / 8, 0), new Vector3f(LOST.width * 7 / 8, LOST.height / 8, 0), new Vector3f(LOST.width * 7 / 8, LOST.height * 7 / 8, 0), new Vector3f(LOST.width / 8, LOST.height * 7 / 8, 0)}) {
                final Geometry c = new Geometry("", new CenterQuad(128, 128));
                c.setMaterial(mat);
                c.rotate(0, 0, FastMath.HALF_PI * (i++));
                c.setLocalTranslation(v);
                cornerNode.attachChild(c);
            }
        }

        {
            loadingBar = new Geometry("", new Mesh());
            loadingBar.setMaterial(Assets.mat.clone());
            loadingBar.setLocalTranslation(LOST.width / 2, LOST.height / 8 + 64, 1);
            guiNode.attachChild(loadingBar);

            loadingBar_ = new Geometry("", new CenterQuad(LOST.width * 3 / 4 * 3 / 4, 32));
            loadingBar_.setMaterial(Assets.mat.clone());
            loadingBar_.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/GUI/Rect.png"));
            loadingBar_.setLocalTranslation(loadingBar.getLocalTranslation().clone().setZ(0));
            guiNode.attachChild(loadingBar_);

            loadingText = new BitmapText(Assets.font.get("Font"));
            loadingText.setColor(ColorRGBA.Black);
            loadingText.setSize(loadingText.getFont().getCharSet().getRenderedSize() / 3f);
            loadingText.setBox(new Rectangle(LOST.width / 2 - LOST.width * 3 / 4 * 3 / 4 / 2, LOST.height / 8 + 64 - 16, LOST.width * 3 / 4 * 3 / 4, 32));
            loadingText.setAlignment(BitmapFont.Align.Center);
            loadingText.setVerticalAlignment(BitmapFont.VAlign.Center);
            loadingText.setLocalTranslation(0, loadingText.getHeight(), 3);
            guiNode.attachChild(loadingText);
        }

        {
            final BitmapText text = new BitmapText(Assets.font.get("FontMetal"));
            text.setColor(ColorRGBA.White);
            text.setText(Constants.GAME_NAME);
            text.setSize(text.getFont().getCharSet().getRenderedSize() * 2);
            text.setBox(new Rectangle(0, 0, LOST.width, LOST.height));
            text.setAlignment(BitmapFont.Align.Center);
            text.setVerticalAlignment(BitmapFont.VAlign.Center);
            text.setLocalTranslation(0, text.getHeight(), 5);
            guiNode.attachChild(text);
        }
    }

    @Override
    protected void show() {
        Thread thread = new Thread(() -> {
            Sounds.load(screenController.app.getAssetManager());
            Assets.loadAll(screenController.app.getAssetManager());
            GlobalVariables.data = DataLoader.load();
            screenController.initAll();
        }, "LoadingScreenThread");
        thread.setUncaughtExceptionHandler((t1, e) -> error = e);
        thread.start();
    }

    @Override
    protected void hide() {

    }

    @Override
    public void update(float tpf) {
        t += tpf;

        if (error != null) {
            throw new RuntimeException(error);
        }

        Vector2f pos = screenController.app.getInputManager().getCursorPosition();
        vector3f.set(FastMath.interpolateLinear(tpf * 10, vector3f.x, pos.x), FastMath.interpolateLinear(tpf * 10, vector3f.y, pos.y), 999);
        selectedBox.setLocalTranslation(vector3f);
        GuiUtils.updateScreen(cornerNode);

        if (c >= 1) {
            Sounds.initSound.play();
            switchScreen(screenController.startScreen);
            return;
        }
        if (!screenController.isAllLoaded) {
            c = 1 - FastMath.exp(-t / 24);
        } else {
            if (Constants.IS_DEVELOPMENT) {
                c = 1;
            } else {
                c += tpf / 10;
            }
        }
        loadingText.setText((int) (c * 100) + "%");
        loadingBar.setMesh(new CenterQuad(LOST.width * 3 / 4 * 3 / 4 * c, 32)); // remember loadingBar_
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {

    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
