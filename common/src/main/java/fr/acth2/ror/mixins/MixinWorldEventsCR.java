package fr.acth2.ror.mixins;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.acth2.ror.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.acth2.ror.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class MixinWorldEventsCR {

    @Shadow
    private TextureManager textureManager;

    private static final ResourceLocation BROKEN_MOON_TEXTURE = new ResourceLocation(References.MODID + ":textures/environment/broken_moon.png");
    private static final ResourceLocation BLOOD_SUN_TEXTURE = new ResourceLocation(References.MODID + ":textures/environment/blood_sun.png");

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    void renderSky(MatrixStack matrixStack, float partialTicks, CallbackInfo ci) {
        ClientWorld world = Minecraft.getInstance().level;

        if (world != null) {
            if (References.isBrokenMoonActive(world)) {
                ci.cancel();
                renderBrokenMoonSky(matrixStack);
            } else if (References.isBloodSunActive(world)) {
                ci.cancel();
                renderBloodSunSky(matrixStack);
            }
        }
    }

    private void renderBrokenMoonSky(MatrixStack matrixStack) {
        RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, false);
        RenderSystem.clearDepth(1.0);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        renderSkyColor(0.1F, 0.1F, 0.1F);
        renderCustomSkybox(matrixStack, BROKEN_MOON_TEXTURE);
    }

    private void renderBloodSunSky(MatrixStack matrixStack) {
        RenderSystem.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT, false);
        RenderSystem.disableTexture();
        RenderSystem.disableBlend();

        renderSkyColor(1.0F, 0.0F, 0.0F);

        RenderSystem.enableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        renderCustomSkybox(matrixStack, BLOOD_SUN_TEXTURE);
    }

    private void renderSkyColor(float r, float g, float b) {
        RenderSystem.clearColor(r, g, b, 1.0F);
    }

    private void renderCustomSkybox(MatrixStack matrixStack, ResourceLocation texture) {
        this.textureManager.bind(texture);

        float size = 16.0F;
        float skyHeight = 30.0F;

        Matrix4f matrix4f = matrixStack.last().pose();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);

        bufferbuilder.vertex(matrix4f, -size, skyHeight, -size).uv(0.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f, size, skyHeight, -size).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f, size, skyHeight, size).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f, -size, skyHeight, size).uv(0.0F, 1.0F).endVertex();

        bufferbuilder.end();
        WorldVertexBufferUploader.end(bufferbuilder);
    }
}