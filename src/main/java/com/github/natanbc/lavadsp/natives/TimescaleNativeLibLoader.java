/*
 * Copyright 2018 natanbc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.natanbc.lavadsp.natives;

import com.github.natanbc.nativeloader.NativeLibLoader;
import com.github.natanbc.nativeloader.feature.X86Feature;

public class TimescaleNativeLibLoader {
    private static NativeLibLoader LOADER = NativeLibLoader.create(TimescaleNativeLibLoader.class, "timescale")
            .withFeature(X86Feature.AVX2);
    private static volatile boolean loaded = false;
    private static volatile String soundTouchVersion;
    private static volatile int soundTouchVersionID;
    private static volatile boolean criticalNativesAvailable;

    public static void loadTimescaleLibrary() {
        if(loaded) return;
        // M1 code
        String osName = System.getProperty("os.name").toLowerCase();
        String osArch = System.getProperty("os.arch").toLowerCase();
        if(osName.equals("mac os x") && osArch.equals("aarch64")) {
            LOADER = NativeLibLoader.create(TimescaleNativeLibLoader.class, "timescale");
        }
        LOADER.load();
        soundTouchVersion = TimescaleLibrary.soundTouchVersion();
        soundTouchVersionID = TimescaleLibrary.soundTouchVersionID();
        criticalNativesAvailable = TimescaleLibrary.criticalMethodsAvailable();
        loaded = true;
    }

    public static boolean isLoaded() {
        return loaded;
    }

    public static String getSoundTouchVersion() {
        loadTimescaleLibrary();
        return soundTouchVersion;
    }

    public static int getSoundTouchVersionID() {
        loadTimescaleLibrary();
        return soundTouchVersionID;
    }

    public static boolean areCriticalNativesAvailable() {
        loadTimescaleLibrary();
        return criticalNativesAvailable;
    }
}
