package com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft;

import com.github.scorchedpsyche.scorchedcraft.shared.core.interfaces.IPluginsScorchedCraft;
import com.github.scorchedpsyche.scorchedcraft.shared.core.scorchedcraft.PluginsScorchedCraftShared;

public class PluginsScorchedCraft extends PluginsScorchedCraftShared {
    public static class Core implements IPluginsScorchedCraft {
//        @Override
//        public static boolean isEnabled()
//        {
//            return true;
//        }


        @Override
        public boolean isEnabled() {

            return false;
        }
    }
}
