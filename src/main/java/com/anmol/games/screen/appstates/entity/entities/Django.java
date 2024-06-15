package com.anmol.games.screen.appstates.entity.entities;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Django extends AbstractEntity {
    float t = 0;

    public Django(Vector3f pos, int level, int element) {
        super(pos, level, element);
        setName("Django");
        maxHp = 63 * level;
        hp = maxHp;
    }

    @Override
    protected void init() {
        ((Geometry) ((Node) spatial).getChild(0)).getMaterial().setColor("BaseColor", Constants.GAME_COLORS[element]);
    }

    @Override
    protected Spatial genrateSpatial() {
        return Assets.models.get("Models/Entities/Django.glb").clone();
    }

    @Override
    protected void update(float tpf) {
        // todo
    }

    @Override
    protected void onDeath() {

    }
}
