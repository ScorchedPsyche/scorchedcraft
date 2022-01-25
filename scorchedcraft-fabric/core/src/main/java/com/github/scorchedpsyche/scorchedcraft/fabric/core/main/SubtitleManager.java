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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.main;

public class SubtitleManager {
    private StringBuilder subtitleMessage = new StringBuilder();
    
    public boolean isEmpty()
    {
        return subtitleMessage.length() == 0;
    }
    
    public String getText()
    {
        return subtitleMessage.toString();
    }
    
    public void reset()
    {
        subtitleMessage.setLength(0);
    }
    
    public void addToStart(String text)
    {
        subtitleMessage.insert(0, text);
    }
    
    public void addToStart(StringBuilder text)
    {
        subtitleMessage.insert(0, text);
    }
    
    public void addToEnd(String text)
    {
        subtitleMessage.append(text);
    }
    
    public void addToEnd(StringBuilder text)
    {
        subtitleMessage.append(text);
    }
}