package fr.acth2.ror.init;

import fr.acth2.ror.init.constructors.blocks.tile.AbyssalGlueTileEntity;
import fr.acth2.ror.init.constructors.blocks.tile.PowerContainerTileEntity;
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
                    VesselPlacerTileEntity::new, ModBlocks.VESSEL_PLACER.get(), ModBlocks.VESSEL_PLACER_SKIN0.get()).build(null));

    public static final RegistryObject<TileEntityType<PowerContainerTileEntity>> POWER_CONTAINER_TILE_ENTITY =
            TILE_ENTITIES.register("power_container_tile_entity", () -> TileEntityType.Builder.of(
                    PowerContainerTileEntity::new, ModBlocks.POWER_CONTAINER.get()).build(null));

    public static final RegistryObject<TileEntityType<AbyssalGlueTileEntity>> ABYSSAL_GLUE_TILE_ENTITY =
            TILE_ENTITIES.register("abyssal_glue_tile_entity", () -> TileEntityType.Builder.of(
                    AbyssalGlueTileEntity::new, ModBlocks.ABYSSAL_GLUE.get()).build(null));

    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}