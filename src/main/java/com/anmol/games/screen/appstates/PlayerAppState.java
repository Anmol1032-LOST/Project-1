package com.anmol.games.screen.appstates;

import com.anmol.games.Assets;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.Screen;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.PointLight;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;


public class PlayerAppState extends Screen {
    static Vector3f toTeleport;
    static float gravity = 29.400002F;
    final float radius = 0.5f;
    final float height = 10.5f;
    final private Vector3f walkDirection = new Vector3f(0, 0, 0);
    final private Vector3f forwardDir = new Vector3f(0, 0, 0);
    final private Vector3f leftDir = new Vector3f(0, 0, 0);
    public CharacterControl player;
    public ChaseCamera chaseCamera;
    boolean left = false;
    boolean right = false;
    boolean forward = false;
    boolean backward = false;
    boolean sprint = false;
    PointLight pl;
    Spatial playerSpatial;

    public static void teleport(Vector3f pos) {
        toTeleport = pos;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        chaseCamera.setEnabled(enabled);
        player.setEnabled(enabled);
        left = false;
        right = false;
        forward = false;
        backward = false;
        sprint = false;
        screenController.app.getInputManager().setCursorVisible(!enabled);
    }

    public void setChaseCameraEnabled(boolean enabled) {
        chaseCamera.setDragToRotate(!enabled);
        screenController.app.getInputManager().setCursorVisible(!enabled);
    }

    @Override
    protected void show() {
        GlobalVariables.bulletAppState.getPhysicsSpace().add(player);
        teleport(GlobalVariables.data.player_pos);
        screenController.app.getRootNode().addLight(pl);
    }

    @Override
    protected void hide() {
        GlobalVariables.bulletAppState.getPhysicsSpace().remove(player);
        screenController.app.getRootNode().removeLight(pl);
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
        switch (name) {
            case "Strafe Left" -> left = isPressed;
            case "Strafe Right" -> right = isPressed;
            case "Walk Forward" -> forward = isPressed;
            case "Walk Backward" -> backward = isPressed;
            case "Sprint" -> sprint = isPressed;
            case "Jump" -> {
                if (isPressed) player.jump();
            }
        }
    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }

    @Override
    protected void init() {
        player = new CharacterControl(new CapsuleCollisionShape(radius, height), 0.07f);
        player.setGravity(gravity);
        player.setFallSpeed(25f);
        player.setJumpSpeed(15);
        player.setEnabled(false);

        playerSpatial = new Node("Player");
        playerSpatial.setMaterial(Assets.mat.clone());
        playerSpatial.addControl(player);
        rootNode.attachChild(playerSpatial);

        pl = new PointLight();
        pl.setRadius(0);

        chaseCamera = new ChaseCamera(screenController.app.getCamera(), playerSpatial, screenController.app.getInputManager());
        chaseCamera.setMaxDistance(0);
        chaseCamera.setDragToRotate(false);
        chaseCamera.setInvertVerticalAxis(true);
        chaseCamera.setLookAtOffset(new Vector3f(0, height / 2, 0));
        chaseCamera.setEnabled(false);
        setupKeys();

        screenController.app.getInputManager().setCursorVisible(true);
    }

    @Override
    public void update(float tpf) {
        if (toTeleport != null) {
            player.setPhysicsLocation(toTeleport.clone());
            toTeleport = null;
        }

        screenController.app.getCamera().getDirection(forwardDir).setY(0).normalizeLocal();
        screenController.app.getCamera().getLeft(leftDir).setY(0).normalizeLocal();

        if (gravity < 29.400002F) {
            gravity += tpf * 9;
        } else {
            gravity = 29.400002F;
        }
        player.setGravity(gravity);

        walkDirection.set(0, 0, 0);
        if (left) {
            walkDirection.addLocal(leftDir);
        }
        if (right) {
            walkDirection.addLocal(leftDir.negate());
        }
        if (forward) {
            walkDirection.addLocal(forwardDir);
        }
        if (backward) {
            walkDirection.addLocal(forwardDir.negate());
        }

        float sprintSpeed = 1;
        final int maxSprintSpeed = 3;
        float sprintStamina = GlobalVariables.data.player_stamina;
        if (sprint && sprintStamina > 0) {
            sprintStamina -= tpf;
            if (sprintStamina > 0.5f) {
                sprintSpeed = maxSprintSpeed;
            } else if (sprintStamina > 0) {
                sprintSpeed = sprintStamina * 2 * maxSprintSpeed;
            }
        } else if (sprintStamina < 1) {
            sprintStamina += tpf / maxSprintSpeed;
        }
        GlobalVariables.data.player_stamina = (sprintStamina <= FastMath.ZERO_TOLERANCE) ? 0 : (sprintStamina >= 1) ? 1 : sprintStamina; // clamp value

        walkDirection.normalizeLocal().multLocal(0.3f * sprintSpeed);
        walkDirection.setY(0);
        player.setWalkDirection(walkDirection);

        pl.setPosition(screenController.app.getCamera().getLocation());

        player.getPhysicsLocation(GlobalVariables.data.player_pos);
    }


    private void setupKeys() {
        screenController.app.getInputManager().addMapping("Strafe Left", new KeyTrigger(KeyInput.KEY_A));
        screenController.app.getInputManager().addMapping("Strafe Right", new KeyTrigger(KeyInput.KEY_D));
        screenController.app.getInputManager().addMapping("Walk Forward", new KeyTrigger(KeyInput.KEY_W));
        screenController.app.getInputManager().addMapping("Walk Backward", new KeyTrigger(KeyInput.KEY_S));
        screenController.app.getInputManager().addMapping("Sprint", new KeyTrigger(KeyInput.KEY_LSHIFT));
        screenController.app.getInputManager().addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));


        screenController.app.getInputManager().addListener(this, "Strafe Left", "Strafe Right", "Walk Forward", "Walk Backward", "Jump", "Sprint");
    }
}
