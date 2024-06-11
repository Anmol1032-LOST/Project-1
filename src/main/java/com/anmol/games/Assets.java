package com.anmol.games;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

import java.util.HashMap;

public class Assets {
    public final static HashMap<String, Spatial> models = new HashMap<>();
    public final static HashMap<String, Texture> textures = new HashMap<>();
    public final static HashMap<String, BitmapFont> font = new HashMap<>();

    public static boolean introLoaded = false;
    public static boolean allLoaded = false;

    public static void loadIntro(AssetManager assetManager) {
        for (String asset : new String[]{"Interface/Text/Anmol.png", "Interface/Text/Lost.png", "Interface/Text/Lost-Full.png", "Textures/Caustic.png"}) {
            Texture texture = assetManager.loadTexture(asset);
            texture.setWrap(Texture.WrapMode.Repeat);
            textures.put(asset, texture);
        }
        font.put("Font", assetManager.loadFont("Interface/Fonts/Xolonium/eng_xolonium_clean.fnt"));
        font.put("FontMetal", assetManager.loadFont("Interface/Fonts/Xolonium/eng_xolonium_metal.fnt"));
        introLoaded = true;
    }

    public static void loadAll(AssetManager assetManager) {
        for (String asset : new String[]{"Textures/Box.png", "Textures/Corner.png", "Textures/Rect.png", "Textures/SelectedBox.png", "Textures/SelectedRect.png", "Textures/CrossHair.png", "Textures/Screen0/Play.png", "Textures/Screen0/Purchase.png", "Textures/Screen0/Inventory.png", "Textures/Screen0/Elements.png"}) {
            textures.put(asset, assetManager.loadTexture(asset));
        }
        for (int c = 0; c < 6; c++) {
            for (int l = 0; l < 4; l++) {
                String asset = "Textures/Materials/C" + c + "L" + l + ".png";
                textures.put(asset, assetManager.loadTexture(asset));
            }
        }
        for (int i = 0; i < 6; i++) {
            String asset = "Textures/Elements/C" + i + ".png";
            textures.put(asset, assetManager.loadTexture(asset));
        }

        for (String asset : new String[]{"Models/LostMap.glb", "Models/LostMapLowLod.glb"}) {
            models.put(asset, assetManager.loadModel(asset));
        }
        assetManager.clearCache();
        allLoaded = true;
    }
}
