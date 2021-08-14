package com.github.scorchedpsyche.scorchedcraft.fabric.core.mixin;

import com.github.scorchedpsyche.scorchedcraft.fabric.core.main.renderers.DebugRenderer;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.BlockOutlineModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.models.StringFormattedModel;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ConsoleUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.PlayerUtil;
import com.github.scorchedpsyche.scorchedcraft.fabric.core.utils.minecraft.ServerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.*;
import net.minecraft.world.BlockView;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static net.minecraft.client.render.block.entity.EnchantingTableBlockEntityRenderer.BOOK_TEXTURE;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
//	float newRed = 0 / 255.0F;
//	float newGreen = 255 / 255.0F;
//	float newBlue = 0 / 255.0F;
//	float newAlpha = 1.0F;
//	BlockState blockstate;
	Box box;
	Camera camera;
//	Vec3d cameraOffset;
	private static BufferBuilder buffer = new BufferBuilder(256);
	
	
	@Inject( method = "render(Lnet/minecraft/client/util/math/MatrixStack;FJZLnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/GameRenderer;Lnet/minecraft/client/render/LightmapTextureManager;Lnet/minecraft/util/math/Matrix4f;)V",
		at = @At( value = "INVOKE", shift = At.Shift.AFTER,
			target = "Lnet/minecraft/client/render/WorldRenderer;checkEmpty(Lnet/minecraft/client/util/math/MatrixStack;)V") )
	private void renderBlockOutline(CallbackInfo info) {
		if( !DebugRenderer.blocks.isEmpty() && MinecraftClient.getInstance().world != null )
		{
			camera = MinecraftClient.getInstance().gameRenderer.getCamera();
//			buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
			if( camera != null )
			{
				VertexConsumer vertexConsumer =
					((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer)
						.getBufferBuilders().getEntityVertexConsumers().getBuffer(RenderLayer.getLines());
				
				MatrixStack matrixStack = new MatrixStack();
				matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion( camera.getPitch()));
				matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion( camera.getYaw() + 180.0F));
				
				for( BlockOutlineModel blockOutline : DebugRenderer.blocks )
				{
//////					blockstate = MinecraftClient.getInstance().world.getBlockState(blockOutline.getBlockPos());
//////					box = blockstate.getOutlineShape(MinecraftClient.getInstance().world, blockOutline.getBlockPos()).getBoundingBox();
////
//////					box = new Box(blockOutline.getBlockPos());
//////					box = box.offset(blockOutline.getBlockPos().getX() - camera.getPos().x,
//////									blockOutline.getBlockPos().getY() - camera.getPos().y,
//////									blockOutline.getBlockPos().getZ() - camera.getPos().z);
////
////					box = new Box( blockOutline.getBlockPos() );
////					ServerUtil.sendMessageToAllPlayers("box.getCenter: " + box.getCenter());
////					ServerUtil.sendMessageToAllPlayers("camera.getPos().y: " + camera.getPos().y);
////					ServerUtil.sendMessageToAllPlayers("blockOutline.getBlockPos().getY(): " + blockOutline.getBlockPos().getY());
////					ServerUtil.sendMessageToAllPlayers("Y - Y: " + (blockOutline.getBlockPos().getY() - camera.getPos().y));
////					box = box.offset(blockOutline.getBlockPos().getX() - camera.getPos().x,
////									blockOutline.getBlockPos().getY() - camera.getPos().y,
////									blockOutline.getBlockPos().getZ() - camera.getPos().z);
//
//					cameraOffset = new Vec3d(blockOutline.getBlockPos().getX() - camera.getPos().x,
//						blockOutline.getBlockPos().getY() - camera.getPos().y,
//						blockOutline.getBlockPos().getZ() - camera.getPos().z);
////					box = new Box(blockOutline.getBlockPos());
////						new Box(new BlockPos(cameraOffset));
////					ServerUtil.sendMessageToAllPlayers(" === " + box.getCenter() + " === ");
////					ServerUtil.sendMessageToAllPlayers("box.getCenter: " + box.getCenter());
//////					box = box.offset(cameraOffset);
//////					ServerUtil.sendMessageToAllPlayers("box.getCenter AFTER: " + box.getCenter());
////					ServerUtil.sendMessageToAllPlayers("cameraOffset: " + cameraOffset);
////					ServerUtil.sendMessageToAllPlayers("camera.getPos: " + camera.getPos());
					
					box = Box.from( new Vec3d(blockOutline.getBlockPos().getX() - camera.getPos().x,
						blockOutline.getBlockPos().getY() - camera.getPos().y,
						blockOutline.getBlockPos().getZ() - camera.getPos().z) );
					
					WorldRenderer.drawBox(
						matrixStack,
						vertexConsumer,
						box,
						blockOutline.getRgba().getRed(),
						blockOutline.getRgba().getGreen(),
						blockOutline.getRgba().getBlue(),
						blockOutline.getRgba().getAlpha() );
					
//					drawVerticesFor(
//						buffer,
//						matrix,
//						box,
//						blockstate,
//						MinecraftClient.getInstance().world.getChunkAsView(
//							MinecraftClient.getInstance().world.getChunk(blockPos).getPos().x,
//							MinecraftClient.getInstance().world.getChunk(blockPos).getPos().z ),
//						blockPos,
//						MinecraftClient.getInstance().gameRenderer.getCamera().getPos().x,
//						MinecraftClient.getInstance().gameRenderer.getCamera().getPos().y,
//						MinecraftClient.getInstance().gameRenderer.getCamera().getPos().z,
//						1,
//						1
//						);
//
//				WorldRenderer.
					
//					ConsoleUtil.logMessage("block highlight: " + blockPos);
				}
			}
//			buffer.end();
//			BufferRenderer.draw(buffer);
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
