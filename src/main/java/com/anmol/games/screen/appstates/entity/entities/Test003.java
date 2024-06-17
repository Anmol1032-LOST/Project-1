package com.anmol.games.screen.appstates.entity.entities;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.anim.AnimComposer;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Test003 extends AbstractEntity {
    float t = 0;
    Vector3f vec = new Vector3f();
    AnimComposer animComposer;
    CharacterControl control;
    float t2 = 0;


    public Test003(Vector3f pos, int level, int element) {
        super(pos, level, element);
        setName("Test003");
        maxHp = 63 * level;
        hp = maxHp;
    }

    @Override
    protected void init() {
        ((Geometry) ((Node) ((Node) spatial).getChild(0)).getChild("MainGeo")).getMaterial().setColor("BaseColor", Constants.GAME_COLORS[element]);
        animComposer = ((Node) spatial).getChild(0).getControl(AnimComposer.class);
        animComposer.setCurrentAction("Walk");

        control = new CharacterControl(new CapsuleCollisionShape(1.5f, 20), 0.03f);
        spatial.addControl(control);
        GlobalVariables.bulletAppState.getPhysicsSpace().add(control);
    }

    @Override
    protected Spatial genrateSpatial() {
        return Assets.models.get("Models/Entities/Test003.glb").clone();
    }

    @Override
    protected void update(float tpf) {
       // todo : attack / walk
    }

    @Override
    protected void onDeath() {

    }
}
