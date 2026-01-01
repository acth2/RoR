package fr.acth2.ror.utils.subscribers.gen.skyria;

import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.gen.utils.MobSpawnData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = References.MODID)
public class SkyriaMonsterSpawnerSubscriber {

    private static final int SPAWN_INTERVAL_TICKS = 100;
    private static final int ATTEMPTS_PER_PLAYER = 4;
    private static final double SPAWN_CHANCE = 5.25;

    private static final int SPAWN_RADIUS_MIN = 0;
    private static final int SPAWN_RADIUS_MAX = 48;
    private static final int SPAWN_HEIGHT_VARIATION = 128;

    public static final List<MobSpawnData> mobListLV1 = new ArrayList<>();

    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onWorldTick(WorldTickEvent event) {
        if (!(event.world instanceof ServerWorld)) return;
        if (event.phase != TickEvent.Phase.END) return;

        ServerWorld serverWorld = (ServerWorld) event.world;
        if (!serverWorld.dimension().location().toString().equals(References.MODID + ":skyria")) {
            return;
        }

        tickCounter++;
        if (tickCounter < SPAWN_INTERVAL_TICKS) {
            return;
        }
        tickCounter = 0;

        for (PlayerEntity player : serverWorld.players()) {
            for (int i = 0; i < ATTEMPTS_PER_PLAYER; i++) {
                trySpawnEntityNearPlayer(serverWorld, player);
            }
        }
    }

    private static void trySpawnEntityNearPlayer(ServerWorld world, PlayerEntity player) {
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
        BlockPos spawnPos = findSpawnHeight(world, basePos, player.getY());
        if (spawnPos == null) return;

        trySpawnPlayerLevelEntity(world, spawnPos);
    }

    private static BlockPos findSpawnHeight(ServerWorld world, BlockPos basePos, double playerY) {
        double minY = Math.max(playerY - SPAWN_HEIGHT_VARIATION, 32);
        double maxY = Math.min(playerY + SPAWN_HEIGHT_VARIATION, world.getMaxBuildHeight());

        for (double y = maxY; y >= minY; y--) {
            BlockPos checkPos = new BlockPos(basePos.getX(), y, basePos.getZ());
            BlockState state = world.getBlockState(checkPos);

            if (!state.isAir() && state.getMaterial().isSolid()) {
                BlockPos above = checkPos.above();
                if (world.isEmptyBlock(above)) {
                    return above;
                }
            }
        }

        for (int y = world.getMaxBuildHeight(); y >= 32; y--) {
            BlockPos checkPos = new BlockPos(basePos.getX(), y, basePos.getZ());
            BlockState state = world.getBlockState(checkPos);

            if (!state.isAir() && state.getMaterial().isSolid()) {
                BlockPos above = checkPos.above();
                if (world.isEmptyBlock(above)) {
                    return above;
                }
            }
        }

        return null;
    }

    private static void trySpawnPlayerLevelEntity(ServerWorld world, BlockPos pos) {
        MobSpawnData chosenMobData = getWeightedRandomMob(world.random);
        if (chosenMobData == null) return;

        BlockPos belowPos = pos.below();
        if (chosenMobData.getRequiredBlock() != null && world.getBlockState(belowPos).getBlock() != chosenMobData.getRequiredBlock()) {
            return;
        }

        EntityType<? extends LivingEntity> chosenEntityType = (EntityType<? extends LivingEntity>) chosenMobData.getEntityType();
        LivingEntity chosenEntity = chosenEntityType.create(world);
        if (chosenEntity == null) return;

        chosenEntity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                world.random.nextFloat() * 360F, 0);
        world.addFreshEntityWithPassengers(chosenEntity);
    }

    private static MobSpawnData getWeightedRandomMob(Random random) {
        int totalWeight = 0;
        for (MobSpawnData data : mobListLV1) {
            totalWeight += data.getSpawnChance();
        }

        if (totalWeight <= 0) return null;

        int randomWeight = random.nextInt(totalWeight);
        int currentWeight = 0;

        for (MobSpawnData data : mobListLV1) {
            currentWeight += data.getSpawnChance();
            if (randomWeight < currentWeight) {
                return data;
            }
        }
        return null;
    }
}
