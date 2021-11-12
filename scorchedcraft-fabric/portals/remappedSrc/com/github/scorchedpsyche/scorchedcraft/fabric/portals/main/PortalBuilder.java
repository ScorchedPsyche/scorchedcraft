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

import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.renderer.DebugRenderer;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.ColorModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.StringFormattedModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.render.BlockRenderModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.render.ElementRenderModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.render.TextRenderModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.MinecraftColors;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ServerUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.managers.PortalManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.models.PortalBlockModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.models.PortalModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.models.PortalSliceModel;
import net.minecraft.block.Block;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

import java.util.*;

public class PortalBuilder {
    private final ColorModel color_AQUA = MinecraftColors.aqua.setAlpha(0.4F);
    private final ColorModel color_BLACK = MinecraftColors.black.setAlpha(0.4F);
    private final ColorModel color_BLUE = MinecraftColors.blue.setAlpha(0.4F);
    private final ColorModel color_DARK_RED = MinecraftColors.dark_red.setAlpha(0.66F);
    private final ColorModel color_GREEN = MinecraftColors.green.setAlpha(0.4F);
    private final ColorModel color_LIGHT_PURPLE = MinecraftColors.light_purple.setAlpha(0.4F);
    private final ColorModel color_RED = MinecraftColors.red.setAlpha(0.4F);
    private final ColorModel color_WHITE = MinecraftColors.white.setAlpha(0.4F);
    private final ColorModel color_YELLOW = MinecraftColors.yellow.setAlpha(0.4F);
    
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
        DebugRenderer.reset();
    
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
                    DebugRenderer.addBlock(entry.getKey(), new BlockRenderModel( entry.getKey(), this.color_DARK_RED )
                        .setRenderType(ElementRenderModel.RenderType.THROUGH_BLOCKS) );
                    invalid++;
                }
                case FRAME -> {
                    DebugRenderer.addBlock(entry.getKey(), new BlockRenderModel( entry.getKey(), this.color_BLUE )
                        .setRenderType(ElementRenderModel.RenderType.THROUGH_BLOCKS) );
                    frame++;
                }
                case PORTAL -> {
                    DebugRenderer.addBlock(entry.getKey(), new BlockRenderModel( entry.getKey(), this.color_LIGHT_PURPLE )
                        .setRenderType(ElementRenderModel.RenderType.THROUGH_BLOCKS) );
                    portal++;
                }
                case AIR -> {
                    DebugRenderer.addBlock(entry.getKey(), getBlockRenderFromPortalBlock(entry.getValue()) );
                    air++;
                }
                case UNCHECKED -> {
                    DebugRenderer.addBlock(entry.getKey(), new BlockRenderModel( entry.getKey(),this.color_BLACK )
                        .setRenderType(ElementRenderModel.RenderType.THROUGH_BLOCKS) );
                    unchecked++;
                }
                default -> {
                    DebugRenderer.addBlock(entry.getKey(), new BlockRenderModel( entry.getKey(), this.color_AQUA )
                        .setRenderType(ElementRenderModel.RenderType.THROUGH_BLOCKS) );
                    unknown++;
                }
            }
        }
        
        ServerUtil.sendMessageToAllPlayers(new StringFormattedModel()
            .gray("VISITED: ").add(visitedBlocks.size()).white(" - ")
            .red("INVALID: ").add(invalid).white(" - ")
            .blue("FRAME: ").add(frame).white(" - ")
            .lightPurple("PORTAL: ").add(portal).white(" - ")
            .aqua("AIR: ").add(air).white(" - ")
            .green("UNCHECKED: ").add(unchecked).white(" - ")
            .yellow("UNKNOWN: ").add(unknown)
        );
    }
    
    private BlockRenderModel getBlockRenderFromPortalBlock(PortalBlockModel portalBlock)
    {
        // TODO: white color here is redundant since all directions are checked
        BlockRenderModel blockRender = new BlockRenderModel( portalBlock.getPos(), this.color_WHITE )
            .setRenderType(ElementRenderModel.RenderType.THROUGH_BLOCKS);
    
        blockRender.setColorFace_BELOW( this.getFaceRenderColorForDirection(portalBlock, Direction.DOWN) );
        blockRender.setColorFace_ABOVE( this.getFaceRenderColorForDirection(portalBlock, Direction.UP) );
        blockRender.setColorFace_NORTH( this.getFaceRenderColorForDirection(portalBlock, Direction.NORTH) );
        blockRender.setColorFace_SOUTH( this.getFaceRenderColorForDirection(portalBlock, Direction.SOUTH) );
        blockRender.setColorFace_EAST( this.getFaceRenderColorForDirection(portalBlock, Direction.EAST) );
        blockRender.setColorFace_WEST( this.getFaceRenderColorForDirection(portalBlock, Direction.WEST) );
        
        return blockRender;
    }
    
    private ColorModel getFaceRenderColorForDirection(PortalBlockModel portalBlock, Direction direction)
    {
        if ( !portalBlock.wasDirectionChecked(direction) )
        {
            return MinecraftColors.yellow;
        }
        
        if ( portalBlock.isDirectionValid(direction) )
        {
            Position position = this.getTextPositionForDirectionFromPortalBlock(portalBlock, direction);
            ServerUtil.sendMessageToAllPlayers("textPos " + position.toString());
            DebugRenderer.addText( position, new TextRenderModel(
                portalBlock.getDistanceToFrameForDirection(direction),
                position, MinecraftColors.white) );
            return MinecraftColors.green;
        }
    
        return MinecraftColors.red;
    }
    
    private Position getTextPositionForDirectionFromPortalBlock(PortalBlockModel portalBlock, Direction direction)
    {
        Vec3f dirVector = direction.getUnitVector();
        dirVector.scale(0.3F);
        
        Vec3d pos = new Vec3d(portalBlock.getPos().getX(), portalBlock.getPos().getY(), portalBlock.getPos().getZ());
        return pos.add(0.5, 0.5, 0.5).add( dirVector.getX(), dirVector.getY(), dirVector.getZ() );
        
//        portalBlock.getPos().add(0.5, 0.5, 0.5).add(direction.getVector().subtract(direction.getOpposite().getVector().) )
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
