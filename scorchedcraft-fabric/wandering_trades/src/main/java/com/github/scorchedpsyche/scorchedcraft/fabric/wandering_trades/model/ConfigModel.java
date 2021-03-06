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

package com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.model;

import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.model.config.MaximumUniqueTradeOffersConfigModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.model.config.WhitelistConfigModel;

public class ConfigModel {
    public ConfigModel(){}
    
    public boolean remove_default_trades = true;
    public MaximumUniqueTradeOffersConfigModel maximum_unique_trade_offers;
    public WhitelistConfigModel whitelist =  new WhitelistConfigModel();
}
