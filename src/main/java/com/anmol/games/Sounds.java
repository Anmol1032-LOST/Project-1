package com.anmol.games;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;

public class Sounds {
    public static AudioNode clickSound;
    public static AudioNode initSound;

    public static void load(AssetManager assetManager) {
        clickSound = new AudioNode(assetManager, "Sounds/IntroAndGui/ClickSound.wav", AudioData.DataType.Buffer);
        clickSound.setLooping(false);
        clickSound.setVolume(0.25f);
        clickSound.setPositional(false);

        initSound = new AudioNode(assetManager, "Sounds/IntroAndGui/InitSound.wav", AudioData.DataType.Buffer);
        initSound.setLooping(false);
        initSound.setVolume(0.5f);
        initSound.setPositional(false);
    }
}
