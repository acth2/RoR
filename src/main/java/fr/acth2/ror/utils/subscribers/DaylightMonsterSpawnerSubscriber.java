package fr.acth2.ror.utils.subscribers;

import fr.acth2.ror.init.ModEntities;
import fr.acth2.ror.utils.References;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = References.MODID)
public class DaylightMonsterSpawnerSubscriber  {
    private static final int SPAWN_INTERVAL_TICKS = 1200;
    private static final int ATTEMPTS_PER_PLAYER = 1;
    private static final double SPAWN_CHANCE = 0.20;

    private static final int SPAWN_RADIUS_MIN = 8;
    private static final int SPAWN_RADIUS_MAX = 24;

    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onWorldTick(WorldTickEvent event) {
        if (!(event.world instanceof ServerWorld)) return;
        if (event.phase != TickEvent.Phase.END) return;

        ServerWorld serverWorld = (ServerWorld) event.world;
        if (!serverWorld.dimension().equals(ServerWorld.OVERWORLD)) {
            return;
        }

        tickCounter++;
        if (tickCounter < SPAWN_INTERVAL_TICKS) {
            return;
        }
        tickCounter = 0;

        for (PlayerEntity player : serverWorld.players()) {
            for (int i = 0; i < ATTEMPTS_PER_PLAYER; i++) {
                trySpawnHopperNearPlayer(serverWorld, player);
            }
        }
    }

    private static void trySpawnHopperNearPlayer(ServerWorld world, PlayerEntity player) {
        Random random = world.random;

        if (random.nextDouble() > SPAWN_CHANCE) {
            return;
        }


        int dx = random.nextInt(SPAWN_RADIUS_MAX - SPAWN_RADIUS_MIN + 1) + SPAWN_RADIUS_MIN;
        int dz = random.nextInt(SPAWN_RADIUS_MAX - SPAWN_RADIUS_MIN + 1) + SPAWN_RADIUS_MIN;

        if (random.nextBoolean()) dx = -dx;
        if (random.nextBoolean()) dz = -dz;

        BlockPos playerPos = player.blockPosition();
        BlockPos basePos = playerPos.offset(dx, 0, dz);
        BlockPos spawnPos = findSpawnHeight(world, basePos);
        if (spawnPos == null) return;

        if (!isDaytime(world)) return;
        if (!hasBrightLight(world, spawnPos, 8)) return;

        spawnHopper(world, spawnPos);
    }

    private static BlockPos findSpawnHeight(ServerWorld world, BlockPos basePos) {
        int topY = Math.min(world.getMaxBuildHeight(), 255);
        for (int y = topY; y > 0; y--) {
            BlockPos checkPos = new BlockPos(basePos.getX(), y, basePos.getZ());
            if (world.isEmptyBlock(checkPos)) {
                BlockPos below = checkPos.below();
                if (!world.isEmptyBlock(below) && world.getBlockState(below).canOcclude()) {
                    return checkPos;
                }
            }
        }
        return null;
    }

    private static boolean isDaytime(ServerWorld world) {
        long dayTime = world.getDayTime() % 24000;
        return dayTime < 12500;
    }

    private static boolean hasBrightLight(ServerWorld world, BlockPos pos, int minLight) {
        int light = world.getMaxLocalRawBrightness(pos);
        return (light >= minLight);
    }

    private static void spawnHopper(ServerWorld world, BlockPos pos) {
        net.minecraft.entity.EntityType<fr.acth2.ror.entities.entity.hopper.EntityHopper> hopperEntityType = ModEntities.HOPPER.get();
        fr.acth2.ror.entities.entity.hopper.EntityHopper hopper = hopperEntityType.create(world);
        if (hopper == null) return;

        hopper.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                world.random.nextFloat() * 360F, 0);
        world.addFreshEntityWithPassengers(hopper);
    }
}