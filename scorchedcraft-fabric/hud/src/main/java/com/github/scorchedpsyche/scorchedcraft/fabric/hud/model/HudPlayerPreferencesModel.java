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

package com.github.scorchedpsyche.scorchedcraft.fabric.hud.model;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.database.DatabaseTables;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Model for ease of use and management of player HUD preferences.
 */
public class HudPlayerPreferencesModel
{
    /**
     * Attempts to loads the preferences from a ResultSet.
     * @param rs The ResultSet to load the preferences from
     * @return The preferences if successful. Null otherwise
     */
    public HudPlayerPreferencesModel loadPreferencesFromResultSet(ResultSet rs)
    {
        try {
            if ( rs.isBeforeFirst() ) {
                id = rs.getInt(1);
                player_uuid = rs.getString(2);
                enabled = rs.getBoolean(3);
                display_mode = rs.getBoolean(4);
                colorize_coordinates = rs.getBoolean(5);
                colorize_nether_portal_coordinates = rs.getBoolean(6);
                colorize_player_orientation = rs.getBoolean(7);
                colorize_tool_durability = rs.getBoolean(8);
                colorize_world_time = rs.getBoolean(9);
                format_world_time = rs.getBoolean(10);
                coordinates = rs.getBoolean(11);
                nether_portal_coordinates = rs.getBoolean(12);
                player_orientation = rs.getBoolean(13);
                plugin_commerce = rs.getBoolean(14);
                plugin_spectator = rs.getBoolean(15);
                server_time = rs.getBoolean(16);
                tool_durability = rs.getBoolean(17);
                world_time = rs.getBoolean(18);
                
                return this;
            }
        } catch (SQLException e)
        {
            ConsoleUtil.logError(
                ScorchedCraftManager.HUD.Name.full,
                "Failed to load player preferences from ResultSet. TRACE:");
            e.printStackTrace();
        }
        
        return null;
    }
    
    public String getPlayerUUID() { return player_uuid; }
    public boolean isHudEnabled() { return enabled; }
    public boolean isDisplayModeExtended() { return display_mode; }
    public boolean colorizeCoordinates() { return colorize_coordinates; }
    public boolean colorizeNetherPortalCoordinates() { return colorize_nether_portal_coordinates; }
    public boolean colorizePlayerOrientation() { return colorize_player_orientation; }
    public boolean colorizeToolDurability() { return colorize_tool_durability; }
    public boolean colorizeWorldTime() { return colorize_world_time; }
    public boolean formatWorldTime() { return format_world_time; }
    public boolean showCoordinates() { return coordinates; }
    public boolean showNetherPortalCoordinates() { return nether_portal_coordinates; }
    public boolean showOrientation() { return player_orientation; }
    public boolean showServerTime() { return server_time; }
    public boolean showToolDurability() { return tool_durability; }
    public boolean showWorldTime() { return world_time; }
    public void setPreference(String preference, boolean value)
    {
        switch (preference) {
            case "format_world_time" -> format_world_time = value;
            
            default -> display_mode = value; // DatabaseTables.Hud.PlayerPreferencesTable.display_mode
        }
    }
    
    /**
     * Inverts the value for a player's specific HUD preference.
     * @param preference The preference to invert the value for
     */
    public void togglePreference(String preference)
    {
        switch (preference) {
            case DatabaseTables.Hud.PlayerPreferencesTable.colorize_coordinates
                -> colorize_coordinates = !colorize_coordinates;
            case DatabaseTables.Hud.PlayerPreferencesTable.colorize_nether_portal_coordinates
                -> colorize_nether_portal_coordinates = !colorize_nether_portal_coordinates;
            case DatabaseTables.Hud.PlayerPreferencesTable.colorize_player_orientation
                -> colorize_player_orientation = !colorize_player_orientation;
            case DatabaseTables.Hud.PlayerPreferencesTable.colorize_tool_durability
                -> colorize_tool_durability = !colorize_tool_durability;
            case DatabaseTables.Hud.PlayerPreferencesTable.colorize_world_time
                -> colorize_world_time = !colorize_world_time;
            case DatabaseTables.Hud.PlayerPreferencesTable.format_world_time
                -> format_world_time = !format_world_time;
            case DatabaseTables.Hud.PlayerPreferencesTable.coordinates
                -> coordinates = !coordinates;
            case DatabaseTables.Hud.PlayerPreferencesTable.nether_portal_coordinates
                -> nether_portal_coordinates = !nether_portal_coordinates;
            case DatabaseTables.Hud.PlayerPreferencesTable.player_orientation
                -> player_orientation = !player_orientation;
            case DatabaseTables.Hud.PlayerPreferencesTable.plugin_commerce
                -> plugin_commerce = !plugin_commerce;
            case DatabaseTables.Hud.PlayerPreferencesTable.plugin_spectator
                -> plugin_spectator = !plugin_spectator;
            case DatabaseTables.Hud.PlayerPreferencesTable.server_time
                -> server_time = !server_time;
            case DatabaseTables.Hud.PlayerPreferencesTable.tool_durability
                -> tool_durability = !tool_durability;
            case DatabaseTables.Hud.PlayerPreferencesTable.world_time
                -> world_time = !world_time;
        }
    }
    
    private int id;
    private String player_uuid;
    private Boolean enabled;
    private Boolean display_mode;
    private Boolean colorize_coordinates;
    private Boolean colorize_nether_portal_coordinates;
    private Boolean colorize_player_orientation;
    private Boolean colorize_tool_durability;
    private Boolean colorize_world_time;
    private Boolean format_world_time;
    private Boolean coordinates;
    private Boolean nether_portal_coordinates;
    private Boolean player_orientation;
    private Boolean plugin_commerce;
    private Boolean plugin_spectator;
    private Boolean server_time;
    private Boolean tool_durability;
    private Boolean world_time;
}