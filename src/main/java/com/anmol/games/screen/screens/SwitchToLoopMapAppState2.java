package com.anmol.games.screen.screens;

import com.anmol.games.*;
import com.anmol.games.screen.Screen;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.CenterQuad;

public class SwitchToLoopMapAppState2 extends Screen {
    Node cornerNode = new Node();
    Node intractable = new Node();
    Node noIntractable = new Node();
    Geometry selectedRect;
    int element;
    Vector3f vec1 = new Vector3f();
    Vector3f vec2 = new Vector3f(0, 0, -1);
    Vector3f pos = new Vector3f();

    @Override
    protected void init() {
        screenController.app.getInputManager().addMapping("SwitchToLoopMapAppState2.click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        screenController.app.getInputManager().addListener(this, "SwitchToLoopMapAppState2.click");

        guiNode.attachChild(cornerNode);
        guiNode.attachChild(intractable);
        guiNode.attachChild(noIntractable);

        GuiUtils.makeScreen(new Node(), cornerNode, false);

        {
            selectedRect = new Geometry("", new Mesh());
            selectedRect.setMaterial(Assets.mat.clone());
            selectedRect.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/GUI/SelectedRect.png"));
            selectedRect.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            selectedRect.setLocalScale(1.1f);
            guiNode.attachChild(selectedRect);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        screenController.playerAppState.setChaseCameraEnabled(!enabled);
    }

    @Override
    protected void show() {
        pos.set(GlobalVariables.data.player_pos);
        for (int i = 0; i < 8; i++) {
            for (int o = 0; o < 8; o++) {
                int n = (7 - o) * 8 + i + 1;
                float width = LOST.width * 3 / 4 / 8 - 8;
                float height = LOST.height * 3 / 4 / 8 - 8;
                float x = LOST.width / 8 + i * LOST.width * 3 / 4 / 8 + (LOST.width * 3 / 4 / 8 - 1) / 2 + 1;
                float y = LOST.height / 8 + o * LOST.height * 3 / 4 / 8 + (LOST.height * 3 / 4 / 8 - 1) / 2 + 1;


                Material mat = Assets.mat.clone();
                mat.setTexture("ColorMap", Assets.textures.get("Textures/GUI/Rect.png"));
                mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

                Geometry g = new Geometry("", new CenterQuad(width, height));
                g.setMaterial(mat);

                g.setLocalTranslation(x, y, 1);
                g.setUserData("phase", n);

                if (n <= Long.bitCount(GlobalVariables.data.story_orbs[element])) {
                    mat.setColor("Color", Constants.GAME_COLORS[element]);
                    intractable.attachChild(g);

                    BitmapText text = new BitmapText(Assets.font.get("Font"));
                    text.setText("" + n);
                    text.setSize(text.getFont().getCharSet().getRenderedSize() / 4f);
                    text.setBox(new Rectangle(x - width / 2, y - height / 2, width, height));
                    text.setAlignment(BitmapFont.Align.Center);
                    text.setVerticalAlignment(BitmapFont.VAlign.Center);
                    text.setLocalTranslation(0, text.getHeight(), 3);
                    noIntractable.attachChild(text);
                } else {
                    mat.setColor("Color", ColorRGBA.Gray);
                    noIntractable.attachChild(g);
                }
            }
        }
    }

    @Override
    protected void hide() {
        intractable.detachAllChildren();
        noIntractable.detachAllChildren();
    }

    @Override
    public void update(float tpf) {
        GuiUtils.updateScreen(cornerNode);
        final CollisionResults collisionResults = new CollisionResults();
        vec1.set(screenController.app.getInputManager().getCursorPosition().x, screenController.app.getInputManager().getCursorPosition().y, 1000);
        final Ray ray = new Ray(vec1, vec2);
        intractable.collideWith(ray, collisionResults);
        if (collisionResults.size() > 0) {
            final Geometry g = collisionResults.getCollision(0).getGeometry();
            if (selectedRect.getUserData("selected") != g) {
                selectedRect.setLocalTranslation(g.getLocalTranslation().add(0, 0, 1));
                selectedRect.setMesh(g.getMesh().clone());
                selectedRect.setUserData("selected", g);
            }
        } else {
            selectedRect.setMesh(new Mesh());
            selectedRect.setUserData("selected", null);
        }

        if (!pos.isSimilar(GlobalVariables.data.player_pos, 0.1f)) {
            switchScreen(screenController.mainGameGuiScreen);

        }
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
        if (!isPressed && name.equals("SwitchToLoopMapAppState2.click")) {
            if (selectedRect.getUserData("selected") != null) {
                int phase = ((Geometry) selectedRect.getUserData("selected")).getUserData("phase");
                screenController.loopMapAppState.set(phase, element);
                switchScreen(screenController.loopMapAppState);
                screenController.lostMapAppState.setEnabled(false);
            } else {
                switchScreen(screenController.switchToLoopMapAppState1);
            }
        }
    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
