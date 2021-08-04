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

package com.github.scorchedpsyche.scorchedcraft.fabric.core;

import com.github.scorchedpsyche.scorchedcraft.shared.core_shared.Core_Shared;
import net.fabricmc.api.ModInitializer;

public class Core implements ModInitializer {
	@Override
	public void onInitialize() {
		System.out.println("CORE: onInitialize");
		Core_Shared coreShared = new Core_Shared();
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
//		CoreShared coreShared = new CoreShared();
	}
}
