package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModDimensions;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.gen.skyria.SkyriaTeleporter;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.world.level.block.Block;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID)
public class PortalTravelHandler {

    @SubscribeEvent
    public static void onEntityTravelToDimension(EntityTravelToDimensionEvent event) {
        if (event.getDimension() != World.NETHER) {
            return;
        }
        if (!(event.getEntity() instanceof ServerPlayerEntity)) {
            return;
        }

        ServerPlayerEntity player = (ServerPlayerEntity) event.getEntity();
        ServerWorld world = player.getLevel();

        if (isPlayerInOurPortal(world, player, ModBlocks.SKYRIA_PORTAL.get())) {
            event.setCanceled(true);
            ServerWorld skyria = world.getServer().getLevel(ModDimensions.ABYSSARIA);
            if (skyria == null) return;

            BlockPos vesselPos = findVesselPlacer(world, player.blockPosition());
            if (vesselPos == null) return;

            PlayerStats.get(player).setLastOverworldPortalPos(vesselPos);
            PortalBlueprint blueprint = PortalBlueprint.scan(world, vesselPos);
            SkyriaTeleporter teleporter = new SkyriaTeleporter(vesselPos.getX(), 107, vesselPos.getZ());
            teleporter.setBlueprint(blueprint);
            player.changeDimension(skyria, teleporter);

        } else if (isPlayerInOurPortal(world, player, ModBlocks.OVERWORLD_PORTAL.get())) {
            event.setCanceled(true);
            ServerWorld overworld = world.getServer().getLevel(World.OVERWORLD);
            if (overworld == null) return;

            BlockPos originPortalPos = PlayerStats.get(player).getLastOverworldPortalPos();
            BlockPos destination;

            if (originPortalPos != null) {
                destination = originPortalPos.north(2).above();
            } else {
                destination = player.getRespawnPosition();
                if (destination == null) {
                    destination = overworld.getSharedSpawnPos();
                }
            }
            player.changeDimension(overworld, new SkyriaTeleporter(destination.getX(), destination.getY(), destination.getZ()));
        }
    }

    private static boolean isPlayerInOurPortal(World world, ServerPlayerEntity player, Block portalBlock) {
        for (BlockPos pos : BlockPos.betweenClosed(player.blockPosition().offset(-1, 0, -1), player.blockPosition().offset(1, 2, 1))) {
            if (world.getBlockState(pos).is(portalBlock)) {
                return true;
            }
        }
        return false;
    }

    private static BlockPos findVesselPlacer(World world, BlockPos portalPos) {
        for (int y = 0; y < 5; y++) {
            for (int x = -2; x <= 2; x++) {
                for (int z = -2; z <= 2; z++) {
                    BlockPos checkPos = portalPos.offset(x, -y, z);
                    if (world.getBlockState(checkPos).is(ModBlocks.VESSEL_PLACER.get())) {
                        return checkPos;
                    }
                }
            }
        }
        return null;
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        // This logic is now handled by the teleporter itself.
    }
}