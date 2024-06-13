package com.anmol.games.screen.appstates.entity;

import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.Screen;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

import java.util.ArrayList;

public class EntityAppState extends Screen {
    public final Node entityNode = new Node("EntityNode");
    public final Node entityNode_ = new Node("EntityNode_");
    public final ArrayList<AbstractEntity> entities = new ArrayList<>();
    public final ArrayList<AbstractEntity> entities_ = new ArrayList<>();
    public final ArrayList<AbstractEntity> tmpEntities = new ArrayList<>();
    public final ArrayList<AbstractEntity> tmpEntities_ = new ArrayList<>();

    public static AbstractEntity getEntity(Spatial s) {
        if (s instanceof AbstractEntity e) {
            return e;
        } else {
            if (s.getParent() != null) {
                return getEntity(s.getParent());
            } else {
                return null;
            }
        }
    }

    public void spawn(AbstractEntity abstractEntity) {
        if (!GlobalVariables.lostMap) {
            tmpEntities_.add(abstractEntity);
            entityNode_.attachChild(abstractEntity);
        } else {
            tmpEntities.add(abstractEntity);
            entityNode.attachChild(abstractEntity);
        }
    }

    @Override
    protected void init() {
        entities.addAll(GlobalVariables.data.entities);
    }

    @Override
    protected void show() {

    }

    @Override
    protected void hide() {
        GlobalVariables.data.entities.clear();
        GlobalVariables.data.entities.addAll(entities);
        rootNode.detachAllChildren();
    }

    @Override
    public void update(float tpf) {
        if (!GlobalVariables.lostMap) {
            rootNode.attachChild(entityNode_);
            rootNode.detachChild(entityNode);

            entities.forEach(abstractEntity -> abstractEntity.update(tpf));
            entities.removeIf(abstractEntity -> !abstractEntity.isAlive);
            entities.addAll(tmpEntities);
            tmpEntities.clear();
            entities_.clear();
            tmpEntities_.clear();
        } else {
            rootNode.detachChild(entityNode_);
            rootNode.attachChild(entityNode);

            entities_.forEach(abstractEntity -> abstractEntity.update(tpf));
            entities_.removeIf(abstractEntity -> !abstractEntity.isAlive);
            entities_.addAll(tmpEntities_);
            tmpEntities_.clear();
        }

    }


    @Override
    protected void action(String name, boolean isPressed, float tpf) {

    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
