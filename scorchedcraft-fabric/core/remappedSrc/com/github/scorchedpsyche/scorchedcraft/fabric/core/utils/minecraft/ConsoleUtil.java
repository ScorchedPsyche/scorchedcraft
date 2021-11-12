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

import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.StringFormattedModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.StringUtil;

public class ConsoleUtil {
    private static final String defaultPrefix = "ScorchedCraft";
    
    /**
     * Logs a debug message (aqua colored) to the console with default prefix (ScorchedCraft).
     * @param message Message to the written to the console
     */
    public static void debugMessage(String message)
    {
        debugMessage(defaultPrefix, message);
    }
    
    /**
     * Logs a debug message (aqua colored) to the console with a plugin's prefix.
     * @param modPrefixName The prefix for the plugin
     * @param message Message to the written to the console
     */
    public static void debugMessage(String modPrefixName, String message)
    {
        System.out.println( new StringFormattedModel()
                .aqua("[").nl().add(validCustomPrefixOrDefault(modPrefixName)).add("] DEBUG: ")
                .add(message).nl().reset()
            .toString() );
//        Bukkit.getConsoleSender().sendMessage(
//            ChatColor.AQUA + "\n[" + validCustomPrefixOrDefault(modPrefixName) +
//                "] DEBUG: " + message + "\n" + ChatColor.RESET);
    }
    
    /**
     * Logs an error (red colored) to the console with default prefix (ScorchedCraft).
     * @param message Message to the written to the console
     */
    public static void logError(String message)
    {
        logError(defaultPrefix, message);
    }
    
    /**
     * Logs an error (red colored) to the console with a plugin's prefix.
     * @param modPrefixName The prefix for the plugin
     * @param message Message to the written to the console
     */
    public static void logError(String modPrefixName, String message)
    {
//        StringFormattedModel errorStr = new StringFormattedModel()
//            .red("\n[").add(validCustomPrefixOrDefault(modPrefixName)).add("] ERROR: " ).nl().nl()
//            .add(message).reset().nl();
        System.out.println( new StringFormattedModel()
            .red("\n[").add(validCustomPrefixOrDefault(modPrefixName)).add("] ERROR: " ).nl().nl()
            .add(message).nl().reset()
            .toString() );
//        Bukkit.getConsoleSender().sendMessage(
//                ChatColor.RED + "\n[" + validCustomPrefixOrDefault(modPrefixName) +
//                        "] ERROR: " + message + "\n" + ChatColor.RESET);
    }
    
    /**
     * Logs an SQL query error (red colored) to the console with a plugin's prefix.
     * @param sql SQL statement that triggered the exception
     * @param sqlMessage The debug error returned from the driver
     */
    public static void logErrorSQL(String sql, String sqlMessage)
    {
        System.out.println( new StringFormattedModel()
            .red("\n[").add(defaultPrefix).add("] ERROR executing SQL: " ).nl().nl().add(sql).nl().nl().add(sqlMessage)
            .toString() );
//        StringFormattedModel errorStr = new StringFormattedModel()
//            .red("\n[").add(defaultPrefix).add("] ERROR executing SQL: " ).nl().nl().add(sql).nl().nl().add(sqlMessage);
//        Bukkit.getConsoleSender().sendMessage(errorStr.toString());
    }
    
    /**
     * Logs an SQL query error (red colored) to the console with a plugin's prefix.
     * @param sql SQL statement that triggered the exception
     * @param sqlMessage The debug error returned from the driver
     */
    public static void logErrorSQLWithPluginPrefix(String modPrefixName, String method, String sql, String sqlMessage)
    {
        System.out.println( new StringFormattedModel()
            .red("\n[").add(validCustomPrefixOrDefault(modPrefixName)).add("] ERROR executing SQL for method")
            .add(method).add(": " ).nl().nl().add(sql).nl().nl().add(sqlMessage)
            .toString() );
//        StringFormattedModel errorStr = new StringFormattedModel()
//            .red("\n[").add(validCustomPrefixOrDefault(modPrefixName)).add("] ERROR executing SQL for method")
//            .add(method).add(": " ).nl().nl().add(sql).nl().nl().add(sqlMessage);
//        Bukkit.getConsoleSender().sendMessage(errorStr.toString());
    }
    
    /**
     * Logs a warning (yellow colored) to the console with default prefix (ScorchedCraft).
     * @param message Message to the written to the console
     */
    public static void logWarning(String message)
    {
        logWarning(defaultPrefix, message);
    }
    
    /**
     * Logs a warning (yellow colored) to the console with a plugin's prefix.
     * @param modPrefixName The prefix for the plugin
     * @param message Message to the written to the console
     */
    public static void logWarning(String modPrefixName, String message)
    {
        System.out.println( new StringFormattedModel()
            .yellow().nl().add(validCustomPrefixOrDefault(modPrefixName)).add("] WARNING: ")
            .add(message).nl().reset()
            .toString() );
//        Bukkit.getConsoleSender().sendMessage(
//            ChatColor.YELLOW + "\n[" + validCustomPrefixOrDefault(modPrefixName) +
//                "] WARNING: " + message + "\n" + ChatColor.RESET);
    }
    
    /**
     * Logs a success (green colored) to the console with default prefix (ScorchedCraft).
     * @param message Message to the written to the console
     */
    public static void logSuccess(String message)
    {
        logSuccess(defaultPrefix, message);
    }
    
    /**
     * Logs a success (green colored) to the console with a plugin's prefix.
     * @param modPrefixName The prefix for the plugin
     * @param message Message to the written to the console
     */
    public static void logSuccess(String modPrefixName, String message)
    {
        System.out.println( new StringFormattedModel()
            .green().nl().add(validCustomPrefixOrDefault(modPrefixName)).add("] SUCCESS: ")
            .add(message).nl().reset()
            .toString() );
//        Bukkit.getConsoleSender().sendMessage(
//            ChatColor.GREEN + "\n[" + validCustomPrefixOrDefault(modPrefixName) +
//                "] SUCCESS: " + message + "\n" + ChatColor.RESET);
    }
    
    /**
     * Logs a message (no color) to the console with default prefix (ScorchedCraft).
     * @param message Message to the written to the console
     */
    public static void logMessage(String message)
    {
        logMessage(defaultPrefix, message);
    }
    
    /**
     * Logs a message (no color) to the console with a plugin's prefix.
     * @param modPrefixName The prefix for the plugin
     * @param message Message to the written to the console
     */
    public static void logMessage(String modPrefixName, String message)
    {
        System.out.println( new StringFormattedModel()
                .add("[").add(validCustomPrefixOrDefault(modPrefixName)).add("] ").add(message)
            .toString() );
//        Bukkit.getConsoleSender().sendMessage("[" + validCustomPrefixOrDefault(modPrefixName) + "] " + message);
    }
    
    private static String validCustomPrefixOrDefault(String pluginPrefixName)
    {
        return ( StringUtil.isNullOrEmpty(pluginPrefixName) ) ? defaultPrefix : pluginPrefixName;
    }
}
