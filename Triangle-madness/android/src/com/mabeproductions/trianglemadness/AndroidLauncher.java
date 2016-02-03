package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class AndroidLauncher extends AndroidApplication implements AdController{
    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-1208705518000908/7909163674";
    private static final String INTERSECTIONAL_AD_UNIT_ID = "ca-app-pub-1208705518000908/2022250474";
    private static AdRequest ad;
    private static RelativeLayout layout;
    public static AdView bannerAd;
    private static View gameView;
    private InterstitialAd intersitialAd;
    
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new GameRunner(this), config);
         
        // Create a gameView and a bannerAd AdView
        setupAds();
        setupIntersitialAds();
        gameView = initializeForView(new GameRunner(this), config);
        layout = new RelativeLayout(this);
        
        
        
        layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
        		ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
        		ViewGroup.LayoutParams.MATCH_PARENT,
        		ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        
        layout.addView(bannerAd, params);
        setContentView(layout);
        
    }
    
    public void setupIntersitialAds(){
    	intersitialAd = new InterstitialAd(this);
    	intersitialAd.setAdUnitId(AndroidLauncher.INTERSECTIONAL_AD_UNIT_ID);
    	AdRequest ad = new AdRequest.Builder()/*.addTestDevice("84DF0F6B279E1E5F921C2BE75617A14A")*/.build();
        intersitialAd.loadAd(ad);
    }
    
    public void setupAds() {
        bannerAd = new AdView(this);
        bannerAd.setVisibility(View.INVISIBLE);
        bannerAd.setBackgroundColor(0xff000000); // black
        bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        bannerAd.setAdSize(AdSize.SMART_BANNER);
    }

	@Override
	public void showAd() {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					bannerAd.setVisibility(View.VISIBLE);
					
					bannerAd.setAdListener(new AdListener() {
						
						@Override
						public void onAdFailedToLoad(int errorCode){
							hideAd();
						}
						
					});
					
					AdRequest.Builder builder = new AdRequest.Builder();
//					builder.addTestDevice("84DF0F6B279E1E5F921C2BE75617A14A");
					AdRequest ad = builder.build();
					bannerAd.loadAd(ad);
				}
			});
		
	}

	@Override
	public void hideAd() {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				bannerAd.setVisibility(View.INVISIBLE);
			}
		});
		
	}

	@Override
	public void showInterAd(final Runnable afterThread) {
		
//		if(intersitialAd.isLoaded()){
			
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (afterThread != null) {
						intersitialAd.setAdListener(new AdListener() {
							@Override
							public void onAdClosed() {
								Gdx.app.postRunnable(afterThread);
								AdRequest.Builder builder = new AdRequest.Builder();
								AdRequest ad = builder.build();
								intersitialAd.loadAd(ad);
							}
						});
					}
					intersitialAd.show();
				}
			});
//		}
	}
	

}
