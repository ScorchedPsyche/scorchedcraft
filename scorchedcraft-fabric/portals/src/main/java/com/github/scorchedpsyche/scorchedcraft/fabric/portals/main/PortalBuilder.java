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

import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.renderers.DebugRenderer;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.RGBAModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.StringFormattedModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ServerUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.managers.PortalManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.models.PortalBlockModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.models.PortalModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.models.PortalSliceModel;
import net.minecraft.block.Block;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.*;

public class PortalBuilder {
    public PortalBuilder(BlockHitResult hitResult, World world)
    {
        this.initialFrameBlockPos = hitResult.getBlockPos();
        this.initialPortalBlockPos = hitResult.getBlockPos().add(hitResult.getSide().getVector());
        this.frameBlock = world.getBlockState(initialFrameBlockPos).getBlock();
        this.initialDirection = hitResult.getSide();
        this.world = world;
//        this.blocksToScan.add( new PortalBlockModel( hitResult.getBlockPos().add(hitResult.getSide().getVector()) ) );
        this.findValidFrames();
    }
    
//    private PortalBlockModel initialFrameBlock;
    private BlockPos initialFrameBlockPos;
    private PortalBlockModel initialPortalBlock;
    private BlockPos initialPortalBlockPos;
    private PortalSliceModel initialPortalSlice;
    private Block frameBlock;
    private Direction initialDirection;
    private World world;
    private Queue<PortalBlockModel> blocksToScan = new LinkedList<>();
    private Map<BlockPos, PortalBlockModel> visitedBlocks = new HashMap<>();
    private Map<BlockPos, PortalBlockModel> validPortalBlocks = new HashMap<>();
    private List<BlockPos> invalidPortalBlocks = new ArrayList<>();
    private List<BlockPos> detectedFramePortals = new ArrayList<>();
    
    private void findValidFrames()
    {
        DebugRenderer.blocksOnlyOutline.clear();
        DebugRenderer.blocksOnlyFaces.clear();
    
//        this.initialFrameBlock = new PortalBlockModel(initialFrameBlockPos);
        this.initialPortalBlock = new PortalBlockModel(initialPortalBlockPos);
        this.initialPortalSlice = new PortalSliceModel();
        
        this.configureInitialPortalBlockAndFrame();
        this.resolveAxisFromPortalBlock(Direction.Axis.Y, this.initialPortalBlock, 1);
        this.resolveAxisFromPortalBlock(Direction.Axis.X, this.initialPortalBlock, 1);
        this.resolveAxisFromPortalBlock(Direction.Axis.Z, this.initialPortalBlock, 1);
    
        PortalModel portal = new PortalModel(this.initialPortalBlock);
//        portal.findMostComplexPossibleShapeFromPortalBlock(initialPortalBlock);
        this.debugPrintDirectionValidity(Direction.UP, this.initialPortalBlock);
        this.debugPrintDirectionValidity(Direction.DOWN, this.initialPortalBlock);
        this.debugPrintDirectionValidity(Direction.EAST, this.initialPortalBlock);
        this.debugPrintDirectionValidity(Direction.WEST, this.initialPortalBlock);
        this.debugPrintDirectionValidity(Direction.NORTH, this.initialPortalBlock);
        this.debugPrintDirectionValidity(Direction.SOUTH, this.initialPortalBlock);
        
        if( this.initialPortalBlock.getConnections().getNumberOfValidConnections() >= 3 )
        {
        
//            if( portal.attemptToReduceShapeAndReturnIfOnlyOnePossible(initialPortalBlock) )
//            {
//                ServerUtil.sendMessageToAllPlayers( new StringFormattedModel().green("FINAL: ") );
//            } else {
//                ServerUtil.sendMessageToAllPlayers( new StringFormattedModel().yellow("POSSIBLE: ") );
//            }
//
//            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().gray("shape: ").add( portal.getShape().toString() ));
//            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().gray("orientation: ").add( portal.getOrientation().toString() ));
//            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().gray("facing: ").add( portal.getFacing().toString() ));
        
        } else {
            ServerUtil.sendMessageToAllPlayers( new StringFormattedModel().red("INVALID PORTAL") );
        }
        
        updateDebug();
    }
    
    /**
     * Sets initial portal block as AIR and the opposite direction of the right-clicked side as a FRAME block.
     */
    private void configureInitialPortalBlockAndFrame()
    {
        this.initialPortalBlock.setAsAir();
        this.visitedBlocks.put(initialPortalBlockPos, initialPortalBlock);
    }
    
    private void validateAxisOnDirectionFromPortalBlock(Direction.Axis axis, Direction direction,
        PortalBlockModel currentPortalBlock, int currentSearchLength)
    {
    
    }
    
    private void resolveAxisFromPortalBlock(Direction.Axis axis, PortalBlockModel currentPortalBlock, int currentSearchLength)
    {
        switch( axis )
        {
            case Y:
                this.resolveDirectionFromPortalBlock( currentPortalBlock, Direction.DOWN, currentSearchLength);
                if( currentPortalBlock.getConnections().getDistanceToFrameForDirection(Direction.DOWN) > -1 )
                {
                    this.resolveDirectionFromPortalBlock( currentPortalBlock, Direction.UP,
                        1 + currentPortalBlock.getConnections().getDistanceToFrameForDirection(Direction.DOWN));
                } else {
                    this.resolveDirectionFromPortalBlock( currentPortalBlock, Direction.UP, currentSearchLength);
                }
                break;
                
            case X:
                this.resolveDirectionFromPortalBlock( currentPortalBlock, Direction.EAST, currentSearchLength);
                if( currentPortalBlock.getConnections().getDistanceToFrameForDirection(Direction.EAST) > -1 )
                {
                    this.resolveDirectionFromPortalBlock( currentPortalBlock, Direction.WEST,
                        1 + currentPortalBlock.getConnections().getDistanceToFrameForDirection(Direction.EAST));
                } else {
                    this.resolveDirectionFromPortalBlock( currentPortalBlock, Direction.WEST, currentSearchLength);
                }
                break;
                
            default:
                this.resolveDirectionFromPortalBlock( currentPortalBlock, Direction.NORTH, currentSearchLength);
                if( currentPortalBlock.getConnections().getDistanceToFrameForDirection(Direction.NORTH) > -1 )
                {
                    this.resolveDirectionFromPortalBlock( currentPortalBlock, Direction.SOUTH,
                        1 + currentPortalBlock.getConnections().getDistanceToFrameForDirection(Direction.SOUTH));
                } else {
                    this.resolveDirectionFromPortalBlock( currentPortalBlock, Direction.SOUTH, currentSearchLength);
                }
                break;
        }
        
//        if( currentPortalBlock.isDirectionValid(Direction.DOWN) && currentPortalBlock.isDirectionValid(Direction.UP) )
//        {
//            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
//                .green("DOWN/UP from ").add(currentPortalBlock.getPos().toShortString())
//                .add(" is valid"));
//        } else if( currentPortalBlock.isDirectionValid(Direction.DOWN) ) {
//            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
//                .green("DOWN from ").add(currentPortalBlock.getPos().toShortString())
//                .add(" is valid"));
//        } else if( currentPortalBlock.isDirectionValid(Direction.UP) ) {
//            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
//                .green("UP from ").add(currentPortalBlock.getPos().toShortString())
//                .add(" is valid"));
//        } else {
//            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
//                .red("DOWN/UP from ").add(currentPortalBlock.getPos().toShortString())
//                .add(" is invalid"));
//        }
        
        if( currentPortalBlock.getConnections().getNumberOfValidConnections() >= 3 )
        {
            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
                .green("Possible portal from ").add(currentPortalBlock.getPos().toShortString())
                .add(" with ").aqua(currentPortalBlock.getConnections().getNumberOfValidConnections())
                .green(" valid connections"));
        } else {
            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
                .red("Impossible portal from ").add(currentPortalBlock.getPos().toShortString())
                .add(" with ").aqua(currentPortalBlock.getConnections().getNumberOfValidConnections())
                .red(" valid connections"));
        }
        
        
    }
    
    private PortalBlockModel resolveDirectionFromPortalBlock(PortalBlockModel currentPortalBlock, Direction direction,
        int currentSearchLength)
    {
        ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
            .gold("current ").add( currentPortalBlock.getPos().toString() ));
        PortalBlockModel directionPortalBlock;
        
        // Check if current block's direction was visited
        if( currentPortalBlock.wasDirectionChecked(direction) )
        {
            //  Current portal block's direction visited!
            return currentPortalBlock;
        } else {
            // Current portal block's direction wasn't visited. Calculate direction block's position based on direction
            BlockPos directionBlockPos = currentPortalBlock.getPos().add(direction.getVector());
    
            // Attempt to get direction portal block from visited list
            directionPortalBlock = this.visitedBlocks.get(directionBlockPos);
    
            // Check if direction portal block was visited
            if( directionPortalBlock == null )
            {
                // Block wasn't yet visited! Initialize new Portal Block with the next block's position and add to visited
                directionPortalBlock = new PortalBlockModel(directionBlockPos);
                this.visitedBlocks.put(directionBlockPos, directionPortalBlock);
    
                // Resolve block's type based on its validity for this portal. If of different type than frame, it's invalidated
                directionPortalBlock.resolveTypeOrInvalidateIfNeeded(frameBlock, world);
                
//                // If it's FRAME set distance to it
//                if ( directionPortalBlock.getType() == PortalBlockModel.Type.AIR )
//                {
//                    // AIR! Must check length
//                    if( currentSearchLength >= PortalManager.maximumPortalLength)
//                    {
//                        // Search length overflow. Mark as invalid to stop the search
//                        directionPortalBlock.setAsInvalid();
//                    }
//                }
            }
        }
        ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
            .gray(" -- direction: ").add( directionPortalBlock.getPos().toString() ));
    
        // When we get here the direction portal block's type is already resolved, so we must now decide if we should
        // continue on same direction or go to the opposite one
        switch ( directionPortalBlock.getType() )
        {
            case INVALID:
                // If direction portal block's type is INVALID, just set this direction as invalid since it didn't find a frame
                currentPortalBlock.setDirectionAsInvalid(direction);
                break;
    
            case FRAME:
                // If direction portal block's type is FRAME, set direction as valid and set distance to frame 0
                // for current block (since direction is a frame)
                currentPortalBlock.setDirectionAsValid(direction, 0);
                ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
                    .gray(" -- frame: ").add( directionPortalBlock.getPos().toString() ));
                break;
    
            case AIR:
                // If direction portal block is AIR, then we must check if maximum search length was reached
                if( currentSearchLength < PortalManager.maximumPortalLength )
                {
                    ServerUtil.sendMessageToAllPlayers("1");
                    // Search length not yet reached. We must check the same direction for the block after the one
                    // after the one at the direction of the current block
                    ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
                        .gray(" -- current: ").add( currentPortalBlock.getPos().toString() ));
                    ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
                        .gray(" -- direction: ").add( directionPortalBlock.getPos().toString() ));
                    this.resolveDirectionFromPortalBlock(directionPortalBlock,
                        direction, ++currentSearchLength);
                    ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
                        .gray(" -- direction: ").add( directionPortalBlock.getPos().toString() ));
                } else {
                    // If length was reached then the direction is invalid
                    directionPortalBlock.setDirectionAsInvalid(direction);
                    ServerUtil.sendMessageToAllPlayers("2");
                }
                ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
                    .gray(" -- direction dir valid: ").add( directionPortalBlock.isDirectionValid(direction) ));
                
                // Cascade down to current portal block the connection validity from the ones at direction
                currentPortalBlock.evaluateDirectionFromAnotherPortalBlock( directionPortalBlock, direction );
                break;
    
            case PORTAL:
                ServerUtil.sendMessageToAllPlayers("portal block 2 " + directionPortalBlock.getPos());
                break;
                
            default:
                ServerUtil.sendMessageToAllPlayers("unchecked " + directionPortalBlock.getType() + " " + directionPortalBlock.getPos());
                break;
        }
    
        ServerUtil.sendMessageToAllPlayers("current " + currentPortalBlock.getPos().toShortString() + " dist frame "
            + currentPortalBlock.getDistanceToFrameForDirection(direction));
        ServerUtil.sendMessageToAllPlayers("direction " + directionPortalBlock.getPos().toShortString() + " dist frame "
            + directionPortalBlock.getDistanceToFrameForDirection(direction));
        
        // When we get here current block's direction connection is already configured according to the direction validity
        if( currentPortalBlock.isDirectionValid(direction) )
        {
            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
                .green(direction.toString()).add(" from ").add(currentPortalBlock.getPos().toShortString())
                .add(" is valid"));
        } else {
            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
                .red(direction.toString()).add(" from ").add(currentPortalBlock.getPos().toShortString())
                .add(" is invalid"));
        }
    
//        PortalBlockModel oppositePortalBlock;
        
        
        
        
        return currentPortalBlock;
    }
    
    private void updateDebug()
    {
        int invalid = 0;
        int frame = 0;
        int portal = 0;
        int air = 0;
        int unchecked = 0;
        int unknown = 0;
        
        for( Map.Entry<BlockPos, PortalBlockModel> entry : visitedBlocks.entrySet() )
        {
            switch (entry.getValue().getType()) {
                case INVALID -> {
                    DebugRenderer.blocksOnlyFaces.putIfAbsent(entry.getKey(), new RGBAModel(255, 0, 0, 0.1F));
                    invalid++;
                }
                case FRAME -> {
                    DebugRenderer.blocksOnlyFaces.putIfAbsent(entry.getKey(), new RGBAModel(0, 0, 255, 0.1F));
                    frame++;
                }
                case PORTAL -> {
                    DebugRenderer.blocksOnlyFaces.putIfAbsent(entry.getKey(), new RGBAModel(255, 0, 255, 0.1F));
                    portal++;
                }
                case AIR -> {
                    DebugRenderer.blocksOnlyFaces.putIfAbsent(entry.getKey(), new RGBAModel(0, 255, 255, 0.2F));
                    air++;
                }
                case UNCHECKED -> {
                    DebugRenderer.blocksOnlyFaces.putIfAbsent(entry.getKey(), new RGBAModel(0, 255, 0, 0.1F));
                    unchecked++;
                }
                default -> {
                    DebugRenderer.blocksOnlyFaces.putIfAbsent(entry.getKey(), new RGBAModel(255, 255, 0, 0.05F));
                    unknown++;
                }
            }
        }
        
        ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
            .white("total visited blocks ").add(visitedBlocks.size()).white(" - ")
            .red("INVALID: ").add(invalid).white(" - ")
            .blue("FRAME: ").add(frame).white(" - ")
            .lightPurple("PORTAL: ").add(portal).white(" - ")
            .aqua("AIR: ").add(air).white(" - ")
            .green("UNCHECKED: ").add(unchecked).white(" - ")
            .yellow("unknown: ").add(unknown)
        );
        
//        ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().lightPurple("detectedFramePortals ").add(detectedFramePortals.size()));
//        ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().red("invalid ").add(invalidPortalBlocks.size()));
//        ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().blue("validPortalBlocks ").add(validPortalBlocks.size()));
//        ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().yellow("visitedBlocks ").add(visitedBlocks.size()));
        
//        invalidPortalBlocks.forEach(frameBlockPos ->
//            DebugRenderer.blocksOnlyFaces.putIfAbsent( frameBlockPos, new RGBAModel( 255, 0, 0, 0.1F ))  );
//
//        detectedFramePortals.forEach(frameBlockPos ->
//            DebugRenderer.blocksOnlyFaces.putIfAbsent( frameBlockPos, new RGBAModel( 0, 0, 255, 0.1F ))  );
//
//        validPortalBlocks.forEach( (key, value) ->
//            DebugRenderer.blocksOnlyFaces.putIfAbsent( key, new RGBAModel( 255, 0, 255, 0.1F ))  );
//
//        for( Map.Entry<BlockPos, PortalBlockModel> entry : visitedBlocks.entrySet() )
//        {
//            DebugRenderer.blocksOnlyFaces.putIfAbsent( entry.getKey(), new RGBAModel( 255, 255, 0, 0.05F ) );
////            if( !DebugRenderer.blocksOnlyOutline.containsKey(entry.getKey()) )
////            {
////                DebugRenderer.blocksOnlyFaces.put( entry.getKey(), new RGBAModel( 255, 255, 0, 0.05F ) );
////            }
//        }
    }
    
    private void debugPrintDirectionValidity(Direction direction, PortalBlockModel portalBlock)
    {
        if(portalBlock.isDirectionValid(direction) )
        {
            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
                .green(direction.asString()).add(" valid")
            );
        } else {
            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
                .red(direction.asString()).add(" invalid")
            );
        }
    }
}
