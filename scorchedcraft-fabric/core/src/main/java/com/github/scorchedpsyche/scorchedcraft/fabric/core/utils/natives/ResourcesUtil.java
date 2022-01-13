/*
 * Copyright (c) 2022 ScorchedPsyche
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
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ResourcesUtil {
    public ResourcesUtil(String pluginPrefix, File moduleFolder, ClassLoader classLoader)
    {
        this.pluginPrefix = pluginPrefix;
        this.moduleFolder = moduleFolder;
        this.classLoader = classLoader;
    }
    
    String pluginPrefix;
    File moduleFolder;
    ClassLoader classLoader;
    
    public boolean copyToModuleConfigFolderIfNotExists(ArrayList<String> resourcePaths)
    {
        // Loops through resources list
        for (String resourcePath : resourcePaths)
        {
            String filePath = resourcePath.replace("files/", "");
            File destinationFile = new File(this.moduleFolder + File.separator + filePath);
            
            // Check if file already exists
            if( !destinationFile.exists() )
            {
                // File must be copied. Check if parent folder exists
                if( !destinationFile.getParentFile().exists() )
                {
                    // Parent must be created. Attempt to create
                    if( !destinationFile.getParentFile().mkdirs() )
                    {
                        // Folder creation failed
                        ConsoleUtil.logError(this.pluginPrefix, "Failed to create mod's folders inside 'config' " +
                            "folder. Check if the folder already exists or write permissions: " + destinationFile.getParentFile());
                        return false;
                    }
                }
                
                // If we got here the parent folder is configured. Load resource as InputStream
                InputStream inputStream = classLoader.getResourceAsStream(resourcePath);
    
                if( inputStream != null )
                {
                    try {
                        // Attempt to copy file
                        FileUtils.copyInputStreamToFile(inputStream, destinationFile);
                        ConsoleUtil.logMessage(this.pluginPrefix, "Copied file: " + destinationFile);
                    } catch (IOException e) {
                        // File copying failed
                        ConsoleUtil.logError(this.pluginPrefix, "Failed to copy file. Report this to the " +
                            "developer: " + destinationFile);
                        e.printStackTrace();
                    }
                } else {
                    // Something went wrong?
                    ConsoleUtil.logError(this.pluginPrefix, "INVALID source file path that should be bundled in jar." +
                        " Report this to the developer: " + resourcePath);
                }
    
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException e) {
                    ConsoleUtil.logError(this.pluginPrefix, "Failed to close InputStream. Report this to the " +
                        "developer: " + destinationFile);
                    e.printStackTrace();
                    e.printStackTrace();
                }
            }
        }
        
        return true;
    }
    
    /**
     * @param classLoader Usually "getClass().getClassLoader()", this is the ClassLoader to search the file from.
     * @param sourcePath Where inside the .jar's resource folder is the file (relative path).
     * @param targetPath Where to put the file.
     * @param targetFileName The final file name.
     * @return True if copy was done successfully.
     */
    public boolean copyToModuleConfigFolderIfNotExistsOLD(ClassLoader classLoader, String sourcePath,
                                                   File targetPath, String targetFileName)
    {
        File finalFilePath = new File(targetPath + File.separator + targetFileName );
        
        if( !finalFilePath.exists() )
        {
            InputStream inputStream = classLoader.getResourceAsStream(sourcePath);
    
            if( inputStream != null )
            {
                try {
                    if( targetPath.exists() && targetPath.isDirectory() || !targetPath.exists() && targetPath.mkdirs() )
                    {
                        FileUtils.copyInputStreamToFile(inputStream, finalFilePath);
                        ConsoleUtil.logMessage(this.pluginPrefix, "Copied file: " + finalFilePath);
                        
                        return true;
                    } else {
                        ConsoleUtil.logError(this.pluginPrefix, "Error checking directory validity/existence: "
                            + targetPath.getAbsolutePath());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                ConsoleUtil.logError(this.pluginPrefix, "INVALID source file path that should be bundled in jar." +
                    " Report this to the developer: " + sourcePath);
            }
        }
        
        return false;
    }
}
