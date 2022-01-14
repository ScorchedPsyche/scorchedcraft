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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft;

public class ScorchedCraftManager {
    public static final boolean debug_mode = true;
    private static final String permission_prefix = "scorchedcraft";

    public static class Name
    {
        public static final String compact = "SC";
        public static final String full = "ScorchedCraft";
        public static final String pomXml = "scorchedcraft";
    }

    public static abstract class Core
    {
        public static class Name
        {
            public static final String compact = "SC - Core";
            public static final String full = "ScorchedCraft - Core";
            public static final String pomXml = "scorchedcraft-core";
        }

        public abstract boolean isEnabled ();

        public static class Messages {
            public enum Type
            {
                ServerMessageToAllPlayers,
                MessageForSpecificPlayer
            }
        }

        public static class Task {
            public static class TitleAndSubtitleSendToPlayer {
                public static final long period = 5L;
            }
        }

        public static class Permissions {
            public static final String core = permission_prefix + ".core";
        }
    }
    
    public static class Portals
    {
        public static class Name
        {
            public static final String compact = "SC - Portals";
            public static final String full = "ScorchedCraft - Portals";
            public static final String pomXml = "scorchedcraft-portals";
        }
    }
    
    public static class Sleep
    {
        public static class Name
        {
            public static final String compact = "SC - Sleep";
            public static final String full = "ScorchedCraft - Sleep";
            public static final String pomXml = "scorchedcraft-sleep";
        }
    }
    
    public static class Seasons
    {
        public static class Name
        {
            public static final String compact = "CES - Seasons";
            public static final String full = "CraftEra Suite - Seasons";
            public static final String pomXml = "craftera_suite-seasons";
        }
        
        public enum Status
        {
            Inactive,
            Active,
            Started,
            Finished,
            Archived
        }
        
        public static class Permissions {
            public static final String seasons = permission_prefix + ".seasons";
        }
    }
    
    public static class SpectatorMode
    {
        public static class Name
        {
            public static final String compact = "CES - Spectator Mode";
            public static final String full = "CraftEra Suite - Spectator Mode";
            public static final String pomXml = "craftera_suite-spectator_mode";
        }
        
        public static class Task
        {
            public static class ProcessPlayersInSpectator
            {
                public static final long period = 5L;
            }
        }
    }
    
    public static class WanderingTrades
    {
        public static class Name
        {
            public static final String compact = "SC - Wandering Trades";
            public static final String full = "ScorchedCraft - Wandering Trades";
            public static final String pomXml = "scorchedcraft-wandering_trades";
        }
    }
}
