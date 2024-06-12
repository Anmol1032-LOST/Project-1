package com.anmol.games;

import com.jme3.math.Vector3f;

import java.io.Serializable;

public class Data implements Serializable {
    public Vector3f player_pos;
    public long player_hp;
    public long player_maxHp;
    public float player_stamina;
    public float player_elementalStamina;
    public long story_phase;
    public long[] story_orbs;

    public Data(Vector3f player_pos, long player_hp, long player_maxHp, float player_stamina, float player_elementalStamina, long story_phase, long[] story_orbs) {
        this.player_pos = player_pos;
        this.player_hp = player_hp;
        this.player_maxHp = player_maxHp;
        this.player_stamina = player_stamina;
        this.player_elementalStamina = player_elementalStamina;
        this.story_phase = story_phase;
        this.story_orbs = story_orbs;
    }
}
