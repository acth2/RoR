package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.utils.References;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID)
public class PortalStructureLogger {

    @SubscribeEvent
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        Block placedBlock = event.getState().getBlock();
        BlockPos pos = event.getPos();

        if (placedBlock == ModBlocks.REALM_REMNANT.get() ||
            placedBlock == ModBlocks.VESSEL_PLACER.get() ||
            placedBlock == ModBlocks.POWER_CONTAINER.get()) {

            String blockName = placedBlock.getRegistryName().toString();
            System.out.println("[PORTAL_SCANNER] Player placed " + blockName + " at: " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ());
        }
    }
}