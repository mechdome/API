package com.mechdome.view.google;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.mechdome.api.R;
import com.mechdome.view.HostOSView;

/**
 * Created by Mario Kosmiskas on 3/29/17.
 */
@SuppressWarnings("unused")
public class AdMobNativeView extends HostOSView {

    public AdMobNativeView(Context context) {
        this(context, null);
    }

    public AdMobNativeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdMobNativeView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AdMobNativeView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AdMobNativeView, 0, 0);

            try {
                boolean isTest = a.getBoolean(R.styleable.AdMobNativeView_testMode, true);
                String appId = a.getString(R.styleable.AdMobNativeView_appID);
                String adUnitId = a.getString(R.styleable.AdMobNativeView_adUnitID);
                init(appId, adUnitId, isTest);
            } finally {
                a.recycle();
            }
        }
    }

    public void init(String appId, String adUnitId, boolean isInTestMode) {
        this.setHostViewClassName("MDNativeAdMobView");
        this.setProperty("isInTestMode", isInTestMode?"true":"false");
        this.setProperty("applicationID", appId);
        this.setProperty("adUnitID", adUnitId);
        this.init();
    }

    protected String getMessage() {
        return "AdMob Native Ad";
    }

}
