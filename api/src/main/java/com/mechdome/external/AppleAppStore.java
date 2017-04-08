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

package com.mechdome.external;

/**
 * Provides access to the App Store
 *
 * Created by Mario Kosmiskas on 4/6/17.
 */
@SuppressWarnings("unused")
public class AppleAppStore {

    public enum Status { Ok, InvalidProduct, PaymentsNotAccepted, Error }

    private static native void apppleBuy(String productId, int quantity, TransactionListener listener);
    private static native void appleRestore(String productId, TransactionListener listener);
    private static native boolean appleHasProduct(String productId);

    public interface TransactionListener {
        void onComplete(Status status);
    }

    public static void buy(String productId, int quantity, TransactionListener listener) {
        try {
            apppleBuy(productId, quantity, listener);
        } catch (UnsatisfiedLinkError ule) {}
    }

    public static void restore(String productId, TransactionListener listener) {
        try {
            appleRestore(productId, listener);
        } catch (UnsatisfiedLinkError ule) {}
    }

    public static boolean hasProduct(String productId) {
        try {
            return appleHasProduct(productId);
        } catch (UnsatisfiedLinkError ule) {}

        return false;
    }

}
