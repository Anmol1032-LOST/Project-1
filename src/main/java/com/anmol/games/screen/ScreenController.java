package com.anmol.games.screen;

import com.anmol.games.LOST;
import com.anmol.games.screen.appstates.GameGui;
import com.anmol.games.screen.appstates.MapAppState;
import com.anmol.games.screen.appstates.PlayerAppState;
import com.anmol.games.screen.screens.*;

public class ScreenController {
    public final LOST app;
    public final SplashScreen splashScreen = new SplashScreen();
    public final LoadingScreen loadingScreen = new LoadingScreen();
    public final StartScreen startScreen = new StartScreen();
    public final AboutCreatorScreen aboutCreatorScreen = new AboutCreatorScreen();
    public final SettingsScreen settingsScreen = new SettingsScreen();
    public final HowToPlayScreen howToPlayScreen = new HowToPlayScreen();
    public final BeforePlayScreen beforePlayScreen = new BeforePlayScreen();
    public final MainGameScreen mainGameScreen = new MainGameScreen();
    public final MapAppState mapAppState = new MapAppState();
    public final PlayerAppState playerAppState = new PlayerAppState();
    public final GameGui gameGui = new GameGui();
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
        aboutCreatorScreen.simpleUpdate(tpf);
        settingsScreen.simpleUpdate(tpf);
        howToPlayScreen.simpleUpdate(tpf);
        beforePlayScreen.simpleUpdate(tpf);
        mainGameScreen.simpleUpdate(tpf);
        mapAppState.simpleUpdate(tpf);
        playerAppState.simpleUpdate(tpf);
        gameGui.simpleUpdate(tpf);

        isAllLoaded = startScreen.loaded;
    }

    public void initAll() {
        startScreen.init(this);
        aboutCreatorScreen.init(this);
        settingsScreen.init(this);
        howToPlayScreen.init(this);
        beforePlayScreen.init(this);
        mainGameScreen.init(this);
        mapAppState.init(this);
        playerAppState.init(this);
        gameGui.init(this);
    }
}