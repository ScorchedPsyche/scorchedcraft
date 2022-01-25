/*
 * Copyright (c) 2021-2021. ScorchedPsyche
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

package com.github.scorchedpsyche.scorchedcraft.fabric.core;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.database.DatabaseManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.PlayerManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.database.CoreDatabaseApi;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.model.ConfigModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.FolderUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.ResourcesUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Core implements ModInitializer {
	public static final boolean debugEnabled = true;
	
	public static MinecraftServer server;
	public DatabaseManager databaseManager;
	public static ConfigModel configuration;
	public static HashMap<ServerPlayerEntity, PlayerManager> playerManagerList = new HashMap<>();
	
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		// Display console ScorchedCraft logo
		ConsoleUtil.logMessage("    __   " + " __  " );
		ConsoleUtil.logMessage("   |__   " + "|  " + "    ScorchedCraft");
		ConsoleUtil.logMessage("    __|  " + "|__  " + "      Suite");
		ConsoleUtil.logMessage("");
//		ConsoleUtil.modLoadStarted(ScorchedCraftManager.Core.Name.full);
		
		FolderUtil.setupSuiteFolders();
		File suiteRootFolder = FolderUtil.getOrCreateSuiteRootFolder();
		
		// Check if Suite's root folder exists
		if( suiteRootFolder != null )
		{
			ResourcesUtil resourcesUtil = new ResourcesUtil(ScorchedCraftManager.Core.Name.full, suiteRootFolder,
				getClass().getClassLoader());
			
			// Copy default mod files if they're not present
			resourcesUtil.copyToModuleConfigFolderIfNotExists(new ArrayList<String>(){{
				add("files/config.yml");
			}});
			
			// Load mod configuration YAML file
			File configSource = new File(suiteRootFolder + File.separator + "config.yml");
			Yaml yaml = new Yaml(new Constructor(ConfigModel.class));
			
			try {
				configuration = yaml.load(new FileInputStream(configSource));
			} catch (IOException e) {
				ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.pomXml, "Failed to load config.yaml");
				e.printStackTrace();
			}
			
			if( isConfigYmlValid() )
			{
				ConsoleUtil.logMessage( ScorchedCraftManager.Core.Name.full,
					"Configuration verified");
				
				// Configure database
				if ( "mysql".equals(configuration.storage_type) )
				{
					databaseManager = new DatabaseManager(DatabaseManager.DatabaseType.MySQL);
				} else
				{
					databaseManager = new DatabaseManager(DatabaseManager.DatabaseType.SQLite);
				}
				
				CoreDatabaseApi coreDatabaseApi = new CoreDatabaseApi();
				
				if( coreDatabaseApi.setupAndVerifySqlTable() )
				{
					
					ServerPlayConnectionEvents.JOIN.register((handler, sender, server) ->
						playerManagerList.putIfAbsent(handler.getPlayer(), new PlayerManager(handler.getPlayer())));
					ServerPlayConnectionEvents.DISCONNECT.register((handler, server) ->
						playerManagerList.remove(handler.getPlayer()));
					
					ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
						Core.server = server;
						ConsoleUtil.logMessage( ScorchedCraftManager.Core.Name.full,
							"Server registered");
					});
				}else {
					// Failed to create database tables! Display error and disable plugin
					ConsoleUtil.logError(ScorchedCraftManager.Core.Name.full, "Failed to create database tables. Disabling!");
				}
			}
		} else {
			ConsoleUtil.logError( ScorchedCraftManager.Core.Name.full,
				"Failed to configure suite's root folder. Check read/write permissions for the CONFIG" +
					"folder inside your Minecraft folder!");
		}
//		ConsoleUtil.modLoadFinished(ScorchedCraftManager.Core.Name.full);
	}
	
	/**
	 * Validates the config.yaml
	 * @return True if section is valid
	 */
	public boolean isConfigYmlValid()
	{
		if( configuration != null )
		{
			if( !configuration.storage_type.equalsIgnoreCase("mysql") &&
				!configuration.storage_type.equalsIgnoreCase("sqlite") )
			{
				ConsoleUtil.logError( ScorchedCraftManager.Core.Name.full,
					"config.yaml's property is either missing or invalid: storage_type");
				
				
				return false;
			}
		}
		
		return true;
	}
}
