package com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.mixin;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
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

@Mixin(WanderingTraderEntity.class)
public class WanderingTraderMixin  {
	@Inject(at = @At("TAIL"), method = "fillRecipes()V")
	private void init(CallbackInfo info) {
//		ConsoleUtil.logMessage("WANDERING TRADER fillRecipes");
//		TradeOfferList trades = ((WanderingTraderEntity)(Object)this).getOffers();
//		trades = new TradeOfferList();
//
//		ConsoleUtil.logMessage("size" + trades.size());
//		for ( TradeOffer offer : trades )
//		{
//			ConsoleUtil.logMessage(" - " + offer.getSellItem().toString());
//		}
		
		TradeOfferList trades = ((WanderingTraderEntity)(Object)this).getOffers();
		ConsoleUtil.logMessage("size: " + trades.size());
		
		MerchantEntityAccessor merchant = ((MerchantEntityAccessor)(Object)this);
		
		TradeOfferList newTrades = new TradeOfferList();
		
		newTrades.add(new TradeOffer(
			new ItemStack(Items.DIAMOND),
			new ItemStack(Items.ELYTRA),
			1,
			0,
			1
			));
		
		merchant.setOffers(newTrades);
		
		TradeOfferList trades2 = ((WanderingTraderEntity)(Object)this).getOffers();
		ConsoleUtil.logMessage("size 2: " + trades.size());
	}
}
