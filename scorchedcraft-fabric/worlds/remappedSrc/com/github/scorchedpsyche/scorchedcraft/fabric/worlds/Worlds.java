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

package com.github.scorchedpsyche.scorchedcraft.fabric.worlds;

import com.github.scorchedpsyche.scorchedcraft.fabric.worlds.managers.WorldsServerManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.TypedActionResult;

public class Worlds implements ModInitializer {
	WorldsServerManager worldsManager = new WorldsServerManager();

	/**
	 * This code runs as soon as Minecraft is in a mod-load-ready state.
	 * However, some things (like resources) may still be uninitialized.
	 * Proceed with mild caution.
	 */
	@Override
	public void onInitialize() {
		System.out.println("WORLDS");
		worldsManager = new WorldsServerManager();

		ServerWorldEvents.LOAD.register( (server, world) -> {
			System.out.println("WORLDS: World loaded: " + world.asString());
			System.out.println("WORLDS: World loaded: " + world.getRegistryKey());
			System.out.println("WORLDS: World loaded: " + world.toServerWorld().getDimension());
			worldsManager.addLoadedWorld(world);
		});
	}
}
