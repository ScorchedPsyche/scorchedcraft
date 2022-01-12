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
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.main.MerchantManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.main.TradeListManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.model.ConfigModel;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemStack;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WanderingTrades implements ModInitializer {
	public static File moduleFolder;
	public static ConfigModel configuration;
	
	public static List<ItemStack> whitelistedPlayerHeads;
	public static MerchantManager merchantManager;
	public static TradeListManager tradeListManager;
	
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
			
			// Copy default mod files if they're not present
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
			
			// Load mod configuration YAML file
			File configSource = new File(moduleFolder + File.separator + "config.yml");
			Yaml yaml = new Yaml(new Constructor(ConfigModel.class));
			
			try {
				configuration = yaml.load(new FileInputStream(configSource));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Configuration loading done. Initialize Managers
			whitelistedPlayerHeads = new ArrayList<>();
			tradeListManager = new TradeListManager(moduleFolder.getAbsolutePath() + File.separator + "trade_lists");
			merchantManager = new MerchantManager();
			
			ConsoleUtil.debugMessage( "size: " + tradeListManager.Trades.offers.size() );
			ConsoleUtil.debugMessage( tradeListManager.Trades.offers.get(0).toString() );
			ConsoleUtil.debugMessage( tradeListManager.Trades.offers.get(1).toString() );
			ConsoleUtil.debugMessage( tradeListManager.Trades.offers.get(2).toString() );
//			ConsoleUtil.debugMessage( tradeListManager.Trades.offers.get(3).toString() );
//			ConsoleUtil.debugMessage( tradeListManager.Trades.offers.get(4).toString() );
//			ConsoleUtil.debugMessage( tradeListManager.Trades.offers.get(5).toString() );
			
			ConsoleUtil.debugMessage( tradeListManager.Trades.offers.get(0).getPriceItem1() );
			ConsoleUtil.debugMessage( "getPrice1 " + tradeListManager.Trades.offers.get(0).getPrice1() );
			ConsoleUtil.debugMessage( tradeListManager.Trades.offers.get(0).getMinecraftId() );
			ConsoleUtil.debugMessage( "getAmount " + tradeListManager.Trades.offers.get(0).getAmount() );
			ConsoleUtil.debugMessage( "getOwnerId " + tradeListManager.Trades.offers.get(0).getOwnerId() );
		} else {
			ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.pomXml, "Error validating module" +
				"folder: " + moduleFolder);
		}
		
		ConsoleUtil.modLoadFinished(ScorchedCraftManager.WanderingTrades.Name.full);
	}
}
