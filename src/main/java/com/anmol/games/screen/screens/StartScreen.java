package com.anmol.games.screen.screens;

import com.anmol.games.Assets;
import com.anmol.games.LOST;
import com.anmol.games.screen.Screen;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.shape.CenterQuad;

public class StartScreen extends Screen {
    float t = 0;
    Node cornerNode = new Node();
    Node intractable = new Node();
    Geometry selectedBox;

    @Override
    protected void init() {
        guiNode.attachChild(cornerNode);
        guiNode.attachChild(intractable);
        {
            selectedBox = new Geometry("", new Mesh());
            selectedBox.setMaterial(Assets.mat.clone());
            selectedBox.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/GUI/SelectedRect.png"));
            selectedBox.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            selectedBox.setLocalScale(1.1f);
            guiNode.attachChild(selectedBox);
        }
        {
            Geometry bg = new Geometry("", new CenterQuad(LOST.width * 3 / 4f, LOST.height * 3 / 4f));
            bg.setMaterial(Assets.mat.clone());
            bg.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/GUI/Rect.png"));
            bg.setLocalTranslation(LOST.width / 2, LOST.height / 2, 0);
            guiNode.attachChild(bg);
        }

        {
            Material mat = Assets.mat.clone();
            mat.setTexture("ColorMap", Assets.textures.get("Textures/GUI/Corner.png"));
            mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            int i = 0;
            for (Vector3f v : new Vector3f[]{new Vector3f(LOST.width / 8, LOST.height / 8, 0), new Vector3f(LOST.width * 7 / 8, LOST.height / 8, 0), new Vector3f(LOST.width * 7 / 8, LOST.height * 7 / 8, 0), new Vector3f(LOST.width / 8, LOST.height * 7 / 8, 0)}) {
                Geometry c = new Geometry("", new CenterQuad(128, 128));
                c.setMaterial(mat);
                c.rotate(0, 0, FastMath.HALF_PI * (i++));
                c.setLocalTranslation(v);
                cornerNode.attachChild(c);
            }
        }

        int i = -1;
        for (String s: new String[]{"START", "SETTINGS", "QUIT"}) {
            Geometry start = new Geometry("", new CenterQuad(64*9, 64));
            start.setUserData("work", s);
            start.setLocalTranslation(LOST.width/2, LOST.height/2-72*i, 2);
            start.setMaterial(Assets.mat.clone());
            start.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/GUI/Rect.png"));
            intractable.attachChild(start);

            BitmapText startText = new BitmapText(Assets.font.get("Font"));
            startText.setText(s);
            startText.setSize(startText.getFont().getCharSet().getRenderedSize() / 3f);
            startText.setBox(new Rectangle(LOST.width / 2 - 32*9, LOST.height / 2 - 32-72*i, 64*9, 64));
            startText.setAlignment(BitmapFont.Align.Center);
            startText.setVerticalAlignment(BitmapFont.VAlign.Center);
            startText.setLocalTranslation(0, startText.getHeight(), 3);
            guiNode.attachChild(startText);

            i++;
        }
    }

    @Override
    protected void show() {
    }

    @Override
    protected void hide() {

    }

    @Override
    public void update(float tpf) {
        t += tpf;
        cornerNode.getChildren().forEach(spatial -> spatial.setLocalScale(0.9f + FastMath.sin(t) * 0.1f));

        final CollisionResults collisionResults = new CollisionResults();
        vec1.set(screenController.app.getInputManager().getCursorPosition().x, screenController.app.getInputManager().getCursorPosition().y, 1000);
        final Ray ray = new Ray(vec1, vec2);
        intractable.collideWith(ray, collisionResults);
        if (collisionResults.size() > 0) {
            final Geometry g = collisionResults.getCollision(0).getGeometry();
            selectedBox.setLocalTranslation(g.getLocalTranslation().add(0, 0, 1));
            selectedBox.setMesh(g.getMesh().clone());
        } else {
            selectedBox.setMesh(new Mesh());
        }
    }

    Vector3f vec1=new Vector3f();
    Vector3f vec2=new Vector3f(0, 0, -1);

    @Override
    protected void action(String name, boolean isPressed, float tpf) {

    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }

}
