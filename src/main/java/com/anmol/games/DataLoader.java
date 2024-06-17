package com.anmol.games;

import java.util.ArrayList;

public class DataLoader {
    public static Data load() {
        return new Data(Constants.PLAYER_POS.clone(), 127, 127, 0, 0, 10, new long[6], new float[6], new float[6], new float[6], new ArrayList<>(), new long[6][4]);
    }
}
