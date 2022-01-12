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
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.model.ConfigModel;
import net.fabricmc.api.ModInitializer;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class WanderingTrades implements ModInitializer {
	public static File moduleFolder;
	public static ConfigModel configuration;
	
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
			ResourcesUtil resourcesUtil = new ResourcesUtil(ScorchedCraftManager.WanderingTrades.Name.full, moduleFolder,
				getClass().getClassLoader());
			
			File destination = new File(moduleFolder.getAbsolutePath() + File.separator + "trade_lists");
			
			resourcesUtil.copyToModuleConfigFolderIfNotExists(new ArrayList<String>(){{
				add("files/trade_lists/heads_decoration.json");
				add("files/trade_lists/heads_players.json");
				add("files/trade_lists/items.json");
				add("files/trade_lists_exporting/CES - Wandering Trades - Decoration Heads - Vanilla.csv");
				add("files/trade_lists_exporting/CES - Wandering Trades - Decoration Heads - Vanilla.json");
				add("files/trade_lists_exporting/CES - Wandering Trades - Decoration Heads - Food.csv");
				add("files/trade_lists_exporting/CES - Wandering Trades - Decoration Heads - Food.json");
				add("files/trade_lists_exporting/CES - Wandering Trades - Decoration Heads - Non-Vanilla.csv");
				add("files/trade_lists_exporting/CES - Wandering Trades - Decoration Heads - Non-Vanilla.json");
				add("files/trade_lists_exporting/export_list.py");
				add("files/config.yml");
				add("files/config.json");
			}});
			
			File configSource = new File(moduleFolder + File.separator + "config.yml");
			
			Yaml yaml = new Yaml(new Constructor(ConfigModel.class));
			
			try {
				configuration = yaml.load(new FileInputStream(configSource));
				ConsoleUtil.debugMessage(configuration.toString());
				
//				ConsoleUtil.debugMessage("remove_default_trades" + config.remove_default_trades);
//				ConfigModel config = mapper.readValue(configSource, ConfigModel.class);
//
				ConsoleUtil.debugMessage("remove_default_trades " + configuration.remove_default_trades);
				ConsoleUtil.debugMessage("maximum_unique_trade_offers.decoration_heads " + configuration.maximum_unique_trade_offers.decoration_heads);
				ConsoleUtil.debugMessage("whitelist.enable_synchronization " + configuration.whitelist.enable_synchronization);
				ConsoleUtil.debugMessage("whitelist.price.item1.minecraft_id " + configuration.whitelist.price.item1.minecraft_id);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} else {
			ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.pomXml, "Error validating module" +
				"folder: " + moduleFolder);
		}
		
		ConsoleUtil.modLoadFinished(ScorchedCraftManager.WanderingTrades.Name.full);
	}
}
