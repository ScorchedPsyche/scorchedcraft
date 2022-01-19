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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.Core;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.StringFormattedModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class WorldUtil {
    public static void sendMessageToAllPlayersInWorld(World world, String message)
    {
        for( PlayerEntity player : world.getPlayers() )
        {
            PlayerUtil.sendMessage( player, message );
        }
    }
    public static void sendMessageToAllPlayersInWorld(World world, StringFormattedModel message)
    {
        for( PlayerEntity player : world.getPlayers() )
        {
            PlayerUtil.sendMessage( player, message );
        }
    }
    
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

        public static boolean canBedsBeUsed(@NotNull ServerWorld world)
        {;
            long currentDayTime = world.getTimeOfDay() - 24000L * Math.floorDiv(world.getTimeOfDay(), 24000L);
            
//            ConsoleUtil.logMessage("world.getTimeOfDay() " + world.getTimeOfDay());
            if( !world.isRaining() )
            {
//                ConsoleUtil.logMessage("!world.isRaining()");
//                ConsoleUtil.logMessage("world.getTimeOfDay() " + world.getTimeOfDay());
//                ConsoleUtil.logMessage("currentDayTime " + currentDayTime);
//                ConsoleUtil.logMessage("BEDS_CAN_BE_USED_START " + DayNightCycle.WEATHER_CLEAR.BEDS_CAN_BE_USED_START);
//                ConsoleUtil.logMessage("BEDS_CAN_BE_USED_END " + DayNightCycle.WEATHER_CLEAR.BEDS_CAN_BE_USED_END);
                // Not raining. Use time for clear weather
                return currentDayTime >= DayNightCycle.WEATHER_CLEAR.BEDS_CAN_BE_USED_START
                    && currentDayTime < DayNightCycle.WEATHER_CLEAR.BEDS_CAN_BE_USED_END;
            }
    
//            ConsoleUtil.logMessage("world.isRaining()");
//            ConsoleUtil.logMessage("BEDS_CAN_BE_USED_START " + DayNightCycle.WEATHER_RAIN.BEDS_CAN_BE_USED_START);
//            ConsoleUtil.logMessage("BEDS_CAN_BE_USED_END " + DayNightCycle.WEATHER_RAIN.BEDS_CAN_BE_USED_END);
            // Use raining time
            return currentDayTime >= DayNightCycle.WEATHER_RAIN.BEDS_CAN_BE_USED_START
                && currentDayTime < DayNightCycle.WEATHER_RAIN.BEDS_CAN_BE_USED_END;
        }
    
        public static boolean skipNightUntilBedsCannotBeUsed(long nextDaySunrise, World world)
        {
            long nextTimeOfDay = world.getTimeOfDay() + 100L;
            
            // Check if skip step won't go over the start of the day
            if( nextTimeOfDay < nextDaySunrise)
            {
//                ConsoleUtil.debugMessage("world.getTime(): " + world.getTime());
//                ConsoleUtil.debugMessage("world.getTimeOfDay(): " + world.getTimeOfDay());
//                ConsoleUtil.debugMessage("world.getTimeOfDay() + 100: " + (world.getTimeOfDay() + 100L));
                ((ServerWorld) world).setTimeOfDay( nextTimeOfDay );
//                ConsoleUtil.logMessage(nextTimeOfDay+ "/" + nextDaySunrise);
                return true;
            } else {
                // Will overflow the day
                ((ServerWorld) world).setTimeOfDay(nextDaySunrise);
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
//                ConsoleUtil.debugMessage("SUNRISE!!!!" );
                
                return false;
            }
        }
    }
    
    public static boolean attemptToClearWeatherDependingOnChance(World world, int chance)
    {
        chance = MathUtil.limitBetween(chance, 0, 100);

//        ConsoleUtil.debugMessage("chance: " + chance);
        if ( chance == 100 )
        {
            // Clear weather
//            ConsoleUtil.debugMessage("chance == 100");
    
            ((ServerWorld) world).setWeather(new Random().nextInt(), 0, false, false );
            return true;
        } else if ( chance == 0 )
        {
//            ConsoleUtil.debugMessage("chance == 0");
//            ConsoleUtil.debugMessage("weather KEEP");
            return false;
        } else {
            int random = new Random().nextInt(101);
//            ConsoleUtil.debugMessage("random/chance " + random + "/" + chance);
            if( random <= chance )
            {
//                ConsoleUtil.debugMessage("weather CLEARED by change");
                ((ServerWorld) world).setWeather(new Random().nextInt(), 0, false, false );
                return true;
            }
        }

//        ConsoleUtil.debugMessage("weather KEEP 2");
        return false;
    }
    
    public static void wakeAllPlayers()
    {
        for( ServerPlayerEntity player : Core.server.getPlayerManager().getPlayerList() )
        {
            if( player.isSleeping() )
            {
                player.clearSleepingPosition();
            }
        }
    }
    
    public static boolean isBlockFromPositionEqualToBlock(BlockPos blockPos, Block block, World world)
    {
        if (world.testBlockState(blockPos, neighbor -> neighbor.isOf(block))) {
//            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().green(blockPos.toString()));
            return true;
        }
        
        return false;
    }
    
    @Nullable
    public static BlockPos returnBlockPositionIfEqualToBlock(BlockPos blockPos, Block block, World world)
    {
        if (world.testBlockState(blockPos, neighbor -> neighbor.isOf(block))) {
            ServerUtil.sendMessageToAllPlayers(new StringFormattedModel().green(blockPos.toString()));
            return blockPos;
        }
        
        return blockPos;
    }
    
    public static boolean isBlockPosAir(BlockPos blockPos, World world)
    {
       return world.testBlockState( blockPos, block -> block.getBlock().equals(Blocks.AIR) );
    }
    
    public static boolean isBlockFaceAirFromHitResult(BlockHitResult hitResult, World world)
    {
        return world.testBlockState(
            hitResult.getBlockPos().add(hitResult.getSide().getVector()),
            block -> block.getBlock().equals(Blocks.AIR ) );
    }
    public static boolean isBlockPosEqual(BlockPos blockPos, Block blockToCompare, World world)
    {
        return world.testBlockState( blockPos, block -> block.getBlock().equals(blockToCompare) );
    }
}
