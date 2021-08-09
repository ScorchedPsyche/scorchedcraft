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
