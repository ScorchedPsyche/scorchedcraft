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
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.WorldUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.portals.managers.PortalManager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class PortalBlockModel implements Comparable<BlockPos> {
    public PortalBlockModel(BlockPos blockPos)
    {
        this.pos = blockPos;
    }
    
//    public enum State {
//        UNCHECKED,
//        INVALID,
//        VALID
//    }
    
    public enum Type {
        UNCHECKED,
        INVALID,
        AIR,
        FRAME,
        PORTAL
    }

    private BlockPos pos;
//    private State state = State.UNCHECKED;
    private Type type = Type.UNCHECKED;
    private PortalConnectionsModel connections = new PortalConnectionsModel();

    public BlockPos getPos()
    {
        return this.pos;
    }
//    public State getState() {
//        return this.state;
//    }
    public Type getType() {
        return this.type;
    }
    public PortalConnectionsModel getConnections() {
        return connections;
    }
    
    public boolean wasDirectionChecked(Direction direction)
    {
        return this.connections.getDirectionState(direction) != PortalConnectionModel.State.UNCHECKED;
    }
    
    public boolean isDirectionValid(Direction direction)
    {
        return this.connections.getDirectionState(direction) == PortalConnectionModel.State.VALID;
    }
    
    public void setDirectionAsValid(Direction direction, int distanceToFrameFromThisBlock)
    {
        this.connections.setConnectionAsValid(direction, distanceToFrameFromThisBlock);
    }
    
    public boolean isDirectionInvalid(Direction direction)
    {
        return this.connections.getDirectionState(direction) == PortalConnectionModel.State.VALID;
    }
    
    public void setDirectionAsInvalid(Direction direction)
    {
        this.connections.setConnectionAsInvalid(direction);
    }
    
    public int getDistanceToFrameForDirection(Direction direction)
    {
        return this.connections.getDistanceToFrameForDirection(direction);
    }
    
    public void resolveTypeOrInvalidateIfNeeded(Block frameBlock, World world)
    {
        if( WorldUtil.isBlockFromPositionEqualToBlock(pos, Blocks.AIR, world) )
        {
            // AIR
            this.setAsAir();
        } else if ( WorldUtil.isBlockFromPositionEqualToBlock(pos, frameBlock, world) )
        {
            // FRAME
            this.setAsFrame();
        } else {
            // Block is invalid against this portal
            this.setAsInvalid();
        }
    }
    
    public void setAsAir()
    {
        this.type = Type.AIR;
    }
    public void setAsFrame()
    {
        this.type = Type.FRAME;
    }
    public void setAsInvalid()
    {
        this.type = Type.INVALID;
    }
    public void setAsPortal()
    {
        this.type = Type.PORTAL;
    }
    
    
    public void evaluateDirectionFromAnotherPortalBlock(PortalBlockModel anotherPortalBlock, Direction direction)
    {
        ServerUtil.sendMessageToAllPlayers(" -- evaluate "
            + anotherPortalBlock.getPos().toShortString() + " to " + this.pos.toShortString());
        if( anotherPortalBlock.isDirectionValid(direction) )
        {
            ServerUtil.sendMessageToAllPlayers("setDirectionAsValid, dist other " + anotherPortalBlock.getDistanceToFrameForDirection(direction));
            this.setDirectionAsValid(direction, (anotherPortalBlock.getDistanceToFrameForDirection(direction) + 1));
        } else if ( anotherPortalBlock.isDirectionInvalid(direction) ){
            ServerUtil.sendMessageToAllPlayers("setDirectionAsInvalid");
            this.setDirectionAsInvalid(direction);
        } else {
            ServerUtil.sendMessageToAllPlayers("evaluateDirectionFromAnotherPortalBlock UNCHECKED "
                + anotherPortalBlock.getPos());
        }
    }
    
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Vec3i) && !(o instanceof PortalBlockModel)) {
            return false;
        } else if (o instanceof Vec3i vec3i) {
            if (this.pos.getX() != vec3i.getX()) {
                return false;
            } else if (this.pos.getY() != vec3i.getY()) {
                return false;
            } else {
                return this.pos.getZ() == vec3i.getZ();
            }
        } else {
            PortalBlockModel PortalBlock = (PortalBlockModel)o;
            if (this.pos.getX() != PortalBlock.pos.getX()) {
                return false;
            } else if (this.pos.getY() != PortalBlock.pos.getY()) {
                return false;
            } else {
                return this.pos.getZ() == PortalBlock.pos.getZ();
            }
        }
    }
    @Override
    public int compareTo(@NotNull BlockPos o) {
        if (this.pos.getY() == o.getY()) {
            return this.pos.getZ() == o.getZ() ? this.pos.getX() - o.getX() : this.pos.getZ() - o.getZ();
        } else {
            return this.pos.getY() - o.getY();
        }
    }
}
