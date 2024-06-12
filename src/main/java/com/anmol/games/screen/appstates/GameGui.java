package com.anmol.games.screen.appstates;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.LOST;
import com.anmol.games.screen.Screen;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.CenterQuad;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Texture;

public class GameGui extends Screen {
    public static String loopText;
    Geometry hp;
    Geometry stamina;
    Geometry elementalStamina;
    Geometry darker;
    float size = 12;
    float t;
    Texture[] textures;
    Geometry element;
    Geometry entityHp;
    Geometry b1CD;
    Geometry b2CD;
    Geometry xCD;
    Geometry crossHair;
    Geometry entityHp_;

    BitmapText info;
    BitmapText bitmapText;

    @Override
    protected void init() {
        {
            textures = new Texture[]{
                    Assets.textures.get("Textures/Elements/C0.png"),
                    Assets.textures.get("Textures/Elements/C1.png"),
                    Assets.textures.get("Textures/Elements/C2.png"),
                    Assets.textures.get("Textures/Elements/C3.png"),
                    Assets.textures.get("Textures/Elements/C4.png"),
                    Assets.textures.get("Textures/Elements/C5.png"),
            };
        }
        {
            Material mat = Assets.mat.clone();
            mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            darker = new Geometry("darker", new CenterQuad(LOST.width, LOST.height));
            darker.setMaterial(mat);
            darker.setLocalTranslation(LOST.width / 2, LOST.height / 2, -1);
            guiNode.attachChild(darker);
        }
        {
            Material mat = Assets.mat.clone();
            mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Exclusion);
            mat.setTexture("ColorMap", Assets.textures.get("Textures/CrossHair.png"));
            crossHair = new Geometry("crossHair", new CenterQuad(32, 32));
            crossHair.setMaterial(mat);
            crossHair.setLocalTranslation(LOST.width / 2, LOST.height / 2, 0);
            guiNode.attachChild(crossHair);
        }
        {
            Material mat = Assets.mat.clone();
            mat.setColor("Color", Constants.GAME_COLORS[2]);
            hp = new Geometry("hp", new Mesh());
            hp.setMaterial(mat);
            hp.setLocalTranslation(LOST.width / 2, size, 0);
            guiNode.attachChild(hp);

            Material mat_ = Assets.mat.clone();
            mat_.setColor("Color", ColorRGBA.Gray);
            Geometry hp_ = new Geometry("hp", new CenterQuad(LOST.width / 3, size));
            hp_.setMaterial(mat_);
            hp_.setLocalTranslation(hp.getLocalTranslation().clone().setZ(-1));
            guiNode.attachChild(hp_);
        }
        {
            Material mat = Assets.mat.clone();
            mat.setColor("Color", Constants.GAME_COLORS[1]);
            stamina = new Geometry("stamina", new Mesh());
            stamina.setMaterial(mat);
            stamina.setLocalTranslation(LOST.width / 2, size * 2, 0);
            guiNode.attachChild(stamina);
        }
        {
            Material mat = Assets.mat.clone();
            mat.setColor("Color", Constants.GAME_COLORS[4]);
            elementalStamina = new Geometry("stamina", new Mesh());
            elementalStamina.setMaterial(mat);
            elementalStamina.setLocalTranslation(LOST.width / 2, size * 2.75f, 0);
            guiNode.attachChild(elementalStamina);
        }
        {
            float eSize = 13f;
            element = new Geometry("element", new CenterQuad(LOST.height / eSize, LOST.height / eSize));
            Material elementMat = Assets.mat.clone();
            elementMat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            element.setMaterial(elementMat);
            element.setLocalTranslation(LOST.width - size * 2.6f, 32, .5f);
            guiNode.attachChild(element);
        }
        {
            info = new BitmapText(Assets.font.get("FontMetal"));
            info.setBox(new Rectangle(0, 0, LOST.width, LOST.height - 16));
            info.setAlignment(BitmapFont.Align.Center);
            info.setVerticalAlignment(BitmapFont.VAlign.Top);
            info.setSize(info.getFont().getCharSet().getRenderedSize() / 3.3f);
            info.setLocalTranslation(0, info.getHeight(), 2);
            guiNode.attachChild(info);
        }
        {
            bitmapText = new BitmapText(Assets.font.get("Font"));
            bitmapText.setBox(new Rectangle(0, size * 3f, LOST.width, LOST.height));
            bitmapText.setAlignment(BitmapFont.Align.Center);
            bitmapText.setVerticalAlignment(BitmapFont.VAlign.Bottom);
            bitmapText.setSize(bitmapText.getFont().getCharSet().getRenderedSize() / 4.2f);
            bitmapText.setLocalTranslation(0, bitmapText.getHeight(), 2);
            guiNode.attachChild(bitmapText);
        }
        {
            Material mat = Assets.mat.clone();
            mat.setColor("Color", Constants.GAME_COLORS[2]);
            entityHp = new Geometry("entityHp", new Mesh());
            entityHp.setMaterial(mat);
            entityHp.setLocalTranslation(LOST.width / 2, LOST.height - size * 4.7f, 0);
            guiNode.attachChild(entityHp);

            Material mat_ = Assets.mat.clone();
            mat_.setColor("Color", ColorRGBA.Gray);
            entityHp_ = new Geometry("entityHp", new CenterQuad(LOST.width / 2.7f, size));
            entityHp_.setMaterial(mat_);
            entityHp_.setLocalTranslation(entityHp.getLocalTranslation().clone().setZ(-1));
            guiNode.attachChild(entityHp_);
        }
        {
            {
                Material mat = Assets.mat.clone();
                mat.setColor("Color", Constants.GAME_COLORS[4]);
                b1CD = new Geometry("b1CD", new Mesh());
                b1CD.setMaterial(mat);
                b1CD.setLocalTranslation(LOST.width - size * 4.3f, 48, 0);
                guiNode.attachChild(b1CD);
            }
            {
                Material mat = Assets.mat.clone();
                mat.setColor("Color", Constants.GAME_COLORS[4]);
                b2CD = new Geometry("b2CD", new Mesh());
                b2CD.setMaterial(mat);
                b2CD.setLocalTranslation(LOST.width - size * 3.2f, 48, 0);
                guiNode.attachChild(b2CD);
            }
            {
                Material mat = Assets.mat.clone();
                mat.setColor("Color", Constants.GAME_COLORS[4]);
                xCD = new Geometry("xCD", new Mesh());
                xCD.setMaterial(mat);
                xCD.setLocalTranslation(LOST.width - size * 2.1f, 48, 0);
                guiNode.attachChild(xCD);
            }

            {
                Material mat = Assets.mat.clone();
                mat.setColor("Color", ColorRGBA.Gray.clone().setAlpha(0.27f));
                mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
                Geometry bg = new Geometry("", new Mesh());
                bg.setMaterial(mat);
                bg.setLocalTranslation(LOST.width - size * 4.5f, 48, -.5f);
                bg.setMesh(new Quad(size * 3.5f, LOST.height / 3));
                guiNode.attachChild(bg);
            }
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

        darker.getMaterial().setColor("Color", new ColorRGBA(0, 0, 0, 0.7f + (FastMath.sin(t / 3)) * 0.2f));

        hp.setMesh(new CenterQuad(LOST.width / 3 * GlobalVariables.data.player_hp / GlobalVariables.data.player_maxHp, size));
        stamina.setMesh(new CenterQuad(LOST.width / 3 * GlobalVariables.data.player_stamina, size * 2 / 3));
        elementalStamina.setMesh(new CenterQuad(LOST.width / 3 * GlobalVariables.data.player_elementalStamina, size / 3));

        // element.getMaterial().setTexture("ColorMap", textures[AttackAppState.element]); // todo

        bitmapText.setText(loopText);
        info.setText("");
        entityHp.setMesh(new Mesh());
        guiNode.detachChild(entityHp_);

        // todo
//        CollisionResults collisionResults = new CollisionResults();
//        Ray ray = new Ray(screenController.app.getCamera().getLocation(), screenController.app.getCamera().getDirection());
//        screenController.app.getRootNode().collideWith(ray, collisionResults);
//        if (collisionResults.size() >= 1) {
//            Spatial s = collisionResults.getClosestCollision().getGeometry();
//            AbstractEntity e = EntityAppState.getEntity(s);
//            if (e != null) {
//                info.setText(e.getName() + "  LV-" + e.level);
//                entityHp.setMesh(new CenterQuad(LOST.width / 2.7f * e.hp / e.maxHp, size / 1.5f));
//                guiNode.attachChild(entityHp_);
//            }
//        }

//        b1CD.setMesh(new Quad(size, LOST.height / 3 * LOST.data.player.B1Time[AttackAppState.element] / 10));
//        b2CD.setMesh(new Quad(size, LOST.height / 3 * LOST.data.player.B2Time[AttackAppState.element] / 27));
//        xCD.setMesh(new Quad(size, LOST.height / 3 * LOST.data.player.XTime[AttackAppState.element] / 71));
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {

    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
