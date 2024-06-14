package com.anmol.games.screen.appstates.entity.entities;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.appstates.PlayerAppState;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.anim.AnimComposer;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Forst extends AbstractEntity {
    float t = 0;
    boolean isActive = false;

    public Forst(Vector3f pos, int level, int element) {
        super(pos, level, element);
        setName("Freee");
        maxHp = 63 * level;
        hp = maxHp;
    }

    @Override
    protected void init() {

    }

    @Override
    protected Spatial genrateSpatial() {
        Node n = (Node) (Assets.models.get("Models/Entities/Forst.glb")).clone();
        ((Geometry) (n.getChild(0))).getMaterial().setColor("BaseColor", Constants.GAME_COLORS[element]);
        return n;
    }

    @Override
    protected void update(float tpf) {
        if (GlobalVariables.data.player_pos.isSimilar(spatial.getLocalTranslation(), 16) && (t == 0)) {
            GlobalVariables.data.player_hp -= 127;
            t = FastMath.ZERO_TOLERANCE;
        }
        if (t > 0) {
            t += tpf;
        }
        if (t > 1) {
            damage(hp);
        }
    }

    @Override
    protected void onDeath() {
        entityAppState.spawn(new Freee(spatial.getLocalTranslation(), level, element));
    }
}
