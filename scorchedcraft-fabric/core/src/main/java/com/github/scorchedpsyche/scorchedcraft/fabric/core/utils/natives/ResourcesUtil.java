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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class ResourcesUtil {
    public ResourcesUtil(String pluginPrefix)
    {
        this.pluginPrefix = pluginPrefix;
    }
    
    String pluginPrefix;
    
    /**
     * @param classLoader Usually "getClass().getClassLoader()", this is the ClassLoader to search the file from.
     * @param sourcePath Where inside the .jar's resource folder is the file (relative path).
     * @param targetPath Where to put the file.
     * @param targetFileName The final file name.
     * @return True if copy was done successfully.
     */
    public boolean copyToModuleConfigFolderIfNotExists(ClassLoader classLoader, String sourcePath,
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
