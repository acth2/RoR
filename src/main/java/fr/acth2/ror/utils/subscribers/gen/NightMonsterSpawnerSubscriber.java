package fr.acth2.ror.utils.subscribers.gen;

import fr.acth2.ror.utils.References;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = References.MODID)
public class NightMonsterSpawnerSubscriber {

    private static final int SPAWN_INTERVAL_TICKS = 600;
    private static final int ATTEMPTS_PER_PLAYER = 2;
    private static final double SPAWN_CHANCE = 2.50;
    private static final int SPAWN_RADIUS_MIN = 8;
    private static final int SPAWN_RADIUS_MAX = 24;
    private static final int REQUIRED_MAX_LIGHT = 7;

    public static final List<RegistryObject<? extends EntityType<? extends LivingEntity>>> mobListLV1
            = new ArrayList<>();

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
        spawnNightMob(world, spawnPos);
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

    private static void spawnNightMob(ServerWorld world, BlockPos pos) {
        if (mobListLV1.isEmpty()) return;

        RegistryObject<? extends EntityType<? extends LivingEntity>> chosenRegObj =
                mobListLV1.get(world.random.nextInt(mobListLV1.size()));

        EntityType<? extends LivingEntity> chosenEntityType = chosenRegObj.get();
        LivingEntity chosenEntity = chosenEntityType.create(world);
        if (chosenEntity == null) return;

        chosenEntity.moveTo(
                pos.getX() + 0.5,
                pos.getY(),
                pos.getZ() + 0.5,
                world.random.nextFloat() * 360F,
                0
        );
        world.addFreshEntityWithPassengers(chosenEntity);
    }
}