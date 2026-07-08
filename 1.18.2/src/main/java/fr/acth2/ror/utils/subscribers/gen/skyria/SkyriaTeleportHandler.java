package fr.acth2.ror.utils.subscribers.gen.skyria;

import fr.acth2.ror.utils.References;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID)
public class SkyriaTeleportHandler {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        PlayerEntity player = event.player;
        World world = player.level;

        if (world.dimension().location().toString().equals(References.MODID + ":skyria")) {
            if (player.getY() <= 32) {
                if (!world.isClientSide) {
                    ServerWorld overworld = world.getServer().getLevel(World.OVERWORLD);
                    if (overworld == null) return;

                    BlockPos teleportPos = new BlockPos(
                            player.getX(),
                            overworld.getMaxBuildHeight(),
                            player.getZ()
                    );

                    player.changeDimension(overworld, new SkyriaTeleporter(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ()));
                    player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 800, 0, false, false));
                }
            }
        }
    }
}