package fr.acth2.ror.mixins;

import fr.acth2.ror.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(FogRenderer.class)
public abstract class MixinBrokenMoonFogRenderer {

    private static boolean locked = false;
    private static boolean locked1 = false;

    private static AtomicBoolean atomicPicker0 = new AtomicBoolean(true);
    private static AtomicBoolean atomicPicker1 = new AtomicBoolean(true);

    @ModifyArgs(
            method = "setupColor",
            at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V")
    )
    private static void adjustFogColors(Args args) {
        ClientWorld world = Minecraft.getInstance().level;

        if (world != null) {
            long worldTime = world.getDayTime() % 24000;
            boolean isNight = worldTime >= 13000 && worldTime < 23000;

            if (isNight && References.brokenMoonPicked == 0 || locked) {
                locked = true;
                args.set(0, 1.0F);
                args.set(1, 0.5F);
                args.set(2, 0.0F);
                args.set(3, 1.0F);
            }

            if (!isNight) {
                locked = false;
                atomicPicker0.set(true);
            }


            if (!isNight && References.event1Picked == 0 || locked1) {
                locked1 = true;
                args.set(0, 1.0F);
                args.set(1, 0.0F);
                args.set(2, 0.0F);
                args.set(3, 1.0F);
            }

            if (!isNight) {
                locked1 = false;
                atomicPicker1.set(true);
            }
        }
    }
}