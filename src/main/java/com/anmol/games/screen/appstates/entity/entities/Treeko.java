package com.anmol.games.screen.appstates.entity.entities;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Treeko extends AbstractEntity {
    float t = 0;

    public Treeko(Vector3f pos, int level, int element) {
        super(pos, level, element);
        setName("Treeko");
        maxHp = level;
        hp = maxHp;
    }

    @Override
    protected void init() {
        ((Geometry) ((Node) spatial).getChild("MainGeo")).getMaterial().setColor("BaseColor", Constants.GAME_COLORS[element]);
    }

    @Override
    protected Spatial genrateSpatial() {
        return Assets.models.get("Models/Entities/Treeko.glb").clone();
    }

    @Override
    protected void update(float tpf) {
        t += tpf;

        if (GlobalVariables.data.player_pos.isSimilar(spatial.getLocalTranslation(), 8)) {
            if (t > 0.037) {
                t = 0;
                GlobalVariables.data.player_hp -= level;
            }
        } else {
            spatial.move(GlobalVariables.data.player_pos.subtract(spatial.getLocalTranslation()).normalizeLocal().multLocal(tpf*6));
        }
    }


    @Override
    protected void onDeath() {

    }
}
