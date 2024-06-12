package com.anmol.games.screen.appstates;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
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

public class SwitchToLoopMapAppState1 extends Screen {
    Node cornerNode = new Node();
    Node intractable = new Node();
    Geometry selectedBox;
    @Override
    protected void init() {
        screenController.app.getInputManager().addMapping("SwitchToLoopMapAppState1.click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        screenController.app.getInputManager().addListener(this, "SwitchToLoopMapAppState1.click");

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

        for (int i = 0; i < 6; i++) {
            Material mat = Assets.mat.clone();
            mat.setTexture("ColorMap", Assets.textures.get("Textures/Elements/C" + i + ".png"));
            mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            Geometry g = new Geometry("", new CenterQuad(104, 104));
            g.setMaterial(mat);
            g.setLocalTranslation(LOST.width/2 + FastMath.sin(i/6f*FastMath.TWO_PI)*256,  LOST.height/2 + FastMath.cos(i/6f*FastMath.TWO_PI)*256, 1);
            g.setUserData("element", i);
            intractable.attachChild(g);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        screenController.playerAppState.setEnabled(!enabled);
    }

    @Override
    protected void show() {

    }

    @Override
    protected void hide() {

    }

    Vector3f vec1 = new Vector3f();
    Vector3f vec2 = new Vector3f(0, 0, -1);
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
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
        if (!isPressed && name.equals("SwitchToLoopMapAppState1.click")) {
            if (selectedBox.getUserData("selected") != null) {
                screenController.switchToLoopMapAppState2.element = ((Geometry) selectedBox.getUserData("selected")).getUserData("element");
                switchScreen(screenController.switchToLoopMapAppState2);
            } else {
                setEnabled(false);
            }
        }
    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
