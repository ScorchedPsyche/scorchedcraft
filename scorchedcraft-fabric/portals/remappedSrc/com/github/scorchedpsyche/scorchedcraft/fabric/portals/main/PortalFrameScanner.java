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

package com.github.scorchedpsyche.scorchedcraft.fabric.portals.main;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.StringFormattedModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.DirectionUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ServerUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.WorldUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.managers.PortalManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.models.PortalModel;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class PortalFrameScanner {
//    public PortalFrameScanner(BlockPos initialBlockPos, Block frameBlock, World world)
//    {
//        this.initialBlockPos = initialBlockPos;
//        this.rangeCorner1 = initialBlockPos.subtract( PortalManager.maximumFrameSideVecLength );
//        this.rangeCorner2 = initialBlockPos.add( PortalManager.maximumFrameSideVecLength );
//
//        portalModel = new PortalModel(frameBlock, world);
//    }
//
//    private final BlockPos initialBlockPos;
////    private final int maxSearchLength = PortalManager.maximumFrameSideLength * 2;
////    private int length = 1;
////    private int height = 1;
////    private int depth = 1;
//    private BlockPos rangeCorner1;
//    private BlockPos rangeCorner2;
//    private BlockPos currentBlockPos;
//    private PortalModel portalModel;
//    private List<BlockPos> scannedBlockPos = new ArrayList<>();
//    private List<BlockPos> validFrameBlockPos = new ArrayList<>();
//    private Queue<BlockPos> newBlocksToScan = new LinkedList<>();
//
//    public List<BlockPos> getValidFrameBlockPos()
//    {
//        return this.validFrameBlockPos;
//    }
//
//    public void scanForPortalFrame()
//    {
//        ServerUtil.sendMessageToAllPlayers( new StringFormattedModel().lightPurple("scanForPortalFrame"));
//        newBlocksToScan.add(initialBlockPos);
//        scanNewBlocks();
//        ServerUtil.sendMessageToAllPlayers( new StringFormattedModel().lightPurple("total ").add(validFrameBlockPos.size()));
//    }
//
//    private boolean scanNewBlocks()
//    {
//        // Get new block to scan from list
//        this.currentBlockPos = newBlocksToScan.poll();
//
//        // Check if it's a valid blockPos and if it hasn't already been scanned
//        if( currentBlockPos != null && !scannedBlockPos.contains(currentBlockPos) )
//        {
//            // Not yet scanned
//            scannedBlockPos.add(currentBlockPos);
////            ServerUtil.sendMessageToAllPlayers(
////                new StringFormattedModel().yellow("scan ").add(currentBlockPos.toString()));
//
//            if( isBlockPosEqualToFrame(currentBlockPos) )
//            {
//                ServerUtil.sendMessageToAllPlayers(
//                    new StringFormattedModel().white("valid ").green(currentBlockPos.toString()));
//                validFrameBlockPos.add(currentBlockPos);
//            }
//            searchNewContinuousEqualBlocksAroundPosition(currentBlockPos);
//            scanNewBlocks();
//        }
//
//        // Finished
//        return true;
//    }
//
//    private void searchNewContinuousEqualBlocksAroundPosition(BlockPos blockPos)
//    {
//        ServerUtil.sendMessageToAllPlayers(
//            new StringFormattedModel().white("searchNewContinuousEqualBlocksAroundPosition ").gray(blockPos.toString()));
//        scanPerpendicularWithHeightOffsetAroundPosition(blockPos, 1);
//        scanPerpendicularWithHeightOffsetAroundPosition(blockPos, 0);
//        scanPerpendicularWithHeightOffsetAroundPosition(blockPos, -1);
//        scanAboveAndBelowPosition(blockPos);
////        scanDiagonalsWithHeightOffsetAroundPosition(blockPos, 0);
//    }
//
//    private void scanPerpendicularWithHeightOffsetAroundPosition(
//        BlockPos blockPos, int heightOffset)
//    {
////        ServerUtil.sendMessageToAllPlayers(
////            new StringFormattedModel().white("scanPerpendicularWithHeightOffsetAroundPosition ").gray(heightOffset));
////        if( depth < maxSearchLength )
////        {
////        }
//        // NORTH
//        compareBlockAndAddToScanList(blockPos.add(0, heightOffset, -1), DirectionUtil.Direction.NORTH);
//
//        // SOUTH
//        compareBlockAndAddToScanList(blockPos.add(0, heightOffset, 1), DirectionUtil.Direction.SOUTH);
//
////        if( length < maxSearchLength )
////        {
////        }
//        // EAST
//        compareBlockAndAddToScanList(blockPos.add(1, heightOffset, 0), DirectionUtil.Direction.EAST);
//
//        // WEST
//        compareBlockAndAddToScanList(blockPos.add(-1, heightOffset, 0), DirectionUtil.Direction.WEST);
//    }
//
//    private void scanAboveAndBelowPosition(
//        BlockPos blockPos)
//    {
////        ServerUtil.sendMessageToAllPlayers(
////            new StringFormattedModel().white("scanAboveAndBelowPosition "));
//        // ABOVE
//        compareBlockAndAddToScanList(blockPos.add(0, 1, 0), Direction.UP);
//
//        // BELOW
//        compareBlockAndAddToScanList(blockPos.add(0, -1, 0), DirectionUtil.Direction.BELOW);
////        if( height < maxSearchLength )
////        {
////        }
//    }
//
//    private void scanDiagonalsWithHeightOffsetAroundPosition(
//        BlockPos blockPos, int heightOffset)
//    {
//        // NORTHEAST
//        compareBlockAndAddToScanList(blockPos.add(1, heightOffset, -1), DirectionUtil.Direction.NORTH_EAST);
//
//        // SOUTHEAST
//        compareBlockAndAddToScanList(blockPos.add(1, heightOffset, 1), DirectionUtil.Direction.SOUTH_EAST);
//
//        // SOUTHWEST
//        compareBlockAndAddToScanList(blockPos.add(-1, heightOffset, 1), DirectionUtil.Direction.SOUTH_WEST);
//
//        // NORTHWEST
//        compareBlockAndAddToScanList(blockPos.add(-1, heightOffset, -1), DirectionUtil.Direction.NORTH_WEST);
////        if( length < maxSearchLength && depth < maxSearchLength  )
////        {
////        }
//    }
//
//    private void compareBlockAndAddToScanList(BlockPos blockPos, Direction direction)
//    {
//        if ( isPositionInsideSearchRange(blockPos) && isBlockPosEqualToFrame(blockPos)
//            && !scannedBlockPos.contains(blockPos) && !newBlocksToScan.contains(blockPos))
////            ServerUtil.sendMessageToAllPlayers(
////                new StringFormattedModel().white("compare ").blue(currentBlockPos.toString()).white(" dir ")
////                    .blue(direction.name()));
//            newBlocksToScan.add(blockPos);
//            ServerUtil.sendMessageToAllPlayers( new StringFormattedModel().blue("added to scan ") );
//    }
//
//    private boolean isBlockPosEqualToFrame(BlockPos blockPos)
//    {
//        return WorldUtil.isBlockFromPositionEqualToBlock(
//            blockPos, this.portalModel.getFrameBlock(), this.portalModel.getWorld());
//    }
//
//    private boolean isPositionInsideSearchRange(BlockPos blockPos)
//    {
//        return blockPos.getX() >= rangeCorner1.getX() && blockPos.getY() >= rangeCorner1.getY() && blockPos.getZ() >= rangeCorner1.getZ()
//            && blockPos.getX() <= rangeCorner2.getX() && blockPos.getY() <= rangeCorner2.getY() && blockPos.getZ() <= rangeCorner2.getZ();
//    }
}
