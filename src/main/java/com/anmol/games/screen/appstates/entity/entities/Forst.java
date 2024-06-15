package com.anmol.games.screen.appstates.entity.entities;

import com.anmol.games.Assets;
import com.anmol.games.Constants;
import com.anmol.games.GlobalVariables;
import com.anmol.games.Sounds;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.audio.AudioNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Forst extends AbstractEntity {
    public Forst(Vector3f pos, int level, int element) {
        super(pos, level, element);
        setName("Forst");
        maxHp = 63*level;
        hp = maxHp;
    }

    boolean isActive = false;

    @Override
    protected void init() {
        ((Geometry) ((Node) spatial).getChild("1")).getMaterial().setColor("BaseColor", Constants.GAME_COLORS[element]);
        ((Geometry) ((Node) spatial).getChild("2")).getMaterial().setColor("BaseColor", Constants.GAME_COLORS[element]);
    }

    @Override
    protected Spatial genrateSpatial() {
        return Assets.models.get("Models/Entities/Forst.glb").clone();
    }

    float t = 0;

    @Override
    protected void update(float tpf) {
        if (!isActive) {
            ((Node) spatial).getChild("Torus").rotate(0, tpf, 0);
            ((Node) spatial).getChild("1").rotate(0, -tpf * 2, 0);
            if (GlobalVariables.data.player_pos.isSimilar(spatial.getLocalTranslation(), 8)) {
                isActive = true;
                AudioNode s = Sounds.forst.clone();
                s.setLocalTranslation(spatial.getLocalTranslation());
                attachChild(s);
                s.play();
            }
        } else if (t < 1) {
            t += tpf;
            ((Node) spatial).getChild("1").setLocalScale(1-t, 1-t, 1-t);
            ((Node) spatial).getChild("2").setLocalScale(1+t*5, 1+t*5, 1+t*5);
        } else {
            if (GlobalVariables.data.player_pos.isSimilar(spatial.getLocalTranslation(), 8)) {
                GlobalVariables.data.player_hp -= hp;
            }
            damage(hp);
        }
    }

    @Override
    protected void onDeath() {

    }
}
