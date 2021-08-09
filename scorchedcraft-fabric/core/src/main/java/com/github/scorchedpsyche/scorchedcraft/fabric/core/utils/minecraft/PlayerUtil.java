package com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.MessageModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.StringFormattedModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Position;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlayerUtil {
    public static int getCoordinateRoundedX(PlayerEntity player)
    {
        return (int) player.getX();
    }

    public static int getCoordinateRoundedY(PlayerEntity player)
    {
        return (int) player.getY();
    }

    public static int getCoordinateRoundedZ(PlayerEntity player)
    {
        return (int) player.getZ();
    }

    public static DimensionType getEnvironment(PlayerEntity player)
    {
        return player.world.getDimension();
    }
    
    /**
     * Sends a message to the player.
     * @param player The player to send the message to
     * @param message Message to be sent
     */
    public static void sendMessage(@Nullable PlayerEntity player, String message)
    {
        if( player != null )
        {
            player.sendMessage( Text.of( message ), false  );
        }
    }
    
    /**
     * Sends a message to the player.
     * @param player The player to send the message to
     * @param message Message to be sent
     */
    public static void sendMessage(@Nullable PlayerEntity player, StringFormattedModel message)
    {
        if( player != null )
        {
            player.sendMessage( Text.of( message.toString() ), false  );
        }
    }

    /**
     * Sends a message to the player with the plugin prefix.
     * @param player The player to send the message to
     * @param pluginPrefix The plugin prefix to the message
     * @param message Message to be sent
     */
    public static void sendMessageWithPluginPrefix(@Nullable PlayerEntity player, String pluginPrefix, String message)
    {
        if( player != null )
        {
            player.sendMessage( Text.of(new StringFormattedModel()
                .gold("[").add(pluginPrefix).add("] ").reset().add(message)
                .toString()), false  );
//            player.sendMessage(ChatColor.GOLD + "[" + pluginPrefix + "] " + ChatColor.RESET + message);
        }
    }
    
    /**
     * Sends a message to the player with the plugin prefix.
     * @param player The player to send the message to
     * @param pluginPrefix The plugin prefix to the message
     * @param message Message to be sent
     */
    public static void sendMessageWithPluginPrefix(@Nullable PlayerEntity player, String pluginPrefix,
        StringFormattedModel message)
    {
        if( player != null )
        {
            sendMessageWithPluginPrefix(player, pluginPrefix, message.toString());
        }
    }

    public static void sendMessagesWithPluginPrefixOnePerLine(@Nullable PlayerEntity player, String pluginPrefix,
        List<MessageModel> messages)
    {
        if( player != null && !messages.isEmpty() )
        {
            StringFormattedModel finalMessage =
                new StringFormattedModel().gold("[").add(pluginPrefix).add("] ").nl()
                    .aquaR("===== Messages =====").nl();

            for(MessageModel message : messages)
            {
                if( message.getDate_end() != 0 )
                {
                    finalMessage.redR(" -> ").add(message.getMessage_cached());
                } else {
                    finalMessage.redR(" -> ").add(message.getMessage());
                }
                finalMessage.nl();
            }
            finalMessage.aquaR("===== Messages =====");
            player.sendMessage( Text.of(finalMessage.toString()), false);
        }
    }

    public static double getDistanceToLocation(PlayerEntity player, Position position )
    {
        return Math.sqrt(
            Math.pow(position.getX() - player.getX(), 2) +
                Math.pow(position.getY() - player.getY(), 2) +
                Math.pow(position.getZ() - player.getZ(), 2));
    }

//    /**
//     * Check's if the player has the permission with LuckPerm
//     * @param player The player to check the permission for
//     * @param permission The permission to be checked against LuckPerms
//     * @return Boolean that indicates if the user has the permission or not
//     */
//    public static boolean hasPermission(@Nullable PlayerEntity player, String permission)
//    {
//        // Check if valid player
//        if( player != null )
//        {
//            User user = CraftEraSuiteCore.luckPerms.getUserManager().getUser(player.getUniqueId());
//
//            // Check if valid LuckPerm user
//            if( user != null )
//            {
//                // Valid user. Must check permissions
//
//                // If string is null or empty then no permission is required and any user has the permission
//                if( StringUtil.isNullOrEmpty(permission) || user.getCachedData().getPermissionData().checkPermission(permission).asBoolean() )
//                {
//                    return true;
//                }
//            } else {
//                // Error fetching a valid user from LuckPerm
//                ConsoleUtil.logError(SuitePluginManager.Core.Name.full,
//                    "Failed to get user's permission from Luck Perms.");
//                PlayerUtil.sendMessageWithPluginPrefix(player.getPlayer(),
//                    SuitePluginManager.Core.Name.compact,
//                    "Failed to get user's permission from Luck Perms. Let your server admin know!!!");
//                return false;
//            }
//
//            // If we got here, player has no permission
//            PlayerUtil.sendMessageWithPluginPrefix(player, SuitePluginManager.Name.full,
//                "Unauthorized: " + permission);
//        }
//
//        return false;
//    }
}
