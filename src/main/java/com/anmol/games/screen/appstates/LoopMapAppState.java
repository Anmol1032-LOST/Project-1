package com.anmol.games.screen.appstates;

import com.anmol.games.Assets;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.Screen;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;

public class LoopMapAppState extends Screen {
    Spatial[] maps = new Spatial[6];
    Spatial map;
    int phase;
    int element;

    public void set(int phase, int element) {
        this.phase = phase;
        this.element = element;
    }

    @Override
    protected void init() {
        maps[0] = Assets.models.get("Models/LoopMap1.glb");
        maps[1] = Assets.models.get("Models/LoopMap2.glb");
        maps[2] = Assets.models.get("Models/LoopMap3.glb");
        maps[3] = Assets.models.get("Models/LoopMap4.glb");
        maps[4] = Assets.models.get("Models/LoopMap5.glb");
        maps[5] = Assets.models.get("Models/LoopMap6.glb");
    }

    @Override
    protected void show() {
        if (phase % 2 == 1) map = maps[0];
        else if ((phase) % 4 == 2) map = maps[1];
        else if ((phase) % 8 == 4) map = maps[2];
        else if ((phase) % 16 == 8) map = maps[3];
        else if ((phase) % 32 == 16) map = maps[4];
        else if ((phase) % 64 == 32) map = maps[5];

        RigidBodyControl r = new RigidBodyControl(0);
        map.addControl(r);
        GlobalVariables.bulletAppState.getPhysicsSpace().add(r);
        guiNode.attachChild(map);
    }

    @Override
    protected void hide() {
        guiNode.detachAllChildren();
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
