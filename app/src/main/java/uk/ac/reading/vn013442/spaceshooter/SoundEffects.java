package uk.ac.reading.vn013442.spaceshooter;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

/**
 * sound effect for shooting bullet
 */
public class SoundEffects {

    private static SoundPool soundPool;
    private static int bulletHit;


    /**
     * import audio file
     *
     * @param context
     */
    public SoundEffects(Context context) {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        bulletHit = soundPool.load(context, R.raw.bulletsound, 1);
    }

    /**
     * audio settings, play
     */
    public void playBulletHitSound() {
        soundPool.play(bulletHit, 1.0f, 1.0f, 1, 0, 1.0f);
    }

}
