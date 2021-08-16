package com.moutamid.themotivitaionhub;

import android.app.Application;

public class AppContext extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
