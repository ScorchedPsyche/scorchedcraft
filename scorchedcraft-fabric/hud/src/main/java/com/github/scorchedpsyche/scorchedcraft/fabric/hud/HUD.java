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

package com.github.scorchedpsyche.scorchedcraft.fabric.hud;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.Core;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.PlayerManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HUD implements ModInitializer {;
	/**
	 * This code runs as soon as Minecraft is in a mod-load-ready state.
	 * However, some things (like resources) may still be uninitialized.
	 * Proceed with mild caution.
	 */
	@Override
	public void onInitialize() {
		
		ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
			Core.server.getCommandManager().getDispatcher().register(
				CommandManager.literal("sc")
					.then( CommandManager.literal("sleep")
							.executes( context -> {
								if( context.getSource().getEntity() != null && context.getSource().getEntity() instanceof ServerPlayerEntity)
								{
									for (Map.Entry<ServerPlayerEntity, PlayerManager> entry : Core.playerManagerList.entrySet()) {
										ConsoleUtil.debugMessage( entry.getKey().getName().toString() );
									}
								}
								
								return 0;
							})
					)
			);
		});
		
		
		
		
	}
}
