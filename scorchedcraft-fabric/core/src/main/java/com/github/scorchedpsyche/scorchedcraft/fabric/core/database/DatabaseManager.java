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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.database;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.database.types.SQLiteDatabase;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.interfaces.IDatabase;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.FolderUtil;

import java.io.File;

public class DatabaseManager
{
    public enum DatabaseType {
        SQLite,
        MySQL
    }

    public DatabaseManager(DatabaseType databaseType)
    {
        switch(databaseType)
        {
            case MySQL:
                break;

            default: // SQLite
                database = new SQLiteDatabase(
                    FolderUtil.getOrCreateSuiteRootFolder() + File.separator +
                        ScorchedCraftManager.Name.pomXml + ".db" );
                break;
        }
    }

    public static IDatabase database;
}
