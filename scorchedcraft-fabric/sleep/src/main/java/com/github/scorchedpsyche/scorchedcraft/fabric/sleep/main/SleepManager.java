/*
 * Copyright (c) 2022 ScorchedPsyche
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

package com.github.scorchedpsyche.scorchedcraft.fabric.sleep.main;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.Core;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.StringFormattedModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.PlayerUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.WorldUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.sleep.Sleep;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SleepManager {
    public SleepManager()
    {
        // Populate worlds
        for( World world : Core.server.getWorlds() )
        {
            worlds.put(world, new WorldNightManager(world));
        }
    }
    
    private final HashMap<World, WorldNightManager> worlds = new HashMap<>();
    
    /**
     * Processes any event of a player trying to sleep, whether it's valid or not (E.g.: trying to sleep in the Nether/End).
     * @param player The player that is trying to sleep which must be processed
     * @param world
     */
    public boolean playerIsTryingToSleep(PlayerEntity player, World world)
    {
        // Check if it's OK for the player to enter the bed right now by Vanilla standards
        if( world.isNight() || world.isRaining() || world.isThundering() )
        {
            // Sleep is Vanilla-valid, which is either:
            // 1 - It's night;
            // 2 - It's day and thundering

            // Add the world to Night Manager if it doesn't exists
            WorldNightManager worldNight = worlds.putIfAbsent( world, new WorldNightManager(world) );

            // Add player to the playersInBed list
            assert worldNight != null;
            worldNight.addPlayerInBed(player);

            // Remove player reservation if they have one
            this.removeNightReservationIfExistsAndWarnPlayers(player, worldNight);

            // Check if the night is still reserved
            if( !worldNight.hasReservations() )
            {
                // No reservations. Check if it's Night time
                if( WorldUtil.DayNightCycle.canBedsBeUsed(worldNight.getWorld()) )
                {
                    // Night Time
                    this.initiateTimeSkipIfNotAlreadyStarted(worldNight);
//                    worldNight.addPlayerWhoSlept( event.getPlayer() );
                } else {
                    // Day Time. Since the sleep attempt is Vanilla valid, then it must be thundering.
                    // Check if the plugin allows sleep during thunderstorm
                    if( Sleep.configuration.chance_to_clear_weather_after_players_sleep > 0
                        && Sleep.configuration.can_player_skip_weather_by_sleeping_during_the_day )
                    {
                        // It's OK to sleep to skip thunderstorms
                        this.initiateTimeSkipIfNotAlreadyStarted(worldNight);
//                        worldNight.addPlayerWhoSlept( event.getPlayer() );
                    } else {
                        // Cannot skip thunderstorms because of server configuration
                        sendMessageToPlayer(player, new StringFormattedModel()
                            .add("Server is configured so that ").redR("you cannot skip weather")
                            .add(" during the day!").toString());
                        return false;
                    }
                }
            } else {
                // Night still reserved. Warn player
                sendMessageToPlayer(player, new StringFormattedModel()
                    .add("Night is ").redR("reserved").add(" by:").nl()
                    .add(worldNight.getStringOfPlayersWithReservationInWorld()).toString());
                return false;
            }
        }
        
        return true;
    }
    
    public void removeNightReservationIfExistsAndWarnPlayers(PlayerEntity player, WorldNightManager worldNight)
    {
        // Attempt to remove night reservation for player
        if( worldNight.removeNightReservationIfExists(player) )
        {
            // Didn't have a night reservation. Let players know
            StringFormattedModel message = new StringFormattedModel().aquaR( player.getDisplayName().asString() ).add(" has ")
                .redR("unreserved").add(" the night!");
            sendMessageToAllPlayersInWorld(worldNight.getWorld(), message);
        }
    }
    
    private void initiateTimeSkipIfNotAlreadyStarted(@NotNull WorldNightManager worldNight)
    {
        // Check if time is already being skipped
        if( !worldNight.isSkippingTheNight() )
        {
            // World is not skipping the night. Initiate it
            worldNight.setSkippingTheNight(true);
    
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        doNightSkip(worldNight);
                    }
                },
                100L
            );
        }
    }
    
    private void doNightSkip(@NotNull WorldNightManager worldNight)
    {
        // Check if night was being skipped
        if( worldNight.isSkippingTheNight() )
        {
            // Check if any players in world are asleep
            if( worldNight.isThereAtLeastOnePlayerInBed() )
            {
                // At least one player asleep. Attempt to skip the night
                if( WorldUtil.DayNightCycle.skipNightUntilBedsCannotBeUsed(worldNight.getWorld()) )
                {
                    // Not yet daylight. Schedule another night skip
                    new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                doNightSkip(worldNight);
                                attemptToEndNightSkip(worldNight);
                            }
                        },
                        1L
                    );
                }
            }
        }
    }
    
    private void attemptToEndNightSkip(@NotNull WorldNightManager worldNight)
    {
        // There are no asleep players. Check if the world is at beds can be used end time
        if( WorldUtil.DayNightCycle.isWorldAtBedsCanBeUsedEndTime(worldNight.getWorld()) )
        {
            // World is one tick after beds can be used ALONG with night was being skipped.
            // This means we should reset everything and let other players know who skipped the night
            WorldUtil.attemptToClearWeatherDependingOnChance(
                worldNight.getWorld(), Sleep.configuration.chance_to_clear_weather_after_players_sleep );
            sendMessageToAllPlayersInWorld(worldNight.getWorld(), new StringFormattedModel()
                .add("Sleepy ones: ").add(worldNight.getStringOfPlayersInBed()));
            worldNight.resetReservations();
            WorldUtil.wakeAllPlayers(worldNight.getWorld());
            worldNight.setSkippingTheNight(false);
        }
    }
    
    private void sendMessageToAllPlayersInWorld(World world, StringFormattedModel message)
    {
        for( PlayerEntity playerInWorld : world.getPlayers() )
        {
            this.sendMessageToPlayer(playerInWorld, message.toString());
        }
    }
    
    private void sendMessageToPlayer(PlayerEntity player, String message)
    {
        PlayerUtil.sendMessageWithPluginPrefix(player, ScorchedCraftManager.Sleep.Name.compact, message);
    }
}
