/*
 * Copyright (c) 2021 ScorchedPsyche
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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public class FolderUtil {
    private static File configFolder;
    private static File suiteRootFolder;
    
    @Nullable
    public static File getOrCreateModuleSubfolder(String moduleName)
    {
        setupSuiteFolders();
        
        if( suiteRootFolder != null )
        {
            File moduleSubfolder = new File(suiteRootFolder + File.separator + moduleName.split("-")[1]);
    
            if( !moduleSubfolder.exists() )
            {
                if( !moduleSubfolder.mkdirs() )
                {
                    printFolderCreationErrorToConsole(moduleSubfolder.getAbsolutePath());
                    return null;
                }
            }
    
            return moduleSubfolder;
        }
        
        return null;
    }

    public static synchronized void setupSuiteFolders()
    {
        ConsoleUtil.debugMessage("setupSuiteFolders");
        configFolder = getOrCreateConfigFolder();
        suiteRootFolder = getOrCreateSuiteRootFolder();
    }
    
    /**
     * @return Returns root "config" folder.
     */
    @Nullable
    private static synchronized File getOrCreateConfigFolder()
    {
        ConsoleUtil.debugMessage("getOrCreateConfigFolder");
        if( configFolder != null )
        {
            return configFolder;
        }
        
        File configDir = FabricLoader.getInstance().getConfigDir().toFile();
        
        if( isDirectoryValid(configDir) )
        {
            return configDir;
        }
    
        return null;
    }

    /**
     * Retrieves or creates the root CraftEra Suite folder which holds all the suite mod's configuration files.
     * @return The root folder inside "Config" folder for the ScorchedCraft Suite configurations
     */
    @Nullable
    public static synchronized File getOrCreateSuiteRootFolder()
    {
        ConsoleUtil.debugMessage("getOrCreateSuiteRootFolder");
        if( suiteRootFolder != null )
        {
            return suiteRootFolder;
        }
        
        String suiteFolderPathString;
    
        try {
            ConsoleUtil.debugMessage("1");
            if( isDirectoryValid( configFolder ) )
            {
                ConsoleUtil.debugMessage("2");
                suiteFolderPathString = configFolder.getCanonicalPath() + File.separator + ScorchedCraftManager.Name.pomXml;
    
                suiteRootFolder = new File(suiteFolderPathString);
    
                if( suiteRootFolder.exists() || suiteRootFolder.mkdir() )
                {
                    ConsoleUtil.debugMessage("3");
                    return suiteRootFolder;
                } else {
                    ConsoleUtil.debugMessage("4");
                    printFolderCreationErrorToConsole(suiteRootFolder.getCanonicalPath());
                }
            }
    
            printFolderCreationErrorToConsole(configFolder.getCanonicalPath());
        } catch (IOException e) {
        e.printStackTrace();
    }
        return null;
    }
    
    /**
     * Check if File:
     *
     * - exists;
     * - is a directory;
     * - can be read;
     * - can be written to.
     * @param directory The File representing the directory.
     * @return True if File passes all checks and is valid to be operated on.
     */
    public static synchronized boolean isDirectoryValid( File directory )
    {
        try {
            if( directory != null )
            {
                if( directory.exists() )
                {
                    if( directory.isDirectory() )
                    {
                        if( directory.canRead() )
                        {
                            if( directory.canWrite() )
                            {
                                return true;
                            }
                            ConsoleUtil.logError("Cannot WRITE folder: " + directory.getCanonicalPath());
                            return false;
                        }
                        ConsoleUtil.logError("Cannot READ folder: " + directory.getCanonicalPath());
                        return false;
                    }
                    ConsoleUtil.logError("Path doesn't points to a directory: " + directory.getCanonicalPath());
                    return false;
                }
                ConsoleUtil.logError("Directory doesn't exists: " + directory.getCanonicalPath());
                return false;
            }
            ConsoleUtil.logError("FolderUtil's \"isDirectoryValid\" reports that the directory is NULL. Report this to the developer!");
        } catch (IOException e) {
            ConsoleUtil.logError("FolderUtil error checking directory validity: \n" +
                " - " + directory.toPath() + "\n" +
                " - " + directory.getAbsolutePath());
            e.printStackTrace();
        }
        
        return false;
    }
    
    public static void printFolderCreationErrorToConsole(String folderPath)
    {
        ConsoleUtil.logError(
            "Main mod configuration folder failed to be created: check folder read/write " +
                "permissions or try to create the folder manually. If everything looks OK and the " +
                "issue still persists, report this to the developer. FOLDER PATH STRUCTURE THAT " +
                "SHOULD HAVE BEEN CREATED: " + folderPath);
    }
}