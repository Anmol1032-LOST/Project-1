package com.anmol.games.screen.screens.screen0;

import com.anmol.games.Assets;
import com.anmol.games.GlobalVariables;
import com.anmol.games.GuiUtils;
import com.anmol.games.LOST;
import com.anmol.games.screen.Screen;
import com.jme3.collision.CollisionResults;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.CenterQuad;

public class Screen0 extends Screen {
    Node cornerNode = new Node();
    Node intractable = new Node();
    Geometry selectedBox;
    Vector3f vec1 = new Vector3f();
    Vector3f vec2 = new Vector3f(0, 0, -1);
    Vector3f pos = new Vector3f();

    @Override
    protected void init() {
        screenController.app.getInputManager().addMapping("Screen0.click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        screenController.app.getInputManager().addListener(this, "Screen0.click");

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

        String[] texture = {"Textures/Screen0/Elements.png", "Textures/Screen0/Inventory.png", "Textures/Screen0/Purchase.png"};
        for (int i = 0; i < 3; i++) {
            Material mat = Assets.mat.clone();
            mat.setTexture("ColorMap", Assets.textures.get(texture[i]));
            mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            Geometry g = new Geometry("", new CenterQuad(104, 104));
            g.setMaterial(mat);
            g.setLocalTranslation(LOST.width / 2 + FastMath.sin(i / 3f * FastMath.TWO_PI) * 256, LOST.height / 2 + FastMath.cos(i / 3f * FastMath.TWO_PI) * 256, 1);
            g.setUserData("action", i);
            intractable.attachChild(g);
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
        onSomeScreen = false;
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

        if (!pos.isSimilar(GlobalVariables.data.player_pos, 0.1f)) {
            switchScreen(screenController.mainGameGuiScreen);

        }
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
        if (!isPressed && name.equals("Screen0.click")) {
            if (selectedBox.getUserData("selected") != null) {
                Screen s;
                int i = ((Geometry) selectedBox.getUserData("selected")).getUserData("action");
                switch (i) {
                    case 0 -> s = screenController.element;
                    case 1 -> s = screenController.inventory;
                    case 2 -> s = screenController.purchase;
                    default -> throw new RuntimeException("Unknown action provided in Screen0");
                }
                onSomeScreen = true;
                switchScreen(s);
            } else {
                switchScreen(screenController.mainGameGuiScreen);
            }
        }
    }

    public boolean onSomeScreen = false;

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
