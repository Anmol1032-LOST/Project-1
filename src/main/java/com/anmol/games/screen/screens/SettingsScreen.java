package com.anmol.games.screen.screens;

import com.anmol.games.Assets;
import com.anmol.games.GuiUtils;
import com.anmol.games.LOST;
import com.anmol.games.screen.Screen;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.font.Rectangle;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

public class SettingsScreen extends Screen {
    final Node cornerNode = new Node();
    final String data = "Sorry for that, there's nothing here\nParanoma had restricted changes in settings.";
    @Override
    protected void init() {
        screenController.app.getInputManager().addMapping("SettingsScreen.click", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        screenController.app.getInputManager().addListener(this, "SettingsScreen.click");
        guiNode.attachChild(cornerNode);
        GuiUtils.makeScreen(guiNode, cornerNode);

        {
            BitmapText text = new BitmapText(Assets.font.get("Font"));
            text.setText(data);
            text.setColor(ColorRGBA.White);
            text.setSize(text.getFont().getCharSet().getRenderedSize()/2f);
            text.setBox(new Rectangle(LOST.width/8, LOST.height/8, LOST.width * 3 / 4f, LOST.height * 3 / 4f));
            text.setAlignment(BitmapFont.Align.Center);
            text.setVerticalAlignment(BitmapFont.VAlign.Center);
            text.setLocalTranslation(0, text.getHeight(), 3);
            guiNode.attachChild(text);
        }
    }

    @Override
    protected void show() {
        t = 0;
    }

    @Override
    protected void hide() {

    }

    float t;
    @Override
    public void update(float tpf) {
        t += tpf;
        GuiUtils.updateScreen(cornerNode, t);
    }

    @Override
    protected void action(String name, boolean isPressed, float tpf) {
        if (name.equals("SettingsScreen.click") && !isPressed) {
            switchScreen(screenController.startScreen);
        }
    }

    @Override
    protected void analog(String name, float value, float tpf) {

    }
}
