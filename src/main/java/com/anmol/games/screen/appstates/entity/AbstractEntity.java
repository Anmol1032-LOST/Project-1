package com.anmol.games.screen.appstates.entity;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public abstract class AbstractEntity extends Node {
    public int hp;
    public int maxHp;
    public int element;
    public int level;
    public Spatial spatial;
    public boolean isAlive = true;
    public EntityAppState entityAppState;

    public AbstractEntity(Vector3f pos, int level, int element) {
        super();
        this.level = level;
        this.element = element;
        this.spatial = genrateSpatial();
        this.attachChild(spatial);
        spatial.setLocalTranslation(pos);
    }


    protected abstract void init();

    protected abstract Spatial genrateSpatial();

    protected abstract void update(float tpf);

    public void damage(int i) {
        hp -= i;
        if (hp <= 0) {
            isAlive = false;
            spatial.removeFromParent();
        }
        onDeath();
    }

    protected abstract void onDeath();
}
