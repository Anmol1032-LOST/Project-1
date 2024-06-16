package com.anmol.games.screen.appstates;

import com.anmol.games.Assets;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.Screen;
import com.anmol.games.screen.appstates.entity.entities.Django;
import com.anmol.games.screen.appstates.entity.entities.Forst;
import com.anmol.games.screen.appstates.entity.entities.Slime;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class LoopMapAppState extends Screen {
    public int phase;
    public int element;
    Spatial[] maps = new Spatial[6];
    Spatial map;

    public void set(int phase, int element) {
        this.phase = phase;
        this.element = element;
    }

    @Override
    protected void init() {
        maps[0] = Assets.models.get("Models/Map/LoopMap1.glb");
        maps[1] = Assets.models.get("Models/Map/LoopMap2.glb");
        maps[2] = Assets.models.get("Models/Map/LoopMap3.glb");
        maps[3] = Assets.models.get("Models/Map/LoopMap4.glb");
        maps[4] = Assets.models.get("Models/Map/LoopMap5.glb");
        maps[5] = Assets.models.get("Models/Map/LoopMap6.glb");
    }

    @Override
    protected void show() {
        GlobalVariables.lostMap = false;
        if (phase % 2 == 1) map = maps[0];
        else if ((phase) % 4 == 2) map = maps[1];
        else if ((phase) % 8 == 4) map = maps[2];
        else if ((phase) % 16 == 8) map = maps[3];
        else if ((phase) % 32 == 16) map = maps[4];
        else if ((phase) % 64 == 32) map = maps[5];

        screenController.playerAppState.teleport(new Vector3f(0, 10, 0));
        RigidBodyControl r = new RigidBodyControl(0);
        map.addControl(r);
        GlobalVariables.bulletAppState.getPhysicsSpace().add(r);
        rootNode.attachChild(map);

        if (element < 6) {
            spawnEntity(phase, element);
        } else {
            spawnEntity(phase);
        }
    }


    @Override
    protected void hide() {
        GlobalVariables.bulletAppState.getPhysicsSpace().removeAll(rootNode);
        rootNode.detachAllChildren();
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

    private void spawnEntity(int phase) {
        switch (phase) {
            case 1 -> {
                screenController.entityAppState.spawn(new Slime(new Vector3f(64, 10, 64), 1, 1));
                screenController.entityAppState.spawn(new Slime(new Vector3f(32, 10, 64), 1, 2));
                screenController.entityAppState.spawn(new Slime(new Vector3f(8, 10, 64), 1, 3));
                screenController.entityAppState.spawn(new Slime(new Vector3f(-8, 10, 64), 1, 4));
                screenController.entityAppState.spawn(new Slime(new Vector3f(-32, 10, 64), 1, 5));
                screenController.entityAppState.spawn(new Slime(new Vector3f(-64, 10, 64), 1, 0));
            }
            case 2 -> {
                screenController.entityAppState.spawn(new Slime(new Vector3f(64, 10, 64), 3, 0));
                screenController.entityAppState.spawn(new Slime(new Vector3f(0, 10, 64), 3, 2));
                screenController.entityAppState.spawn(new Slime(new Vector3f(-64, 10, 64), 3, 4));

                screenController.entityAppState.spawn(new Forst(new Vector3f(64, 10, -64), 1, 1));
                screenController.entityAppState.spawn(new Forst(new Vector3f(0, 10, -64), 1, 3));
                screenController.entityAppState.spawn(new Forst(new Vector3f(-64, 10, -64), 1, 5));
            }
            case 3 -> {
                spawnEntity(1);
                screenController.entityAppState.spawn(new Forst(new Vector3f(64, 10, -64), 7, 1));
                screenController.entityAppState.spawn(new Forst(new Vector3f(0, 10, -64), 7, 3));
                screenController.entityAppState.spawn(new Forst(new Vector3f(-64, 10, -64), 7, 5));
            }
            case 4 -> {
                screenController.entityAppState.spawn(new Forst(new Vector3f(-64, 10, -64), 1, 1));
                screenController.entityAppState.spawn(new Forst(new Vector3f(0, 10, -64), 1, 3));
                screenController.entityAppState.spawn(new Forst(new Vector3f(64, 10, -64), 1, 5));
            }
            case 5 -> {
                spawnEntity(4);
                spawnEntity(2);
            }
            case 6 -> {
                spawnEntity(4);
                spawnEntity(1);

                screenController.entityAppState.spawn(new Django(new Vector3f(64, 10, 64), 3, 0));
                screenController.entityAppState.spawn(new Django(new Vector3f(0, 10, 64), 3, 2));
                screenController.entityAppState.spawn(new Django(new Vector3f(-64, 10, 64), 3, 4));
            }
            case 7 -> {
                screenController.entityAppState.spawn(new Django(new Vector3f(64, 10, 64), 7, 0));
                screenController.entityAppState.spawn(new Django(new Vector3f(0, 10, 64), 7, 2));
                screenController.entityAppState.spawn(new Django(new Vector3f(-64, 10, 64), 7, 4));
            }
        }
    }

    private void spawnEntity(int phase, int element) {
        switch (phase) {
        }
    }
}
