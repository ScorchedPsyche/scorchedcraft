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
import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.model.StringFormattedModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.PlayerUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.WorldUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.MathUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.sleep.Sleep;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;

public class SleepManager {
    public SleepManager()
    {
    }
    
    private final ServerWorld mainWorld = Core.server.getWorlds().iterator().next();
    private final WorldNightManager worldNightManager = new WorldNightManager(mainWorld);
    public final long lowerNightReservationLimit = WorldUtil.DayNightCycle.SUNRISE - 110L;
    public final long upperNightReservationLimit = WorldUtil.DayNightCycle.SUNRISE + 110L;
    
    public boolean isNightReservedExceptForPlayer(ServerPlayerEntity player)
    {
//        WorldNightManager worldNight = worlds.putIfAbsent( world, new WorldNightManager(world) );
    
        this.removeNightReservationIfExistsAndWarnPlayers(player);
        
        if( worldNightManager.hasReservations() )
        {
            // Night still reserved. Warn player
            sendMessageToPlayer(player, new StringFormattedModel()
                .add("Night is ").redR("reserved").add(" by:").nl()
                .add(worldNightManager.getStringOfPlayersWithReservationInWorld()).toString());
            
            return true;
        }
        
        return false;
    }
    
    /**
     * Processes any event of a player trying to sleep, whether it's valid or not (E.g.: trying to sleep in the Nether/End).
     * @param player The player that is trying to sleep which must be processed
     */
    public boolean playerIsTryingToSleep(ServerPlayerEntity player)
    {
//        ConsoleUtil.logMessage("playerIsTryingToSleep");
        // Check if it's OK for the player to enter the bed right now by Vanilla standards
        if( mainWorld.isNight() || mainWorld.isRaining() || mainWorld.isThundering() )
        {
//            ConsoleUtil.logMessage("mainWorld.isNight() || mainWorld.isRaining() || mainWorld.isThundering()");
            // Sleep is Vanilla-valid, which is either:
            // 1 - It's night;
            // 2 - It's day and thundering

            // Add the world to Night Manager if it doesn't exists

            // Add player to the playersInBed list
            worldNightManager.addPlayerInBed(player);

            // Remove player reservation if they have one
            this.removeNightReservationIfExistsAndWarnPlayers(player);

            // Check if the night is still reserved
            if( !worldNightManager.hasReservations() )
            {
//                ConsoleUtil.logMessage("!hasReservations");
                // No reservations. Check if it's Night time
                if( WorldUtil.DayNightCycle.canBedsBeUsed(mainWorld) )
                {
//                    ConsoleUtil.logMessage("canBedsBeUsed");
                    // Night Time
                    this.initiateTimeSkipIfNotAlreadyStarted();
//                    worldNight.addPlayerWhoSlept( event.getPlayer() );
                } else {
//                    ConsoleUtil.logMessage("!canBedsBeUsed");
                    // Day Time. Since the sleep attempt is Vanilla valid, then it must be thundering.
                    // Check if server allows sleep during thunderstorm
                    if( mainWorld.isRaining() || mainWorld.isThundering() )
                    {
//                        ConsoleUtil.logMessage("mainWorld.isRaining() || mainWorld.isThundering()");
                        if( Sleep.configuration.chance_to_clear_weather_after_players_sleep > 0
                            && Sleep.configuration.can_player_skip_weather_by_sleeping_during_the_day )
                        {
//                            ConsoleUtil.logMessage("b4 initiateTimeSkipIfNotAlreadyStarted");
                            // It's OK to sleep to skip thunderstorms
                            this.initiateTimeSkipIfNotAlreadyStarted();
//                        worldNight.addPlayerWhoSlept( event.getPlayer() );
                        } else {
//                            ConsoleUtil.logMessage("b4 initiateTimeSkipIfNotAlreadyStarted");
                            // Cannot skip thunderstorms because of server configuration
                            sendMessageToPlayer(player, new StringFormattedModel()
                                .add("Server is configured so that ").redR("you cannot skip weather")
                                .add(" during the day!").toString());
                            return false;
                        }
                    }
                }
            } else {
                // Night still reserved. Warn player
                sendMessageToPlayer(player, new StringFormattedModel()
                    .add("Night is ").redR("reserved").add(" by:").nl()
                    .add(worldNightManager.getStringOfPlayersWithReservationInWorld()).toString());
                return false;
            }
        } else {
            // There's no reason for the player to be able to sleep
            return false;
        }
        
        return true;
    }
    
    public void toggleNightReservationForPlayer(ServerPlayerEntity player)
    {
//        ConsoleUtil.debugMessage("toggleNightReservationForPlayer");
        // Check if world has daylight cycle
        if( !player.getWorld().getDimension().hasEnderDragonFight()
            && !player.getWorld().getDimension().hasCeiling() )
        {
            // Attempt to add a night reservation for player
            if( !worldNightManager.playerHasReservation(player) )
            {
                this.addNightReservationIfPossibleAndWarnPlayers(player);
            } else {
                this.removeNightReservationIfExistsAndWarnPlayers(player);
            }
        } else {
            sendMessageToPlayer(player, new StringFormattedModel().add("This world ").redR("doesn't have")
                .add(" a daylight cycle.").toString());
        }
    }
    
    public void addNightReservationIfPossibleAndWarnPlayers(ServerPlayerEntity player)
    {
        // Attempt to remove night reservation for player
        if( worldNightManager.addNightReservationIfPossible(player) )
        {
            // Didn't have a night reservation. Let players know
            StringFormattedModel message = new StringFormattedModel().aquaR( player.getDisplayName().asString() ).add(" has ")
                .greenR("reserved").add(" the night!");
            sendMessageToAllPlayers(message);
        }
    }
    
    public void removeNightReservationIfExistsAndWarnPlayers(ServerPlayerEntity player)
    {
        // Attempt to remove night reservation for player
        if( worldNightManager.removeNightReservationIfExists(player) )
        {
            // Didn't have a night reservation. Let players know
            StringFormattedModel message = new StringFormattedModel().aquaR( player.getDisplayName().asString() ).add(" has ")
                .redR("unreserved").add(" the night!");
            sendMessageToAllPlayers(message);
        }
    }
    
    private void initiateTimeSkipIfNotAlreadyStarted()
    {
        long currentDaySunrise = 24000L * Math.floorDiv(mainWorld.getTimeOfDay(), 24000L);
        long nextDaySunrise;
    
        if( !mainWorld.isRaining() && !mainWorld.isThundering() )
        {
            // Weather clear
            nextDaySunrise = WorldUtil.DayNightCycle.WEATHER_CLEAR.BEDS_CAN_BE_USED_END + currentDaySunrise;
        } else {
            // Raining or thundering
            nextDaySunrise = WorldUtil.DayNightCycle.WEATHER_RAIN.BEDS_CAN_BE_USED_END + currentDaySunrise;
        }
        
//        ConsoleUtil.logMessage("initiateTimeSkipIfNotAlreadyStarted");
        // Check if time is already being skipped
        if( !worldNightManager.isSkippingTheNight() )
        {
//            ConsoleUtil.logMessage("!worldNightManager.isSkippingTheNight()");
            // World is not skipping the night. Initiate it
            worldNightManager.setSkippingTheNight(true);
    
            new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        doNightSkip(nextDaySunrise);
                    }
                },
                MathUtil.ticksToMilliseconds(100)
            );
        }
    }
    
    private void doNightSkip(long nextDaySunrise)
    {
//        ConsoleUtil.logMessage("doNightSkip");
        // Check if night was being skipped
        if( worldNightManager.isSkippingTheNight() )
        {
//            ConsoleUtil.logMessage("isSkippingTheNight");
            // Check if any players in world are asleep
            if( this.areAnyPlayersSleepingOnBed() )
            {
//                ConsoleUtil.logMessage("isThereAtLeastOnePlayerInBed");
                // At least one player asleep. Attempt to skip the night
                if( WorldUtil.DayNightCycle.skipNightUntilBedsCannotBeUsed(nextDaySunrise, mainWorld) )
                {
//                    ConsoleUtil.logMessage("skipNightUntilBedsCannotBeUsed");
                    // Not yet daylight. Schedule another night skip
                    new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                doNightSkip(nextDaySunrise);
                                attemptToEndNightSkip(nextDaySunrise);
                            }
                        },
                        MathUtil.ticksToMilliseconds(1)
                    );
                } else {
                    worldNightManager.setSkippingTheNight(false);
//                    ConsoleUtil.logMessage("!skipNightUntilBedsCannotBeUsed");
                }
            } else {
                worldNightManager.setSkippingTheNight(false);
            }
        }
    }
    
    private void attemptToEndNightSkip(long nextDaySunrise)
    {
//        ConsoleUtil.logMessage("attemptToEndNightSkip");
        // There are no asleep players. Check if the world is at beds can be used end time
        if( !this.areAnyPlayersSleepingOnBed() )
        {
//            ConsoleUtil.debugMessage("Cancelled night skip !areAnyPlayersSleepingOnBed");
            worldNightManager.setSkippingTheNight(false);
        } else if ( mainWorld.getTimeOfDay() >= nextDaySunrise )
        {
            // World is one tick after beds can be used ALONG with night was being skipped.
            // This means we should reset everything and let other players know who skipped the night
//            ConsoleUtil.debugMessage("Night skip ended mainWorld.getTimeOfDay() >= nextDaySunrise");
            WorldUtil.attemptToClearWeatherDependingOnChance(
                mainWorld, Sleep.configuration.chance_to_clear_weather_after_players_sleep );
            sendMessageToAllPlayers( new StringFormattedModel()
                .add("Sleepy ones: ").add(worldNightManager.getStringOfPlayersInBed()));
            worldNightManager.resetReservations();
            WorldUtil.wakeAllPlayers();
            worldNightManager.setSkippingTheNight(false);
        }
    }
    
    private boolean areAnyPlayersSleepingOnBed()
    {
        for(ServerPlayerEntity player : Core.server.getPlayerManager().getPlayerList() )
        {
            if( player.isSleeping() )
            {
                return true;
            }
        }
        
        return false;
    }
    
    private void sendMessageToAllPlayers(StringFormattedModel message)
    {
        for( ServerPlayerEntity player : Core.server.getPlayerManager().getPlayerList() )
        {
            this.sendMessageToPlayer(player, message.toString());
        }
    }
    
    public void resetReservationsAndWarnPlayersIfNightIsReserved()
    {
        if( mainWorld.getTimeOfDay() >= this.lowerNightReservationLimit &&
            mainWorld.getTimeOfDay() <= this.upperNightReservationLimit )
        {
            if( worldNightManager.getReservations().size() > 0 )
            {
                this.sendMessageToAllPlayers(new StringFormattedModel()
                    .add("Night reservations ").green("CLEARED").white("! You may sleep/reserve this day's night.")
                );
                worldNightManager.resetReservations();
            }
        }
    }
    
    private void sendMessageToPlayer(PlayerEntity player, String message)
    {
        PlayerUtil.sendMessageWithPluginPrefix(player, ScorchedCraftManager.Sleep.Name.compact, message);
    }
}
