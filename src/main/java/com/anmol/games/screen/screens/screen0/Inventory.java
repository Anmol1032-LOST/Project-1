package com.anmol.games.screen.screens.screen0;

import com.anmol.games.*;
import com.anmol.games.screen.Screen;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.CenterQuad;

public class Inventory extends Screen {
    Node cornerNode = new Node();
    Node intractable = new Node();
    Geometry selectedBox;
    Vector3f vec1 = new Vector3f();
    Vector3f vec2 = new Vector3f(0, 0, -1);
    Vector3f pos = new Vector3f();
    String[][] materialInfo = new String[6][4];
    Geometry icon;
    BitmapText info;

    @Override
    protected void init() {
        for (int i = 0; i < 6; i++) {
            materialInfo[i][0] = "A crystal of " + Constants.GAME_ELEMENT_NAMES[i] + " Existence.";
            materialInfo[i][1] = "A crystal of " + Constants.GAME_ELEMENT_NAMES[i] + " Existence with some Paranoma Existence.";
            materialInfo[i][2] = "A sheet full of " + Constants.GAME_ELEMENT_NAMES[i] + " elemental Existence and Paranoma Existence.";
            materialInfo[i][3] = "A orb full of " + Constants.GAME_ELEMENT_NAMES[i] + " elemental Energy, with Paranoma Existence as a protective membrane.";
        }

        screenController.app.getInputManager().addMapping("Inventory.click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        screenController.app.getInputManager().addListener(this, "Inventory.click");

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

        float w = LOST.width * 3 / 4 / 2 / 7;
        for (int i = 0; i < 6; i++) {
            for (int o = 0; o < 4; o++) {
                Geometry g = new Geometry("", new CenterQuad(w - 2, w - 2));
                g.setMaterial(Assets.mat.clone());
                g.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
                g.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/Materials/C" + i + "L" + o + ".png"));
                g.setLocalTranslation(LOST.width / 2 - LOST.width * 3 / 4f / 2 + i * w + w, LOST.height / 2 + LOST.height * 3 / 4f / 2 - o * w - w, 1);
                g.setUserData("info", "Owned: " + GlobalVariables.data.inventory[i][o] + '\n' + materialInfo[i][o]);
                intractable.attachChild(g);
            }
        }

        float height = LOST.height * 3 / 4 / 2 - 64;
        icon = new Geometry("", new CenterQuad(height, height));
        icon.setLocalTranslation(LOST.width / 2 + LOST.width * 3 / 4f / 2 - height / 4 - LOST.width * 3 / 4 / 4, LOST.height / 2 + LOST.height * 3 / 4f / 2 - height / 2 - 64, 0);
        icon.setMaterial(Assets.mat.clone());
        icon.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        guiNode.attachChild(icon);

        info = new BitmapText(Assets.font.get("Font"));
        info.setColor(ColorRGBA.White);
        info.setText("");
        info.setSize(info.getFont().getCharSet().getRenderedSize() / 3f);
        info.setBox(new Rectangle(LOST.width / 2 + LOST.width * 3 / 4f / 2 - height / 4 - LOST.width * 3 / 4 / 4 - height / 2, LOST.height / 2 + LOST.height * 3 / 4f / 2 - height / 2 - 64 - height * 1.5f, height, height));
        info.setAlignment(BitmapFont.Align.Center);
        info.setVerticalAlignment(BitmapFont.VAlign.Center);
        info.setLocalTranslation(0, info.getHeight(), 5);
        guiNode.attachChild(info);
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
                icon.getMaterial().setTexture("ColorMap", g.getMaterial().getParamValue("ColorMap"));
                info.setText(g.getUserData("info"));
                guiNode.attachChild(icon);
            }
        } else {
            info.setText("");
            guiNode.detachChild(icon);
            selectedBox.setMesh(new Mesh());
            selectedBox.setUserData("selected", null);
        }

        if (!pos.isSimilar(GlobalVariables.data.player_pos, 0.1f)) {
            switchScreen(screenController.mainGameGuiScreen);
        }
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
        if (!isPressed && name.equals("Inventory.click")) {
            if (selectedBox.getUserData("selected") != null) {
                // todo
            } else {
                switchScreen(screenController.screen0);
            }
        }
    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
