/*
 * Copyright (c) 2022 ScorchedPsyche
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

package com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.main;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.concurrent.CompletableFuture;
//
//public class HeadSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
//    @Override
//    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
//        Identifier entityTypeId = context.getArgument("type", Identifier.class);
//        EntityType<?> entityType = Registry.ENTITY_TYPE.getOrEmpty(entityTypeId).orElse(null);
//
//        if (!DefaultAttributeContainer.builder.(entityType)) {
//            // TODO: Fail
//        }
//
//        DefaultAttributeContainer attributeContainer = DefaultAttributeRegistry.get(entityType);
//        // You will need mixin to get the 'instances map'. Lets assume we can just access it for the sake of the tutorial
//        for (EntityAttribute attribute : attributeContainer.instances().keySet()) {
//            Identifier attributeId = Registry.ATTRIBUTE.getId(attribute);
//            if (attributeId != null) {
//                builder.suggest(attributeId.toString());
//            }
//        }
//
//        return builder.buildFuture();
//    }
//}