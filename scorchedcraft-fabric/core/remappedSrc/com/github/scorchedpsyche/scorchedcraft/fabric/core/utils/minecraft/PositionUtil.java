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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.natives.MathUtil;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

public class PositionUtil {
    public static double distanceBetweenPositions(@NotNull BlockPos p1, @NotNull BlockPos p2)
    {
        return MathUtil.distanceBetween3dCoordinates( p1.getX(), p1.getY(), p1.getZ(), p2.getX(), p2.getY(), p2.getZ() );
    }
}
