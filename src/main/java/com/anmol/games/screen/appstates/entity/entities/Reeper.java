package com.anmol.games.screen.appstates.entity.entities;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.anim.AnimComposer;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Reeper extends AbstractEntity {
    float t = 0;
    Vector3f vec = new Vector3f();
    AnimComposer animComposer;
    float t2 = 0;


    public Reeper(Vector3f pos, int level, int element) {
        super(pos, level, element);
        setName("Reeper");
        maxHp = 63 * level;
        hp = maxHp;
    }

    @Override
    protected void init() {
        ((Geometry) ((Node) ((Node) spatial).getChild(0)).getChild("MainGeo")).getMaterial().setColor("BaseColor", Constants.GAME_COLORS[element]);
        animComposer = ((Node) spatial).getChild(0).getControl(AnimComposer.class);
        animComposer.setCurrentAction("Walk");
    }

    @Override
    protected Spatial genrateSpatial() {
        return Assets.models.get("Models/Entities/Reeper.glb").clone();
    }

    @Override
    protected void update(float tpf) {
       // todo : attack / walk
    }

    @Override
    protected void onDeath() {

    }
}
