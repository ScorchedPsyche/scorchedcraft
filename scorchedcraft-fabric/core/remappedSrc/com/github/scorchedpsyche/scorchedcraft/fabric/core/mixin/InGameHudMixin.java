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

import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ServerUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
//	@Shadow public abstract void render(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f);
	
//	Box box;
	Camera camera;
//	private static BufferBuilder buffer = new BufferBuilder(256);
	@Inject( method = "renderStatusEffectOverlay(Lnet/minecraft/client/util/math/MatrixStack;)V",
		at = @At("RETURN"))
//@Inject( method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V",
//		at = @At( value = "INVOKE", shift = At.Shift.AFTER, ordinal = 1,
//			target = "Lnet/minecraft/client/gui/hud/InGameHud;renderStatusEffectOverlay(Lnet/minecraft/client/util/math/MatrixStack;)V") )
	private void renderDebug(CallbackInfo info) {
//		ServerUtil.sendMessageToAllPlayers("1");
//		if(
////			(!DebugRenderer.blocksOnlyOutline.isEmpty() || !DebugRenderer.blocksOnlyFaces.isEmpty())
////			&&
//			MinecraftClient.getInstance().world != null )
//		{
//			ServerUtil.sendMessageToAllPlayers("2");
//			camera = MinecraftClient.getInstance().gameRenderer.getCamera();
////			buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
//			if( camera != null )
//			{
//				ServerUtil.sendMessageToAllPlayers("3");
////				BufferBuilderStorage bufferBuilderStorage =
////					((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getBufferBuilders();
////				VertexConsumerProvider.Immediate immediate = bufferBuilderStorage.getEntityVertexConsumers();
////				VertexConsumer vertexConsumer = immediate.getBuffer(RenderLayer.getLines());
////				BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
//
////				this.drawBlocksOnlyOutline(vertexConsumer);
////				this.drawBlocksOnlyFaces();
//				this.renderText();
//
////				for( Map.Entry<BlockPos, RGBAModel> blockFaces : DebugRenderer.blocksOnlyFaces.entrySet() )
////				{
//////					box = Box.from( new Vec3d(blockFaces.getKey().getX() - camera.getPos().x,
//////						blockFaces.getKey().getY() - camera.getPos().y,
//////						blockFaces.getKey().getZ() - camera.getPos().z) ););
//////					WorldRenderer.drawBox(
//////						bufferBuilder,
//////						blockFaces.getKey().getX(),
//////						blockFaces.getKey().getY(),
//////						blockFaces.getKey().getZ(),
//////						blockFaces.getKey().getX() + 1,
//////						blockFaces.getKey().getY() + 1,
//////						blockFaces.getKey().getZ() + 1,
//////						blockFaces.getValue().getRed(),
//////						blockFaces.getValue().getGreen(),
//////						blockFaces.getValue().getBlue(),
//////						blockFaces.getValue().getAlpha() );
////				}
//			}
////			buffer.end();
////			BufferRenderer.draw(buffer);
//		}
	}
	
	public void renderText()
	{
//		MAYBE THIS GETS CLEARED LATER ON THE REDNGING PIPELINE. SEE IF WE HAVE TO DO THIS MIXING LATER
		ServerUtil.sendMessageToAllPlayers("rendering text");
		Position textPos = new Vec3d(0, 90, 0);
		Text text = Text.of("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Double scale = 1.0;
		
		MatrixStack matrixStack = new MatrixStack();
		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch() ));
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion( camera.getYaw() + 180.0F));
		matrixStack.translate(
			textPos.getX() - camera.getPos().x,
			textPos.getY() - camera.getPos().y,
			textPos.getZ() - camera.getPos().z );
		matrixStack.push();
		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion( -camera.getYaw() ));
		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch() ));
//		matrix.translate(offX, offY, 0.0)
//		matrixStack.scale(-0.025f * scale.floatValue(), -0.025f * scale.floatValue(), 1f);
		
		MinecraftClient.getInstance().textRenderer.draw(
			matrixStack,
			text,
			-MinecraftClient.getInstance().textRenderer.getWidth(text) / 2f,
			0f,
			-1
		);
		
//		Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
//		ServerUtil.sendMessageToAllPlayers("text rendered");
//
//		MatrixStack matrixStack = new MatrixStack();
//
////        matrixStack.scale(2, 2, 2);
//		matrixStack.translate(0, 100, 0);
//		matrixStack.multiply( camera.getRotation() );
////        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch()));
////        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion( camera.getYaw() + 180.0F));
////        matrixStack.translate();
////        matrixStack.push();
//
//		MinecraftClient.getInstance().textRenderer.draw(
//			matrixStack,
//			Text.of("testing this piece of shit"),
//			-MinecraftClient.getInstance().textRenderer.getWidth(Text.of("testing this piece of shit")) / 2F,
//			0,
//			-1);
	}
	
//	private void drawBlocksOnlyFaces()
//	{
//		Tessellator tessellator = Tessellator.getInstance();
//		BufferBuilder bufferBuilder = tessellator.getBuffer();
//		RenderSystem.enableBlend();
//		RenderSystem.setShader(GameRenderer::getPositionColorShader);
//		RenderSystem.blendFuncSeparate(
//			GlStateManager.SrcFactor.SRC_ALPHA,
//			GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
//			GlStateManager.SrcFactor.ONE,
//			GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
//		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
//
//		for( Map.Entry<BlockPos, RGBAModel> blockFaces : DebugRenderer.blocksOnlyFaces.entrySet() )
//		{
//			box = Box.from( new Vec3d(blockFaces.getKey().getX() - camera.getPos().x,
//				blockFaces.getKey().getY() - camera.getPos().y,
//				blockFaces.getKey().getZ() - camera.getPos().z) );
//
//			MatrixStack matrixStack = new MatrixStack();
//			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch()));
//			matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion( camera.getYaw() + 180.0F));
//			Matrix4f matrix = matrixStack.peek().getModel();
//
//			drawVerticesFor(
//				bufferBuilder,
//				matrix,
//				box,
//				blockFaces.getValue().getRed(),
//				blockFaces.getValue().getGreen(),
//				blockFaces.getValue().getBlue(),
//				blockFaces.getValue().getAlpha()
//				);
//
////			WorldRenderer.drawBox(
////				bufferBuilder,
////				box.minX,
////				box.minY,
////				box.minZ,
////				box.maxX,
////				box.maxY,
////				box.maxZ,
//////				posWithCameraOffset.getY(),
//////				posWithCameraOffset.getZ(),
//////				posWithCameraOffset.getX() + 1,
//////				posWithCameraOffset.getY() + 1,
//////				posWithCameraOffset.getZ() + 1,
////				blockFaces.getValue().getRed(),
////				blockFaces.getValue().getGreen(),
////				blockFaces.getValue().getBlue(),
////				blockFaces.getValue().getAlpha() );
//		}
//		tessellator.draw();
//	}
//
//	private static void drawVerticesFor(BufferBuilder buffer, Matrix4f matrix, Box box, float red, float green, float blue, float alpha) {
//
//		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
//
//		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
//
//		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
//
//		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
//
//		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
//
//		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
//	}
//
//	private void drawBlocksOnlyOutline(VertexConsumer vertexConsumer)
//	{
//		MatrixStack matrixStack = new MatrixStack();
//		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch()));
//		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion( camera.getYaw() + 180.0F));
//
//		for( Map.Entry<BlockPos, RGBAModel> blockOutline : DebugRenderer.blocksOnlyOutline.entrySet() )
//		{
//			box = Box.from( new Vec3d(blockOutline.getKey().getX() - camera.getPos().x,
//				blockOutline.getKey().getY() - camera.getPos().y,
//				blockOutline.getKey().getZ() - camera.getPos().z) );
//
//			WorldRenderer.drawBox(
//				matrixStack,
//				vertexConsumer,
//				box,
//				blockOutline.getValue().getRed(),
//				blockOutline.getValue().getGreen(),
//				blockOutline.getValue().getBlue(),
//				blockOutline.getValue().getAlpha() );
//		}
//	}
//
////	private static void drawVerticesFor(BufferBuilder buffer, Matrix4f matrix, Box box, BlockState state,
////        BlockView world, BlockPos pos, double cameraX, double cameraY, double cameraZ, float color, float alpha)
////	{
////		box = box.offset(pos.getX() - cameraX, pos.getY() - cameraY, pos.getZ() - cameraZ);
////		if (Block.shouldDrawSide(state, world, pos, Direction.DOWN, pos.add(1, 1, 1))) {
////			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
////		}
////		if (Block.shouldDrawSide(state, world, pos, Direction.UP, pos.add(1, 1, 1))) {
////			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
////		}
////		if (Block.shouldDrawSide(state, world, pos, Direction.NORTH, pos.add(1, 1, 1))) {
////			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
////		}
////		if (Block.shouldDrawSide(state, world, pos, Direction.SOUTH, pos.add(1, 1, 1))) {
////			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
////		}
////		if (Block.shouldDrawSide(state, world, pos, Direction.WEST, pos.add(1, 1, 1))) {
////			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
////		}
////		if (Block.shouldDrawSide(state, world, pos, Direction.EAST, pos.add(1, 1, 1))) {
////			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(color, color, color, alpha).next();
////			buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(color, color, color, alpha).next();
////		}
////	}
}
