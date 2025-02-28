package fr.acth2.ror.utils.subscribers.gen.overworld;

import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.ModEntities;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.gen.utils.MobSpawnData;
import net.minecraft.block.Blocks;
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
public class NPCSpawnSubscriber {

    private static int SPAWN_INTERVAL_TICKS = 12000;
    private static int ATTEMPTS_PER_PLAYER = 1;
    private static double SPAWN_CHANCE = 2.45;

    private static final int SPAWN_RADIUS_MIN = 8;
    private static final int SPAWN_RADIUS_MAX = 32;

    static {
        if (CoinsManager.hasLeastCoins(10000)) {
            SPAWN_INTERVAL_TICKS = 1000;
            ATTEMPTS_PER_PLAYER = 3;
            SPAWN_CHANCE = 3.0D;
        }
    }

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
        BlockPos spawnPos = findSpawnHeight(world, basePos);
        if (spawnPos == null) return;

        trySpawnPlayerLevelEntity(world, spawnPos);
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

    private static void trySpawnPlayerLevelEntity(ServerWorld world, BlockPos pos) {
        if (CoinsManager.getCoins() <= 2500) {
            MobSpawnData chosenMobData = mobListLV1.get(world.random.nextInt(mobListLV1.size()));

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
    }

}
