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

import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class FolderUtil {
    private static File configFolder;
    private static File suiteRootFolder;

    public static synchronized void setup()
    {
        configFolder = getConfigFolder();
        suiteRootFolder = getOrCreateSuiteSubfolder();
    }

    @Nullable
    public static File getOrCreateModuleSubfolder(String moduleName)
    {
        if( suiteRootFolder != null )
        {
            File pluginSubfolder = new File(suiteRootFolder + File.separator + moduleName.split("-")[1]);

            if( !pluginSubfolder.exists() )
            {
                if( !pluginSubfolder.mkdirs() )
                {
                    ConsoleUtil.logError(
                        "Plugin configuration folder failed to be created: check folder write permissions or try to " +
                            "create the folder manually. If everything looks OK and the issue still persists, " +
                            "report this to the developer. FOLDER PATH STRUCTURE THAT SHOULD HAVE BEEN CREATED: " +
                            pluginSubfolder );
                    return null;
                }
            }

            return pluginSubfolder;
        }

        return null;
    }

    /**
     * Retrieves or creates the root CraftEra Suite folder which holds all of the suite plugin's configuration files.
     * @return The root folder inside Plugins folder for the CraftEra Suite configurations
     */
    public static synchronized File getOrCreateSuiteSubfolder()
    {
        if( suiteRootFolder != null )
        {
            return suiteRootFolder;
        }

//        suiteRootFolder = new File(getConfigFolder().getPath() + File.separator + "scorchedcraft");
//        File cesFolder = new File( cesRootPath );
//
//        if ( !cesFolder.exists() )
//        {
//            if (!cesFolder.mkdirs()) {
//                ConsoleUtil.logError(
//                    "Main CraftEra Suite configuration folder failed to be created: check folder write " +
//                        "permissions or try to create the folder manually. If everything looks OK and the " +
//                        "issue still persists, report this to the developer. FOLDER PATH STRUCTURE THAT " +
//                        "SHOULD HAVE BEEN CREATED: " + cesFolder );
//            }
//        }

        return suiteRootFolder;
    }

    /**
     * @return Returns root Plugins folder.
     */
    @Nullable
    private static synchronized File getConfigFolder()
    {
        if( configFolder != null )
        {
            return configFolder;
        }
        
//        File dataFolder = FabricLoader.getInstance().getConfigDir().toFile();
    
        File path = null;
    
        path = FabricLoader.getInstance().getConfigDir().toFile();
    
        return path;

//        StringBuilder path;
//        try {
//            path = new StringBuilder(dataFolder.getCanonicalPath());
//        } catch( IOException ex ) {
//            path = new StringBuilder(dataFolder.getAbsolutePath());
//        }
//
//        String pattern = Pattern.quote(File.separator);
//        String[] pathSplit = path.toString().split(pattern);
//        path = new StringBuilder();
//
//        for ( int i = 0; i < pathSplit.length - 1; i++ )
//        {
//            path.append(pathSplit[i]).append(File.separator).append("config").append(File.separator);
//        }

//        return new File(path.toString());
    }
}