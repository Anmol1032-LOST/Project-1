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

class OctoGramOrb extends AbstractEntity {
    final Vector3f dir;
    float t = 0;
    public OctoGramOrb(Vector3f pos, int level, int element, Vector3f dir) {
        super(pos, level, element);
        setName("Octo Gram Orb");
        maxHp = 1;
        hp = 1;
        this.dir = dir;
    }

    @Override
    protected void init() {

    }

    @Override
    protected Spatial genrateSpatial() {
        Geometry g = new Geometry("", new Sphere(32, 32, 1));
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
        if (spatial.getLocalTranslation().distanceSquared(GlobalVariables.data.player_pos) < 32) {
            GlobalVariables.data.player_hp -= level;
            damage(hp);
        }
    }

    @Override
    protected void onDeath() {

    }
}

public class OctoGram extends AbstractEntity {
    float t = 0;
    Vector3f vec = new Vector3f();
    AnimComposer animComposer;
    float t2 = 0;


    public OctoGram(Vector3f pos, int level, int element) {
        super(pos, level, element);
        setName("Octo Gram");
        maxHp = 63 * level;
        hp = maxHp;
    }

    @Override
    protected void init() {
        ((Geometry) ((Node) ((Node) spatial).getChild(0)).getChild("MainGeo")).getMaterial().setColor("BaseColor", Constants.GAME_COLORS[element]);
        animComposer = ((Node) spatial).getChild(0).getControl(AnimComposer.class);
        animComposer.setCurrentAction("MainAction");
    }

    @Override
    protected Spatial genrateSpatial() {
        return Assets.models.get("Models/Entities/OctoGram.glb").clone().scale(3);
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
            entityAppState.spawn(new OctoGramOrb(spatial.getLocalTranslation(), level*16, element, GlobalVariables.data.player_pos.subtract(spatial.getLocalTranslation())));
        }

        float v = (FastMath.sin(t2/3) + FastMath.sin(t2/5)) * 3;
        vec.set((FastMath.sin(v)) * 45, 45, FastMath.cos(v) * 45);

        vec.addLocal(GlobalVariables.data.player_pos);

        spatial.lookAt(GlobalVariables.data.player_pos, Vector3f.UNIT_Y);
        spatial.setLocalTranslation(vec);
    }

    @Override
    protected void onDeath() {

    }
}
