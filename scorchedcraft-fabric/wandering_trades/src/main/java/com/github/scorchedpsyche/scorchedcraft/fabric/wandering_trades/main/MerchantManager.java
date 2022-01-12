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

import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.StringUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.WanderingTrades;
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.model.TradeEntryModel;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SkullItem;
import net.minecraft.nbt.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MerchantManager {
    public MerchantManager()
    {
        this.setup();
    }
    
    private final List<TradeOffer> decorationHeads = new ArrayList<>();
    private final List<TradeOffer> items = new ArrayList<>();
    private final List<TradeOffer> playerHeads = new ArrayList<>();
    private final List<TradeOffer> playerHeadsWhitelisted = new ArrayList<>();
    
    /***
     * Setup for the Merchant Manager class which preloads user files and creates static lists from them.
     */
    private void setup()
    {
        int totalLoadedSuccessfully = 0;
        
        // Loops through trade files
        for (TradeEntryModel trade : WanderingTrades.tradeListManager.Trades.offers)
        {
            // Check if recipe is valid
            if ( isRecipeValid(trade) )
            {
                // Checks type of item
                if (!trade.getMinecraftId().equalsIgnoreCase("player_head"))
                {
                    // Other Items
                    loadItemRecipe(trade);
                    totalLoadedSuccessfully++;
                } else
                {
                    // Heads (Decoration and Player)
                    if (trade.getOwnerId() == null && trade.getTexture() != null)
                    {
                        // Decoration Heads
                        loadDecorationHeadRecipe(trade);
                        totalLoadedSuccessfully++;
                    } else
                    {
                        // Player Head
//                        addPlayerHeadFromFile( trade );
//                        LoggerCore.Log( "Loaded player heads trades" );
                    }
                }
            }
        }
        ConsoleUtil.logMessage(ScorchedCraftManager.WanderingTrades.Name.full,
            "Loaded a total of " + totalLoadedSuccessfully + " item and decoration head trades" );

////        synchronizeWhitelistedPlayersIfNeeded();
//        // WHITELISTED Player's Heads synchronization
//        if (CraftEraSuiteWanderingTrades.config.getBoolean("whitelist.enable_synchronization")) // TO DO
//        {
////            are_whitelisted_player_heads_synchronized = CraftEraSuiteWanderingTrades.config.getBoolean("whitelist.enable_synchronization");
//            // Check if whitelist is empty
//            if (Bukkit.hasWhitelist() && Bukkit.getWhitelistedPlayers().size() > 0)
//            {
//                // Check if config.yml
//                if ( isConfigYmlMissingWhitelistConfig() )
//                {
//                    // Not empty
//                    for (OfflinePlayer offlinePlayer : Bukkit.getWhitelistedPlayers())
//                    {
//                        loadWhitelistedPlayerHeadRecipe(offlinePlayer);
//                    }
//                    ConsoleUtil.logMessage(SuitePluginManager.WanderingTrades.Name.full,
//                        "Loaded whitelisted player heads trades");
//                }
//            } else
//            {
//                // Empty whitelist
//                ConsoleUtil.logError(SuitePluginManager.WanderingTrades.Name.full,
//                    "Whitelist synchronization is ON (check config.yml) but the whitelist is " +
//                        "empty or doesn't exists");
//            }
//        }
    }
    
    /***
     * Loads item recipe from the files.
     * @param trade Recipe to be loaded and processed
     */
    private void loadItemRecipe(TradeEntryModel trade)
    {
        Item sellItem = Registry.ITEM.get( Identifier.tryParse(trade.getMinecraftId()) );
        Item firstBuyItem = Registry.ITEM.get( Identifier.tryParse(trade.getPriceItem1()) );
    
        // Check if ingredient is valid
        if (trade.getPriceItem2() != null)
        {
            Item secondBuyItem = Registry.ITEM.get( Identifier.tryParse(trade.getPriceItem2()) );
        
            // Ingredient2 valid - add trade with both ingredients
            items.add(new TradeOffer(
                new ItemStack(firstBuyItem, trade.getPrice1()),
                new ItemStack(secondBuyItem, trade.getPrice2()),
                new ItemStack(sellItem, trade.getAmount()),
                trade.getUsesMax(),
                trade.getExperienceReward(),
                trade.getPriceMultiplier()
            ));
        } else
        {
            // No second ingredient - add trade
            items.add(new TradeOffer(
                new ItemStack(firstBuyItem, trade.getPrice1()),
                new ItemStack(sellItem, trade.getAmount()),
                trade.getUsesMax(),
                trade.getExperienceReward(),
                trade.getPriceMultiplier()
            ));
        }
    }
    
    /***
     * Loads decoration head recipe from the files.
     * @param trade Recipe to be loaded and processed
     */
    private void loadDecorationHeadRecipe(TradeEntryModel trade)
    {
        ItemStack decorationHead = createDecorationHead(trade);
    
        // Valid recipe
        Item firstBuyItem = Registry.ITEM.get( Identifier.tryParse(trade.getPriceItem1()) );
    
        // Check if ingredient is valid
        if (trade.getPriceItem2() != null)
        {
            Item secondBuyItem = Registry.ITEM.get( Identifier.tryParse(trade.getPriceItem2()) );
        
            // Ingredient2 valid - add trade with both ingredients
            decorationHeads.add(new TradeOffer(
                new ItemStack(firstBuyItem, trade.getPrice1()),
                new ItemStack(secondBuyItem, trade.getPrice2()),
                decorationHead,
                trade.getUsesMax(),
                trade.getExperienceReward(),
                trade.getPriceMultiplier()
            ));
        } else
        {
            // No second ingredient - add trade
            decorationHeads.add(new TradeOffer(
                new ItemStack(firstBuyItem, trade.getPrice1()),
                decorationHead,
                trade.getUsesMax(),
                trade.getExperienceReward(),
                trade.getPriceMultiplier()
            ));
        }
    }
    
    /***
     * Creates a decoration head Item Stack.
     * @param trade Trade entry from .json file
     * @return Item stack
     */
    private ItemStack createDecorationHead(TradeEntryModel trade)
    {
        UUID randomUUID = UUID.randomUUID();
        GameProfile profile = new GameProfile(randomUUID, trade.getName());
        profile.getProperties().put("textures", new Property("textures", trade.getTexture()));
    
        ItemStack decorationHead = new ItemStack( Items.PLAYER_HEAD, trade.getAmount());
        NbtCompound nbt = decorationHead.getOrCreateNbt();
        nbt.put("SkullOwner", NbtHelper.writeGameProfile(new NbtCompound(), profile) );
        
        return decorationHead;
    }
    
    /***
     * Adds decoration heads to Wandering Trader's offers.
     */
    public TradeOfferList addDecorationHeadsToOffers(TradeOfferList tradeOffers)
    {
        if( !decorationHeads.isEmpty() )
        {
            Collections.shuffle( decorationHeads );
            
            int nbrOfTradesToAdd = decorationHeads.size();
            
            if( nbrOfTradesToAdd > WanderingTrades.configuration.maximum_unique_trade_offers.decoration_heads )
            {
                nbrOfTradesToAdd = WanderingTrades.configuration.maximum_unique_trade_offers.decoration_heads;
            }
            
            for (int i = 0; i < nbrOfTradesToAdd; i++)
            {
                tradeOffers.add( decorationHeads.get(i) );
            }
        }
        
        return tradeOffers;
    }
    
    /***
     * Checks if the recipe from the trade list is valid.
     * @param trade Recipe from the trade list
     * @return True if recipe is valid.
     */
    private boolean isRecipeValid( TradeEntryModel trade )
    {
        boolean isValid = true;
        Identifier identifier;
        
        // Check if material was provided
        if( trade.getMinecraftId() == null )
        {
            // Material wasn't provided
            isValid = false;
            ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.full,
                "Missing 'minecraft_id' for a trade offer" );
        } else {
            identifier = Identifier.tryParse(trade.getMinecraftId());
            
            if( identifier != null )
            {
//                item = Registry.ITEM.get( identifier );
    
                // Check if decoration head either texture or owner
                if( trade.getMinecraftId().equalsIgnoreCase("player_head") &&
                    (   StringUtil.isNullOrEmpty(trade.getOwnerId()) &&
                        StringUtil.isNullOrEmpty(trade.getTexture()) ) )
                {
                    // Missing both
                    isValid = false;
                    ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.full,
                        "On: " + trade.getMinecraftId() + ". You are 'getOwnerId' and 'getTexture'. " +
                            "Item was not added" );
                }
            } else {
                // Item invalid
                isValid = false;
                ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.full,
                    "Invalid 'minecraft_id': " + trade.getMinecraftId() + ". Item was not added" );
            }
        }
        
        // Check if ingredient 1 was provided
        if( trade.getPriceItem1() == null )
        {
            // Ingredient 1 wasn't provided
            isValid = false;
            ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.full,
                "Missing 'price_item1' for: " + trade.getMinecraftId() );
        } else {
            identifier = Identifier.tryParse(trade.getPriceItem1());
    
            if( identifier == null )
            {
                // Ingredient 1 invalid
                isValid = false;
                ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.full,
                    "Invalid 'price_item1' for: " + trade.getPriceItem1() + ". Item was not added" );
            }
        }
        
        // Check if Ingredient 2 or it's price are missing when one is provided
        if( (trade.getPrice2() == null && trade.getPriceItem2() != null) || (trade.getPrice2() != null && trade.getPriceItem2() == null) )
        {
            // One is missing
            isValid = false;
            ConsoleUtil.logError(ScorchedCraftManager.WanderingTrades.Name.full,
                "On: " + trade.getMinecraftId() + ". You are missing either 'price_item2' or " +
                    "'price2'. " +
                    "Item was not added" );
        }
        
        return isValid;
    }
}
