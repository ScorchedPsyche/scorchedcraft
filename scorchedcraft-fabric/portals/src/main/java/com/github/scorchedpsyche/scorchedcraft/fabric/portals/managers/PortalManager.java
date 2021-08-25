/*
 * Copyright (c) 2021. ScorchedPsyche
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

package com.github.scorchedpsyche.scorchedcraft.fabric.portals.managers;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.WorldUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.main.PortalBuilder;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class PortalManager {
    public enum Orientation {
        HORIZONTAL,
        VERTICAL,
        CURVED
    }
//
//    public enum Direction {
//        EAST_WEST,
//        NORTH_SOUTH,
//        NORTHEAST_SOUTHWEST,
//        NORTHWEST_SOUTH_EAST
//    }
    
    public enum State {
        UNCHECKED,
        PARTIALLY_CHECKED,
        INVALID,
        VALID,
        AIR,
        PORTAL,
        FRAME
    }
    
    public static final int maximumFrameSideLength = 23;
    public static final Vec3i maximumFrameSideVecLength = new Vec3i(23, 23, 23);
    public static final int maximumPortalLength = 21;
    public static final int maximumSearchLength = 20;
    public static final int maximumFrameSize = maximumFrameSideLength * 4;
    
    public boolean searchNewPortalFromBlockAndReturnSuccess(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult)
    {
        // Player used Flint and Steel?
        if( Item.getRawId(player.getStackInHand(hand).getItem()) == Item.getRawId(Items.FLINT_AND_STEEL) )
        {
            // Used Flint and Steel. Used it on a block and air is on the adjacent hit face of that block?
            if( hitResult.getType() == HitResult.Type.BLOCK && WorldUtil.isBlockFaceAirFromHitResult(hitResult, world) )
            {
                // Used on block. Save block and check if a valid frame
                Block block = world.getBlockState(hitResult.getBlockPos()).getBlock();
            
                System.out.println("FLINT AND STEEL on " + block.getTranslationKey());
    
//                PortalFrameScanner portalFrameScanner = new PortalFrameScanner(hitResult.getBlockPos(), block, world);
//                portalFrameScanner.scanForPortalFrame();
                
                PortalBuilder portalBuilder = new PortalBuilder(hitResult, world);
                
                
//                PortalModel portal = new PortalModel(block);
//                portal.addFrameBlock(new PortalFrameBlockModel(hitResult.getBlockPos(), 0));
////                if( isThereAValidFrameAtBlockPositionAtWorld(block, hitResult.getBlockPos(), world) )
//                if( searchPortalFromBlockPosition(hitResult.getBlockPos(), world, portal) )
//                {
//                    PlayerUtil.sendMessageWithPluginPrefix(player, ModsScorchedCraft.Portals.Name.compact, new StringFormattedModel()
//                        .blue("Valid portal!") );
//                } else {
//                    PlayerUtil.sendMessageWithPluginPrefix(player, ModsScorchedCraft.Portals.Name.compact, new StringFormattedModel()
//                        .red("No valid portal!") );
//                }
            }
        }
        
        return false;
    }
    
//    private boolean searchPortalFromBlockPosition(BlockPos blockPos, World world, PortalModel portal)
//    {
//        searchNext(blockPos, blockPos.add(1, 0, 0), world, portal);
//
//        return false;
//    }
    
//    private boolean searchNext(BlockPos initialBlockPos, BlockPos currentBlockPos, World world, PortalModel portal)
//    {
//        debugMessage("searchNext");
//
//        // ABOVE - EAST
//        if( WorldUtil.isBlockFromPositionEqualToBlock(currentBlockPos.add(1, 1, 0), portal.getFrameBlock(), world) )
//        {
//            debugMessage("A EAST: " + currentBlockPos);
//            debugMessage("A EAST: " + currentBlockPos.add(1, 1, 0));
//            if ( searchNext(initialBlockPos, currentBlockPos.add(1, 1, 0), world, portal) )
//            {
//                return true;
//            }
//        }
//        // ABOVE - WEST
//        else if ( WorldUtil.isBlockFromPositionEqualToBlock(currentBlockPos.add(-1, 1, 0), portal.getFrameBlock(), world) )
//        {
//            debugMessage("A WEST: " + currentBlockPos);
//            debugMessage("A WEST: " + currentBlockPos.add(-1, 1, 0));
//            if( searchNext(initialBlockPos, currentBlockPos.add(-1, 1, 0), world, portal) )
//            {
//                return true;
//            }
//        }
//        // BELOW - EAST
//        else if ( WorldUtil.isBlockFromPositionEqualToBlock(currentBlockPos.add(1, -1, 0), portal.getFrameBlock(), world) )
//        {
//            debugMessage("B EAST: " + currentBlockPos);
//            debugMessage("B EAST: " + currentBlockPos.add(1, -1, 0));
//            if( searchNext(initialBlockPos, currentBlockPos.add(1, -1, 0), world, portal) )
//            {
//                return true;
//            }
//        }
//        // BELOW - WEST
//        else if ( WorldUtil.isBlockFromPositionEqualToBlock(currentBlockPos.add(-1, -1, 0), portal.getFrameBlock(), world) )
//        {
//            debugMessage("B WEST: " + currentBlockPos);
//            debugMessage("B WEST: " + currentBlockPos.add(-1, -1, 0));
//            if( searchNext(initialBlockPos, currentBlockPos.add(-1, -1, 0), world, portal) )
//            {
//                return true;
//            }
//        }
//
////        if( currentBlockPos.equals(initialBlockPos) )
////        {
////            debugMessage("finished");
////            // Search finished. If frame size >= 4, then portal is valid.
////            return portal.getFrameSize() >= 4;
////        } else {
////            debugMessage("not finished");
////            searchNextVertical(initialBlockPos, currentBlockPos, world, portal);
////        }
//
//        return false;
//    }
    
//    private boolean searchNextVertical(BlockPos initialBlockPos, BlockPos currentBlockPos, World world, PortalModel portal)
//    {
//        debugMessage("searchNextVertical: " + currentBlockPos);
//        debugMessage("searchNextVertical - next: " + currentBlockPos.add(1, 1, 0));
//        // ABOVE - EAST
//        if( WorldUtil.isBlockFromPositionEqualToBlock(currentBlockPos.add(1, 1, 0), portal.getFrameBlock(), world) )
//        {
//            debugMessage("A EAST: " + currentBlockPos);
//            debugMessage("A EAST: " + currentBlockPos.add(1, 1, 0));
//            if ( searchNextVertical(initialBlockPos, currentBlockPos.add(1, 1, 0), world, portal) )
//            {
//                return true;
//            }
//        }
//        // ABOVE - WEST
//        else if ( WorldUtil.isBlockFromPositionEqualToBlock(currentBlockPos.add(-1, 1, 0), portal.getFrameBlock(), world) )
//        {
//            debugMessage("A WEST: " + currentBlockPos);
//            debugMessage("A WEST: " + currentBlockPos.add(-1, 1, 0));
//            if( searchNextVertical(initialBlockPos, currentBlockPos.add(-1, 1, 0), world, portal) )
//            {
//                return true;
//            }
//        }
//        // BELOW - EAST
//        else if ( WorldUtil.isBlockFromPositionEqualToBlock(currentBlockPos.add(1, -1, 0), portal.getFrameBlock(), world) )
//        {
//            debugMessage("B EAST: " + currentBlockPos);
//            debugMessage("B EAST: " + currentBlockPos.add(1, -1, 0));
//            if( searchNextVertical(initialBlockPos, currentBlockPos.add(1, -1, 0), world, portal) )
//            {
//                return true;
//            }
//        }
//        // BELOW - WEST
//        else if ( WorldUtil.isBlockFromPositionEqualToBlock(currentBlockPos.add(-1, -1, 0), portal.getFrameBlock(), world) )
//        {
//            debugMessage("B WEST: " + currentBlockPos);
//            debugMessage("B WEST: " + currentBlockPos.add(-1, -1, 0));
//            if( searchNextVertical(initialBlockPos, currentBlockPos.add(-1, -1, 0), world, portal) )
//            {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    private BlockPos searchEastWithHeightOffset(BlockPos currentBlockPos, int offset, World world, PortalModel portal)
//    {
//        return WorldUtil.returnBlockPositionIfEqualToBlock(currentBlockPos.add(1, offset, 0), portal.getFrameBlock(), world);
//    }
//
//    private boolean searchWestWithHeightOffset(BlockPos currentBlockPos, int offset, World world, PortalModel portal)
//    {
//        return WorldUtil.isBlockFromPositionEqualToBlock(currentBlockPos.add(-1, offset, 0), portal.getFrameBlock(), world);
//    }
//
//    private boolean searchSouthWithHeightOffset(BlockPos currentBlockPos, int offset, World world, PortalModel portal)
//    {
//        return WorldUtil.isBlockFromPositionEqualToBlock(currentBlockPos.add(0, offset, 1), portal.getFrameBlock(), world);
//    }
//
//    private boolean searchNorthWithHeightOffset(BlockPos currentBlockPos, int offset, World world, PortalModel portal)
//    {
//        return WorldUtil.isBlockFromPositionEqualToBlock(currentBlockPos.add(0, offset, -1), portal.getFrameBlock(), world);
//    }
//
//    private boolean searchAdjacentUntilReturnToSourceBlock(BlockPos startingBlockPos, BlockPos currentBlockPos,
//        World world, PortalModel portal)
//    {
//        if( startingBlockPos.equals(currentBlockPos) )
//        {
//            return portal.getFrameSize() >= 4;
//        } else {
//            if (portal.getWidth() < maximumFrameSideLength && portal.getHeight() < maximumFrameSideLength) {
//                // ABOVE-EAST
//                BlockPos tmpBlockPos = currentBlockPos.add(1, 1, 0);
//                if (world.testBlockState(tmpBlockPos, neighbor -> neighbor.isOf(portal.getFrameBlock()))) {
//                    ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().gold("[ABOVE-EAST]").aqua(portal.getFrameBlock().getName().getString()).white(" at ").green(currentBlockPos.toString()));
//                    portal.addFrameBlock(new PortalFrameBlockModel(currentBlockPos, MathUtil.distanceBetween3dCoordinates(
//                        currentBlockPos.getX(), currentBlockPos.getY(), currentBlockPos.getZ(), tmpBlockPos.getX(), tmpBlockPos.getX(), tmpBlockPos.getX()
//                    )));
//                    searchAdjacentUntilReturnToSourceBlock(startingBlockPos, tmpBlockPos, world, portal);
//                }
//            }
//        }
//
//        return false;
//    }
//
//    public boolean isThereAValidFrameAtBlockPositionAtWorld(Block block, BlockPos blockPos, World world)
//    {
//        PortalModel portal = new PortalModel(block, world);
//        portal.addFrameBlock(new PortalFrameBlockModel(blockPos, 0));
//
//        return searchNextAdjacentFrameBlockFromPosition(blockPos, block, blockPos, world, portal);
//
////        if( world.testBlockState(blockPos.add(0, 0, 1), neighbor -> neighbor.isOf(block)) )
////        {
////            ServerUtil.sendMessageToAllPlayers( new StringFormattedModel()
////                .aqua(block.getName().getString()).white(" at ").green(blockPos.toString()) );
////            return true;
////        }
//    }
//
//    public boolean searchNextAdjacentFrameBlockFromPosition(BlockPos startingBlockPos, Block block, BlockPos blockPos,
//        World world, PortalModel portal)
//    {
//        // Test all possible directions
//        if( startingBlockPos.equals(blockPos) && portal.getFrameSize() != 1 )
//        {
//            return portal.getFrameSize() >= 4;
//        } else {
//            if (portal.getWidth() < maximumFrameSideLength && portal.getHeight() < maximumFrameSideLength)
//            {
//                // ABOVE-EAST
//                BlockPos tmpBlockPos = blockPos.add(1, 1, 0);
//                if (world.testBlockState(tmpBlockPos, neighbor -> neighbor.isOf(block)))
//                {
//                    ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().gold("[ABOVE-EAST]").aqua(block.getName().getString()).white(" at ").green(blockPos.toString()));
//                    portal.addFrameBlock(new PortalFrameBlockModel(blockPos, MathUtil.distanceBetween3dCoordinates(
//                        blockPos.getX(), blockPos.getY(), blockPos.getZ(), tmpBlockPos.getX(), tmpBlockPos.getX(), tmpBlockPos.getX()
//                        )));
//                    searchNextAdjacentFrameBlockFromPosition(startingBlockPos, block, tmpBlockPos, world, portal);
//                }
//                // ABOVE-WEST
//                tmpBlockPos = blockPos.add(-1, 1, 0);
//                if (world.testBlockState(tmpBlockPos, neighbor -> neighbor.isOf(block)))
//                {
//                    ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().gold("[ABOVE-WEST]").aqua(block.getName().getString()).white(" at ").green(blockPos.toString()));
//                    portal.addFrameBlock(new PortalFrameBlockModel(blockPos, MathUtil.distanceBetween3dCoordinates(
//                        blockPos.getX(), blockPos.getY(), blockPos.getZ(), tmpBlockPos.getX(), tmpBlockPos.getX(), tmpBlockPos.getX()
//                    )));
//                    searchNextAdjacentFrameBlockFromPosition(startingBlockPos, block, tmpBlockPos, world, portal);
//                }
//                // BELOW-WEST
//                tmpBlockPos = blockPos.add(-1, -1, 0);
//                if (world.testBlockState(tmpBlockPos, neighbor -> neighbor.isOf(block)))
//                {
//                    ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().gold("[BELOW-WEST]").aqua(block.getName().getString()).white(" at ").green(blockPos.toString()));
//                    portal.addFrameBlock(new PortalFrameBlockModel(blockPos, MathUtil.distanceBetween3dCoordinates(
//                        blockPos.getX(), blockPos.getY(), blockPos.getZ(), tmpBlockPos.getX(), tmpBlockPos.getX(), tmpBlockPos.getX()
//                        )));
//                    searchNextAdjacentFrameBlockFromPosition(startingBlockPos, block, tmpBlockPos, world, portal);
//                }
//                // BELOW-EAST
//                tmpBlockPos = blockPos.add(1, -1, 0);
//                if (world.testBlockState(tmpBlockPos, neighbor -> neighbor.isOf(block)))
//                {
//                    ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().gold("[BELOW-EAST]").aqua(block.getName().getString()).white(" at ").green(blockPos.toString()));
//                    portal.addFrameBlock(new PortalFrameBlockModel(blockPos, MathUtil.distanceBetween3dCoordinates(
//                        blockPos.getX(), blockPos.getY(), blockPos.getZ(), tmpBlockPos.getX(), tmpBlockPos.getX(), tmpBlockPos.getX()
//                        )));
//                    searchNextAdjacentFrameBlockFromPosition(startingBlockPos, block, tmpBlockPos, world, portal);
//                }
//            }
//        }
//
//        return false;
//    }
}
