package jp.co.crowdworks.hogehogeworks;

import android.app.Application;

import com.nifty.cloud.mb.core.NCMB;

public class HogeHogeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        NCMB.initialize(this,
                "90bdda4e9747b7ee696be5e8fe28fb2264b10771039939bb0801e759c2c59bbf", //APP KEY
                "cf027606e4226bd48dc77bb8ec4e3a836a7ba00b63f475bbff991917386b0143"); //CLIENT KEY
    }
}
