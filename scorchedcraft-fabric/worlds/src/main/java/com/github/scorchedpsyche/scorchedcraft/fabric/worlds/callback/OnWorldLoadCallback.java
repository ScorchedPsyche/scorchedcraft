package com.github.scorchedpsyche.scorchedcraft.fabric.worlds.callback;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;

public interface OnWorldLoadCallback {
//    Event<OnWorldLoadCallback> EVENT = EventFactory.createArrayBacked(OnWorldLoadCallback.class,
//        (listeners) -> (server, world) -> {
//            for (OnWorldLoadCallback listener : listeners)
//            {
//                ActionResult result = listener.interact(server, world);
//
//                if( result != ActionResult.PASS )
//                {
//                    return result;
//                }
//            }
//        });
//
//    ActionResult interact((MinecraftServer server, ServerWorld world);
}
