/*
 * Copyright (c) 2021 ScorchedPsyche
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.scorchedpsyche.scorchedcraft.fabric.seasons;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.StringFormattedModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.FolderUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.seasons.main.SeasonManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.seasons.main.SeasonsDatabaseApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Seasons implements ModInitializer {
	public static SeasonManager seasonManager;
	
	private SeasonsDatabaseApi seasonsDatabaseApi;
	
	/**
	 * This code runs as soon as Minecraft is in a mod-load-ready state.
	 * However, some things (like resources) may still be uninitialized.
	 * Proceed with mild caution.
	 */
	@Override
	public void onInitialize() {
		// Attempt to setup the rest of the plugin
		seasonsDatabaseApi = new SeasonsDatabaseApi();
		
		
		ConsoleUtil.debugMessage(FolderUtil.getOrCreateSuiteSubfolder().toString());
		
//		// Setup and verify DB tables
//		if( seasonsDatabaseApi.setupAndVerifySqlTable() )
//		{
//			seasonManager = new SeasonManager(seasonsDatabaseApi).setCurrentSeason(seasonsDatabaseApi.fetchCurrentSeason());
//
//			// Check if there's no active season
//			if( seasonManager.current == null )
//			{
//				// No active season. Display warning
//				ConsoleUtil.logWarning( ScorchedCraftManager.Seasons.Name.full,
//					"Module is enabled but there is no active season? Ignore if this is intended.");
//			}
//
////			// Add plugin commands
////			addPluginCommands();
////
////			// Listeners
////			getServer().getPluginManager().registerEvents(new SeasonsCommandListener(seasonManager), this);
////			getServer().getPluginManager().registerEvents(new PlayerJoinSeasonsListener(seasonManager), this);
//		} else {
//			// Failed to create database tables! Display error and disable plugin
//			ConsoleUtil.logError(ScorchedCraftManager.Seasons.Name.full, "Failed to create database tables. Disabling!");
////			Bukkit.getPluginManager().disablePlugin(this);
//		}
	}
}
