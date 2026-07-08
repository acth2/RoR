package fr.acth2.ror.utils.subscribers.gen.skyria;

import fr.acth2.ror.entities.constructors.traders.SkyriaTraderEntity;
import fr.acth2.ror.init.ModEntities;
import fr.acth2.ror.utils.References;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = References.MODID)
public class StructureNPCSpawnerSubscriber {
    private static final Random RANDOM = new Random();
    private static final int SPAWN_COOLDOWN = 200;
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.world instanceof ServerWorld)) return;

        ServerWorld world = (ServerWorld) event.world;

        if (!world.dimension().location().toString().equals("ror:skyria")) {
            return;
        }

        tickCounter++;
        if (tickCounter < SPAWN_COOLDOWN) return;
        tickCounter = 0;
        world.players().forEach(player -> {
            checkAndSpawnNPCAroundPlayer(world, player);
        });
    }

    private static void checkAndSpawnNPCAroundPlayer(ServerWorld world, PlayerEntity player) {
        BlockPos playerPos = player.blockPosition();
        int searchRadius = 32;

        for (int y = 132; y <= 150; y++) {
            for (int x = playerPos.getX() - searchRadius; x <= playerPos.getX() + searchRadius; x++) {
                for (int z = playerPos.getZ() - searchRadius; z <= playerPos.getZ() + searchRadius; z++) {
                    BlockPos checkPos = new BlockPos(x, y, z);

                    BlockState state = world.getBlockState(checkPos);
                    String blockName = state.getBlock().getRegistryName().toString();

                    if (blockName.equals("ror:skyria_brick") || blockName.equals("ror:polished_skyria_brick")) {
                        BlockPos abovePos = checkPos.above();
                        if (world.isEmptyBlock(abovePos)) {
                            if (!isTraderNearby(world, checkPos)) {
                                spawnTrader(world, abovePos);
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean isTraderNearby(ServerWorld world, BlockPos pos) {
        AxisAlignedBB searchArea = new AxisAlignedBB(
                pos.getX() - 8, pos.getY() - 8, pos.getZ() - 8,
                pos.getX() + 8, pos.getY() + 8, pos.getZ() + 8
        );

        return !world.getEntitiesOfClass(SkyriaTraderEntity.class, searchArea).isEmpty();
    }

    private static void spawnTrader(ServerWorld world, BlockPos pos) {
        SkyriaTraderEntity trader = new SkyriaTraderEntity(ModEntities.SKYRIA_TRADER.get(), world);
        if (trader != null) {
            trader.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            trader.setNoAi(false);
            trader.setPersistenceRequired();

            world.addFreshEntity(trader);
            System.out.println("Spawned Skyria Trader at structure: " + pos);
        }
    }
}