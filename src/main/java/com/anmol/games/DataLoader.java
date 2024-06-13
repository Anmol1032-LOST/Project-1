package com.anmol.games;

import com.jme3.math.Vector3f;

import java.util.ArrayList;

public class DataLoader {
    public static Data load() {
        return new Data(new Vector3f(0, 376, 0), 127, 127, 0, 0, 1, new long[6], new float[6], new float[6], new float[6], new ArrayList<>());
    }
}
