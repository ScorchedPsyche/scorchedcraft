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

import net.minecraft.util.math.BlockPos;
import org.w3c.dom.css.RGBColor;

public class BlockOutlineModel {
    public BlockOutlineModel(BlockPos blockPos, int red, int green, int blue, int alpha)
    {
        this.blockPos = blockPos;
        this.rgba = new RGBAModel(red, green, blue, alpha);
    }
    public BlockOutlineModel(BlockPos blockPos, int red, int green, int blue)
    {
        this.blockPos = blockPos;
        this.rgba = new RGBAModel(red, green, blue, 1);
    }
    
    private final BlockPos blockPos;
    private final RGBAModel rgba;
    
    public BlockPos getBlockPos() {
        return blockPos;
    }
    public RGBAModel getRgba() {
        return rgba;
    }
}
