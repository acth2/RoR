package fr.acth2.ror.mixins;

import fr.acth2.ror.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

import fr.acth2.ror.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@OnlyIn(Dist.CLIENT)
@Mixin(FogRenderer.class)
public abstract class MixinBrokenMoonFogRenderer {

    @ModifyArgs(
            method = "setupColor",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V")
    )
    private static void adjustFogColors(Args args) {
        ClientWorld world = Minecraft.getInstance().level;

        if (world != null) {
            // Check server-side event status instead of client-side random
            if (References.isBrokenMoonActive(world)) {
                args.set(0, 1.0F);
                args.set(1, 0.5F);
                args.set(2, 0.0F);
                args.set(3, 1.0F);
            } else if (References.isBloodSunActive(world)) {
                args.set(0, 1.0F);
                args.set(1, 0.0F);
                args.set(2, 0.0F);
                args.set(3, 1.0F);
            }
        }
    }
}