/*
 * Copyright (c) 2021. ScorchedPsyche
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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.models;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.MathUtil;

public class RGBAModel {
    public RGBAModel(int red, int green, int blue)
    {
        this.red = convertToFloat(red);
        this.green = convertToFloat(green);
        this.blue = convertToFloat(blue);
        this.alpha = 1;
    }
    public RGBAModel(int red, int green, int blue, float alpha)
    {
        this(red, green, blue);
        this.alpha = MathUtil.clamp(alpha, 0, 1);
    }
    
    private final float red;
    private final float green;
    private final float blue;
    private float alpha;
    
    public float getRed() {
        return red;
    }
    public float getGreen() {
        return green;
    }
    public float getBlue() {
        return blue;
    }
    public float getAlpha() {
        return alpha;
    }
    
    private float convertToFloat(int color)
    {
        return MathUtil.clamp(color, 0F, 255F) / 255.0F;
    }
}
