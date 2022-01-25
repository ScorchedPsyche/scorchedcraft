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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.model;

public class TitleAndSubtitleModel {
    public TitleAndSubtitleModel(int fadeIn, int stay, int fadeOut)
    {
        this.fadeIn = fadeIn;
        this.stay = stay;
        this.fadeOut = fadeOut;
    }
    
    public StringFormattedModel title = new StringFormattedModel();
    public StringFormattedModel subtitle = new StringFormattedModel();
    private int fadeIn = 0;
    private int stay = 20;
    private int fadeOut = 0;
    
    public int getFadeIn() {
        return fadeIn;
    }
    
    public int getStay() {
        return stay;
    }
    
    public int getFadeOut() {
        return fadeOut;
    }
}