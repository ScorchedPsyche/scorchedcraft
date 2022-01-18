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
import com.github.scorchedpsyche.scorchedcraft.fabric.sleep.Sleep;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WorldNightManager {
    public WorldNightManager(@NotNull World world) {
        this.world = world;
        this.reservations = new ArrayList<>();
        this.playersInBed = new ArrayList<>();
    }
    
    @NotNull
    private final World world;
    private List<PlayerEntity> reservations;
    private List<PlayerEntity> playersInBed;
    private boolean skippingTheNight = false;
    
    @NotNull
    public World getWorld()
    {
        return this.world;
    }
    public List<PlayerEntity> getReservations()
    {
        return reservations;
    }
    
    public boolean isSkippingTheNight() {
//        ConsoleUtil.debugMessage("setSkippingTheNight: " + skippingTheNight);
        return skippingTheNight;
    }
    public void setSkippingTheNight(boolean value)
    {
//        ConsoleUtil.debugMessage("setSkippingTheNight: " + value);
        this.skippingTheNight = value;
    }
    
    /**
     * Attempts to add a night reservation for the player.
     * @param player The player that wishes to reserve the night
     * @return True if the night was reserved for the player. False if the player already had a reservation
     */
    public boolean addNightReservationIfPossible(PlayerEntity player)
    {
        if( reservations.contains(player) )
        {
            return false;
        }
        
        reservations.add(player);
        return true;
    }
    
    /**
     * Attempts to remove the player's night reservation.
     * @param player The player to attempt to remove the reservation for
     * @return True if the night reservation for the player was removed. False if the player didn't have a reservation
     */
    public boolean removeNightReservationIfExists(PlayerEntity player)
    {
        return reservations.remove(player);
    }
    
    public void resetReservations()
    {
        reservations.clear();
//        playersWhoSlept.clear();
    }
    
    /**
     * Checks if the world has any night reservations.
     * @return True if there is any night reservation
     */
    public boolean hasReservations()
    {
        return !reservations.isEmpty();
    }
    
    /**
     * Checks if the player has a night reservation for the world.
     * @return True if there is a world night reservation for the player
     */
    public boolean playerHasReservation(PlayerEntity player)
    {
        return reservations.contains(player);
    }
    
    /**
     * Checks if players can sleep at the moment:
     * 1 - thundering;
     * 2 - if players can sleep through the day to skip it OR if chance to clear weather is 0.
     * @return True if players can sleep in this world at the moment
     */
    public boolean canSleepThroughThunderstorms()
    {
        return world.isThundering() && Sleep.configuration.can_player_skip_weather_by_sleeping_during_the_day;
    }
    /**
     * Checks if players can sleep at the moment:
     * 1 - thundering;
     * 2 - if players can sleep through the day to skip it OR if chance to clear weather is 0.
     * @return True if players can sleep in this world at the moment
     */
    public boolean canSleepThroughWeather()
    {
        return Sleep.configuration.chance_to_clear_weather_after_players_sleep != 0;
    }
    
    public StringFormattedModel getStringOfPlayersWithReservationInWorld()
    {
        if( !reservations.isEmpty() )
        {
            StringFormattedModel listOfPlayers = new StringFormattedModel();
            
            for(int i = 0; i < reservations.size(); i++)
            {
                listOfPlayers.aquaR( reservations.get(i).getDisplayName().asString() );
                if( i != reservations.size() - 1)
                {
                    listOfPlayers.add(", ");
                } else {
                    listOfPlayers.add(".");
                }
            }
            
            return listOfPlayers;
        }
        
        return new StringFormattedModel();
    }
    
    public StringFormattedModel getStringOfPlayersInBed()
    {
        this.updatePlayersInBedList();
        
        if( !playersInBed.isEmpty() )
        {
            StringFormattedModel listOfPlayers = new StringFormattedModel();
            
            for(int i = 0; i < playersInBed.size(); i++)
            {
                listOfPlayers.aquaR (playersInBed.get(i).getDisplayName().asString() );
                if( i != playersInBed.size() - 1)
                {
                    listOfPlayers.add(", ");
                } else {
                    listOfPlayers.add(".");
                }
            }
            
            return listOfPlayers;
        }
        
        return new StringFormattedModel();
    }
    
    public void addPlayerInBed(PlayerEntity player)
    {
        if( !playersInBed.contains(player) )
        {
//            ConsoleUtil.debugMessage("addPlayerInBed");
            playersInBed.add(player);
        }
    }
    
    public void removePlayerInBed(PlayerEntity player)
    {
//        ConsoleUtil.debugMessage("removePlayerInBed");
        playersInBed.remove(player);
        
        // After player removal from bed check if there are still players in bed
        if( playersInBed.isEmpty() )
        {
            // No players in bed. Then we must set skipping the night as false
            this.skippingTheNight = false;
        }
    }
    
    public boolean isThereAtLeastOnePlayerInBed()
    {
        return !this.playersInBed.isEmpty();
    }
    
    public void updatePlayersInBedList()
    {
        this.playersInBed.clear();
        
        for( ServerPlayerEntity player : Core.server.getPlayerManager().getPlayerList() )
        {
            if( player.isSleeping() )
            {
                this.playersInBed.add(player);
            }
        }
    }
    
    private void sendMessageToPlayer(PlayerEntity player, String message)
    {
        PlayerUtil.sendMessageWithPluginPrefix(player, ScorchedCraftManager.Sleep.Name.compact, message);
    }
}
