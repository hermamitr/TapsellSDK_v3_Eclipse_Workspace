package ir.tapsell.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import ir.tapsell.samplev3.R;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAd;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellRewardListener;
import ir.tapsell.sdk.TapsellShowOptions;

public class MainActivity extends Activity {

    private static final String ZONE_ID = "5a5dbd5cc21bf000010d1686";

    private Button requestAdBtn, showAddBtn;
    private TapsellAd ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

        requestAdBtn = (Button) findViewById(R.id.btnRequestAd);
        showAddBtn = (Button) findViewById(R.id.btnShowAd);
        
        showAddBtn.setEnabled(false);

        Tapsell.setRewardListener(new TapsellRewardListener() {
            @Override
            public void onAdShowFinished(TapsellAd ad, boolean completed) {
                Log.e("MainActivity", "isCompleted? " + completed);
                // store user
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Info")
                        .setMessage("Video view finished")
                        .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        requestAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAd(ZONE_ID);
            }
        });
        
        showAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ad != null && ad.isValid()) {
                    showAddBtn.setEnabled(false);
                    
                    TapsellShowOptions showOptions = new TapsellShowOptions();
                    showOptions.setBackDisabled(false);
                    showOptions.setImmersiveMode(true);
                    showOptions.setRotationMode(TapsellShowOptions.ROTATION_UNLOCKED);
                    
                    ad.show(MainActivity.this, showOptions, new TapsellAdShowListener() {
						
						@Override
						public void onOpened(TapsellAd arg0) {
							Log.d("Tapsell Sample","Ad Opened");
						}
						
						@Override
						public void onClosed(TapsellAd arg0) {
							Log.d("Tapsell Sample","Ad Closed");
						}
					});
                }
            }
        });
    }

    private void loadAd(String zoneId) {

        TapsellAdRequestOptions options = new TapsellAdRequestOptions(TapsellAdRequestOptions.CACHE_TYPE_STREAMED);

        Tapsell.requestAd(this, zoneId, options, new TapsellAdRequestListener() {
            @Override
            public void onError(String error) {
                Log.d("Tapsell Sample", "Error: " + error);
            }

            @Override
            public void onAdAvailable(TapsellAd ad) {

            	MainActivity.this.ad = ad;
                showAddBtn.setEnabled(true);
                Log.d("Tapsell Sample", "Ad is available");
            }

            @Override
            public void onNoAdAvailable() {
                Log.d("Tapsell Sample", "No ad available");
            }

            @Override
            public void onNoNetwork() {
                Log.d("Tapsell Sample", "No network");
            }

            @Override
            public void onExpiring(TapsellAd ad) {
                showAddBtn.setEnabled(false);
                loadAd(ZONE_ID);
            }
        });

    }

}
