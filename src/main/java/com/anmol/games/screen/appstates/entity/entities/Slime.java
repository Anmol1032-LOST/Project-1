package com.anmol.games.screen.appstates.entity.entities;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.anim.AnimComposer;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Slime extends AbstractEntity {
    public Slime(Vector3f pos, int level, int element) {
        super(pos, level, element);
        setName("Slime");
        maxHp = 63 * level;
        hp = maxHp;
    }

    @Override
    protected void init() {

    }

    @Override
    protected Spatial genrateSpatial() {
        Node n = ((Node) Assets.models.get("Models/Entities/Slime.glb"));
        ((Geometry) n.getChild(0)).getMaterial().setColor("Emissive", Constants.GAME_COLORS[element]);
        n.getChild(0).getControl(AnimComposer.class).setCurrentAction("MainAnim");
        return n;
    }

    @Override
    protected void update(float tpf) {
        if (GlobalVariables.data.player_pos.isSimilar(spatial.getLocalTranslation(), 32)){
            GlobalVariables.data.player_hp -= level;
            damage(level);
        }
    }

    @Override
    protected void onDeath() {

    }
}
