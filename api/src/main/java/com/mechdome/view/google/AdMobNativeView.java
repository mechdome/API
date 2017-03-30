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
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.mechdome.api.R;
import com.mechdome.view.HostOSView;

/**
 * An AdMob Native Ad View
 *
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
