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

import com.github.scorchedpsyche.scorchedcraft.fabric.core.Core;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.FolderUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.ResourcesUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.main.MerchantManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.main.TradeListManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.model.ConfigModel;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtString;
import net.minecraft.server.Whitelist;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.command.WhitelistCommand;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WanderingTrades implements ModInitializer {
	public static File moduleFolder;
	public static ConfigModel configuration;
	
//	public static List<ItemStack> whitelistedPlayerHeads;
	public static MerchantManager merchantManager;
	public static TradeListManager tradeListManager;
	
	/**
	 * This code runs as soon as Minecraft is in a mod-load-ready state.
	 * However, some things (like resources) may still be uninitialized.
	 * Proceed with mild caution.
	 */
	@Override
	public void onInitialize() {
//		ConsoleUtil.modLoadStarted(ScorchedCraftManager.WanderingTrades.Name.full);
		
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
				add("files/trade_lists_exporting/SC - Wandering Trades - Decoration Heads - Vanilla.csv");
				add("files/trade_lists_exporting/SC - Wandering Trades - Decoration Heads - Vanilla.json");
				add("files/trade_lists_exporting/SC - Wandering Trades - Decoration Heads - Food.csv");
				add("files/trade_lists_exporting/SC - Wandering Trades - Decoration Heads - Food.json");
				add("files/trade_lists_exporting/SC - Wandering Trades - Decoration Heads - Non-Vanilla.csv");
				add("files/trade_lists_exporting/SC - Wandering Trades - Decoration Heads - Non-Vanilla.json");
				add("files/trade_lists_exporting/SC - Wandering Trades - Decoration Heads - Pokemon.csv");
				add("files/trade_lists_exporting/SC - Wandering Trades - Decoration Heads - Pokemon.json");
				add("files/trade_lists_exporting/export_list.py");
				add("files/config.yml");
			}});
			
			// Load mod configuration YAML file
			File configSource = new File(moduleFolder + File.separator + "config.yml");
			Yaml yaml = new Yaml(new Constructor(ConfigModel.class));
			
			try {
				configuration = yaml.load(new FileInputStream(configSource));
			} catch (IOException e) {
				ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.pomXml, "Failed to load config.yaml");
				e.printStackTrace();
			}
			
			if( this.isConfigYmlValid() )
			{
				// Configuration loading done. Initialize Managers
//				whitelistedPlayerHeads = new ArrayList<>();
				tradeListManager = new TradeListManager(moduleFolder.getAbsolutePath() + File.separator + "trade_lists");
				merchantManager = new MerchantManager();
				
				if( configuration.whitelist.enable_synchronization )
				{
//					whitelistedPlayerHeads = new ArrayList<>();
					
					ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
						loadPlayerHeads();
					});
				}
			}
		} else {
			ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.pomXml, "Error validating module" +
				"folder: " + moduleFolder);
		}
		
//		ConsoleUtil.modLoadFinished(ScorchedCraftManager.WanderingTrades.Name.full);
	}
	
	public static void loadPlayerHeads()
	{
		merchantManager.playerHeadsWhitelisted = new ArrayList<>();
		Whitelist whitelist = Core.server.getPlayerManager().getWhitelist();
		String[] players = whitelist.getNames();;
		
		if( players.length > 0 )
		{
//			world.removeBlock(new BlockPos(0, -64, 0), false);
//			skullBlockEntity = new SkullBlockEntity(
//				new BlockPos(0, -64, 0), Blocks.PLAYER_HEAD.getDefaultState());
//			world.addBlockEntity(skullBlockEntity);
//			skullBlockEntity.setWorld(world);
			
//			ChestBlockEntity chest = (ChestBlockEntity) world.getBlockEntity(new BlockPos(1, 108, 2));
//			int slot = 0;
			int totalHeadsLoaded = 0;
			
			for(String whitelistedPlayerName : whitelist.getNames())
			{
				ItemStack playerHead = new ItemStack( Items.PLAYER_HEAD, WanderingTrades.configuration.whitelist.heads_rewarded_per_trade);
				playerHead.setSubNbt(  "SkullOwner", NbtString.of(whitelistedPlayerName) );
				merchantManager.loadWhitelistedPlayerHeadRecipe(playerHead);
				totalHeadsLoaded++;
//				whitelistedPlayerHeads.add(playerHead);
				
//				skullBlockEntity.setOwner(new GameProfile((UUID)null, whitelistedPlayerName));
//				if (chest != null) {
//					chest.setStack(slot++, playerHead);
//				} else {
//					ConsoleUtil.logMessage("CHEST NULL ");
//				}
				
//				ConsoleUtil.logMessage( "SkullBlockEntity NBT: " + skullBlockEntity.createNbt() );
//
//				skullBlockEntity.setStackNbt(playerHead);
//				playerHead.setSubNbt("SkullOwner", NbtHelper.writeGameProfile(new NbtCompound(), skullBlockEntity.getOwner()));
				
//				if (playerHead.getNbt() != null) {
//					ConsoleUtil.logMessage(playerHead.getNbt().toString());
//				} else {
//					ConsoleUtil.logMessage("NULL " + whitelistedPlayerName);
//				}
			}
			
			ConsoleUtil.logMessage( ScorchedCraftManager.WanderingTrades.Name.full,
				"Loaded a total of " + totalHeadsLoaded + " player heads");
		}
		
//		for( ItemStack playerHead : whitelistedPlayerHeads )
//		{
//			if (playerHead.getNbt() != null) {
//				merchantManager.loadWhitelistedPlayerHeadRecipe(playerHead);
//			}
//		}
//		ConsoleUtil.logMessage(ScorchedCraftManager.WanderingTrades.Name.pomXml,
//			"Starting to preloading player heads");
//
//		ServerWorld world = Core.server.getWorlds().iterator().next();
//
//		ArmorStandEntity armorStand = new ArmorStandEntity(world, 0, -80, 0);
//		armorStand.setNoGravity(true);
//		world.spawnEntity(armorStand);
//
//		for( ItemStack whitelistedPlayerHead : whitelistedPlayerHeads )
//		{
//			armorStand.equipStack(EquipmentSlot.HEAD, whitelistedPlayerHead);
//		}
//
//		ConsoleUtil.logMessage(ScorchedCraftManager.WanderingTrades.Name.pomXml,
//			"Finished preloading player heads");
//
////		DefaultedList<ItemStack> inventory = DefaultedList.ofSize(length, ItemStack.EMPTY);
////		inventory.addAll(whitelistedPlayerHeads);
	}
	
	/**
	 * Validates the config.yaml's whitelist section
	 * @return True if section is valid
	 */
	public boolean isConfigYmlValid()
	{
		boolean validYAML = true;
		
		if( configuration.maximum_unique_trade_offers != null )
		{
			if( configuration.maximum_unique_trade_offers.items < 0  )
			{
				validYAML = false;
				ConsoleUtil.logError( ScorchedCraftManager.Name.full,
					"config.yaml's property is either missing or invalid (cannot be negative): maximum_unique_trade_offers.items");
			}
			
			if( configuration.maximum_unique_trade_offers.decoration_heads < 0  )
			{
				validYAML = false;
				ConsoleUtil.logError( ScorchedCraftManager.Name.full,
					"config.yaml's property is either missing or invalid (cannot be negative): maximum_unique_trade_offers.decoration_heads");
			}
			
			if( configuration.maximum_unique_trade_offers.player_heads < 0  )
			{
				validYAML = false;
				ConsoleUtil.logError( ScorchedCraftManager.Name.full,
					"config.yaml's property is either missing or invalid (cannot be negative): maximum_unique_trade_offers.player_heads");
			}
		} else {
			validYAML = false;
			ConsoleUtil.logError( ScorchedCraftManager.Name.full,
				"config.yaml's section is missing: maximum_unique_trade_offers");
		}
		
		if( configuration.whitelist != null )
		{
			if( configuration.whitelist.number_of_player_head_offers <= 0  )
			{
				validYAML = false;
				ConsoleUtil.logError( ScorchedCraftManager.Name.full,
					"config.yaml's property is either missing or invalid (cannot be negative or 0): whitelist.number_of_player_head_offers");
			}
			
			if( configuration.whitelist.heads_rewarded_per_trade <= 0  )
			{
				validYAML = false;
				ConsoleUtil.logError( ScorchedCraftManager.WanderingTrades.Name.full,
					"config.yaml's property is either missing or invalid (cannot be negative or 0): whitelist.heads_rewarded_per_trade");
			}
			
			if( configuration.whitelist.maximum_number_of_trades <= 0  )
			{
				validYAML = false;
				ConsoleUtil.logError( ScorchedCraftManager.Name.full,
					"config.yaml's property is either missing or invalid (cannot be negative or 0): whitelist.maximum_number_of_trades");
			}
			
			if( configuration.whitelist.experience_rewarded_for_each_trade < 0  )
			{
				validYAML = false;
				ConsoleUtil.logError( ScorchedCraftManager.Name.full,
					"config.yaml's property is either missing or invalid (cannot be negative): whitelist.experience_rewarded_for_each_trade");
			}
			
			if( configuration.whitelist.trade_price_multiplier < 0  )
			{
				validYAML = false;
				ConsoleUtil.logError( ScorchedCraftManager.Name.full,
					"config.yaml's property is either missing or invalid (cannot be negative): whitelist.trade_price_multiplier");
			}
			
			if( configuration.whitelist.price == null  )
			{
				validYAML = false;
				ConsoleUtil.logError( ScorchedCraftManager.Name.full,
					"config.yaml's property is either missing or invalid: whitelist.price");
			} else {
				if( configuration.whitelist.price.item1 == null  )
				{
					validYAML = false;
					ConsoleUtil.logError( ScorchedCraftManager.Name.full,
						"config.yaml's property is either missing or invalid: whitelist.price.item1");
				} else {
					if( configuration.whitelist.price.item1.minecraft_id == null  )
					{
						validYAML = false;
						ConsoleUtil.logError( ScorchedCraftManager.Name.full,
							"config.yaml's property is either missing or invalid: whitelist.price.item1.minecraft_id");
					} else {
						if( Identifier.tryParse(configuration.whitelist.price.item1.minecraft_id) == null )
						{
							validYAML = false;
							ConsoleUtil.logError( ScorchedCraftManager.Name.full,
								"config.yaml's property is either missing or invalid: whitelist.price.item1.minecraft_id");
						}
					}
					
					if( configuration.whitelist.price.item1.quantity <= 0 )
					{
						validYAML = false;
						ConsoleUtil.logError( ScorchedCraftManager.Name.full,
							"config.yaml's property is either missing or invalid: whitelist.price.item1.quantity");
					}
				}
				
				if( configuration.whitelist.price.item2 != null  )
				{
					if( configuration.whitelist.price.item2.minecraft_id == null  )
					{
						validYAML = false;
						ConsoleUtil.logError( ScorchedCraftManager.Name.full,
							"config.yaml's property is either missing or invalid: whitelist.price.item2.minecraft_id");
					} else {
						if( Identifier.tryParse(configuration.whitelist.price.item2.minecraft_id) == null )
						{
							validYAML = false;
							ConsoleUtil.logError( ScorchedCraftManager.Name.full,
								"config.yaml's property is either missing or invalid: whitelist.price.item2.minecraft_id");
						}
					}
					
					if( configuration.whitelist.price.item2.quantity <= 0 )
					{
						validYAML = false;
						ConsoleUtil.logError( ScorchedCraftManager.WanderingTrades.Name.full,
							"config.yaml's property is either missing or invalid: whitelist.price.item2.quantity");
					}
				}
			}
		} else {
			validYAML = false;
			ConsoleUtil.logError( ScorchedCraftManager.WanderingTrades.Name.full,
				"config.yaml's section is missing: whitelist");
		}
		
		return validYAML;
	}
}
