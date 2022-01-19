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

package com.github.scorchedpsyche.scorchedcraft.fabric.sleep;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.Core;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.FolderUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.MathUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.ResourcesUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.sleep.main.SleepManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.sleep.model.ConfigModel;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Sleep implements ModInitializer {
	public static File moduleFolder;
	public static ConfigModel configuration;
	
	private SleepManager sleepManager;
	
	/**
	 * This code runs as soon as Minecraft is in a mod-load-ready state.
	 * However, some things (like resources) may still be uninitialized.
	 * Proceed with mild caution.
	 */
	@Override
	public void onInitialize() {
		
		moduleFolder = FolderUtil.getOrCreateModuleSubfolder(ScorchedCraftManager.Sleep.Name.pomXml);
		
		if (FolderUtil.isDirectoryValid(moduleFolder)) {
			ResourcesUtil resourcesUtil = new ResourcesUtil(ScorchedCraftManager.Sleep.Name.full, moduleFolder,
				getClass().getClassLoader());
			
			// Copy default mod files if they're not present
			resourcesUtil.copyToModuleConfigFolderIfNotExists(new ArrayList<>() {{
				add("files/config.yml");
			}});
			
			// Load mod configuration YAML file
			File configSource = new File(moduleFolder + File.separator + "config.yml");
			Yaml yaml = new Yaml(new Constructor(ConfigModel.class));
			
			try {
				configuration = yaml.load(new FileInputStream(configSource));
				
				if ( this.isConfigYmlValid() ) {
					// Configuration loading done. Initialize Managers
					ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
						sleepManager = new SleepManager();
						
						new java.util.Timer().scheduleAtFixedRate(
							new java.util.TimerTask() {
								@Override
								public void run() {
									sleepManager.resetReservationsAndWarnPlayersIfNightIsReserved();
								}
							},
							MathUtil.ticksToMilliseconds(1),
							MathUtil.ticksToMilliseconds(100)
						);
						
						Core.server.getCommandManager().getDispatcher().register(
							CommandManager.literal("sc")
								.then( CommandManager.literal("sleep")
										.executes( context -> {
//									ConsoleUtil.debugMessage("command executed");
									if( context.getSource().getEntity() != null && context.getSource().getEntity() instanceof ServerPlayerEntity )
									{
//										ConsoleUtil.debugMessage("command is player");
										sleepManager.toggleNightReservationForPlayer(context.getSource().getPlayer());
									}
									
									return 0;
								})
							)
						);
						
						UseBlockCallback.EVENT.register((player, world, hand, hitResult) ->
						{
							ServerPlayerEntity playerServer = ((ServerPlayerEntity) player);
							
							BlockEntity blockEntity =  player.getWorld().getBlockEntity( hitResult.getBlockPos() );
//							ConsoleUtil.logMessage("RIGHT CLICK");
							
							if( blockEntity != null && blockEntity.getType() == BlockEntityType.BED &&
								!world.getDimension().hasEnderDragonFight() && !world.getDimension().hasCeiling())
							{
//								ConsoleUtil.logMessage("TRYING SLEEP");
								
								if( !sleepManager.isNightReservedExceptForPlayer(playerServer) )
								{
									if( sleepManager.playerIsTryingToSleep(playerServer) )
									{
//										ConsoleUtil.logMessage("sleeping");
										player.sleep( blockEntity.getPos() );
										return ActionResult.FAIL;
									}
								} else {
//									ConsoleUtil.logMessage("ActionResult.FAIL");
									return ActionResult.FAIL;
								}
							}
							
//							ConsoleUtil.logMessage("ActionResult.PASS");
							return ActionResult.PASS;
						});
						
						ServerPlayConnectionEvents.DISCONNECT.register((handler, server2) ->
							this.sleepManager.removeNightReservationIfExistsAndWarnPlayers(handler.player));
					});
				}
			} catch (IOException e) {
				ConsoleUtil.logError(ScorchedCraftManager.Sleep.Name.pomXml, "Failed to load config.yaml");
				e.printStackTrace();
			}
		} else {
			ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.pomXml, "Error validating module" +
				"folder: " + moduleFolder);
		}
	}
	
	/**
	 * Validates the config.yaml
	 * @return True if section is valid
	 */
	public boolean isConfigYmlValid()
	{
		boolean validYAML = true;
		
		if( configuration == null )
		{
			validYAML = false;
			ConsoleUtil.logError( ScorchedCraftManager.Sleep.Name.full,
				"config.yaml's is invalid");
		} else {
			configuration.chance_to_clear_weather_after_players_sleep = MathUtil.clamp(
				configuration.chance_to_clear_weather_after_players_sleep, 0, 100);
		}
		
		return validYAML;
	}
}
