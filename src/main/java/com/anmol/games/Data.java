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
    public float[] player_B1Time;
    public float[] player_B2Time;
    public float[] player_XTime;

    public Data(Vector3f player_pos, long player_hp, long player_maxHp, float player_stamina, float player_elementalStamina, long story_phase, long[] story_orbs, float[] player_B1Time, float[] player_B2Time, float[] player_XTime) {
        this.player_pos = player_pos;
        this.player_hp = player_hp;
        this.player_maxHp = player_maxHp;
        this.player_stamina = player_stamina;
        this.player_elementalStamina = player_elementalStamina;
        this.story_phase = story_phase;
        this.story_orbs = story_orbs;
        this.player_B1Time = player_B1Time;
        this.player_B2Time = player_B2Time;
        this.player_XTime = player_XTime;
    }
}