package com.anmol.games.screen.appstates.entity.entities.pranoma;

import com.anmol.games.Assets;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.material.RenderState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

class ParanomaCube extends AbstractEntity {
    public ParanomaCube(Vector3f pos, int element) {
        super(pos, 1, element);
        setName("Paranoma Cube");
        maxHp = 128;
        hp = maxHp;
    }

    @Override
    protected void init() {

    }

    @Override
    protected Spatial genrateSpatial() {
        Geometry g = new Geometry("", new Box(1, 1, 1));
        g.setMaterial(Assets.mat.clone());
        g.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/Elements/C" + element + ".png"));
        g.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        return null;
    }

    @Override
    protected void update(float tpf) {

    }

    @Override
    protected void onDeath() {

    }
}

public class Paranoma1 extends AbstractEntity {
    AbstractEntity g0;
    AbstractEntity g1;
    AbstractEntity g2;
    AbstractEntity g3;
    AbstractEntity g4;
    AbstractEntity g5;

    public Paranoma1(Vector3f pos, int element) {
        super(pos, 1, element);
        setName("Paranoma");
        maxHp = 256;
        hp = maxHp;
    }

    @Override
    protected void init() {
        g0 = new ParanomaCube(spatial.getLocalTranslation(), 0);
        g1 = new ParanomaCube(spatial.getLocalTranslation(), 1);
        g2 = new ParanomaCube(spatial.getLocalTranslation(), 2);
        g3 = new ParanomaCube(spatial.getLocalTranslation(), 3);
        g4 = new ParanomaCube(spatial.getLocalTranslation(), 4);
        g5 = new ParanomaCube(spatial.getLocalTranslation(), 5);

        entityAppState.spawn(g0);
        entityAppState.spawn(g1);
        entityAppState.spawn(g2);
        entityAppState.spawn(g3);
        entityAppState.spawn(g4);
        entityAppState.spawn(g5);
    }

    @Override
    protected Spatial genrateSpatial() {
        return null;
    }

    @Override
    protected void update(float tpf) {

    }

    @Override
    protected void onDeath() {

    }
}
