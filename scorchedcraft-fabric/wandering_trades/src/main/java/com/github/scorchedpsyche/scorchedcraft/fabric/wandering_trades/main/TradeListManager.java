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

package com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.main;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.scorchedcraft.ScorchedCraftManager;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.model.TradeEntryModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.model.TradeListModel;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.Collections;

public class TradeListManager {
    public TradeListManager(String tradeListsFolder)
    {
        loadTradeLists(tradeListsFolder);
    }
    
    public TradeListModel Trades = new TradeListModel();
    
    private void loadTradeLists(String tradeListsFolder)
    {
        File[] files = new File(tradeListsFolder).listFiles();
        if( files != null )
        {
            for(File file : files)
            {
                try
                {
                    Reader reader = Files.newBufferedReader(file.toPath());
                    
                    TradeEntryModel[] json = new Gson().fromJson(reader, TradeEntryModel[].class);
                    
                    if (json != null)
                    {
                        Collections.addAll(Trades.offers, json);
                        
                        ConsoleUtil.logMessage(ScorchedCraftManager.WanderingTrades.Name.full,
                            "LOADED FILE: " + file.getName());
                    }
                    
                    reader.close();
                } catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
            
            files = null;
        }
    }
}
