package com.anmol.games;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;

public class Constants {
    public static final boolean IS_DEVELOPMENT = false;
    public static final String GAME_NAME = "L.O.S.T.";
    public static final String GAME_NAME_FULL = "Luminosity of Stygian Transcendent";
    public static final ColorRGBA[] GAME_COLORS = new ColorRGBA[]{
            new ColorRGBA(1, 0, 0.5f, 1),
            new ColorRGBA(1, 0.5f, 0, 1),
            new ColorRGBA(0.5f, 1, 0, 1),
            new ColorRGBA(0, 1, .5f, 1),
            new ColorRGBA(0, .5f, 1, 1),
            new ColorRGBA(.5f, 0, 1, 1),
    };
    public static final String[] GAME_ELEMENT_NAMES = new String[]{
            "Capella", "Narabedla", "Rigel", "Sirius", "Procyon", "Pollux"
    };
    public static final float B1_TIME = 10;
    public static final float B2_TIME = 27;
    public static final float X_TIME = 71;
    public static final float A2_TIME = 0.73f;
    public static final Vector3f PLAYER_POS = new Vector3f(0, 372, 0);
}
