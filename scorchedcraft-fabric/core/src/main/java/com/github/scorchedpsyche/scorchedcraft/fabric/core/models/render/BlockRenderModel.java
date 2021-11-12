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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.models.render;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.ColorModel;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class BlockRenderModel extends ElementRenderModel {
    public BlockRenderModel(BlockPos blockPos, ColorModel singleColor)
    {
        this.blockPos = blockPos;
        this.setColorFace_BELOW( singleColor );
        this.setColorFace_ABOVE( singleColor );
        this.setColorFace_NORTH( singleColor );
        this.setColorFace_SOUTH( singleColor );
        this.setColorFace_EAST( singleColor );
        this.setColorFace_WEST( singleColor );
    }
    
    BlockPos blockPos;
    private ColorModel colorFace_BELOW;
    private ColorModel colorFace_ABOVE;
    private ColorModel colorFace_NORTH;
    private ColorModel colorFace_SOUTH;
    private ColorModel colorFace_EAST;
    private ColorModel colorFace_WEST;
    
    public ColorModel getColorFace_BELOW() {
        return colorFace_BELOW;
    }
    public void setColorFace_BELOW(ColorModel colorFace_BELOW) {
        this.colorFace_BELOW = colorFace_BELOW;
    }
    
    public ColorModel getColorFace_ABOVE() {
        return colorFace_ABOVE;
    }
    public void setColorFace_ABOVE(ColorModel colorFace_ABOVE) {
        this.colorFace_ABOVE = colorFace_ABOVE;
    }
    
    public ColorModel getColorFace_NORTH() {
        return colorFace_NORTH;
    }
    public void setColorFace_NORTH(ColorModel colorFace_NORTH) {
        this.colorFace_NORTH = colorFace_NORTH;
    }
    
    public ColorModel getColorFace_SOUTH() {
        return colorFace_SOUTH;
    }
    public void setColorFace_SOUTH(ColorModel colorFace_SOUTH) {
        this.colorFace_SOUTH = colorFace_SOUTH;
    }
    
    public ColorModel getColorFace_EAST() {
        return colorFace_EAST;
    }
    public void setColorFace_EAST(ColorModel colorFace_EAST) {
        this.colorFace_EAST = colorFace_EAST;
    }
    
    public ColorModel getColorFace_WEST() {
        return colorFace_WEST;
    }
    public void setColorFace_WEST(ColorModel colorFace_WEST) {
        this.colorFace_WEST = colorFace_WEST;
    }
    
    public BlockRenderModel setRenderType(RenderType renderType) {
        super.renderType = renderType;
        
        return this;
    }
    
    public BlockRenderModel setRenderVisibility(RenderVisibility renderVisibility) {
        super.renderVisibility = renderVisibility;
    
        return this;
    }
}
