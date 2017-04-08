/*
 * Copyright (C) 2017 MechDome - http://www.mechdome.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mechdome.view.google;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.mechdome.view.HostOSView;

/**
 * An AdMob Native Ad View
 *
 * Created by Mario Kosmiskas on 4/1/17.
 */
@SuppressWarnings("unused")
public class AdMobBannerView extends HostOSView {

    private int mNativeHeight;

    public AdMobBannerView(Context context) {
        this(context, null);
    }

    public AdMobBannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AdMobBannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AdMobBannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int density = metrics.densityDpi;

        // Temporary value to allow for visual layout in Android Studio
        mNativeHeight = density==DisplayMetrics.DENSITY_HIGH?200:100;
    }

    private void setNativeHeight(int h) {
        mNativeHeight = h;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        setMeasuredDimension(w, mNativeHeight);
    }

    public void init(String appId, String adUnitId, boolean isInTestMode) {
        this.setHostViewClassName("MDBannerAdMobView");
        this.setProperty("isInTestMode", isInTestMode?"true":"false");
        this.setProperty("applicationID", appId);
        this.setProperty("adUnitID", adUnitId);
        this.init();
    }

    protected String getMessage() {
        return "AdMob Banner Ad";
    }
}
