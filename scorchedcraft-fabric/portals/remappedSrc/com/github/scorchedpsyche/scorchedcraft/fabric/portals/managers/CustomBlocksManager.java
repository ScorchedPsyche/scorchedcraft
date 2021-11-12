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

package com.github.scorchedpsyche.scorchedcraft.fabric.portals.managers;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ModsScorchedCraft;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.PlayerUtil;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CustomBlocksManager {
    public static final Block NETHER_PORTAL
        = new Block(FabricBlockSettings.of(Material.PORTAL).resistance(0f).hardness(-1f));
    
    public static void registerCustomBlocks()
    {
        Registry.register(Registry.BLOCK,
            new Identifier("sc", "nether_portal"), NETHER_PORTAL);
    
        ConsoleUtil.logMessage(ModsScorchedCraft.Portals.Name.full, "Custom blocks registered.");
    }
}
