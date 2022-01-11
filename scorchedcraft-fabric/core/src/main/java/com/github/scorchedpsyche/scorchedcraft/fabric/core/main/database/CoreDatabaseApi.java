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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.main.database;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.database.DatabaseManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.database.DatabaseTables;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.MessageModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoreDatabaseApi
{
    public boolean setupAndVerifySqlTable()
    {
        // Check if Core table exists
        if( !DatabaseManager.database.tableExists( DatabaseTables.Configuration.table_name) )
        {
            String sql = "CREATE TABLE "
                + DatabaseTables.Configuration.table_name + "(\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	" + DatabaseTables.Configuration.Table.module + " TEXT,\n"
                + "	" + DatabaseTables.Configuration.Table.name + " TEXT,\n"
                + "	" + DatabaseTables.Configuration.Table.value_bool + " INTEGER,\n"
                + "	" + DatabaseTables.Configuration.Table.value_int + " INTEGER,\n"
                + "	" + DatabaseTables.Configuration.Table.value_string + " TEXT\n);";
        
            // Doesn't exist. Create it
            if ( DatabaseManager.database.executeSqlAndDisplayErrorIfNeeded(sql) )
            {
                // Successfully created table
                ConsoleUtil.logMessage(ScorchedCraftManager.Core.Name.full,
                    "Table successfully created: " + DatabaseTables.Configuration.table_name);
                return true;
            }
        
            // If we got here table creation failed
            ConsoleUtil.logError( ScorchedCraftManager.Core.Name.full,
                "Failed to create table: " + DatabaseTables.Configuration.table_name);
        
            return false;
        }
        
        // Check if Core table exists
        if( !DatabaseManager.database.tableExists( DatabaseTables.Core.playerMessagesTableName) )
        {
            String sql = "CREATE TABLE "
                + DatabaseTables.Core.playerMessagesTableName + "(\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	" + DatabaseTables.Core.MessagesTable.type + " INTEGER,\n"
                + "	" + DatabaseTables.Core.MessagesTable.pending + " INTEGER,\n"
                + "	" + DatabaseTables.Core.MessagesTable.player_uuid + " TEXT,\n"
                + "	" + DatabaseTables.Core.MessagesTable.date_end + " INTEGER,\n"
                + "	" + DatabaseTables.Core.MessagesTable.message + " TEXT NOT NULL,\n"
                + "	" + DatabaseTables.Core.MessagesTable.message_cached + " TEXT\n);";
            
            // Doesn't exist. Create it
            if ( DatabaseManager.database.executeSqlAndDisplayErrorIfNeeded(sql) )
            {
                // Successfully created table
                ConsoleUtil.logMessage(ScorchedCraftManager.Core.Name.full,
                    "Table successfully created: " + DatabaseTables.Core.playerMessagesTableName);
                return true;
            }
            
            // If we got here table creation failed
            ConsoleUtil.logError( ScorchedCraftManager.Core.Name.full,
                "Failed to create table: " + DatabaseTables.Core.playerMessagesTableName);
            
            return false;
        }
        
        // If we got here table exists
        return true;
    }
    
    public List<MessageModel> fetchPendingServerMessages()
    {
        List<MessageModel> messages = new ArrayList<>();
        String sql = "SELECT * FROM " + DatabaseTables.Core.playerMessagesTableName + " WHERE \n"
            + DatabaseTables.Core.MessagesTable.type + " = "
            + ScorchedCraftManager.Core.Messages.Type.ServerMessageToAllPlayers.ordinal()
            + " AND "
            + DatabaseTables.Core.MessagesTable.pending + " = 1";
        
        try (Connection conn = DriverManager.getConnection(
            DatabaseManager.database.getDatabaseUrl());
             Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            
            // Check if there are any messages
            if ( !DatabaseUtil.isResultSetNullOrEmpty(rs) ) {
                while(rs.next())
                {
                    // Add message to the list
                    messages.add(new MessageModel().loadFromResultSet(rs));

//                    // Check if message is already finished
//                    if( messages.get(messages.size()-1).getDate_end() < DateUtil.Time.getUnixNow() )
//                    {
//                        // Message finished. Remove this message from the returning list
//                        messages.remove(messages.size()-1);
//                    }
                }
            }
        } catch (SQLException e) {
            ConsoleUtil.logError( ScorchedCraftManager.Core.Name.full,
                "SQLite query failed for 'fetchServerMessages': " + sql);
            ConsoleUtil.logError( e.getMessage() );
        }
        
        return messages;
    }
    
    public void markMessageAsNotPending(int messageId)
    {
        String sql = "UPDATE " + DatabaseTables.Core.playerMessagesTableName
            + " SET " + DatabaseTables.Core.MessagesTable.pending + " = 0"
            + " WHERE id = " + messageId;
        
        if ( !DatabaseManager.database.executeSqlAndDisplayErrorIfNeeded(sql) )
        {
            // Error updating entry
            ConsoleUtil.logError( ScorchedCraftManager.Core.Name.full,
                "markMessageAsNotPending failed to update table: " + DatabaseTables.Core.playerMessagesTableName);
        }
    }
    
    public MessageModel newServerMessage(MessageModel message)
    {
        String sql = "INSERT INTO " + DatabaseTables.Core.playerMessagesTableName +
            " ("
            + DatabaseTables.Core.MessagesTable.type + ", "
            + DatabaseTables.Core.MessagesTable.pending + ", "
            + DatabaseTables.Core.MessagesTable.player_uuid + ", "
            + DatabaseTables.Core.MessagesTable.date_end + ", "
            + DatabaseTables.Core.MessagesTable.message + ", "
            + DatabaseTables.Core.MessagesTable.message_cached + ") \n" +
            "VALUES("
            + message.getType() + ", "
            + message.isPending() + ", '"
            + message.getPlayer_uuid() + "', "
            + message.getDate_end() + ", '"
            + message.getMessage() + "', '"
            + message.getMessage_cached() + "') \n";
        
        if ( DatabaseManager.database.executeSqlAndDisplayErrorIfNeeded(sql) )
        {
            sql = "SELECT * FROM " + DatabaseTables.Seasons.table_name +
                " ORDER BY id DESC LIMIT 1";
            
            try (Connection conn = DriverManager.getConnection(
                DatabaseManager.database.getDatabaseUrl());
                 Statement stmt = conn.createStatement()) {
                ResultSet rs = stmt.executeQuery(sql);
                
                if( !DatabaseUtil.isResultSetNullOrEmpty(rs) )
                {
                    return new MessageModel().loadFromResultSet(rs);
                }
            } catch (SQLException e) {
                ConsoleUtil.logError(
                    "SQLite query failed: " + sql);
                ConsoleUtil.logError( e.getMessage() );
            }
        }
        
        // Error updating entry
        ConsoleUtil.logError( ScorchedCraftManager.Core.Name.full,
            "newServerMessage failed to insert into table: " + DatabaseTables.Core.playerMessagesTableName);
        
        return null;
    }
}
