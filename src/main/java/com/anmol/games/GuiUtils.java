package com.anmol.games;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.CenterQuad;

public class GuiUtils {
    public static void makeScreen(Node guiNode, Node cornerNode) {
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
    }

    public static void updateScreen(Node cornerNode, float t) {
        cornerNode.getChildren().forEach(spatial -> spatial.setLocalScale(0.9f + FastMath.sin(t) * 0.1f));
    }
}
