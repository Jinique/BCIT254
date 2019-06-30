package com.example.eyeballmaze.model;

import android.content.Context;
import android.media.MediaPlayer;

public class SFX {
	private MediaPlayer mp;

	public void bgm(Context param1, int param2, boolean param3)
	{
		mp = MediaPlayer.create(param1, param2);
		mp.setLooping(param3);
		mp.start();
		mp.setVolume(0.10f, 0.10f);
	}

	public void bgm_start()
	{
		mp.start();
	}

	public void bgm_stop()
	{
		mp.stop();
	}

	public void bgm_pause()
	{
		mp.pause();
	}
}
