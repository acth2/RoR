package fr.acth2.ror.utils.subscribers.client;

import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.RedstoneParticleData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = "ror")
public class WorldParticleHandler {

    private static final Random RANDOM = new Random();

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.world instanceof ServerWorld) {
            ServerWorld world = (ServerWorld) event.world;

            if (!fr.acth2.ror.utils.ParticleConfig.WORLD_PARTICLES_ENABLED.get()) {
                return;
            }

            if (world.dimension() != World.OVERWORLD) {
                return;
            }

            if (world.getGameTime() % 10 == 0) {
                spawnRandomSurfaceParticles(world);
            }
        }
    }

    private static void spawnRandomSurfaceParticles(ServerWorld world) {
        List<? extends PlayerEntity> players = world.players();

        if (players.isEmpty()) {
            return;
        }

        PlayerEntity player = players.get(RANDOM.nextInt(players.size()));

        int particleCount = 3 + RANDOM.nextInt(3);

        for (int i = 0; i < particleCount; i++) {
            double x = player.getX() + (RANDOM.nextDouble() - 0.5) * 16;
            double z = player.getZ() + (RANDOM.nextDouble() - 0.5) * 16;

            BlockPos surfacePos = world.getHeightmapPos(
                    net.minecraft.world.gen.Heightmap.Type.MOTION_BLOCKING,
                    new BlockPos(x, 0, z)
            );

            if (surfacePos.getY() > world.getSeaLevel() - 2) {
                double posX = surfacePos.getX() + 0.5;
                double posY = surfacePos.getY() + 0.1 + RANDOM.nextDouble() * 0.3 + RANDOM.nextInt(2);
                double posZ = surfacePos.getZ() + 0.5;

                float red = RANDOM.nextInt(255 + 1 - 100) + 100;
                float green = RANDOM.nextInt(255 + 1 - 100) + 100;
                float blue = RANDOM.nextInt(255 + 1 - 100) + 100;

                if (RANDOM.nextInt(10) == 1 && PlayerStats.get(player).getLevel() >= 30) {
                    sendColoredParticle(world, posX, posY, posZ, red, green, blue, 1, 35.0f);
                }
            }
        }
    }

    public static void sendColoredParticle(ServerWorld world, double posX, double posY, double posZ,
                                           float red, float green, float blue, int count, float size) {
        IParticleData particleData = new RedstoneParticleData(red, green, blue, size);

        world.sendParticles(
                particleData,
                posX, posY, posZ,
                count,
                (RANDOM.nextDouble() - 0.5) * 0.1,
                0.02,
                (RANDOM.nextDouble() - 0.5) * 0.1,
                0.05
        );
    }

    public static void toggleParticles() {
        boolean current = fr.acth2.ror.utils.ParticleConfig.WORLD_PARTICLES_ENABLED.get();
        fr.acth2.ror.utils.ParticleConfig.WORLD_PARTICLES_ENABLED.set(!current);
        fr.acth2.ror.utils.ParticleConfig.SPEC.save();
    }

    public static boolean areParticlesEnabled() {
        return fr.acth2.ror.utils.ParticleConfig.WORLD_PARTICLES_ENABLED.get();
    }
}