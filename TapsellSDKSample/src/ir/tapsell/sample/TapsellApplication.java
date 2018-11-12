package ir.tapsell.sample;

import android.app.Application;

import ir.tapsell.sdk.Tapsell;

public class TapsellApplication extends Application {

    private static final String TAPSELL_KEY = "kilkhmaqckffopkpfnacjkobgrgnidkphkcbtmbcdhiokqetigljpnnrbfbnpnhmeikjbq";
   
    @Override
    public void onCreate() {
        super.onCreate();
        Tapsell.initialize(this, TAPSELL_KEY);
    }
}
