package fr.acth2.ror.utils.subscribers.gen.overworld;

import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.gen.utils.MobSpawnData;
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
public class NightMonsterSpawnerSubscriber {

    private static final int SPAWN_INTERVAL_TICKS = 100;
    private static final int ATTEMPTS_PER_PLAYER = 13;
    private static final double SPAWN_CHANCE = 4.75;
    private static final int SPAWN_RADIUS_MIN = 8;
    private static final int SPAWN_RADIUS_MAX = 24;
    private static final int REQUIRED_MAX_LIGHT = 7;

    public static final List<MobSpawnData> mobListLV1 = new ArrayList<>();

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
                trySpawnSurfaceMonsterNearPlayer(serverWorld, player);
            }
        }
    }

    private static void trySpawnSurfaceMonsterNearPlayer(ServerWorld world, PlayerEntity player) {
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

        BlockPos spawnPos = findSurfaceSpawnPos(world, basePos);
        if (spawnPos == null) return;

        if (!isNighttime(world)) return;

        if (!isDarkEnough(world, spawnPos, REQUIRED_MAX_LIGHT)) return;

        trySpawnPlayerLevelEntity(world, spawnPos);
    }

    private static BlockPos findSurfaceSpawnPos(ServerWorld world, BlockPos basePos) {
        int startY = Math.min(world.getMaxBuildHeight(), 255);
        for (int y = startY; y >= 0; y--) {
            BlockPos checkPos = new BlockPos(basePos.getX(), y, basePos.getZ());
            if (!world.isEmptyBlock(checkPos)) {
                BlockPos abovePos = checkPos.above();
                if (world.isEmptyBlock(abovePos)) {
                    return abovePos;
                }
            }
        }
        return null;
    }

    private static boolean isNighttime(ServerWorld world) {
        long dayTime = world.getDayTime() % 24000;
        return dayTime >= 12500 && dayTime < 24000;
    }

    private static boolean isDarkEnough(ServerWorld world, BlockPos pos, int maxLight) {
        int light = world.getMaxLocalRawBrightness(pos);
        return (light <= maxLight);
    }

    private static void trySpawnPlayerLevelEntity(ServerWorld world, BlockPos pos) {
        if (mobListLV1.isEmpty()) return;

        MobSpawnData chosenMobData = getWeightedRandomMob(world.random);
        if (chosenMobData == null) return;

        if (chosenMobData.getRequiredBlock() != null &&
                world.getBlockState(pos.below()).getBlock() != chosenMobData.getRequiredBlock()) {
            return;
        }

        EntityType<? extends LivingEntity> chosenEntityType = (EntityType<? extends LivingEntity>) chosenMobData.getEntityType();
        if (chosenMobData.getEventID() != -1) {
            if (chosenMobData.getEventID() == 0 && References.brokenMoonPicked == 0 || new Random().nextInt(100) == 0) {
                LivingEntity chosenEntity = chosenEntityType.create(world);
                if (chosenEntity == null) return;

                chosenEntity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                        world.random.nextFloat() * 360F, 0);
                world.addFreshEntityWithPassengers(chosenEntity);
                world.addFreshEntityWithPassengers(chosenEntity);
                world.addFreshEntityWithPassengers(chosenEntity);
            }

            if (chosenMobData.getEventID() == 1 && References.event1Picked == 0 || new Random().nextInt(100) == 0) {
                LivingEntity chosenEntity = chosenEntityType.create(world);
                if (chosenEntity == null) return;

                chosenEntity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                        world.random.nextFloat() * 360F, 0);
                world.addFreshEntityWithPassengers(chosenEntity);
                world.addFreshEntityWithPassengers(chosenEntity);
                world.addFreshEntityWithPassengers(chosenEntity);
            }
        } else {
            LivingEntity chosenEntity = chosenEntityType.create(world);
            if (chosenEntity == null) return;

            chosenEntity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5,
                    world.random.nextFloat() * 360F, 0);
            world.addFreshEntityWithPassengers(chosenEntity);
        }
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
