package fr.acth2.ror.utils.subscribers.mod;

import com.google.common.collect.Lists;
import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModDimensions;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import java.util.List;

public class PortalBlueprint {

    private final List<BlockData> blocks;

    private PortalBlueprint(List<BlockData> blocks) {
        this.blocks = blocks;
    }

    public static PortalBlueprint scan(World world, BlockPos center) {
        List<BlockData> foundBlocks = Lists.newArrayList();
        Block vesselPlacer = ModBlocks.VESSEL_PLACER.get();
        Block remnant = ModBlocks.REALM_REMNANT.get();
        Block powerContainer = ModBlocks.POWER_CONTAINER.get();
        Block skyriaPortal = ModBlocks.SKYRIA_PORTAL.get();
        Block overworldPortal = ModBlocks.OVERWORLD_PORTAL.get();

        for (int y = -2; y <= 7; y++) {
            for (int x = -6; x <= 6; x++) {
                for (int z = -6; z <= 6; z++) {
                    BlockPos currentPos = center.offset(x, y, z);
                    BlockState state = world.getBlockState(currentPos);
                    Block block = state.getBlock();

                    if (block == vesselPlacer || block == remnant || block == powerContainer || block == skyriaPortal || block == overworldPortal) {
                        foundBlocks.add(new BlockData(state, currentPos.subtract(center)));
                    }
                }
            }
        }
        return new PortalBlueprint(foundBlocks);
    }

    public void build(ServerWorld world, BlockPos center) {
        boolean isSkyria = world.dimension().equals(ModDimensions.SKYRIA);

        for (BlockData data : blocks) {
            Block blockToPlace = data.state.getBlock();
            if (!(blockToPlace instanceof NetherPortalBlock)) {
                BlockState stateToPlace = data.state;
                if (isSkyria) {
                    if (blockToPlace == ModBlocks.REALM_REMNANT.get()) {
                        stateToPlace = ModBlocks.SKYRIA_BRICK.get().defaultBlockState();
                    } else if (blockToPlace == ModBlocks.POWER_CONTAINER.get()) {
                        stateToPlace = ModBlocks.POLISHED_SKYRIA_BRICK.get().defaultBlockState();
                    } else if (blockToPlace == ModBlocks.VESSEL_PLACER.get()) {
                        stateToPlace = ModBlocks.VESSEL_PLACER_SKIN0.get().defaultBlockState();
                    }
                }
                world.setBlock(center.offset(data.relativePos), stateToPlace, 3);
            }
        }

        for (BlockData data : blocks) {
            if (data.state.getBlock() instanceof NetherPortalBlock) {
                BlockState returnPortalState = ModBlocks.OVERWORLD_PORTAL.get().defaultBlockState()
                        .setValue(NetherPortalBlock.AXIS, data.state.getValue(NetherPortalBlock.AXIS));
                world.setBlock(center.offset(data.relativePos), returnPortalState, 3);
            }
        }
        
        world.sendBlockUpdated(center, world.getBlockState(center), world.getBlockState(center), 3);
    }

    public ListNBT serialize() {
        ListNBT list = new ListNBT();
        for (BlockData data : blocks) {
            list.add(data.serialize());
        }
        return list;
    }

    public static PortalBlueprint deserialize(ListNBT list) {
        List<BlockData> foundBlocks = Lists.newArrayList();
        for (int i = 0; i < list.size(); i++) {
            foundBlocks.add(BlockData.deserialize(list.getCompound(i)));
        }
        return new PortalBlueprint(foundBlocks);
    }

    private static class BlockData {
        public final BlockState state;
        public final BlockPos relativePos;

        public BlockData(BlockState state, BlockPos relativePos) {
            this.state = state;
            this.relativePos = relativePos;
        }

        public CompoundNBT serialize() {
            CompoundNBT nbt = new CompoundNBT();
            nbt.put("state", NBTUtil.writeBlockState(state));
            nbt.put("pos", NBTUtil.writeBlockPos(relativePos));
            return nbt;
        }

        public static BlockData deserialize(CompoundNBT nbt) {
            BlockState state = NBTUtil.readBlockState(nbt.getCompound("state"));
            BlockPos pos = NBTUtil.readBlockPos(nbt.getCompound("pos"));
            return new BlockData(state, pos);
        }
    }
}