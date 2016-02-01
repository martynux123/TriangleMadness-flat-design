package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public interface AdController {
	
	public void showAd();
	public void hideAd();
	
	public void showInterAd(Runnable after);

}
