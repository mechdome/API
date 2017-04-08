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

package com.mechdome.view;

import android.graphics.Canvas;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Parcel;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * An Android view that delegates rendering to a native view in the host OS
 *
 * Created by Mario Kosmiskas on 3/29/17.
 */
public class HostOSView extends View {

    private static final String KEY_CLASS_NAME = "md_classname";
    private static final String PROPERTY_PREFIX = "prop_";

    // Used by native code, do not rename
    @SuppressWarnings("unused")
    private long mPtr;

    Map<String,String> mParams = new HashMap<>();

    private native boolean init(Parcel parcel);
    private native void attached();
    private native void destroy();
    private native void setHostViewFrame(int x, int y, int w, int h);
    private native void setVisible(boolean flag);
    private native void touchEvent(MotionEvent ev);

    public HostOSView(Context context) {
        super(context);
    }

    public HostOSView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HostOSView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HostOSView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setHostViewClassName(String classname) {
        mParams.put(KEY_CLASS_NAME, classname);
    }

    public void setProperty(String name, String value) {
        mParams.put(PROPERTY_PREFIX + name, value);
    }

    @SuppressWarnings("unused")
    public boolean init() {
        if (!mParams.containsKey(KEY_CLASS_NAME)) {
            throw new IllegalArgumentException("No host view class name set");
        }

        Parcel p = Parcel.obtain();
        try {
            p.writeMap(mParams);
            mParams.clear();
            return init(p);
        } catch (UnsatisfiedLinkError ule) {
            return false;
        } finally {
            p.recycle();
        }
    }

    protected String getMessage() {
        return "Host OS View";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPtr > 0) {
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        } else {
            canvas.drawColor(Color.rgb(0xFF, 0xA5, 00));
            Paint p = new Paint();
            p.setTextSize(32);
            canvas.drawText(getMessage(), 5, this.getHeight()/2, p);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int[] outCoords = new int[2];
        this.getLocationOnScreen(outCoords);
        if (mPtr > 0) {
            setHostViewFrame(outCoords[0], outCoords[1], right-left, bottom-top);
        }
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getParent().requestTransparentRegion(this);
        try {
            attached();
        } catch (UnsatisfiedLinkError ule) {}
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        try {
            destroy();
        } catch (UnsatisfiedLinkError ule) {}
    }

    @Override
    public void onWindowVisibilityChanged(final int visibility) {
        try {
            setVisible(visibility == View.VISIBLE);
        } catch (UnsatisfiedLinkError ule) {}
    }

    @Override
    public void onVisibilityChanged(final View changedView, final int visibility) {
        try {
            setVisible(visibility == View.VISIBLE);
        } catch (UnsatisfiedLinkError ule) {}
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            touchEvent(ev);
            return true;
        } catch (UnsatisfiedLinkError ule) {}

        return false;
    }

}
