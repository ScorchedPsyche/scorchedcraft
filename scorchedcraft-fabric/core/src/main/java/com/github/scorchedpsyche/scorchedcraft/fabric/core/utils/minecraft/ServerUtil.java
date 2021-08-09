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
    public static void sendMessageToAllPlayers(String message)
    {
        for( PlayerEntity player : Core.server.getPlayerManager().getPlayerList() )
        {
            PlayerUtil.sendMessage( player, message );
        }
    }
    public static void sendMessageToAllPlayers(StringFormattedModel message)
    {
        for( PlayerEntity player : Core.server.getPlayerManager().getPlayerList() )
        {
            PlayerUtil.sendMessage( player, message );
        }
    }
}
