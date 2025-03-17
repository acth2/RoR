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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(WorldRenderer.class)
public abstract class MixinBrokenMoonWorldRenderer {

    @Shadow
    private TextureManager textureManager;

    private static final ResourceLocation BROKEN_MOON_TEXTURE = new ResourceLocation(References.MODID + ":textures/environment/broken_moon.png");
    private final Random random = new Random();
    private static boolean locked = false;

    private static AtomicBoolean atomicPicker = new AtomicBoolean(true);

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    void renderSky(MatrixStack matrixStack, float partialTicks, CallbackInfo ci) {
        ClientWorld world = Minecraft.getInstance().level;

        long worldTime = world.getDayTime() % 24000;
        boolean isNight = worldTime >= 13000 && worldTime < 23000;

        if (isNight && atomicPicker.getAndSet(false)) {
            References.brokenMoonPicked = random.nextInt(50);
            References.brokenMoonWarning = true;
        }

        if (isNight && References.brokenMoonPicked == 0 || locked) {
            locked = true;
            ci.cancel();

            RenderSystem.clearDepth(1.0);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();

            renderSkyColor(0.1F, 0.1F, 0.1F);
            renderCustomMoon(matrixStack);
        }

        if (!isNight) {
            locked = false;
            References.brokenMoonPicked = 1;
            References.brokenMoonWarning = false;
            atomicPicker.set(true);
        }
    }

    private void renderSkyColor(float r, float g, float b) {
        RenderSystem.clearColor(r, g, b, 1.0F);
    }

    private void renderCustomMoon(MatrixStack matrixStack) {
        this.textureManager.bind(BROKEN_MOON_TEXTURE);

        float moonSize = 16.0F;
        float skyHeight = 30.0F;

        Matrix4f matrix4f = matrixStack.last().pose();
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();

        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);

        bufferbuilder.vertex(matrix4f, -moonSize, skyHeight, -moonSize).uv(0.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f, moonSize, skyHeight, -moonSize).uv(1.0F, 0.0F).endVertex();
        bufferbuilder.vertex(matrix4f, moonSize, skyHeight, moonSize).uv(1.0F, 1.0F).endVertex();
        bufferbuilder.vertex(matrix4f, -moonSize, skyHeight, moonSize).uv(0.0F, 1.0F).endVertex();

        bufferbuilder.end();
        WorldVertexBufferUploader.end(bufferbuilder);
    }
}