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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.model.render;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.model.ColorModel;
import net.minecraft.util.math.Position;

public class TextRenderModel {
    public TextRenderModel(int textAsInt, Position position, ColorModel color)
    {
        this.text = String.valueOf(textAsInt);
        this.position = position;
        this.color = color;
    }
    public TextRenderModel(String text, Position position, ColorModel color)
    {
        this.text = text;
        this.position = position;
        this.color = color;
    }
    
    private String text;
    private Position position;
    private float scale;
    private ColorModel color;
    private int opacity;
    private int background_color;
    private int background_opacity;
    
    public String getText() {
        return text;
    }
    public Position getPosition() {
        return position;
    }
    public ColorModel getColor() {
        return color;
    }
}
