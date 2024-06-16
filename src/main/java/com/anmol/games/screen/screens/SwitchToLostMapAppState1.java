package com.anmol.games.screen.screens;

import com.anmol.games.*;
import com.anmol.games.screen.Screen;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.RenderState;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.CenterQuad;

public class SwitchToLostMapAppState1 extends Screen {
    Node cornerNode = new Node();
    Node intractable = new Node();
    Geometry selectedBox;
    Vector3f vec1 = new Vector3f();
    Vector3f vec2 = new Vector3f(0, 0, -1);
    Vector3f pos = new Vector3f();

    @Override
    protected void init() {
        screenController.app.getInputManager().addMapping("SwitchToLostMapAppState1.click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        screenController.app.getInputManager().addListener(this, "SwitchToLostMapAppState1.click");

        guiNode.attachChild(cornerNode);
        guiNode.attachChild(intractable);

        GuiUtils.makeScreen(guiNode, cornerNode, false);

        {
            selectedBox = new Geometry("", new Mesh());
            selectedBox.setMaterial(Assets.mat.clone());
            selectedBox.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/GUI/SelectedBox.png"));
            selectedBox.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            selectedBox.setLocalScale(1.1f);
            guiNode.attachChild(selectedBox);
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
    }

    @Override
    protected void hide() {
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
            if (selectedBox.getUserData("selected") != g) {
                selectedBox.setLocalTranslation(g.getLocalTranslation().add(0, 0, 1));
                selectedBox.setMesh(g.getMesh().clone());
                selectedBox.setUserData("selected", g);
            }
        } else {
            selectedBox.setMesh(new Mesh());
            selectedBox.setUserData("selected", null);
        }

        Geometry geometry = new Geometry("", new CenterQuad(64 * 9, 64));
        geometry.setLocalTranslation(LOST.width / 2, LOST.height / 2, 2);
        geometry.setMaterial(Assets.mat.clone());
        geometry.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/GUI/Rect.png"));
        intractable.attachChild(geometry);

        BitmapText text = new BitmapText(Assets.font.get("Font"));
        text.setText("Exit Loop");
        text.setSize(text.getFont().getCharSet().getRenderedSize() / 3f);
        text.setBox(new Rectangle(LOST.width / 2 - 32 * 9, LOST.height / 2 - 32, 64 * 9, 64));
        text.setAlignment(BitmapFont.Align.Center);
        text.setVerticalAlignment(BitmapFont.VAlign.Center);
        text.setLocalTranslation(0, text.getHeight(), 3);
        guiNode.attachChild(text);
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
        if (!isPressed && name.equals("SwitchToLostMapAppState1.click")) {
            if (screenController.loopMapAppState.phase == GlobalVariables.data.story_phase && screenController.loopMapAppState.element == 6) {
                GlobalVariables.data.story_phase++;
            }
            switchScreen(screenController.mainGameGuiScreen);
            screenController.loopMapAppState.setEnabled(false);
            screenController.lostMapAppState.setEnabled(true);
            screenController.playerAppState.teleport(Constants.PLAYER_POS);
        }
    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
