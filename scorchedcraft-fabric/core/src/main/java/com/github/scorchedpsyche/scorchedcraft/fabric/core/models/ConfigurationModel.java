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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.models;

public class ConfigurationModel
{
    public ConfigurationModel(){}
    public ConfigurationModel(String module, String name, boolean value)
    {
        this(module, name);
        this.value_bool = value;
    }
    public ConfigurationModel(String module, String name, int value)
    {
        this(module, name);
        this.value_int = value;
    }
    public ConfigurationModel(String module, String name, String value)
    {
        this(module, name);
        this.value_string = value;
    }
    
    private ConfigurationModel(String module, String name)
    {
        this.module = module;
        this.name = name;
    }
    
    private int id;
    private String module;
    private String name;
    private boolean value_bool;
    private int value_int;
    private String value_string;
}
