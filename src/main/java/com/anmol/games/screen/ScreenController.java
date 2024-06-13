package com.anmol.games.screen;

import com.anmol.games.LOST;
import com.anmol.games.screen.appstates.*;
import com.anmol.games.screen.appstates.entity.EntityAppState;
import com.anmol.games.screen.screens.*;
import com.anmol.games.screen.screens.screen0.*;

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
    public final LostMapAppState lostMapAppState = new LostMapAppState();
    public final LoopMapAppState loopMapAppState = new LoopMapAppState();
    public final SwitchToLoopMapAppState1 switchToLoopMapAppState1 = new SwitchToLoopMapAppState1();
    public final SwitchToLoopMapAppState2 switchToLoopMapAppState2 = new SwitchToLoopMapAppState2();
    public final PlayerAppState playerAppState = new PlayerAppState();
    public final AttackAppState attackAppState = new AttackAppState();
    public final GameGui gameGui = new GameGui();
    public final EntityAppState entityAppState = new EntityAppState();

    public final Screen0 screen0 = new Screen0();
    public final Element element = new Element();
    public final Inventory inventory = new Inventory();
    public final Purchase purchase = new Purchase();

    public final MainGameGuiScreen mainGameGuiScreen = new MainGameGuiScreen();
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
        lostMapAppState.simpleUpdate(tpf);
        loopMapAppState.simpleUpdate(tpf);
        switchToLoopMapAppState1.simpleUpdate(tpf);
        switchToLoopMapAppState2.simpleUpdate(tpf);
        playerAppState.simpleUpdate(tpf);
        attackAppState.simpleUpdate(tpf);
        gameGui.simpleUpdate(tpf);
        entityAppState.simpleUpdate(tpf);

        screen0.simpleUpdate(tpf);
        element.simpleUpdate(tpf);
        inventory.simpleUpdate(tpf);
        purchase.simpleUpdate(tpf);

        mainGameGuiScreen.simpleUpdate(tpf);


        isAllLoaded = startScreen.loaded && aboutCreatorScreen.loaded && settingsScreen.loaded && howToPlayScreen.loaded && beforePlayScreen.loaded && mainGameScreen.loaded && lostMapAppState.loaded && loopMapAppState.loaded && switchToLoopMapAppState1.loaded && switchToLoopMapAppState2.loaded && playerAppState.loaded && attackAppState.loaded && gameGui.loaded && entityAppState.loaded && mainGameGuiScreen.loaded && screen0.loaded && element.loaded && inventory.loaded && purchase.loaded;
        ;
    }

    public void initAll() {
        startScreen.init(this);
        aboutCreatorScreen.init(this);
        settingsScreen.init(this);
        howToPlayScreen.init(this);
        beforePlayScreen.init(this);
        mainGameScreen.init(this);
        lostMapAppState.init(this);
        loopMapAppState.init(this);
        switchToLoopMapAppState1.init(this);
        switchToLoopMapAppState2.init(this);
        playerAppState.init(this);
        attackAppState.init(this);
        gameGui.init(this);
        entityAppState.init(this);

        screen0.init(this);
        element.init(this);
        inventory.init(this);
        purchase.init(this);

        mainGameGuiScreen.init(this);
    }
}