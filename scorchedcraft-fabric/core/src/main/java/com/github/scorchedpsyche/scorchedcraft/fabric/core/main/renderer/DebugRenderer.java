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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.main.renderer;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.model.render.BlockRenderModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.model.render.TextRenderModel;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Position;

import java.util.HashMap;
import java.util.Map;

public class DebugRenderer {
//    public static List<BlockOutlineModel> blocks = new ArrayList<>();
//    public static Map<BlockPos, ColorRenderModel> blocksOnlyOutline = new HashMap<>();
//    public static Map<BlockPos, ColorRenderModel> blocksOnlyFaces = new HashMap<>();
    
    private static boolean shouldRender = false;
    private static boolean shouldRenderBlocks = false;
    private static boolean shouldRenderText = false;
    private static Map<BlockPos, BlockRenderModel> blocks = new HashMap<>();
    private static Map<Position, TextRenderModel> texts = new HashMap<>();
    
    public static Map<BlockPos, BlockRenderModel> getBlocks()
    {
        return blocks;
    }
    public static Map<Position, TextRenderModel> getTexts()
    {
        return texts;
    }
    
    public static boolean shouldRender()
    {
        return shouldRender;
    }
    public static boolean shouldRenderBlocks()
    {
        return shouldRenderBlocks;
    }
    public static boolean shouldRenderText()
    {
        return shouldRenderText;
    }
    
    public static void reset()
    {
        shouldRender = false;
        shouldRenderBlocks = false;
        shouldRenderText = false;
        blocks.clear();
        texts.clear();
    }
    
    public static void addBlock(BlockPos blockPos, BlockRenderModel blockRender)
    {
        if( blockPos != null && blockRender != null )
        {
            blocks.put(blockPos, blockRender);
            shouldRender = true;
            shouldRenderBlocks = true;
        }
    }
    
    public static void addText(Position position, TextRenderModel textRender)
    {
        if( position != null && textRender != null )
        {
            texts.put(position, textRender);
            shouldRender = true;
            shouldRenderText = true;
        }
    }
}
