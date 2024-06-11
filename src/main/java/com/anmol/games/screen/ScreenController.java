package com.anmol.games.screen;

import com.anmol.games.LOST;
import com.anmol.games.screen.screens.IntroScreen;
import com.anmol.games.screen.screens.LoadingScreen;
import com.anmol.games.screen.screens.SplashScreen;

public class ScreenController {
    public final LOST app;
    public final SplashScreen splashScreen = new SplashScreen();
    public final LoadingScreen loadingScreen = new LoadingScreen();
    public final IntroScreen introScreen = new IntroScreen();

    public ScreenController(LOST app) {
        this.app = app;
        splashScreen.init(this);
        splashScreen.setEnabled(true);
    }

    public void update(final float tpf) {
        splashScreen.simpleUpdate(tpf);
        loadingScreen.simpleUpdate(tpf);
        introScreen.simpleUpdate(tpf);
    }

    public void initAll() {
        introScreen.init(this);
    }
}