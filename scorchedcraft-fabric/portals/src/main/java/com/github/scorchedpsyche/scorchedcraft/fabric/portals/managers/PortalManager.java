package com.github.scorchedpsyche.scorchedcraft.fabric.portals.managers;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.StringFormattedModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ModsScorchedCraft;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.PlayerUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ServerUtil;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PortalManager {
    public static enum Orientation {
        HORIZONTAL,
        VERTICAL
    }
    
    public static enum Direction {
        EAST_WEST,
        NORTH_SOUTH,
        NORTHEAST_SOUTHWEST,
        NORTHWEST_SOUTH_EAST
    }
    
    public boolean detectNewPortalAtReturnSuccess(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult)
    {
        // Player used Flint and Steel?
        if( Item.getRawId(player.getStackInHand(hand).getItem()) == Item.getRawId(Items.FLINT_AND_STEEL) )
        {
            // Used Flint and Steel. Used it on a block?
            if( hitResult.getType() == HitResult.Type.BLOCK )
            {
                // Used on block. Save block and check if a valid frame
                Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
            
                System.out.println("FLINT AND STEEL on " + block.getTranslationKey());
            
                if( isThereAValidFrameAtBlockPositionAtWorld(block, hitResult.getBlockPos(), world) )
                {
                } else {
                    PlayerUtil.sendMessageWithPluginPrefix(player, ModsScorchedCraft.Portals.Name.compact, new StringFormattedModel()
                        .red("No equal neighbor found!") );
                }
            }
        } else {
            System.out.println("NO FLINTA!");
        }
        
        return false;
    }
    
    public boolean isThereAValidFrameAtBlockPositionAtWorld(Block block, BlockPos blockPos, World world)
    {
        ServerUtil.sendMessageToAllPlayers( new StringFormattedModel()
            .yellow(block.getName().getString()).white(" at ").green(blockPos.toString()) );
        
        if( world.testBlockState(blockPos.add(0, 0, 1), neighbor -> neighbor.isOf(block)) )
        {
            ServerUtil.sendMessageToAllPlayers( new StringFormattedModel()
                .aqua(block.getName().getString()).white(" at ").green(blockPos.toString()) );
            return true;
        }
        
        return false;
    }
}
