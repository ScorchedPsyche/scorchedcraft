package com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.mixin;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.WanderingTrades;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOfferList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Mixin(WanderingTraderEntity.class)
public class WanderingTraderMixin  {
	@Inject(at = @At("TAIL"), method = "fillRecipes()V")
	private void init(CallbackInfo info) {
		TradeOfferList trades = ((WanderingTraderEntity)(Object)this).getOffers();
		
		MerchantEntityAccessor merchant = ((MerchantEntityAccessor)(Object)this);
		
		TradeOfferList newTrades = new TradeOfferList();
		
		if( !WanderingTrades.configuration.remove_default_trades )
		{
			newTrades.addAll(trades);
		}
		
		if( WanderingTrades.configuration.whitelist.enable_synchronization )
		{
			newTrades = WanderingTrades.merchantManager.addWhitelistedPlayerHeadsToOffers( newTrades );
		}
		newTrades = WanderingTrades.merchantManager.addItemsToOffers( newTrades );
		newTrades = WanderingTrades.merchantManager.addDecorationHeadsToOffers( newTrades );
		
		merchant.setOffers(newTrades);
	}
}
