package com.anmol.games.screen.screens;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GuiUtils;
import com.anmol.games.LOST;
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

public class StartScreen extends Screen {
    final Node cornerNode = new Node();
    final Node intractable = new Node();
    final String play = "PLAY";
    final String howToPlay = "How To Play";
    final String settings = "Settings";
    final String aboutCreator = "About the Creator";
    final String exit = "Exit";
    float t = 0;
    Geometry selectedRect;
    Vector3f vec1 = new Vector3f();
    Vector3f vec2 = new Vector3f(0, 0, -1);

    @Override
    protected void init() {
        screenController.app.getInputManager().addMapping("StartScreen.click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        screenController.app.getInputManager().addListener(this, "StartScreen.click");
        guiNode.attachChild(cornerNode);
        guiNode.attachChild(intractable);
        {
            selectedRect = new Geometry("", new Mesh());
            selectedRect.setMaterial(Assets.mat.clone());
            selectedRect.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/GUI/SelectedRect.png"));
            selectedRect.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            selectedRect.setLocalScale(1.1f);
            guiNode.attachChild(selectedRect);
        }
        GuiUtils.makeScreen(guiNode, cornerNode);

        int i = -2;
        for (String s : new String[]{play, howToPlay, settings, aboutCreator, exit}) {
            Geometry geometry = new Geometry("", new CenterQuad(64 * 9, 64));
            geometry.setUserData("work", s);
            geometry.setLocalTranslation(LOST.width / 2, LOST.height / 2 - 72 * i, 2);
            geometry.setMaterial(Assets.mat.clone());
            geometry.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/GUI/Rect.png"));
            intractable.attachChild(geometry);

            BitmapText text = new BitmapText(Assets.font.get("Font"));
            text.setText(s);
            text.setSize(text.getFont().getCharSet().getRenderedSize() / 3f);
            text.setBox(new Rectangle(LOST.width / 2 - 32 * 9, LOST.height / 2 - 32 - 72 * i, 64 * 9, 64));
            text.setAlignment(BitmapFont.Align.Center);
            text.setVerticalAlignment(BitmapFont.VAlign.Center);
            text.setLocalTranslation(0, text.getHeight(), 3);
            guiNode.attachChild(text);
            i++;
        }
    }

    @Override
    protected void show() {
        t = 0;
        selectedRect.setUserData("selected", null);
    }

    @Override
    protected void hide() {

    }

    @Override
    public void update(float tpf) {
        t += tpf;
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
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
        if (!isPressed && name.equals("StartScreen.click") && selectedRect.getUserData("selected") != null) {
            String input = ((Geometry) selectedRect.getUserData("selected")).getUserData("work");
            switch (input) {
                case play -> {
                    if (Constants.IS_DEVELOPMENT) {
                        switchScreen(screenController.mainGameScreen);
                    } else {
                        switchScreen(screenController.beforePlayScreen);
                    }
                }
                case howToPlay -> switchScreen(screenController.howToPlayScreen);
                case settings -> switchScreen(screenController.settingsScreen);
                case aboutCreator -> switchScreen(screenController.aboutCreatorScreen);
                case exit -> screenController.app.stop();
            }
        }
    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }

}
