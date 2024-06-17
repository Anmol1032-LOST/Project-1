package com.anmol.games.screen.appstates.entity.entities.pranoma;

import com.anmol.games.Assets;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

public class Paranoma2 extends AbstractEntity {
    ParanomaCube g0;
    ParanomaCube g1;
    ParanomaCube g2;
    ParanomaCube g3;
    ParanomaCube g4;
    ParanomaCube g5;
    ParanomaCube[] g;
    Phase phase = Phase.ACTIVE;
    float t = 0;
    float t0;

    public Paranoma2(Vector3f pos, int element) {
        super(pos, 1, element);
        setName("Paranoma");
        maxHp = 256;
        hp = maxHp;
    }

    @Override
    protected void init() {
        g0 = new ParanomaCube(spatial.getLocalTranslation(), 0);
        g1 = new ParanomaCube(spatial.getLocalTranslation(), 1);
        g2 = new ParanomaCube(spatial.getLocalTranslation(), 2);
        g3 = new ParanomaCube(spatial.getLocalTranslation(), 3);
        g4 = new ParanomaCube(spatial.getLocalTranslation(), 4);
        g5 = new ParanomaCube(spatial.getLocalTranslation(), 5);

        entityAppState.spawn(g0);
        entityAppState.spawn(g1);
        entityAppState.spawn(g2);
        entityAppState.spawn(g3);
        entityAppState.spawn(g4);
        entityAppState.spawn(g5);

        g = new ParanomaCube[6];
        g[0] = g0;
        g[1] = g1;
        g[2] = g2;
        g[3] = g3;
        g[4] = g4;
        g[5] = g5;
    }

    @Override
    protected Spatial genrateSpatial() {
        return Assets.models.get("Models/Entities/Paranoma.glb");
    }

    @Override
    protected void update(float tpf) {
        t += tpf;
        t0 += tpf;
        switch (phase) {
            case ACTIVE -> {
                for (int i = 0; i < 6; i++) {
                    g[i].moveTo.set(spatial.getLocalTranslation().add(FastMath.sin(i / 6f * FastMath.TWO_PI) * 16, 0, FastMath.cos(i / 6f * FastMath.TWO_PI) * 16));
                }
                if (t > 4) {
                    t0 = 0;
                    t = 0;
                    phase = Phase.values()[FastMath.rand.nextInt(1, 3)];
                }
            }
            case ROTATE -> {
                for (int i = 0; i < 6; i++) {
                    g[i].moveTo.set(GlobalVariables.data.player_pos.add(FastMath.sin((i / 6f + t) * FastMath.TWO_PI) * 16, 1, FastMath.cos((i / 6f + t) * FastMath.TWO_PI) * 16));
                }
                if (t0 > 0.47) {
                    t0 = 0;
                    GlobalVariables.data.player_hp -= 1;
                }
                if (t > 6) {
                    t = 0;
                    t0 = 0;
                    phase = Phase.ACTIVE;
                }
            }
            case EXPLODE -> {
                if (t < 1) {
                    Vector3f s = spatial.getLocalTranslation().subtract(0, 22, 0);
                    for (int i = 0; i < 6; i++) {
                        g[i].moveTo.set(s);
                    }
                    t0 = 0;
                } else if (t < 1.3f) {
                    for (int i = 0; i < 6; i++) {
                        g[i].moveTo.set(spatial.getLocalTranslation().add(FastMath.sin(i / 6f * FastMath.TWO_PI) * t0 * 128, -22, FastMath.cos(i / 6f * FastMath.TWO_PI) * t0 * 128));
                    }
                } else {
                    t = 0;
                    t0 = 0;
                    phase = Phase.ACTIVE;
                }
            }
            case HIDE -> {
                for (int i = 0; i < 6; i++) {
                    g[i].moveTo.set(spatial.getLocalTranslation().add(0, -30, 0));
                }
                if (t > 7) {
                    t = 0;
                    t0 = 0;
                    phase = Phase.ACTIVE;
                }
            }
        }
    }

    @Override
    protected void onDeath() {
        for (int i = 0; i < 6; i++) {
            if (g[i].isAlive) {
                g[i].damage(g[i].hp);
            }
        }
    }

    enum Phase {
        ACTIVE,
        ROTATE,
        EXPLODE,
        HIDE
    }
}
