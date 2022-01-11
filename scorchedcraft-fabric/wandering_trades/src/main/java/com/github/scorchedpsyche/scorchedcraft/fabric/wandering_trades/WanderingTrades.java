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

package com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.FolderUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.ResourcesUtil;
import net.fabricmc.api.ModInitializer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class WanderingTrades implements ModInitializer {
	public static File moduleFolder;
	
	/**
	 * This code runs as soon as Minecraft is in a mod-load-ready state.
	 * However, some things (like resources) may still be uninitialized.
	 * Proceed with mild caution.
	 */
	@Override
	public void onInitialize() {
		ConsoleUtil.modLoadStarted(ScorchedCraftManager.WanderingTrades.Name.full);
		
		moduleFolder = FolderUtil.getOrCreateModuleSubfolder(ScorchedCraftManager.WanderingTrades.Name.pomXml);
	
		if( FolderUtil.isDirectoryValid(moduleFolder) )
		{
			ResourcesUtil resourcesUtil = new ResourcesUtil(ScorchedCraftManager.WanderingTrades.Name.full);
			
			File destination = new File(moduleFolder.getAbsolutePath() + File.separator + "trade_lists");
			
			resourcesUtil.copyToModuleConfigFolderIfNotExists( getClass().getClassLoader(),
				"files/config.yml", moduleFolder, "config.yml");
			
			// Check if "trade_lists" folder already exists
			if( !destination.exists() )
			{
				// Since it doesn't exist, we copy the default trade lists
				resourcesUtil.copyToModuleConfigFolderIfNotExists( getClass().getClassLoader(),
					"files/trade_lists/heads_decoration.json", destination, "heads_decoration.json");
				resourcesUtil.copyToModuleConfigFolderIfNotExists( getClass().getClassLoader(),
					"files/trade_lists/heads_players.json", destination, "heads_players.json");
				resourcesUtil.copyToModuleConfigFolderIfNotExists( getClass().getClassLoader(),
					"files/trade_lists/items.json", destination, "items.json");
			}
			
			
		} else {
			ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.pomXml, "Error validating module" +
				"folder: " + moduleFolder);
		}
		
		ConsoleUtil.modLoadFinished(ScorchedCraftManager.WanderingTrades.Name.full);
	}
}
