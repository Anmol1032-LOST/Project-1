package com.anmol.games;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.material.Material;
import com.jme3.scene.Spatial;
import com.jme3.texture.Texture;

import java.util.HashMap;

public class Assets {
    public final static HashMap<String, Spatial> models = new HashMap<>();
    public final static HashMap<String, Texture> textures = new HashMap<>();
    public final static HashMap<String, BitmapFont> font = new HashMap<>();

    public static Material mat;

    public static boolean interfaceLoaded = false;
    public static boolean allLoaded = false;

    public static void loadInterface(AssetManager assetManager) {
        for (String asset : new String[]{"Textures/GUI/Box.png", "Textures/GUI/Corner.png", "Textures/GUI/Rect.png", "Textures/GUI/SelectedBox.png", "Textures/GUI/SelectedRect.png", "Textures/Caustic.png"}) {
            Texture texture = assetManager.loadTexture(asset);
            texture.setWrap(Texture.WrapMode.Repeat);
            textures.put(asset, texture);
        }

        font.put("Font", assetManager.loadFont("Interface/Fonts/Xolonium/eng_xolonium_clean.fnt"));
        font.put("FontMetal", assetManager.loadFont("Interface/Fonts/Xolonium/eng_xolonium_metal.fnt"));
        interfaceLoaded = true;

        mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
    }

    public static void loadAll(AssetManager assetManager) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (String asset : new String[]{"Interface/Text/Anmol.png", "Interface/Text/Lost.png", "Interface/Text/Lost-Full.png", "Textures/CrossHair.png", "Textures/Screen0/Play.png", "Textures/Screen0/Purchase.png", "Textures/Screen0/Inventory.png", "Textures/Screen0/Elements.png", "Interface/Icon/Icon4096.png"}) {
            Texture texture = assetManager.loadTexture(asset);
            texture.setWrap(Texture.WrapMode.Repeat);
            textures.put(asset, texture);
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

        for (String asset : new String[]{"Models/LostMap.glb", "Models/LostMapLowLod.glb", "Models/Center.glb", "Models/Orb.glb", "Models/LoopMap1.glb", "Models/LoopMap2.glb", "Models/LoopMap3.glb", "Models/LoopMap4.glb", "Models/LoopMap5.glb", "Models/LoopMap6.glb",
                "Models/Entities/Slime.glb"
        }) {
            models.put(asset, assetManager.loadModel(asset));
        }

        assetManager.clearCache();
        System.gc();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        allLoaded = true;
    }
}
