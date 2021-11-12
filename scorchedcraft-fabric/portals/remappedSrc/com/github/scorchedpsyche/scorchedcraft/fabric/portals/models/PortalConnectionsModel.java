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

import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Map;

public class PortalConnectionsModel {
    public PortalConnectionsModel()
    {
        this.connections = new HashMap<>();
        this.connections.put(Direction.UP, new PortalConnectionModel());
        this.connections.put(Direction.DOWN, new PortalConnectionModel());
        this.connections.put(Direction.NORTH, new PortalConnectionModel());
        this.connections.put(Direction.SOUTH, new PortalConnectionModel());
        this.connections.put(Direction.EAST, new PortalConnectionModel());
        this.connections.put(Direction.WEST, new PortalConnectionModel());
    }
    
    private int validConnections;
    private int invalidConnections;
    private int uncheckedConnections = 6;
    private Map<Direction, PortalConnectionModel> connections;
    
    public void setConnectionAsValid(Direction direction, int distantToFrameFromThisBlock)
    {
        if( this.connections.get(direction).getState() == PortalConnectionModel.State.INVALID  )
        {
            invalidConnections--;
        }
        validConnections++;
        this.connections.get(direction).setAsValid(distantToFrameFromThisBlock);
    }
    public void setConnectionAsInvalid(Direction direction)
    {
        if( this.connections.get(direction).getState() == PortalConnectionModel.State.VALID  )
        {
            validConnections--;
        }
        invalidConnections++;
        this.connections.get(direction).setAsInvalid();
    }
    
    public int getNumberOfValidConnections()
    {
        return this.validConnections;
    }
    
    public boolean isConnectionValid(Direction direction) {
        return this.connections.get(direction).getState() == PortalConnectionModel.State.INVALID;
    }
    
    public PortalConnectionModel.State getDirectionState(Direction direction)
    {
        return this.connections.get(direction).getState();
    }
    
    public int getDistanceToFrameForDirection(Direction direction)
    {
        return this.connections.get(direction).getDistanceToFrame();
    }
}
