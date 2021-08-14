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
