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

package com.github.scorchedpsyche.scorchedcraft.fabric.wandering_trades.model.config;

public class WhitelistConfigModel {
    public boolean enable_synchronization = false;
    public int number_of_player_head_offers = 0;
    public int heads_rewarded_per_trade = 0;
    public int maximum_number_of_trades = 0;
    public int experience_rewarded_for_each_trade = 0;
    public int trade_price_multiplier = 0;
    public PriceWhitelistConfigModel price;
}
