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
import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.FolderUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

import java.io.File;

public class Core implements ModInitializer {
	public static MinecraftServer server;
	public DatabaseManager databaseManager;
	
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
			ConsoleUtil.logMessage( ScorchedCraftManager.Core.Name.full,
				"Configuration folder verified");
			
			ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
				Core.server = server;
				ConsoleUtil.logMessage( ScorchedCraftManager.Core.Name.full,
					"Server registered");
			});
		} else {
			ConsoleUtil.logError( ScorchedCraftManager.Core.Name.full,
				"Failed to configure suite's root folder. Check read/write permissions for the CONFIG" +
					"folder inside your Minecraft folder!");
		}
//		ConsoleUtil.modLoadFinished(ScorchedCraftManager.Core.Name.full);
	}
}
