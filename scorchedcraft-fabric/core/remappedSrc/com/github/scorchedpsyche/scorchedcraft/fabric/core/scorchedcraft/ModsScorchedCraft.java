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

public class ModsScorchedCraft {
    public static final boolean debug_mode = true;
    private static final String permission_prefix = "craftera_suite";

    public static class Name
    {
        public static final String compact = "CES";
        public static final String full = "CraftEra Suite";
        public static final String pomXml = "craftera_suite";
    }

    public static abstract class Core
    {
        public static class Name
        {
            public static final String compact = "CES - Core";
            public static final String full = "CraftEra Suite - Core";
            public static final String pomXml = "craftera_suite-core";
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
            public static final String compact = "CES - Portals";
            public static final String full = "CraftEra Suite - Portals";
            public static final String pomXml = "craftera_suite-portals";
        }
    }
}
