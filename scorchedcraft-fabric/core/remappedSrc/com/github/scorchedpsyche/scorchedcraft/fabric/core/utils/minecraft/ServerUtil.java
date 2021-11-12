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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.Core;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.StringFormattedModel;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

public class ServerUtil {
    public static void sendMessageToAllPlayers(String message, boolean sendOnActionBar)
    {
        for( PlayerEntity player : Core.server.getPlayerManager().getPlayerList() )
        {
            PlayerUtil.sendMessage( player, message, sendOnActionBar );
        }
    }
    public static void sendMessageToAllPlayers(StringFormattedModel message, boolean sendOnActionBar)
    {
        sendMessageToAllPlayers(message.toString(), sendOnActionBar);
    }
    
    public static void sendMessageToAllPlayers(String message)
    {
        sendMessageToAllPlayers(message, false);
    }
    public static void sendMessageToAllPlayers(StringFormattedModel message)
    {
        sendMessageToAllPlayers(message, false);
    }
}
