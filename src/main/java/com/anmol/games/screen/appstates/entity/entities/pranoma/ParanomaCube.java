package com.anmol.games.screen.appstates.entity.entities.pranoma;

import com.anmol.games.Assets;
import com.anmol.games.GlobalVariables;
import com.anmol.games.screen.appstates.entity.AbstractEntity;
import com.jme3.material.RenderState;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

public class ParanomaCube extends AbstractEntity {
    public Vector3f moveTo = new Vector3f();
float t;
    public ParanomaCube(Vector3f pos, int element) {
        super(pos, 1, element);
        setName("Paranoma Cube");
        maxHp = 512*level*level;
        hp = maxHp;
    }

    @Override
    protected void init() {

    }

    @Override
    protected Spatial genrateSpatial() {
        Geometry g = new Geometry("", new Box(1, 1, 1));
        g.setMaterial(Assets.mat.clone());
        g.getMaterial().setTexture("ColorMap", Assets.textures.get("Textures/Elements/C" + element + ".png"));
        g.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        return g;
    }

    @Override
    protected void update(float tpf) {
      spatial.setLocalTranslation(FastMath.interpolateLinear(tpf*16, spatial.getLocalTranslation(), moveTo));
      spatial.lookAt(GlobalVariables.data.player_pos, Vector3f.UNIT_Y);
      t += tpf;
      if (GlobalVariables.data.player_pos.isSimilar(spatial.getLocalTranslation(), 1)) {
          if (t > 0.73) {
              t = 0;
              GlobalVariables.data.player_hp -= (long) level * level;
          }
      }
    }

    @Override
    protected void onDeath() {

    }
}
