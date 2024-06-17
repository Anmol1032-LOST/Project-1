package com.anmol.games.screen.appstates.entity.entities;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.anim.AnimComposer;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;


class ReeperOrb extends AbstractEntity {
    final Vector3f dir;
    float t = 0;

    public ReeperOrb(Vector3f pos, int level, int element, Vector3f dir) {
        super(pos, level, element);
        setName("Reeper Orb");
        maxHp = 8 * level;
        hp = maxHp;
        this.dir = dir;
    }

    @Override
    protected void init() {

    }

    @Override
    protected Spatial genrateSpatial() {
        Geometry g = new Geometry("", new Sphere(32, 32, 4));
        g.setMaterial(Assets.mat);
        return g;
    }

    @Override
    protected void update(float tpf) {
        t += tpf;
        if (t > 10) {
            damage(hp);
        }
        spatial.move(dir.mult(tpf));
        if (spatial.getLocalTranslation().distanceSquared(GlobalVariables.data.player_pos) < 64) {
            GlobalVariables.data.player_hp -= level;
            damage(hp);
        }
    }

    @Override
    protected void onDeath() {

    }
}


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
        animComposer.setCurrentAction("Fly");
    }

    @Override
    protected Spatial genrateSpatial() {
        return Assets.models.get("Models/Entities/Reeper.glb").clone();
    }

    @Override
    protected void update(float tpf) {
        t += tpf;
        t2 += tpf;
        if (t > 4.7f) {
            t = 0;
            if (FastMath.rand.nextInt(3) == 0) {
                t2 = FastMath.rand.nextFloat(-100, 100);
            }
            entityAppState.spawn(new ReeperOrb(spatial.getLocalTranslation(), level * 16, element, GlobalVariables.data.player_pos.subtract(spatial.getLocalTranslation())));
        }

        float v = (FastMath.sin(t2 / 3) + FastMath.sin(t2 / 5)) * 3;
        vec.set((FastMath.sin(v)) * 45, 45, FastMath.cos(v) * 45);

        vec.addLocal(GlobalVariables.data.player_pos);

        spatial.lookAt(GlobalVariables.data.player_pos, Vector3f.UNIT_Y);
        spatial.setLocalTranslation(vec);
    }

    @Override
    protected void onDeath() {

    }
}
