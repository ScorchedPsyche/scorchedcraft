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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.mixin;

import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Function;

@Mixin(TextRenderer.class)
public abstract class TextRendererMixin {
//    @Mixin(targets = "TextRenderer$Drawer.class")
//    public static class InternalDrawer{
//
//    }
    
    /**
     * @param color the text color in 0xAARRGGBB
     * @param outlineColor the outline color in 0xAARRGGBB
     */
//    public void drawWithOutline(OrderedText text, float x, float y, int color, int outlineColor, Matrix4f matrix, VertexConsumerProvider vertexConsumers, int light) {
//        int i = outlineColor;
//        InternalDrawer.
//        TextRenderer.Drawer drawer = new TextRenderer.Drawer(vertexConsumers, 0.0F, 0.0F, i, false, matrix, TextRenderer.TextLayerType.NORMAL, light);
//
//        for(int j = -1; j <= 1; ++j) {
//            for(int k = -1; k <= 1; ++k) {
//                if (j != 0 || k != 0) {
//                    float[] fs = new float[]{x};
//                    text.accept((l, style, m) -> {
//                        boolean bl = style.isBold();
//                        FontStorage fontStorage = this.getFontStorage(style.getFont());
//                        Glyph glyph = fontStorage.getGlyph(m);
//                        drawer.x = fs[0] + (float)j * glyph.getShadowOffset();
//                        drawer.y = y + (float)k * glyph.getShadowOffset();
//                        fs[0] += glyph.getAdvance(bl);
//                        return drawer.accept(l, style.withColor(i), m);
//                    });
//                }
//            }
//        }
//
//        TextRenderer.Drawer drawer2 = new TextRenderer.Drawer(vertexConsumers, x, y, tweakTransparency(color), false, matrix, TextRenderer.TextLayerType.POLYGON_OFFSET, light);
//        text.accept(drawer2);
//        drawer2.drawLayer(0, x);
//    }
}
