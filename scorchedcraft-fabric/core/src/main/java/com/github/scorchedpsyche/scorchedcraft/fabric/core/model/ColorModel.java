/*
 * Copyright (c) 2021 ScorchedPsyche
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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.model;

public class ColorModel {
    /**
     * RGBA color as integer on the format 0xAARRGGBB (ARGB):
     *
     * - Add "0x" ALWAYS;
     * - Add alpha: range from "FF" (255, opaque) to "00" (transparent);
     * - Add RED: range from "FF" (255, red) to "00" (white);
     * - Add GREEN: range from "FF" (255, green) to "00" (white);
     * - Add BLUE: range from "FF" (255, blue) to "00" (white);
     *
     * You can use
     * @param hex Integer with "0x00" added before the hex code
     */
    public ColorModel(int hex, int alpha, int red, int green, int blue,
                      int hex_shadow, int alpha_shadow, int red_shadow, int green_shadow, int blue_shadow)
    {
        this.hex = hex;
        this.alpha = alpha;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha_float = alpha / 255.0F;
        this.red_float = red / 255.0F;
        this.green_float = green / 255.0F;
        this.blue_float = blue / 255.0F;
        this.hex_shadow = hex_shadow;
        this.alpha_shadow = alpha_shadow;
        this.red_shadow = red_shadow;
        this.green_shadow = green_shadow;
        this.blue_shadow = blue_shadow;
    }
    
//    public ColorModel(int hex)
//    {
//        this(hex, (hex >> 16) & 0xFF, );
//    }
    

//    public ColorModel(int hex, int alpha, int red, int green, int blue,
//                      int hex_shadow, int alpha_shadow, int red_shadow, int green_shadow, int blue_shadow)
//    {
//        this.hex = hex;
//        this.alpha = alpha;
//        this.red = red;
//        this.green = green;
//        this.blue = blue;
//        this.hex_shadow = hex_shadow;
//        this.alpha_shadow = alpha_shadow;
//        this.red_shadow = red_shadow;
//        this.green_shadow = green_shadow;
//        this.blue_shadow = blue_shadow;
//    }
//
//
//
//
//    public ColorModel(int hex)
//    {
//        this(hex, (hex >> 16) & 0xFF, (hex >> 8) & 0xFF, hex & 0xFF);
//    }
//
//    public ColorModel(int hex, int hex_shadow)
//    {
//        this(hex, (hex >> 16) & 0xFF, (hex >> 8) & 0xFF, hex & 0xFF, hex_shadow);
//    }
//
//    public ColorModel(int hex, int red, int green, int blue)
//    {
//        this(hex, red, green, blue,
//            ((red / 4) & (green / 4) & (blue / 4)) & 0xFF);
//    }
//
//    public ColorModel(int hex, int red, int green, int blue, int hex_shadow)
//    {
//        this(hex, red, green, blue, hex_shadow,
//            (hex_shadow >> 16) & 0xFF, (hex_shadow >> 8) & 0xFF, hex_shadow & 0xFF);
//    }
    
    private int hex;
    private int alpha;
    private float alpha_float;
    private int red;
    private float red_float;
    private int green;
    private float green_float;
    private int blue;
    private float blue_float;
    private int hex_shadow;
    private int alpha_shadow;
    private int red_shadow;
    private int green_shadow;
    private int blue_shadow;
    
    public int asInt() {
        return hex;
    }
    public int getAlpha() {
        return this.alpha;
    }
    public int getRed() {
        return this.red;
    }
    public int getGreen() {
        return this.green;
    }
    public int getBlue() {
        return this.blue;
    }
    public float getAlphaAsFloat() {
        return this.alpha_float;
    }
    public float getRedAsFloat() {
        return this.red_float;
    }
    public float getGreenAsFloat() {
        return this.green_float;
    }
    public float getBlueAsFloat() {
        return this.blue_float;
    }
    
    public int shadowAsInt() {
        return hex_shadow;
    }
    public int getAlphaShadow() {
        return this.alpha_shadow;
    }
    public int getRedShadow() {
        return this.red_shadow;
    }
    public int getGreenShadow() {
        return this.green_shadow;
    }
    public int getBlueShadow() {
        return this.blue_shadow;
    }
    
    public ColorModel setAlpha(float alphaAsFloat)
    {
        this.alpha_float = alphaAsFloat;
        this.alpha = (alphaAsFloat >= 1.0 ? 255 : (alphaAsFloat <= 0.0 ? 0 : (int)Math.floor(alphaAsFloat * 256.0)));
        return this;
    }
    
    public String toString()
    {
        StringBuilder strBuilder = new StringBuilder("RGBA[");
        strBuilder.append(this.red);
        strBuilder.append(",");
        strBuilder.append(this.green);
        strBuilder.append(",");
        strBuilder.append(this.blue);
        strBuilder.append("]\n");
        strBuilder.append("Shadow RGA[");
        strBuilder.append(this.red_shadow);
        strBuilder.append(",");
        strBuilder.append(this.green_shadow);
        strBuilder.append(",");
        strBuilder.append(this.blue_shadow);
        strBuilder.append("]");
        
        return strBuilder.toString();
    }
}
