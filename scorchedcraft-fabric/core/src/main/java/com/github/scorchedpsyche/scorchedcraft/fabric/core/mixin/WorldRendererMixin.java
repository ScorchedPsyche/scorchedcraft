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

package com.github.scorchedpsyche.scorchedcraft.fabric.core.mixin;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.renderers.DebugRenderer;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.RGBAModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ServerUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
	@Shadow public abstract void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f);
	
	Box box;
	Camera camera;
//	private static BufferBuilder buffer = new BufferBuilder(256);
	
	@Inject( method = "render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V",
		at = @At("TAIL") )
//	at = @At( value = "INVOKE", shift = At.Shift.AFTER,
//		target = "Lnet/minecraft/client/render/WorldRenderer;checkEmpty(Lnet/minecraft/client/util/math/MatrixStack;)V") )
	private void renderDebug(CallbackInfo info) {
		if( (!DebugRenderer.blocksOnlyOutline.isEmpty() || !DebugRenderer.blocksOnlyFaces.isEmpty())
			&& MinecraftClient.getInstance().world != null )
		{
			camera = MinecraftClient.getInstance().gameRenderer.getCamera();
//			buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
			if( camera != null )
			{
				BufferBuilderStorage bufferBuilderStorage =
					((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getBufferBuilders();
				VertexConsumerProvider.Immediate immediate = bufferBuilderStorage.getEntityVertexConsumers();
				VertexConsumer vertexConsumer = immediate.getBuffer(RenderLayer.getLines());
				BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
				
				this.drawBlocksOnlyOutline(vertexConsumer);
				this.drawBlocksOnlyFaces();
				this.renderText();
			}
		}
	}
	
	public void renderText() {
		MinecraftClient.getInstance().getProfiler().swap("outline");
		Text text = Text.of("32");
		MatrixStack matrixStack = new MatrixStack();
		Position textPos = new Vec3d(0, 100, 0);
		BufferBuilderStorage bufferBuilderStorage =
			((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getBufferBuilders();
		VertexConsumerProvider.Immediate immediate = bufferBuilderStorage.getEntityVertexConsumers();
		
		double d = camera.getPos().squaredDistanceTo(textPos.getX(), textPos.getY(), textPos.getZ());
		if (!(d > 4096.0D)) {
			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));
			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(camera.getYaw() + 180.0F));
			matrixStack.translate(
				textPos.getX() - camera.getPos().x,
				textPos.getY() - camera.getPos().y,
				textPos.getZ() - camera.getPos().z);
			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-camera.getYaw()));
			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));
			matrixStack.scale(-0.025F, -0.025F, 0.025F);
			
			Matrix4f matrix4f = matrixStack.peek().getModel();
			float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
			int j = (int) (g * 255.0F) << 24;
			TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
			float h = (float) (-textRenderer.getWidth(text) / 2);
			textRenderer.draw(text, h, 0, 553648127, false, matrix4f, immediate, false, j, 16);
			textRenderer.draw(text, h, 0, -1, false, matrix4f, immediate, false, 0, 15728880);
			
			
//			if (seeThrough) {
//				textRenderer.draw((Text) text, h, (float) i, -1, false, matrix4f, immediate, false, 0, 16);
//			}
			
			matrixStack.pop();
		}
	}
	
	private void drawBlocksOnlyFaces()
	{
		GL11.glDepthFunc(GL11.GL_ALWAYS);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferBuilder = tessellator.getBuffer();
		RenderSystem.enableBlend();
		RenderSystem.blendFuncSeparate(
			GlStateManager.SrcFactor.SRC_ALPHA,
			GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
			GlStateManager.SrcFactor.ONE,
			GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
//		RenderSystem.blendFuncSeparate(
//			GlStateManager.SrcFactor.SRC_ALPHA,
//			GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
//			GlStateManager.SrcFactor.ONE,
//			GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
		
//		bufferBuilder.begin(RenderLayer.getTranslucent().getDrawMode(), RenderLayer.getTranslucent().getVertexFormat());
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
		
		for( Map.Entry<BlockPos, RGBAModel> blockFaces : DebugRenderer.blocksOnlyFaces.entrySet() )
		{
			box = Box.from( new Vec3d(blockFaces.getKey().getX() - camera.getPos().x,
				blockFaces.getKey().getY() - camera.getPos().y,
				blockFaces.getKey().getZ() - camera.getPos().z) );
			
			MatrixStack matrixStack = new MatrixStack();
			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch()));
			matrixStack.multiply( Vec3f.POSITIVE_Y.getDegreesQuaternion( camera.getYaw() + 180.0F) );
			Matrix4f matrix = matrixStack.peek().getModel();
			
			drawVerticesFor(
				bufferBuilder,
				matrix,
				box,
				blockFaces.getValue().getRed(),
				blockFaces.getValue().getGreen(),
				blockFaces.getValue().getBlue(),
				blockFaces.getValue().getAlpha()
				);
		}
		tessellator.draw();
		RenderSystem.disableBlend();
		RenderSystem.defaultBlendFunc();
		GL11.glDepthFunc(GL11.GL_LEQUAL);
	}
	
	private static void drawVerticesFor(BufferBuilder buffer, Matrix4f matrix, Box box, float red, float green, float blue, float alpha)
	{
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();

		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();

		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();

		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();

		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();

		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
	}
	
	private void drawBlocksOnlyOutline(VertexConsumer vertexConsumer)
	{
		MatrixStack matrixStack = new MatrixStack();
		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch()));
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion( camera.getYaw() + 180.0F));
		
		for( Map.Entry<BlockPos, RGBAModel> blockOutline : DebugRenderer.blocksOnlyOutline.entrySet() )
		{
			box = Box.from( new Vec3d(blockOutline.getKey().getX() - camera.getPos().x,
				blockOutline.getKey().getY() - camera.getPos().y,
				blockOutline.getKey().getZ() - camera.getPos().z) );
			
			WorldRenderer.drawBox(
				matrixStack,
				vertexConsumer,
				box,
				blockOutline.getValue().getRed(),
				blockOutline.getValue().getGreen(),
				blockOutline.getValue().getBlue(),
				blockOutline.getValue().getAlpha() );
		}
	}
	
//	private static void drawVerticesFor(BufferBuilder buffer, Matrix4f matrix, Box box, BlockState state,
//        BlockView world, BlockPos pos, double cameraX, double cameraY, double cameraZ, float color, float alpha)
//	{
//		box = box.offset(pos.getX() - cameraX, pos.getY() - cameraY, pos.getZ() - cameraZ);
//		if (Block.shouldDrawSide(state, world, pos, Direction.DOWN, pos.add(1, 1, 1))) {
//			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
//		}
//		if (Block.shouldDrawSide(state, world, pos, Direction.UP, pos.add(1, 1, 1))) {
//			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
//		}
//		if (Block.shouldDrawSide(state, world, pos, Direction.NORTH, pos.add(1, 1, 1))) {
//			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
//		}
//		if (Block.shouldDrawSide(state, world, pos, Direction.SOUTH, pos.add(1, 1, 1))) {
//			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
//		}
//		if (Block.shouldDrawSide(state, world, pos, Direction.WEST, pos.add(1, 1, 1))) {
//			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
//		}
//		if (Block.shouldDrawSide(state, world, pos, Direction.EAST, pos.add(1, 1, 1))) {
//			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
//			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
//		}
//	}
}
