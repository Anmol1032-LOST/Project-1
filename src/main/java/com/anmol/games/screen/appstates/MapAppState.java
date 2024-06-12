package com.anmol.games.screen.appstates;

import com.anmol.games.Assets;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.Screen;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.scene.Spatial;

public class MapAppState extends Screen {
    public final float SCALE = 128;
    RigidBodyControl rigidBodyControl;

    @Override
    protected void init() {
        Spatial map = Assets.models.get("Models/LostMap.glb");
        map.scale(SCALE);
        rootNode.attachChild(map);

        Spatial lowLodMap = Assets.models.get("Models/LostMapLowLod.glb");
        lowLodMap.scale(SCALE);
        rigidBodyControl = new RigidBodyControl(CollisionShapeFactory.createMeshShape(lowLodMap), 0);
        map.addControl(rigidBodyControl);
    }

    @Override
    protected void show() {
        GlobalVariables.bulletAppState.getPhysicsSpace().add(rigidBodyControl);
    }

    @Override
    protected void hide() {
        GlobalVariables.bulletAppState.getPhysicsSpace().add(rigidBodyControl);
    }

    @Override
    public void update(float tpf) {

    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {

    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
