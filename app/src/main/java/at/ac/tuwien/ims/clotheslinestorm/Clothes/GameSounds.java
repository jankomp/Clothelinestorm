package at.ac.tuwien.ims.clotheslinestorm.Clothes;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import java.util.HashMap;

import at.ac.tuwien.ims.clotheslinestorm.R;

/**
 * class for the ingame sounds
 * @author Jan Kompatscher
 * */

public class GameSounds {
    public static final int SOUND_FALL = 1;
    public static final int SOUND_MUD = 2;
    public static final int SOUND_CAUGHT = 3;

    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;

    Context context;

    public GameSounds(Context context){
        this.context = context;
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
        soundPoolMap = new HashMap<Integer, Integer>();
        soundPoolMap.put(SOUND_FALL, soundPool.load(context, R.raw.down, 1));
        soundPoolMap.put(SOUND_MUD, soundPool.load(context, R.raw.mud, 1));
        soundPoolMap.put(SOUND_CAUGHT, soundPool.load(context, R.raw.caught, 1));
    }

    /**
     * play eigther the fall, mud or catch -sound
     * @author Jan Kompatscher
     * */
    public void playSound(int sound) {
        /* Updated: The next 4 lines calculate the current volume in a scale of 0.0 to 1.0 */
        AudioManager mgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float volume = streamVolumeCurrent / streamVolumeMax;
        if(sound == SOUND_MUD){
            volume /= 2;
        }
        /* Play the sound with the correct volume */
        soundPool.play(soundPoolMap.get(sound), volume, volume, 1, 0, 1f);
    }
}
