package fr.acth2.ror.mixins;

import fr.acth2.ror.init.constructors.items.Glider;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(PlayerModel.class)
public class PlayerModelMixin {

    @Inject(method = "setupAnim(Lnet/minecraft/entity/Entity;FFFFF)V",
            at = @At("TAIL"))
    private void onSetupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                             float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            PlayerModel<?> model = (PlayerModel<?>) (Object) this;

            if (Glider.isHoldingGlider(player) && !player.isOnGround() && player.getDeltaMovement().y < 0) {
                model.rightArm.xRot = (float) Math.toRadians(-180);
                model.leftArm.xRot = (float) Math.toRadians(-180);

                model.body.xRot = (float) Math.toRadians(15);

                model.rightArm.yRot = 0;
                model.leftArm.yRot = 0;
                model.rightArm.zRot = 0;
                model.leftArm.zRot = 0;

                model.head.xRot = (float) Math.toRadians(-10);
            }
        }
    }
}