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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.ColorModel;

public class MinecraftColors
{
    public static final ColorModel black = new ColorModel(
        0, 255, 0, 0, 0,
        0, 255, 0, 0, 0);
    public static final ColorModel dark_blue = new ColorModel(
        0xFF0000AA,  255, 0, 170, 0,
        0xFF00002A, 255, 0, 0, 42);
    public static final ColorModel dark_green = new ColorModel(
        0xFF00AA00,  255, 0, 0, 170,
        0xFF002A00, 255, 0, 42, 0);
    public static final ColorModel dark_aqua = new ColorModel(
        0xFF00AAAA,  255, 0, 170, 170,
        0xFF002A2A, 255,0, 42, 42);
    public static final ColorModel dark_red = new ColorModel(
        0xFFAA0000,  255, 170, 0, 0,
        0xFF2A0000, 255, 42, 0, 0);
    public static final ColorModel dark_purple = new ColorModel(
        0xFFAA00AA,  255, 170, 0, 170,
        0xFF2A002A, 255, 42, 0, 42);
    public static final ColorModel gold = new ColorModel(
        0xFFFFAA00,  255, 255, 170, 0,
        0xFF2A2A00, 255,42, 42, 0);
    public static final ColorModel gray = new ColorModel(
        0xFF555555,  255, 170, 170, 170,
        0xFF2A2A2A, 255, 42, 42, 42);
    public static final ColorModel dark_gray = new ColorModel(
        0xFF5555FF,  0, 85, 85, 85,
        0xFF151515, 255, 21, 21, 21);
    public static final ColorModel blue = new ColorModel(
        0xFF5555FF,  255, 85, 85, 255,
        0xFF15153F, 255, 21, 21, 63);
    public static final ColorModel green = new ColorModel(
        0xFF55FF55,  255, 85, 255, 85,
        0xFF153F15, 255, 21, 63, 21);
    public static final ColorModel aqua = new ColorModel(
        0xFF55FFFF,  255, 85, 255, 255,
        0xFF153F3F, 255, 21, 63, 63);
    public static final ColorModel red = new ColorModel(
        0xFFFF5555,  255, 255, 85, 85,
        0xFF3F1515, 255, 63, 21, 21);
    public static final ColorModel light_purple = new ColorModel(
        0xFFFF55FF,  255, 255, 85, 255,
        0xFF3F153F, 255, 63, 21, 63);
    public static final ColorModel yellow = new ColorModel(
        0xFFFFFF55,  255, 255, 255, 85,
        0xFF3F3F15, 255, 63, 63, 21);
    public static final ColorModel white = new ColorModel(
        0xFFFFFFFF,  255, 255, 255, 255,
        0xFF3F3F3F, 255, 63, 63, 63);
    public static final ColorModel minecoin_gold = new ColorModel(
        0xFFDDD605,  255, 221, 214, 5,
        0xFF373501, 255, 55, 53, 1);
}
