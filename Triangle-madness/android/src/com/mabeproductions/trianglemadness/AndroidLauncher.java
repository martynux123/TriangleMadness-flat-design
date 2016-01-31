package com.mabeproductions.trianglemadness;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class AndroidLauncher extends AndroidApplication {
    private static final String BANNER_AD_UNIT_ID = "ca-app-pub-1208705518000908/7909163674";
    
    public AdView bannerAd;
     
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new GameRunner(), config);
         
/*        // Create a gameView and a bannerAd AdView
        View gameView = initializeForView(new GameRunner(), config);
        setupAds();
        
        // Define the layout
        RelativeLayout layout = new RelativeLayout(this);
        
        AdRequest.Builder builder = new AdRequest.Builder();
        builder.addTestDevice("84DF0F6B279E1E5F921C2BE75617A14A");
        AdRequest ad = builder.build();
        bannerAd.loadAd(ad);
        
        layout.addView(gameView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.addView(bannerAd, params);
        setContentView(layout);
*/
    }
     
    public void setupAds() {
        bannerAd = new AdView(this);
        bannerAd.setVisibility(View.VISIBLE);
        bannerAd.setBackgroundColor(0xff000000); // black
        bannerAd.setAdUnitId(BANNER_AD_UNIT_ID);
        bannerAd.setAdSize(AdSize.SMART_BANNER);
    }
}
