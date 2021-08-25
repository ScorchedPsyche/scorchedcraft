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

package com.github.scorchedpsyche.scorchedcraft.fabric.portals;

import com.github.scorchedpsyche.scorchedcraft.fabric.portals.managers.CustomBlocksManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.managers.PortalManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.util.ActionResult;

public class Portals implements ModInitializer {
	public PortalManager portalManager;
	
	/**
	 * This code runs as soon as Minecraft is in a mod-load-ready state.
	 * However, some things (like resources) may still be uninitialized.
	 * Proceed with mild caution.
	 */
	@Override
	public void onInitialize() {
		System.out.println("PORTALS");
		
		CustomBlocksManager.registerCustomBlocks();
		
		this.portalManager = new PortalManager();

		ServerLifecycleEvents.SERVER_STARTED.register((server) -> {
			System.out.println("PORTALS: ServerLifecycleEvents.SERVER_STARTED");
//			UseItemCallback.EVENT.register( (player, world2, hand) -> {
//				System.out.println("PORTALS: UseItemCallback");
//
//				return TypedActionResult.pass(ItemStack.EMPTY);
//			});
			UseBlockCallback.EVENT.register( (player, world, hand, hitResult) -> {
				if( world.isClient() )
				{
					System.out.println("PORTALS SERVER: UseEntityCallback");
					portalManager.searchNewPortalFromBlockAndReturnSuccess(player, world, hand, hitResult);
				}

				return ActionResult.PASS;
			});
			
//			UseEntityCallback.EVENT.register( (player, world, hand, entity, hitResult) -> {
//				if( !world.isClient() )
//				{
//					System.out.println("PORTALS: UseEntityCallback");
//				}
//
//				return ActionResult.PASS;
//			});
		});
	}
}
