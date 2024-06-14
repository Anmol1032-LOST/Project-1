package com.anmol.games.screen.appstates;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.Sounds;
import com.anmol.games.screen.Screen;
import com.jme3.anim.AnimComposer;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResults;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.Random;

public class LostMapAppState extends Screen {
    public final float SCALE = 128;
    RigidBodyControl rigidBodyControl;
    Node orbNode = new Node();
    Node center;
    float t = 0;
    Node surrounding;
    RigidBodyControl rigidBodyControl2;

    @Override
    protected void init() {
        rootNode.attachChild(orbNode);

        Spatial map = Assets.models.get("Models/Map/LostMap.glb").clone();
        map.scale(SCALE);
        rootNode.attachChild(map);

        Spatial lowLodMap = Assets.models.get("Models/Map/LostMapLowLod.glb").clone();
        lowLodMap.scale(SCALE);
        rigidBodyControl = new RigidBodyControl(CollisionShapeFactory.createMeshShape(lowLodMap), 0);
        map.addControl(rigidBodyControl);

        {
            center = (Node) Assets.models.get("Models/Map/Center.glb").clone();
            center.setLocalTranslation(0, 385, 0);
            rootNode.attachChild(center);
        }

        {
            Random rand = new Random(123);
            Spatial s_ = Assets.models.get("Models/Map/Orb.glb").clone();
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 64; j++) {
                    if ((GlobalVariables.data.story_orbs[i] & 1L << j) == 0) {
                        float r = rand.nextFloat(2 * SCALE, 16 * SCALE);
                        float a = rand.nextFloat(FastMath.TWO_PI);
                        float x = FastMath.sin(a) * r;
                        float z = FastMath.cos(a) * r;

                        Spatial s = s_.clone();
                        s.setUserData("index", j);
                        s.setUserData("element", i);
                        ((Geometry) ((Node) s).getChild(0)).getMaterial().setColor("BaseColor", Constants.GAME_COLORS[i]);

                        CollisionResults collisionResults = new CollisionResults();
                        Ray ray = new Ray(new Vector3f(x, 512, z), Vector3f.UNIT_Y.negate());
                        map.collideWith(ray, collisionResults);

                        s.setLocalTranslation(collisionResults.getClosestCollision().getContactPoint().addLocal(0, 4, 0));
                        orbNode.attachChild(s);
                    }
                }
            }
        }

        {
            surrounding = (Node) Assets.models.get("Models/Map/Surrounding.glb");
            surrounding.setLocalTranslation(center.getLocalTranslation());
            startAnimation(surrounding);
            rootNode.attachChild(surrounding);
        }
    }

    private void startAnimation(Spatial s) {
        if (s instanceof Node) {
            for (int i = 0; i < ((Node) s).getChildren().size(); i++) {
                startAnimation(((Node) s).getChild(i));
            }
        } else {
            AnimComposer a = s.getControl(AnimComposer.class);
            if (a != null) {
                a.setCurrentAction((String) a.getAnimClipsNames().toArray()[0]);
            }
        }
    }

    @Override
    protected void show() {
        GlobalVariables.lostMap = true;
        GlobalVariables.bulletAppState.getPhysicsSpace().add(rigidBodyControl);
        GlobalVariables.bulletAppState.getPhysicsSpace().add(rigidBodyControl2);
        center.getChildren().forEach(spatial -> {
            RigidBodyControl rigidBodyControl = new RigidBodyControl(0);
            spatial.addControl(rigidBodyControl);
            rigidBodyControl.setKinematic(true);
            rigidBodyControl.setKinematicSpatial(true);
            GlobalVariables.bulletAppState.getPhysicsSpace().add(rigidBodyControl);
        });
        surrounding.getChildren().forEach(spatial -> {
            RigidBodyControl rigidBodyControl = new RigidBodyControl(0);
            spatial.addControl(rigidBodyControl);
            rigidBodyControl.setKinematic(true);
            rigidBodyControl.setKinematicSpatial(true);
            GlobalVariables.bulletAppState.getPhysicsSpace().add(rigidBodyControl);
        });
    }

    @Override
    protected void hide() {
        GlobalVariables.bulletAppState.getPhysicsSpace().remove(rigidBodyControl);
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
        center.getChild("Torus.001").rotate(FastMath.sin(t) * tpf, FastMath.sin(t / 2) * tpf, FastMath.cos(t / 3) * tpf);
        center.getChild("Torus.002").rotate(FastMath.sin(t / 5) * tpf, FastMath.sin(t / 7) * tpf, FastMath.cos(t / 11) * tpf);
        center.getChild("Torus.003").rotate(FastMath.sin(t * 2) * tpf, FastMath.sin(t) * tpf, FastMath.cos(t * 6) * tpf);
        center.getChild("Torus.004").rotate(FastMath.sin(t * 9) * tpf, FastMath.sin(t * 4) * tpf, FastMath.cos(t * 9) * tpf);
        center.getChild("Torus.005").rotate(FastMath.sin(t * 5) * tpf, FastMath.sin(t * 7) * tpf, FastMath.cos(t * 12) * tpf);
        center.getChild("Torus.006").rotate(FastMath.sin(t / 7) * tpf, FastMath.sin(t * 8) * tpf, FastMath.cos(t * 4) * tpf);
        center.getChild("Torus").rotate(FastMath.sin(t / 12) * tpf, FastMath.sin(t * 4) * tpf, FastMath.cos(t * 9) * tpf);
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
