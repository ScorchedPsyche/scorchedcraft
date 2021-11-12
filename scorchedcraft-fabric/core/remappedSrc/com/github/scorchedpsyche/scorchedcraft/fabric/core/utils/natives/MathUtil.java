/*
 * Copyright (c) 2021. ScorchedPsyche
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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives;

public class MathUtil {
    public static double distanceBetween3dCoordinates(float x0, float y0, float z0, float x1, float y1, float z1)
    {
        return Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2) + Math.pow(z0 - z1, 2));
    }
    
    public static int clamp(int value, int min, int max)
    {
        if (value < min){
            return min;
        }
        else if (value > max){
            return max;
        }
        
        return value;
    }
    
    public static float clamp(float value, float min, float max)
    {
        if (value < min){
            return min;
        }
        else if (value > max){
            return max;
        }
        
        return value;
    }
}
