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

package com.github.scorchedpsyche.scorchedcraft.fabric.portals.models;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ServerUtil;
import net.minecraft.util.math.Direction;

import java.util.BitSet;

public class PortalModel {
    public PortalModel(PortalBlockModel initialPortalBlock)
    {
        this.initialPortalBlock = initialPortalBlock;
    
        // Done to avoid trimming if mask ends up with leading zeroes
        this.connectionsBitSet.set(6, true);
        
        this.connectionsBitSet.set(0, this.initialPortalBlock.isDirectionValid(Direction.DOWN));
        this.connectionsBitSet.set(1, this.initialPortalBlock.isDirectionValid(Direction.UP));
        this.connectionsBitSet.set(2, this.initialPortalBlock.isDirectionValid(Direction.EAST));
        this.connectionsBitSet.set(3, this.initialPortalBlock.isDirectionValid(Direction.WEST));
        this.connectionsBitSet.set(4, this.initialPortalBlock.isDirectionValid(Direction.NORTH));
        this.connectionsBitSet.set(5, this.initialPortalBlock.isDirectionValid(Direction.SOUTH));
        
        StringBuilder bitSetStr = new StringBuilder();
        for( int i = 0; i < this.connectionsBitSet.length(); i++ )
        {
            ServerUtil.sendMessageToAllPlayers(i + ": " + this.connectionsBitSet.get(i));
            bitSetStr.append( this.connectionsBitSet.get(i) ? 1: 0 );
        }
        ServerUtil.sendMessageToAllPlayers("BitSet: " + bitSetStr);
    }
    
    public enum Shape {
        INVALID,
        SLICE,
        CURVED,
        T,
        CROSS,
        DOUBLE_CROSS
    }
    
    public enum Orientation {
        UNDEFINED,
        VERTICAL,
        HORIZONTAL
    }
    
    public enum Facing {
        UNDEFINED,
        EAST_WEST,
        NORTH_SOUTH
    }
    
    private BitSet connectionsBitSet = new BitSet(7);;
    private Shape shape = Shape.INVALID;
    private Orientation orientation = Orientation.UNDEFINED;
    private Facing facing = Facing.UNDEFINED;
    
//    private Direction direction = Direction.UNDEFINED;
    private PortalBlockModel initialPortalBlock;
    
    public Shape getShape() {
        return shape;
    }
    public Orientation getOrientation() {
        return orientation;
    }
    public Facing getFacing() {
        return facing;
    }
    
    public void findMostComplexPossibleShapeFromPortalBlock(PortalBlockModel currentPortalBlock)
    {
        if( currentPortalBlock.getConnections().getNumberOfValidConnections() >= 3 )
        {
            switch (currentPortalBlock.getConnections().getNumberOfValidConnections()) {
                case 6  -> this.shape = Shape.DOUBLE_CROSS;
                case 5  -> this.shape = Shape.T;
                default -> this.shape = Shape.CURVED; // 3 or 4 connections
            }
        } else {
            this.shape = Shape.INVALID;
        }
    }
    
    public boolean attemptToReduceShapeAndReturnIfOnlyOnePossible(PortalBlockModel portalBlock)
    {
        boolean downValid = portalBlock.isDirectionValid(Direction.DOWN);
        boolean upValid = portalBlock.isDirectionValid(Direction.UP);
        boolean eastValid = portalBlock.isDirectionValid(Direction.EAST);
        boolean westValid = portalBlock.isDirectionValid(Direction.WEST);
        boolean southValid = portalBlock.isDirectionValid(Direction.SOUTH);
        boolean northValid = portalBlock.isDirectionValid(Direction.NORTH);
        
        
        // Must do like SEARCH NORTH HORIZONTALLY
        
        return false;
    }
}
