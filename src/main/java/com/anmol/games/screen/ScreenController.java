package com.anmol.games.screen;

import com.anmol.games.LOST;
import com.anmol.games.screen.screens.StartScreen;
import com.anmol.games.screen.screens.LoadingScreen;
import com.anmol.games.screen.screens.SplashScreen;

public class ScreenController {
    public final LOST app;
    public final SplashScreen splashScreen = new SplashScreen();
    public final LoadingScreen loadingScreen = new LoadingScreen();
    public final StartScreen startScreen = new StartScreen();
    public boolean isAllLoaded = false;

    public ScreenController(LOST app) {
        this.app = app;
        splashScreen.init(this);
        splashScreen.setEnabled(true);
    }

    public void update(final float tpf) {
        splashScreen.simpleUpdate(tpf);
        loadingScreen.simpleUpdate(tpf);
        startScreen.simpleUpdate(tpf);

        isAllLoaded = startScreen.loaded;
    }

    public void initAll() {
        startScreen.init(this);
    }
}