package com.anmol.games.screen.appstates;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.LOST;
import com.anmol.games.screen.Screen;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.anmol.games.screen.appstates.entity.EntityAppState;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.CenterQuad;
import com.jme3.scene.shape.Line;

public class AttackAppState extends Screen {
    boolean isA2 = false;
    float A2Timer = 0;

    Geometry A1;


    @Override
    protected void init() {
        screenController.app.getInputManager().addMapping("A1", new KeyTrigger(KeyInput.KEY_F), new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        screenController.app.getInputManager().addMapping("A2", new KeyTrigger(KeyInput.KEY_Z), new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        screenController.app.getInputManager().addMapping("B1", new KeyTrigger(KeyInput.KEY_E));
        screenController.app.getInputManager().addMapping("B2", new KeyTrigger(KeyInput.KEY_Q));
        screenController.app.getInputManager().addMapping("X", new KeyTrigger(KeyInput.KEY_X));
        screenController.app.getInputManager().addMapping("Up", new KeyTrigger(KeyInput.KEY_UP), new MouseAxisTrigger(MouseInput.AXIS_WHEEL, false));
        screenController.app.getInputManager().addMapping("Dn", new KeyTrigger(KeyInput.KEY_DOWN), new MouseAxisTrigger(MouseInput.AXIS_WHEEL, true));
        screenController.app.getInputManager().addListener(this, "A1", "A2", "B1", "B2", "X", "Up", "Dn");
    }

    @Override
    protected void show() {

    }

    @Override
    protected void hide() {

    }

    @Override
    public void update(float tpf) {
        if (isA2) {
            A2Timer += tpf;
        }
        GlobalVariables.data.player_B1Time[GlobalVariables.element] += tpf;
        GlobalVariables.data.player_B2Time[GlobalVariables.element] += tpf;
        GlobalVariables.data.player_XTime[GlobalVariables.element] += tpf;

        for (int i = 0; i < 6; i++) {
            if (i == GlobalVariables.element) continue;
            float v = 3.7f;
            GlobalVariables.data.player_B1Time[i] += tpf / v;
            GlobalVariables.data.player_B2Time[i] += tpf / v;
            GlobalVariables.data.player_XTime[i] += tpf / v;
        }

        if (GlobalVariables.data.player_B1Time[GlobalVariables.element] >= 10) {
            GlobalVariables.data.player_B1Time[GlobalVariables.element] = 10;
        }
        if (GlobalVariables.data.player_B2Time[GlobalVariables.element] >= 27) {
            GlobalVariables.data.player_B2Time[GlobalVariables.element] = 27;
        }
        if (GlobalVariables.data.player_XTime[GlobalVariables.element] >= 71) {
            GlobalVariables.data.player_XTime[GlobalVariables.element] = 71;
        }

        elementalUpdate(tpf);
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
        switch (name) {
            case "A1" -> {
                if (isPressed) {
                    A1();
                }
            }
            case "A2" -> {
                if (!isPressed && A2Timer > Constants.A2_TIME) {
                    A2();
                }
                isA2 = isPressed;
                A2Timer = 0;
            }
            case "B1" -> {
                if (GlobalVariables.data.player_B1Time[GlobalVariables.element] >= Constants.B1_TIME) {
                    if (GlobalVariables.data.player_elementalStamina > 0.13f) {
                        GlobalVariables.data.player_elementalStamina -= 0.13f;
                        GlobalVariables.data.player_B1Time[GlobalVariables.element] = 0;
                        B1();
                    }
                }
            }
            case "B2" -> {
                if (GlobalVariables.data.player_B2Time[GlobalVariables.element] >= Constants.B2_TIME) {
                    if (GlobalVariables.data.player_elementalStamina > 0.37f) {
                        GlobalVariables.data.player_elementalStamina -= 0.37f;
                        GlobalVariables.data.player_B2Time[GlobalVariables.element] = 0;
                        B2();
                    }
                }
            }
            case "X" -> {
                if (GlobalVariables.data.player_XTime[GlobalVariables.element] >= Constants.X_TIME) {
                    if (GlobalVariables.data.player_elementalStamina > 0.45f) {
                        GlobalVariables.data.player_elementalStamina -= 0.45f;
                        GlobalVariables.data.player_XTime[GlobalVariables.element] = 0;
                        X();
                    }
                }
            }
            case "Up" -> {
                if (isPressed) {
                    GlobalVariables.element += 1;
                    GlobalVariables.element %= 6;
                }
            }
            case "Dn" -> {
                if (isPressed) {
                    GlobalVariables.element = (GlobalVariables.element + 5) % 6;
                }
            }
        }
    }

    @Override
    protected void analog(String name, float value, float tpf) {
    }

    private void A1() {
        CollisionResults collisionResults = new CollisionResults();
        Ray ray = new Ray(screenController.app.getCamera().getLocation(), screenController.app.getCamera().getDirection());
        screenController.app.getRootNode().collideWith(ray, collisionResults);
        if (collisionResults.size() >= 1 && collisionResults.getClosestCollision().getDistance() < 32) {
            Spatial s = collisionResults.getClosestCollision().getGeometry();
            AbstractEntity e = EntityAppState.getEntity(s);
            if (e != null) {
                if (GlobalVariables.element == e.element) {
                    e.damage(1);
                } else if ((GlobalVariables.element + 2) % 6 == e.element) {
                    e.damage(4);
                } else {
                    e.damage(2);
                }

                GlobalVariables.data.player_elementalStamina += 0.07f;

                if (A1 != null) {
                    rootNode.detachChild(A1);
                }
                A1 = new Geometry("", new Line(screenController.app.getCamera().getLocation().add(FastMath.rand.nextFloat(-1, 1), FastMath.rand.nextFloat(-1, 1), FastMath.rand.nextFloat(-1, 1)), e.spatial.getLocalTranslation()));
                A1.setMaterial(Assets.mat.clone());
                A1.getMaterial().getAdditionalRenderState().setWireframe(true);
                A1.getMaterial().setColor("Color", Constants.GAME_COLORS[GlobalVariables.element]);
                A1.setUserData("time", 0.3f);
                rootNode.attachChild(A1);
            }
        }
    }

    private void A2() {
    }

    private void B1() {
    }

    private void B2() {
    }

    private void X() {
    }

    private void elementalUpdate(float tpf) {
        if (A1 != null) {
            float time = A1.getUserData("time");
            if (time > 0) {
                A1.setUserData("time", time-tpf);
            } else {
                rootNode.detachChild(A1);
                A1 = null;
            }
        }
    }
}