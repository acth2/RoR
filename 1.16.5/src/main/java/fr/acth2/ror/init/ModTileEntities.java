package fr.acth2.ror.init;

import fr.acth2.ror.api.Services;
import fr.acth2.ror.init.constructors.blocks.tile.AbyssalGlueTileEntity;
import fr.acth2.ror.init.constructors.blocks.tile.PowerContainerTileEntity;
import fr.acth2.ror.init.constructors.blocks.tile.VesselPlacerTileEntity;
import fr.acth2.ror.utils.References;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class ModTileEntities {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES =
            (DeferredRegister<TileEntityType<?>>) Services.REGISTRY.makeBlockEntityRegistry(References.MODID);

    @SuppressWarnings("unchecked")
    public static final RegistryObject<TileEntityType<VesselPlacerTileEntity>> VESSEL_PLACER_TILE_ENTITY =
            TILE_ENTITIES.register("vessel_placer_tile_entity", () ->
                    (TileEntityType<VesselPlacerTileEntity>) Services.TILE_ENTITIES.create(
                            VesselPlacerTileEntity::new,
                            ModBlocks.VESSEL_PLACER.get(),
                            ModBlocks.VESSEL_PLACER_SKIN0.get()
                    ));

    @SuppressWarnings("unchecked")
    public static final RegistryObject<TileEntityType<PowerContainerTileEntity>> POWER_CONTAINER_TILE_ENTITY =
            TILE_ENTITIES.register("power_container_tile_entity", () ->
                    (TileEntityType<PowerContainerTileEntity>) Services.TILE_ENTITIES.create(
                            PowerContainerTileEntity::new,
                            ModBlocks.POWER_CONTAINER.get()
                    ));

    @SuppressWarnings("unchecked")
    public static final RegistryObject<TileEntityType<AbyssalGlueTileEntity>> ABYSSAL_GLUE_TILE_ENTITY =
            TILE_ENTITIES.register("abyssal_glue_tile_entity", () ->
                    (TileEntityType<AbyssalGlueTileEntity>) Services.TILE_ENTITIES.create(
                            AbyssalGlueTileEntity::new,
                            ModBlocks.ABYSSAL_GLUE.get()
                    ));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}