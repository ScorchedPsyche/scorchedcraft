package com.github.scorchedpsyche.scorchedcraft.fabric.core.utils;

import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class WorldUtil {
    public class DayNightCycle
    {
        public static final long SUNRISE = 23992;
        public static final long VILLAGER_WORK_START = 2000;
        public static final long VILLAGER_WORK_END = 9000;

        public class WEATHER_CLEAR
        {
            public static final long BEDS_CAN_BE_USED_START = 12542;
            public static final long BEDS_CAN_BE_USED_END = 23460;
            public static final long MONSTERS_SPAWN_START = 13187;
            public static final long MONSTERS_SPAWN_END = 23031;
        }

        public class WEATHER_RAIN
        {
            public static final long BEDS_CAN_BE_USED_START = 12010;
            public static final long BEDS_CAN_BE_USED_END = 23992;
            public static final long MONSTERS_SPAWN_START = 12969;
            public static final long MONSTERS_SPAWN_END = 13187;
        }

        public static boolean canBedsBeUsed(@NotNull World world)
        {
            if( !world.isRaining() )
            {
                // Not raining. Use time for clear weather
                return world.getTime() >= DayNightCycle.WEATHER_CLEAR.BEDS_CAN_BE_USED_START
                    && world.getTime() < DayNightCycle.WEATHER_CLEAR.BEDS_CAN_BE_USED_END;
            }

            // Use raining time
            return world.getTime() >= DayNightCycle.WEATHER_RAIN.BEDS_CAN_BE_USED_START
                && world.getTime() < DayNightCycle.WEATHER_RAIN.BEDS_CAN_BE_USED_END;
        }

        public static boolean isWorldAtBedsCanBeUsedEndTime(World world)
        {
            if( !world.isRaining() )
            {
                // Not raining. Use time for clear weather
                return world.getTime() == DayNightCycle.WEATHER_CLEAR.BEDS_CAN_BE_USED_END;
            }

            // Use raining time
            return world.getTime() == DayNightCycle.WEATHER_RAIN.BEDS_CAN_BE_USED_END;
        }
    }
}
