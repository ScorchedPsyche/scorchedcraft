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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.model.render;

public class ElementRenderModel {
    public enum RenderType{
        IF_VISIBLE,
        THROUGH_BLOCKS
    }
    
    public enum RenderVisibility {
        DISPLAY_ALWAYS,
        HIDE_WHEN_SNEAKING
    }
    
    protected RenderType renderType = RenderType.IF_VISIBLE;
    protected RenderVisibility renderVisibility = RenderVisibility.DISPLAY_ALWAYS;
    
    public RenderType getRenderType() {
        return renderType;
    }
    public RenderVisibility getRenderVisibility() {
        return renderVisibility;
    }
}
