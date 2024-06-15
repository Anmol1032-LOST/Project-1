package com.anmol.games.screen.appstates.entity.entities;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Slime extends AbstractEntity {
    public Slime(Vector3f pos, int level, int element) {
        super(pos, level, element);
        setName("Slime");
        maxHp = 63*level;
        hp = maxHp;
    }

    @Override
    protected void init() {
        ((Geometry) ((Node) spatial).getChild("MainGeo")).getMaterial().setColor("BaseColor", Constants.GAME_COLORS[element]);
    }

    @Override
    protected Spatial genrateSpatial() {
        return Assets.models.get("Models/Entities/Slime.glb").clone();
    }

    float t = 0;

    @Override
    protected void update(float tpf) {
        if (GlobalVariables.data.player_pos.isSimilar(spatial.getLocalTranslation(), 8)) {
            t += tpf;
            if (t > 0.07f) {
                t = 0;
                float c = (float) hp / maxHp;
                ((Node) spatial).getChild("MainGeo").setLocalScale(c, c, c);
                GlobalVariables.data.player_hp -= 1;
                damage(1);
            }
        }
    }

    @Override
    protected void onDeath() {

    }
}
