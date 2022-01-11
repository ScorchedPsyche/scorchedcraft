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
     * Logs a debug message (aqua colored) to the console with a mod's prefix.
     * @param modPrefixName The prefix for the mod
     * @param message Message to the written to the console
     */
    public static void debugMessage(String modPrefixName, String message)
    {
        System.out.println("\n[" + validCustomPrefixOrDefault(modPrefixName) + "] DEBUG: " +
            message + "\n");
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
     * Logs an error (red colored) to the console with a mod's prefix.
     * @param modPrefixName The prefix for the mod
     * @param message Message to the written to the console
     */
    public static void logError(String modPrefixName, String message)
    {
        System.out.println("\n[" + validCustomPrefixOrDefault(modPrefixName) + "] ERROR: \n\n" +
            message + "\n");
    }
    
    /**
     * Logs an SQL query error (red colored) to the console with a mod's prefix.
     * @param sql SQL statement that triggered the exception
     * @param sqlMessage The debug error returned from the driver
     */
    public static void logErrorSQL(String sql, String sqlMessage)
    {
        System.out.println("\n[" + defaultPrefix + "] ERROR executing SQL: \n\n" + sql + "\n\n" + sqlMessage);
    }
    
    /**
     * Logs an SQL query error (red colored) to the console with a mod's prefix.
     * @param sql SQL statement that triggered the exception
     * @param sqlMessage The debug error returned from the driver
     */
    public static void logErrorSQLWithPluginPrefix(String modPrefixName, String method, String sql, String sqlMessage)
    {
        System.out.println("\n[" + validCustomPrefixOrDefault(modPrefixName) + "] ERROR executing SQL for method" +
            method + ": \n\n" + sql + "\n\n" + sqlMessage);
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
     * Logs a warning (yellow colored) to the console with a mod's prefix.
     * @param modPrefixName The prefix for the mod
     * @param message Message to the written to the console
     */
    public static void logWarning(String modPrefixName, String message)
    {
        System.out.println("\n" + validCustomPrefixOrDefault(modPrefixName) + "] WARNING: " +
            message + "\n");
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
     * Logs a success (green colored) to the console with a mod's prefix.
     * @param modPrefixName The prefix for the mod
     * @param message Message to the written to the console
     */
    public static void logSuccess(String modPrefixName, String message)
    {
        System.out.println("\n" + validCustomPrefixOrDefault(modPrefixName) + "] SUCCESS: " +
            message + "\n");
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
     * Logs a message (no color) to the console with a mod's prefix.
     * @param modPrefixName The prefix for the mod
     * @param message Message to the written to the console
     */
    public static void logMessage(String modPrefixName, String message)
    {
        System.out.println("[" + validCustomPrefixOrDefault(modPrefixName) + "] " + message);
    }
    
    /**
     * Logs a message (no color) to the console notifying that the mod LOADING has begun.
     * @param modPrefixName The prefix for the mod
     */
    public static void modLoadStarted(String modPrefixName)
    {
        System.out.println("[" + validCustomPrefixOrDefault(modPrefixName) + "] LOADING STARTED");
    }
    
    /**
     * Logs a message (no color) to the console notifying that the mod LOADING has begun.
     * @param modPrefixName The prefix for the mod
     */
    public static void modLoadFinished(String modPrefixName)
    {
        System.out.println("[" + validCustomPrefixOrDefault(modPrefixName) + "] LOADING FINISHED");
    }
    
    private static String validCustomPrefixOrDefault(String mod)
    {
        return ( StringUtil.isNullOrEmpty(mod) ) ? defaultPrefix : mod;
    }
}
