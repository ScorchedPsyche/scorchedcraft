package com.github.scorchedpsyche.scorchedcraft.fabric.portals.models;

import com.github.scorchedpsyche.scorchedcraft.fabric.portals.managers.PortalManager;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PortalModel {
    @Nullable PortalManager.Orientation orientation;
    @Nullable PortalManager.Direction direction;
    
    public PortalModel()
    {
    }

    private String name;
    private World world;
}
