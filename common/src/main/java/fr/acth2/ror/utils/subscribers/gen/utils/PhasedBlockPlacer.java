package fr.acth2.ror.utils.subscribers.gen.utils;

import net.minecraft.world.ISeedReader;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod.EventBusSubscriber
public class PhasedBlockPlacer {
    private static final Queue<BlockToPlace> blockQueue = new ConcurrentLinkedQueue<>();
    private static final int BLOCKS_PER_TICK = 2048;

    public static void addToQueue(List<BlockToPlace> blocks) {
        blockQueue.addAll(blocks);
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!blockQueue.isEmpty()) {
                ISeedReader world = ServerTickHandler.getLastWorld();
                if (world != null) {
                    for (int i = 0; i < BLOCKS_PER_TICK && !blockQueue.isEmpty(); i++) {
                        BlockToPlace block = blockQueue.poll();
                        if (block != null) {
                            world.setBlock(block.pos, block.state, 2);
                        }
                    }
                }
            }
        }
    }
}
