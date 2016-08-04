package com.example.chan.smartcar;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

/**
 * Created by hachan on 4/8/2016.
 */
public class RingtonePlayingService extends Service {
    private Ringtone ringtone;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this, ringtoneUri);
        ringtone.play();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
    }
}


