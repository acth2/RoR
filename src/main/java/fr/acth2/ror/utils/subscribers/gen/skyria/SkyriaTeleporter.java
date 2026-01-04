package fr.acth2.ror.utils.subscribers.gen.skyria;

import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.utils.subscribers.mod.PortalBlueprint;
import fr.acth2.ror.utils.subscribers.mod.PortalScanner;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.Optional;
import java.util.function.Function;

public class SkyriaTeleporter implements ITeleporter {

    private final BlockPos pos;
    private PortalBlueprint blueprint;

    public SkyriaTeleporter(double x, double y, double z) {
        this.pos = new BlockPos(x, y, z);
    }

    public void setBlueprint(PortalBlueprint blueprint) {
        this.blueprint = blueprint;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        Entity newEntity = repositionEntity.apply(false);

        if (blueprint != null) {
            BlockPos portalCenter = findOrCreatePortal(destWorld, new BlockPos(pos.getX(), 110, pos.getZ()));
            BlockPos safeLandingPos = findSafeLandingSpot(destWorld, portalCenter);

            newEntity.teleportTo(safeLandingPos.getX() + 0.5, safeLandingPos.getY(), safeLandingPos.getZ() + 0.5);

        } else {
            newEntity.teleportTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
        }
        
        newEntity.setPortalCooldown();
        return newEntity;
    }

    private BlockPos findOrCreatePortal(ServerWorld world, BlockPos searchCenter) {
        Optional<BlockPos> existingPortal = BlockPos.findClosestMatch(searchCenter, 16, 16,
                (pos) -> world.getBlockState(pos).is(ModBlocks.VESSEL_PLACER.get()));

        if (existingPortal.isPresent()) {
            return existingPortal.get();
        } else {
            blueprint.build(world, searchCenter);
            return searchCenter;
        }
    }

    private BlockPos findSafeLandingSpot(ServerWorld world, BlockPos portalCenter) {
        PortalScanner.ScanResult result = PortalScanner.scan(world, portalCenter);
        if (result.success) {
            if (result.axis == Direction.Axis.X) {
                return portalCenter.north(2).above();
            } else {
                return portalCenter.east(2).above();
            }
        }
        return portalCenter.north(2).above();
    }
}