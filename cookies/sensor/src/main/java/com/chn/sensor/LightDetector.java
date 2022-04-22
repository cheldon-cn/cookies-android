/*
 * Copyright (C) 2016 Nishant Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chn.sensor;

import static android.hardware.Sensor.TYPE_LIGHT;

import android.hardware.SensorEvent;

public class LightDetector extends SensorDetector {

    public interface LightListener {

        void onDark();

        void onLight();
    }

    private final LightListener lightListener;

    private final float threshold;

    public LightDetector(LightListener lightListener) {
        this(3f, lightListener);
    }

    public LightDetector(float threshold, LightListener lightListener) {
        super(TYPE_LIGHT);
        this.threshold = threshold;
        this.lightListener = lightListener;
    }

    @Override
    protected void onSensorEvent(SensorEvent sensorEvent) {
        float lux = sensorEvent.values[0];
        if (lux < threshold) {
            // Dark
            lightListener.onDark();
        } else {
            // Not Dark
            lightListener.onLight();
        }
    }
}
