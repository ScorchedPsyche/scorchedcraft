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

package com.github.scorchedpsyche.scorchedcraft.fabric.seasons.main;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.database.DatabaseManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.database.DatabaseTables;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.DatabaseUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.seasons.model.SeasonModel;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
public class SeasonsDatabaseApi
{
    public boolean setupAndVerifySqlTable()
    {
        // Check if Seasons table exists
        if( !DatabaseManager.database.tableExists( DatabaseTables.Seasons.table_name) )
        {
            // Doesn't exists. Create it
            if ( DatabaseManager.database.executeSqlAndDisplayErrorIfNeeded(
                "CREATE TABLE " + DatabaseTables.Seasons.table_name + "(\n"
                    + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                    + "	" + DatabaseTables.Seasons.Table.number + " NUMERIC DEFAULT 1 UNIQUE NOT NULL,\n"
                    + "	" + DatabaseTables.Seasons.Table.title + " TEXT,\n"
                    + "	" + DatabaseTables.Seasons.Table.subtitle + " TEXT,\n"
                    + "	" + DatabaseTables.Seasons.Table.status + " INTEGER DEFAULT " + ScorchedCraftManager.Seasons.Status.Inactive.ordinal() + ",\n"
                    + "	" + DatabaseTables.Seasons.Table.account + " NUMERIC DEFAULT 1,\n"
                    + "	" + DatabaseTables.Seasons.Table.date_start + " INTEGER DEFAULT 0,\n"
                    + "	" + DatabaseTables.Seasons.Table.date_end + " INTEGER DEFAULT 0,\n"
                    + "	" + DatabaseTables.Seasons.Table.minecraft_version_start + " TEXT DEFAULT 0,\n"
                    + "	" + DatabaseTables.Seasons.Table.minecraft_version_end + " TEXT DEFAULT 0\n"
                    + ");") )
            {
                // Successfully created table
                ConsoleUtil.logMessage( ScorchedCraftManager.Seasons.Name.full,
                    "Table successfully created: " + DatabaseTables.Seasons.table_name);

                String sql = "INSERT INTO " + DatabaseTables.Seasons.table_name + " ("
                    + DatabaseTables.Seasons.Table.number + ", "
                    + DatabaseTables.Seasons.Table.title + ", "
                    + DatabaseTables.Seasons.Table.subtitle + ", "
                    + DatabaseTables.Seasons.Table.status + ", "
                    + DatabaseTables.Seasons.Table.account + ", "
                    + DatabaseTables.Seasons.Table.date_start + ", "
                    + DatabaseTables.Seasons.Table.minecraft_version_start + ") \n" +
                    "VALUES("
                    + "1, "
                    + "'No Title', "
                    + "'No Subtitle', "
                    + "'" + ScorchedCraftManager.Seasons.Status.Active.ordinal() + "', "
                    + true + ", "
                    + 0 + ", "
                    + 0 + ")";

                if ( DatabaseManager.database.executeSqlAndDisplayErrorIfNeeded(sql) )
                {
                    ConsoleUtil.logMessage( ScorchedCraftManager.Seasons.Name.full,
                        "Created Season 1! Change the title and subtitle as desired.");
                } else {
                    ConsoleUtil.logError( ScorchedCraftManager.Seasons.Name.full,
                        "Failed to create first season.");
                }

                return true;
            }

            // If we got here table creation failed
            ConsoleUtil.logError( ScorchedCraftManager.Seasons.Name.full,
                "Failed to create table: " + DatabaseTables.Seasons.table_name);

            return false;
        }

        // If we got here table exists
        return true;
    }

    @Nullable
    public SeasonModel fetchCurrentSeason()
    {
        String sql = "SELECT * FROM " + DatabaseTables.Seasons.table_name + " WHERE "
            + DatabaseTables.Seasons.Table.status + " = " + ScorchedCraftManager.Seasons.Status.Started.ordinal()
            + " OR "
            + DatabaseTables.Seasons.Table.status + " = " + ScorchedCraftManager.Seasons.Status.Active.ordinal()
            + " LIMIT 1";

        try (Connection conn = DriverManager.getConnection(
            DatabaseManager.database.getDatabaseUrl());
             Statement stmt = conn.createStatement())
        {
            ResultSet rs = stmt.executeQuery(sql);

            if( !DatabaseUtil.isResultSetNullOrEmpty(rs) )
            {
                return new SeasonModel().loadDataFromResultSet(rs);
            }
        } catch (SQLException e) {
            ConsoleUtil.logError( ScorchedCraftManager.SpectatorMode.Name.full,
                "SQLite query failed for 'fetchCurrentSeason': " + sql);
            ConsoleUtil.logError( e.getMessage() );
        }

        return null;
    }

    @Nullable
    public Integer fetchNextAvailableSeasonNumber()
    {
        String sql = "SELECT * FROM " + DatabaseTables.Seasons.table_name + " ORDER BY "
            + DatabaseTables.Seasons.Table.number + " DESC ";

        try (Connection conn = DriverManager.getConnection(
            DatabaseManager.database.getDatabaseUrl());
             Statement stmt = conn.createStatement())
        {
            ResultSet rs = stmt.executeQuery(sql);

            if( !DatabaseUtil.isResultSetNullOrEmpty(rs) )
            {
                return new SeasonModel().loadDataFromResultSet(rs).getNumber() + 1;
            }
        } catch (SQLException e) {
            ConsoleUtil.logError( ScorchedCraftManager.SpectatorMode.Name.full,
                "SQLite query failed for 'fetchCurrentSeason': " + sql);
            ConsoleUtil.logError( e.getMessage() );
        }

        return null;
    }
}