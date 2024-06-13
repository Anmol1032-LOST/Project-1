package com.anmol.games.screen.appstates;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.Sounds;
import com.anmol.games.screen.Screen;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.Optional;
import java.util.Random;

public class LostMapAppState extends Screen {
    public final float SCALE = 128;
    RigidBodyControl rigidBodyControl;
    Node orbNode = new Node();

    Node center;
    float t = 0;
    Vector3f vec = new Vector3f();

    @Override
    protected void init() {
        rootNode.attachChild(orbNode);
        screenController.app.getInputManager().addMapping("LostMapAppState.interact", new KeyTrigger(KeyInput.KEY_G));
        screenController.app.getInputManager().addListener(this, "LostMapAppState.interact");

        Spatial map = Assets.models.get("Models/LostMap.glb").clone();
        map.scale(SCALE);
        rootNode.attachChild(map);

        Spatial lowLodMap = Assets.models.get("Models/LostMapLowLod.glb").clone();
        lowLodMap.scale(SCALE);
        rigidBodyControl = new RigidBodyControl(CollisionShapeFactory.createMeshShape(lowLodMap), 0);
        map.addControl(rigidBodyControl);

        {
            center = (Node) Assets.models.get("Models/Center.glb").clone();
            center.setLocalTranslation(0, 385, 0);
            rootNode.attachChild(center);
        }

        {
            Random rand = new Random(123);
            Spatial s_ = Assets.models.get("Models/Orb.glb").clone();
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 64; j++) {
                    if ((GlobalVariables.data.story_orbs[i] & 1L << j) == 0) {
                        float r = rand.nextFloat(128, 16 * SCALE);
                        float a = rand.nextFloat(FastMath.TWO_PI);
                        float x = FastMath.sin(a) * r;
                        float z = FastMath.cos(a) * r;

                        Spatial s = s_.clone();
                        s.setUserData("index", j);
                        s.setUserData("element", i);
                        ((Geometry) ((Node) s).getChild(0)).getMaterial().setColor("Emissive", Constants.GAME_COLORS[i]);

                        CollisionResults collisionResults = new CollisionResults();
                        Ray ray = new Ray(new Vector3f(x, 512, z), Vector3f.UNIT_Y.negate());
                        map.collideWith(ray, collisionResults);

                        s.setLocalTranslation(collisionResults.getClosestCollision().getContactPoint().addLocal(0, 4, 0));
                        orbNode.attachChild(s);
                    }
                }
            }
        }
    }

    @Override
    protected void show() {
        GlobalVariables.bulletAppState.getPhysicsSpace().add(rigidBodyControl);
        center.getChildren().forEach(spatial -> {
            RigidBodyControl rigidBodyControl = new RigidBodyControl(0);
            spatial.addControl(rigidBodyControl);
            rigidBodyControl.setKinematic(true);
            rigidBodyControl.setKinematicSpatial(true);
            GlobalVariables.bulletAppState.getPhysicsSpace().add(rigidBodyControl);
        });
    }

    @Override
    protected void hide() {
        GlobalVariables.bulletAppState.getPhysicsSpace().add(rigidBodyControl);
        GlobalVariables.bulletAppState.getPhysicsSpace().removeAll(center);
    }

    @Override
    public void update(float tpf) {
        t += tpf;
        for (Spatial s : orbNode.getChildren()) {
            if (GlobalVariables.data.player_pos.distanceSquared(s.getLocalTranslation()) <= 8) {
                Sounds.initSound.play();
                int index = s.getUserData("index");
                GlobalVariables.data.story_orbs[(int) s.getUserData("element")] |= 1L << index;
                orbNode.detachChild(s);
            }
        }
        center.getChild(0).rotate(FastMath.sin(t) * tpf, FastMath.sin(2 * t) * tpf, FastMath.sin(3 * tpf) * tpf);
        center.getChild("T0").rotate(FastMath.sin(t) * tpf, FastMath.sin(t / 2) * tpf, FastMath.cos(t / 3) * tpf);
        center.getChild("T1").rotate(FastMath.sin(t / 5) * tpf, FastMath.sin(t / 7) * tpf, FastMath.cos(t / 11) * tpf);
        center.getChild("T2").rotate(FastMath.sin(t * 2) * tpf, FastMath.sin(t) * tpf, FastMath.cos(t * 6) * tpf);
        center.getChild("T3").rotate(FastMath.sin(t * 9) * tpf, FastMath.sin(t * 4) * tpf, FastMath.cos(t * 9) * tpf);
        center.getChild("T4").rotate(FastMath.sin(t * 5) * tpf, FastMath.sin(t * 7) * tpf, FastMath.cos(t * 12) * tpf);
        center.getChild("T5").rotate(FastMath.sin(t / 7) * tpf, FastMath.sin(t * 8) * tpf, FastMath.cos(t * 4) * tpf);
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
        if (name.equals("LostMapAppState.interact") && !isPressed && screenController.playerAppState.enabled) {
            if (vec.set(GlobalVariables.data.player_pos).setY(0).lengthSquared() < 256) {
                screenController.switchToLoopMapAppState1.setEnabled(true);
            }
        }
    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}