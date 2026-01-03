package fr.acth2.ror.init;

import fr.acth2.ror.init.constructors.blocks.tile.VesselPlacerTileEntity;
import fr.acth2.ror.utils.References;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, References.MODID);

    public static final RegistryObject<TileEntityType<VesselPlacerTileEntity>> VESSEL_PLACER_TILE_ENTITY =
            TILE_ENTITIES.register("vessel_placer_tile_entity", () -> TileEntityType.Builder.of(
                    VesselPlacerTileEntity::new, ModBlocks.VESSEL_PLACER.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}