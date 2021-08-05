package com.github.scorchedpsyche.scorchedcraft.shared.core.managers.server;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class WorldsServerManager {
    public WorldsServerManager()
    {
        worlds = new ArrayList<>();
//			System.out.println("LOADED: " );
//			System.out.println(" -- world.asString() " + world.asString());
//			System.out.println(" -- world.isClient() " + world.isClient);
//			System.out.println(" -- world.getDimension()toString " + world.getDimension().toString());
//			System.out.println(" -- world.getDimension()getSuffix " + world.getDimension().getSuffix());
//			System.out.println(" -- world.getDimension()OVERWORLD_ID " + world.getDimension().equals(DimensionType.OVERWORLD_REGISTRY_KEY));
//			System.out.println(" -- world.getDimension()THE_NETHER_ID " + world.getDimension().equals(DimensionType.THE_NETHER_ID));
//			System.out.println(" -- world.getDimension()THE_END_ID " + world.getDimension().equals(DimensionType.THE_END_ID));
//			System.out.println(" -- world.toServerWorld().toString() " + world.toServerWorld().toString());
//			System.out.println(" -- world.toServerWorld().getDebugString() " + world.toServerWorld().getDebugString());
//			System.out.println(" -- server.getServerIp() " + server.getServerIp());
//			System.out.println(" -- server.getServerMotd() " + server.getServerMotd());
//			System.out.println(" -- server.getServerModName() " + server.getServerModName());
//			for( ServerWorld serverWorld : server.getWorlds() )
//			{
//				System.out.println( " " );
//				System.out.println( " -> OVERWORLD " + (serverWorld.getRegistryKey() == World.OVERWORLD) );
//				System.out.println( " -> NETHER " + (serverWorld.getRegistryKey() == World.NETHER) );
//				System.out.println( " -> END " + (serverWorld.getRegistryKey() == World.END) );
//				System.out.println( " " );
//			}
    }

    List<World> worlds;

    public void addLoadedWorld(ServerWorld world)
    {
        this.worlds.add(world);
    }
}
