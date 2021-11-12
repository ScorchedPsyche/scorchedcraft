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

import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.renderer.DebugRenderer;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.ColorModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.render.BlockRenderModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.render.ElementRenderModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.render.TextRenderModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.MinecraftColors;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ServerUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.Glyph;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.OrderedText;
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
	
	MinecraftClient mcClient = MinecraftClient.getInstance();
	GameRenderer gameRenderer = mcClient.gameRenderer;
	Camera camera;
	double fov;
//	float viewDistance;
//	private static BufferBuilder buffer = new BufferBuilder(256);
	
	@Inject( method = "render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V",
		at = @At("TAIL") )
//	@Inject( method = "render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V",
//		at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", shift = At.Shift.AFTER) )
	private void renderDebug(CallbackInfo info) {
		// Check if should render either text or blocks
		if( DebugRenderer.shouldRender() )
		{
			// GameRenderer initialization
//			this.camera = new Camera();
			this.camera = gameRenderer.getCamera();
			
			// GameRenderer.render > GameRenderer.renderWorld
			MatrixStack matrix = new MatrixStack();
//			if (this.mcClient.getCameraEntity() == null) {
//				this.mcClient.setCameraEntity(this.mcClient.player);
//			}
//			this.gameRenderer.updateTargetedEntity(mcClient.getTickDelta());
//			Camera camera = this.camera;
//			this.viewDistance = (float)(this.mcClient.options.viewDistance * 16);
//			MatrixStack matrixStack = new MatrixStack();
//			this.fov = ( (IGameRenderer)gameRenderer ).invokeGetFov( camera, mcClient.getTickDelta(), false );
//			matrixStack.peek().getModel().multiply( this.gameRenderer.getBasicProjectionMatrix(fov) );
//			Matrix4f matrix4f = matrixStack.peek().getModel();
//			this.gameRenderer.loadProjectionMatrix(matrix4f);
//			camera.update(
//				this.mcClient.world,
//				(Entity)(this.mcClient.getCameraEntity() == null ? this.mcClient.player : this.mcClient.getCameraEntity()),
//				!this.mcClient.options.getPerspective().isFirstPerson(),
//				this.mcClient.options.getPerspective().isFrontView(),
//				this.mcClient.getTickDelta());
			
			matrix.multiply( Vec3f.POSITIVE_X.getDegreesQuaternion(this.camera.getPitch()) );
			matrix.multiply( Vec3f.POSITIVE_Y.getDegreesQuaternion(this.camera.getYaw() + 180.0F) );
//			mcClient.worldRenderer.setupFrustum(
//				matrixStack,
//				camera.getPos(),
//				mcClient.gameRenderer.getBasicProjectionMatrix( Math.max(fov, mcClient.options.fov) ) );
			
//			this.drawBlocks();
			this.drawText(matrix, this.camera);
			
			
			// Should render! Check if camera is valid
//			this.camera = gameRenderer.getCamera();
//			this.camera = new Camera();
//			if (this.mcClient.getCameraEntity() == null) {
//				this.mcClient.setCameraEntity(this.mcClient.player);
//			}
//			this.gameRenderer.updateTargetedEntity( mcClient.getTickDelta() );
//			this.camera.updateEyeHeight();
//			MatrixStack matrixStack = new MatrixStack();
//			this.fov = ( (IGameRenderer)gameRenderer ).invokeGetFov( camera, mcClient.getTickDelta(), false );
//			matrixStack.peek().getModel().multiply(this.getBasicProjectionMatrix(fov));
//
//			MatrixStack matrix = new MatrixStack();
//			matrix.peek().getModel().multiply( this.gameRenderer.getBasicProjectionMatrix(fov) );
//			Matrix4f matrix4f = matrix.peek().getModel();
//			this.gameRenderer.loadProjectionMatrix(matrix4f);
//			this.camera.update(
//				this.mcClient.world,
//				(Entity)(this.mcClient.getCameraEntity() == null ? this.mcClient.player : this.mcClient.getCameraEntity()),
//				!this.mcClient.options.getPerspective().isFirstPerson(),
//				this.mcClient.options.getPerspective().isFrontView(),
//				this.mcClient.getTickDelta());
//			matrix.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(camera.getPitch()));
//			matrix.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(camera.getYaw() + 180.0F));

			
//			buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
//			if( this.camera != null )
//			{
//				BufferBuilderStorage bufferBuilderStorage =
//					((WorldRendererAccessor) this.mcClient.worldRenderer).getBufferBuilders();
//				VertexConsumerProvider.Immediate immediate = bufferBuilderStorage.getEntityVertexConsumers();
//				VertexConsumer vertexConsumer = immediate.getBuffer(RenderLayer.getLines());
//				BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
//
////				this.drawBlocksOnlyOutline(vertexConsumer);
////				this.drawBlocksOnlyFaces();
////				this.renderText();
//				this.drawBlocks();
//				this.drawText();
//
//				// Reset OpenGL's depth function
//				GL11.glDepthFunc(GL11.GL_LEQUAL);
//			}
		}
	}
	
	private void drawBlocks()
	{
		// Check if there are any blocks to be rendered
		if( DebugRenderer.shouldRenderBlocks() )
		{
			// Should render blocks! Set up rendering pipeline
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBuffer();
			RenderSystem.enableBlend();
			RenderSystem.blendFuncSeparate(
				GlStateManager.SrcFactor.SRC_ALPHA,
				GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
				GlStateManager.SrcFactor.ONE,
				GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
			
			// Configure matrix based on current camera Yaw and Pitch
			MatrixStack matrixStack = new MatrixStack();
			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch()));
			matrixStack.multiply( Vec3f.POSITIVE_Y.getDegreesQuaternion( camera.getYaw() + 180.0F) );
			Matrix4f matrix = matrixStack.peek().getPositionMatrix();
			
			for( Map.Entry<BlockPos, BlockRenderModel> block : DebugRenderer.getBlocks().entrySet() )
			{
				// Configure OpenGL's depth function if block should render through blocks
				if( block.getValue().getRenderType() == ElementRenderModel.RenderType.THROUGH_BLOCKS )
				{
					// Render through blocks
					GL11.glDepthFunc(GL11.GL_ALWAYS);
				} else {
					// Don't render though blocks
					GL11.glDepthFunc(GL11.GL_LEQUAL);
				}
				
				drawVerticesForBox(
					bufferBuilder,
					matrix,
					Box.from( new Vec3d(block.getKey().getX() - camera.getPos().x,
						block.getKey().getY() - camera.getPos().y,
						block.getKey().getZ() - camera.getPos().z) ),
					block.getValue()
				);
			}
			
			tessellator.draw();
			
			// Reset OpenGL
			RenderSystem.disableBlend();
			RenderSystem.defaultBlendFunc();
		}
	}
	
	private void drawText(MatrixStack matrixStack, Camera camera)
	{
		// Check if there is any text to be rendered
		if( DebugRenderer.shouldRenderText() )
		{
			BufferBuilderStorage bufferBuilderStorage = ((WorldRendererAccessor) mcClient.worldRenderer).getBufferBuilders();
			VertexConsumerProvider.Immediate immediate = bufferBuilderStorage.getEntityVertexConsumers();
			
			float backgroundOpacity = mcClient.options.getTextBackgroundOpacity(0.25F);
			int backgroundColor = (int) (backgroundOpacity * 255.0F) << 24;
//			TextRenderer textRenderer = mcClient.textRenderer;
			
			
			int totalDrawn = 0;
//			RenderSystem.clear(256, MinecraftClient.IS_SYSTEM_MAC);
//			gameRenderer.loadProjectionMatrix( gameRenderer.getBasicProjectionMatrix( fov ) );
			
			for( Map.Entry<Position, TextRenderModel> textRender : DebugRenderer.getTexts().entrySet() )
			{
				totalDrawn++;
//				MatrixStack matrixStack = RenderSystem.getModelViewStack();
//				matrixStack.push();
//				RenderSystem.applyModelViewMatrix();
				
//				gameRenderer.loadProjectionMatrix( gameRenderer.getBasicProjectionMatrix( fov ) );
				
				
				
//				MatrixStack matrixStack = new MatrixStack();
				
//				matrixStack.peek().getModel().multiply(this.gameRenderer.getBasicProjectionMatrix(this.fov));
//				Matrix4f matrix4f = matrixStack.peek().getModel();
//				this.gameRenderer.loadProjectionMatrix(matrix4f);
//				camera.update(
//					this.mcClient.world,
//					this.mcClient.getCameraEntity() == null ? this.mcClient.player : this.mcClient.getCameraEntity(),
//					!this.mcClient.options.getPerspective().isFirstPerson(),
//					this.mcClient.options.getPerspective().isFrontView(),
//					this.mcClient.getTickDelta() );
//				matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch() ));
//				matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(camera.getYaw() + 180.0F));
				
//				MatrixStack.Entry entry = matrixStack.peek();
//				entry.getModel().loadIdentity();
//				entry.getNormal().loadIdentity();
//				matrixStack.push();
//				this.mcClient.worldRenderer.setupFrustum(matrixStack, camera.getPos(), this.gameRenderer.getBasicProjectionMatrix(
//					Math.max(fov, this.mcClient.options.fov)));

//				double d = ((IGameRenderer) mcClient.gameRenderer).invokeGetFov( camera, mcClient.getTickDelta(), true);
//
//				mcClient.gameRenderer.updateTargetedEntity(mcClient.getTickDelta());
//				matrixStack.peek().getModel().multiply(mcClient.gameRenderer.getBasicProjectionMatrix(d));
//				((IGameRenderer) mcClient.gameRenderer).invokeBobViewWhenHurt( matrixStack, mcClient.getTickDelta() );
//
//				if (mcClient.options.bobView) {
//					((IGameRenderer) mcClient.gameRenderer).invokeBobView( matrixStack, mcClient.getTickDelta() );
//				}
//
//				camera.update(
//					mcClient.world,
//					mcClient.cameraEntity == null ? mcClient.player : mcClient.cameraEntity,
//					!mcClient.options.getPerspective().isFirstPerson(),
//					mcClient.options.getPerspective().isFrontView(),
//					mcClient.getTickDelta());
//				((IGameRenderer) gameRenderer).invokeGetFov(camera, mcClient.getTickDelta(), true);
				
//				mcClient.worldRenderer.setupFrustum(
//					matrixStack, camera.getPos(),mcClient.gameRenderer.getBasicProjectionMatrix( Math.max(d, mcClient.options.fov)) );
				
//				ServerUtil.sendMessageToAllPlayers("FoV changing " + ((IGameRenderer) gameRenderer).invokeGetFov(camera, mcClient.getTickDelta(), true));
//				ServerUtil.sendMessageToAllPlayers("FoV " + ((IGameRenderer) gameRenderer).invokeGetFov(camera, mcClient.getTickDelta(), false));
				
				matrixStack.translate(
					textRender.getValue().getPosition().getX() - camera.getPos().x,
					textRender.getValue().getPosition().getY() - camera.getPos().y,
					textRender.getValue().getPosition().getZ() - camera.getPos().z);
				
				matrixStack.multiply( Vec3f.POSITIVE_Y.getDegreesQuaternion( -camera.getYaw() ) );
				matrixStack.multiply( Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch() ) );
				
				matrixStack.scale(-0.025F, -0.025F, 0.025F);
				
				if (this.mcClient.options.bobView)
				{
					PlayerEntity playerEntity = (PlayerEntity)this.mcClient.getCameraEntity();
					float g = playerEntity.horizontalSpeed - playerEntity.prevHorizontalSpeed;
					float h = -(playerEntity.horizontalSpeed + g * this.mcClient.getTickDelta());
					float i = MathHelper.lerp( this.mcClient.getTickDelta(), playerEntity.prevStrideDistance, playerEntity.strideDistance);
					matrixStack.translate(
						-(double)(MathHelper.sin(h * 3.1415927F) * i * 0.5F),
						-(double)(-Math.abs(MathHelper.cos(h * 3.1415927F) * i)),
						0.0D);
					matrixStack.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(MathHelper.sin(h * 3.1415927F) * i * 3.0F));
					matrixStack.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(Math.abs(MathHelper.cos(h * 3.1415927F - 0.2F) * i) * 5.0F));
					ServerUtil.sendMessageToAllPlayers("view bobbing undone");
				}
				
				mcClient.textRenderer.drawWithOutline(
					Text.of( textRender.getValue().getText() ).asOrderedText(),
					0,
					0,
					textRender.getValue().getColor().asInt(),
					MinecraftColors.black.asInt(),
					matrixStack.peek().getPositionMatrix(),
					immediate,
					15728880);
				
//				Matrix4f matrix4f = matrixStack.peek().getModel();
//				float textCenter = (float) (-textRenderer.getWidth(textRender.getValue().getText()) / 2);
//
////				textRenderer.draw(
////					matrixStack,
////					textRender.getValue().getText(),
////					-textRenderer.getWidth(textRender.getValue().getText()) / 2f,
////					0f,
////					-1
////				);

				
				
//				textRenderer.draw(
//					textRender.getValue().getText(), 0, 0, 553648127,
//					false, matrix4f, immediate, false, backgroundColor, 16); // background
//
//				textRenderer.draw(
//					textRender.getValue().getText(), 0, 0, textRender.getValue().getColor().asInt(),
//					false, matrix4f, immediate, false, 0, 15728880);
			}
			
//			ServerUtil.sendMessageToAllPlayers("total drawn: " + totalDrawn);
		}
	}
	
	public void renderText() {
		MatrixStack matrixStack = new MatrixStack();
		BufferBuilderStorage bufferBuilderStorage =
			((WorldRendererAccessor) mcClient.getInstance().worldRenderer).getBufferBuilders();
		VertexConsumerProvider.Immediate immediate = bufferBuilderStorage.getEntityVertexConsumers();
		
		Position textPos = new Vec3d(0, 100, 0);
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
			
			Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
			float g = mcClient.getInstance().options.getTextBackgroundOpacity(0.25F);
			int j = (int) (g * 255.0F) << 24;
			TextRenderer textRenderer = mcClient.getInstance().textRenderer;
			
			Text text = Text.of("32");
			float h = (float) (-textRenderer.getWidth(text) / 2);
			textRenderer.draw(text, h, 0, 553648127, false, matrix4f, immediate, false, j, 16); // background
			ColorModel color = MinecraftColors.minecoin_gold;
			ServerUtil.sendMessageToAllPlayers(color.toString());
			textRenderer.draw(text, h, 0, color.asInt(), false, matrix4f, immediate, false, 0, 15728880);
			
			
			
//			if (seeThrough) {
//				textRenderer.draw((Text) text, h, (float) i, -1, false, matrix4f, immediate, false, 0, 16);
//			}
			
			matrixStack.pop();
		}
	}
	
//	private void drawBlocksOnlyFaces()
//	{
//		GL11.glDepthFunc(GL11.GL_ALWAYS);
//		Tessellator tessellator = Tessellator.getInstance();
//		BufferBuilder bufferBuilder = tessellator.getBuffer();
//		RenderSystem.enableBlend();
//		RenderSystem.blendFuncSeparate(
//			GlStateManager.SrcFactor.SRC_ALPHA,
//			GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
//			GlStateManager.SrcFactor.ONE,
//			GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
//		RenderSystem.setShader(GameRenderer::getPositionColorShader);
////		RenderSystem.blendFuncSeparate(
////			GlStateManager.SrcFactor.SRC_ALPHA,
////			GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
////			GlStateManager.SrcFactor.ONE,
////			GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
//
////		bufferBuilder.begin(RenderLayer.getTranslucent().getDrawMode(), RenderLayer.getTranslucent().getVertexFormat());
//		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
//
//		for( Map.Entry<BlockPos, ColorRenderModel> blockFaces : DebugRenderer.blocksOnlyFaces.entrySet() )
//		{
//			box = Box.from( new Vec3d(blockFaces.getKey().getX() - camera.getPos().x,
//				blockFaces.getKey().getY() - camera.getPos().y,
//				blockFaces.getKey().getZ() - camera.getPos().z) );
//
//			MatrixStack matrixStack = new MatrixStack();
//			matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch()));
//			matrixStack.multiply( Vec3f.POSITIVE_Y.getDegreesQuaternion( camera.getYaw() + 180.0F) );
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
//		}
//		tessellator.draw();
//		RenderSystem.disableBlend();
//		RenderSystem.defaultBlendFunc();
//		GL11.glDepthFunc(GL11.GL_LEQUAL);
//	}
	
	private static void drawVerticesForBox(BufferBuilder buffer, Matrix4f matrix, Box box, BlockRenderModel block)
	{
		drawVerticesForFaceBelow(buffer, matrix, box,
			block.getColorFace_BELOW().getRedAsFloat(), block.getColorFace_BELOW().getGreenAsFloat(),
			block.getColorFace_BELOW().getBlueAsFloat(), block.getColorFace_BELOW().getAlphaAsFloat());
		
		drawVerticesForFaceAbove(buffer, matrix, box,
			block.getColorFace_ABOVE().getRedAsFloat(), block.getColorFace_ABOVE().getGreenAsFloat(),
			block.getColorFace_ABOVE().getBlueAsFloat(), block.getColorFace_ABOVE().getAlphaAsFloat());
		
		drawVerticesForFaceNorth(buffer, matrix, box,
			block.getColorFace_NORTH().getRedAsFloat(), block.getColorFace_NORTH().getGreenAsFloat(),
			block.getColorFace_NORTH().getBlueAsFloat(), block.getColorFace_NORTH().getAlphaAsFloat());
		
		drawVerticesForFaceSouth(buffer, matrix, box,
			block.getColorFace_SOUTH().getRedAsFloat(), block.getColorFace_SOUTH().getGreenAsFloat(),
			block.getColorFace_SOUTH().getBlueAsFloat(), block.getColorFace_SOUTH().getAlphaAsFloat());
		
		drawVerticesForFaceEast(buffer, matrix, box,
			block.getColorFace_EAST().getRedAsFloat(), block.getColorFace_EAST().getGreenAsFloat(),
			block.getColorFace_EAST().getBlueAsFloat(), block.getColorFace_EAST().getAlphaAsFloat());
		
		drawVerticesForFaceWest(buffer, matrix, box,
			block.getColorFace_WEST().getRedAsFloat(), block.getColorFace_WEST().getGreenAsFloat(),
			block.getColorFace_WEST().getBlueAsFloat(), block.getColorFace_WEST().getAlphaAsFloat());
	}
	
	private static void drawVerticesForFaceBelow(BufferBuilder buffer, Matrix4f matrix, Box box, float red, float green, float blue, float alpha)
	{
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
	}
	
	private static void drawVerticesForFaceAbove(BufferBuilder buffer, Matrix4f matrix, Box box, float red, float green, float blue, float alpha)
	{
		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
	}
	
	private static void drawVerticesForFaceNorth(BufferBuilder buffer, Matrix4f matrix, Box box, float red, float green, float blue, float alpha)
	{
		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
	}
	
	private static void drawVerticesForFaceSouth(BufferBuilder buffer, Matrix4f matrix, Box box, float red, float green, float blue, float alpha)
	{
		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
	}
	
	private static void drawVerticesForFaceEast(BufferBuilder buffer, Matrix4f matrix, Box box, float red, float green, float blue, float alpha)
	{
		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
	}
	
	private static void drawVerticesForFaceWest(BufferBuilder buffer, Matrix4f matrix, Box box, float red, float green, float blue, float alpha)
	{
		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
	}
	
	private static void drawVerticesFor(BufferBuilder buffer, Matrix4f matrix, Box box, float red, float green, float blue, float alpha)
	{
//		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.minX, (float) box.minY, (float) box.minZ).color(red, green, blue, alpha).next();
//
//		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.maxZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.maxX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();
//		buffer.vertex(matrix, (float) box.minX, (float) box.maxY, (float) box.minZ).color(red, green, blue, alpha).next();


	}
	
//	private void drawBlocksOnlyOutline(VertexConsumer vertexConsumer)
//	{
//		MatrixStack matrixStack = new MatrixStack();
//		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch()));
//		matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion( camera.getYaw() + 180.0F));
//
//		for( Map.Entry<BlockPos, ColorRenderModel> blockOutline : DebugRenderer.blocksOnlyOutline.entrySet() )
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
