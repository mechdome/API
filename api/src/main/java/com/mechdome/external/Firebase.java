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
 * Provides access to the host OS's Firebase SDK
 *
 * Created by Mario Kosmiskas on 3/29/17.
 */
@SuppressWarnings("unused")
public class Firebase {

    /**
     * Retrieves the token from the native Firebase SDK
     * @return Firebase token
     */
    @SuppressWarnings("unused")
    public static native String getToken();
}
