package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.constructors.blocks.AbyssalGlue;
import fr.acth2.ror.utils.References;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class AbyssariaHandler {
    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (event.getWorld() == null || event.getWorld().isClientSide()) return;
        if (!(event.getChunk() instanceof Chunk)) return;

        Chunk chunk = (Chunk) event.getChunk();
        World world = (World) event.getWorld();

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                BlockPos pos = new BlockPos(chunk.getPos().getMinBlockX() + x, 1, chunk.getPos().getMinBlockZ() + z);
                BlockState state = world.getBlockState(pos);
                if (state.getBlock() instanceof AbyssalGlue && world.getBlockEntity(pos) == null) {
                    BlockEntity te = ModBlocks.ABYSSAL_GLUE.get().createTileEntity(state, world);
                    if (te != null) {
                        world.setBlockEntity(pos, te);
                    }
                }
            }
        }
    }
}