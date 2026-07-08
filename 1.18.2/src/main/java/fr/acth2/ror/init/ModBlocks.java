package fr.acth2.ror.init;

import fr.acth2.ror.api.Services;
import fr.acth2.ror.api.block.Props;
import fr.acth2.ror.init.constructors.blocks.*;
import fr.acth2.ror.utils.References;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.fluid.Fluid;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            Services.REGISTRY.makeBlockRegistry(References.MODID);
    public static final DeferredRegister<Item> ITEMS =
            Services.REGISTRY.makeItemRegistry(References.MODID);

    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block",
            () -> new Block((AbstractBlock.Properties) Props.wood())
    );

    public static final RegistryObject<Block> SKYRIA_BRICK = BLOCKS.register("skyria_brick",
            () -> new Block(((AbstractBlock.Properties) Props.stone()).strength(6, 4.5F))
    );

    public static final RegistryObject<Block> POLISHED_SKYRIA_BRICK = BLOCKS.register("polished_skyria_brick",
            () -> new Block(((AbstractBlock.Properties) Props.stone()).strength(6, 4.5F))
    );

    public static final RegistryObject<Block> RESTORATION_STATION = BLOCKS.register("restoration_station",
            RestorationStation::new
    );

    public static final RegistryObject<Block> VESSEL_PLACER = BLOCKS.register("vessel_placer",
            () -> new VesselPlacer());

    public static final RegistryObject<Block> VESSEL_PLACER_SKIN0 = BLOCKS.register("vessel_placer_skin0",
            () -> new VesselPlacerSkinBlock((((AbstractBlock.Properties) Props.stone()))
                    .strength(1.5f, 6.0f)
                    .sound(SoundType.STONE)
                    .harvestLevel(1)
                    .harvestTool(ToolType.PICKAXE)
                    .requiresCorrectToolForDrops()));


    public static final RegistryObject<Block> REALM_REMNANT = BLOCKS.register("realm_remnant",
            () -> new Block((((AbstractBlock.Properties) Props.stone()))
                    .strength(2.0f, 8.0f)
                    .sound(SoundType.STONE)
                    .harvestLevel(1)
                    .harvestTool(ToolType.PICKAXE)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> POWER_CONTAINER = BLOCKS.register("power_container",
            () -> new PowerContainerBlock());

    public static final RegistryObject<Block> INFRANIUM_ORE = BLOCKS.register("infranium_ore", InfraniumOre::new);
    public static final RegistryObject<Block> GEFRANIUM_ORE = BLOCKS.register("gefranium_ore", GefraniumOre::new);
    public static final RegistryObject<Block> ORONIUM_ORE = BLOCKS.register("oronium_ore", OroniumOre::new);
    public static final RegistryObject<Block> RADIUM_ORE = BLOCKS.register("radium_ore", RadiumOre::new);
    public static final RegistryObject<Block> SULFUR_ORE = BLOCKS.register("sulfur_ore", SulfurOre::new);
    public static final RegistryObject<Block> CLOUPIS_ORE = BLOCKS.register("cloupis_ore", CloupisOre::new);

    public static final RegistryObject<Block> ABYSSAL_STONE = BLOCKS.register("abyssal_stone",
            () -> new Block((((AbstractBlock.Properties) Props.stone()).strength(1.5F, 5F)))
    );

    public static final RegistryObject<Block> ABYSSAL_ANDESITE = BLOCKS.register("abyssal_andesite",
            () -> new Block((((AbstractBlock.Properties) Props.stone()).strength(1.5F, 5F)))
    );

    public static final RegistryObject<Block> ABYSSAL_BRICK = BLOCKS.register("abyssal_brick",
            () -> new Block((((AbstractBlock.Properties) Props.stone()).strength(1.7F, 5.5F)))
    );

    public static final RegistryObject<Block> GEFRANIUM_BLOCK = BLOCKS.register("gefranium_block",
            () -> new Block((((AbstractBlock.Properties) Props.metal()).strength(0.7f, 4.5f)))

    );

    public static final RegistryObject<Block> CLOUPIS_BLOCK = BLOCKS.register("cloupis_block",
            () -> new Block((((AbstractBlock.Properties) Props.metal()).strength(0.7f, 4.5f)))

    );

    public static final RegistryObject<Block> RADIUM_BLOCK = BLOCKS.register("radium_block",
            () -> new Block((((AbstractBlock.Properties) Props.metal()).strength(0.7f, 4.5f))
                    .lightLevel(state -> 5))

    );

    public static final RegistryObject<Block> ORONIUM_BLOCK = BLOCKS.register("oronium_block",
            () -> new Block((((AbstractBlock.Properties) Props.metal()).strength(0.7f, 4.5f)))

    );

    public static final RegistryObject<Block> INFRANIUM_BLOCK = BLOCKS.register("infranium_block",
            () -> new Block((((AbstractBlock.Properties) Props.metal()).strength(0.7f, 4.5f)))

    );

    public static final RegistryObject<Block> SKYRIA_PORTAL = BLOCKS.register("skyria_portal",
            () -> new SkyriaPortalBlock((((AbstractBlock.Properties) Props.glass()))
                    .noCollission()
                    .randomTicks()
                    .strength(-1.0F)
                    .sound(SoundType.GLASS)
                    .lightLevel(state -> 11)));

    public static final RegistryObject<Block> OVERWORLD_PORTAL = BLOCKS.register("overworld_portal",
            () -> new OverworldPortalBlock((((AbstractBlock.Properties) Props.glass()))
                    .noCollission()
                    .randomTicks()
                    .strength(-1.0F)
                    .sound(SoundType.GLASS)
                    .lightLevel(state -> 11)));

    public static final RegistryObject<Block> SKYRIA_AIR = BLOCKS.register("skyria_air", SkyriaAir::new);

    public static final RegistryObject<Block> CLOUD_PIECE = BLOCKS.register("cloud_piece", CloudPiece::new);

    public static final RegistryObject<Block> ABYSSAL_GLUE = BLOCKS.register("abyssal_glue",
            () -> new AbyssalGlue());

    public static final RegistryObject<Item> SKYRIA_PORTAL_ITEM = ITEMS.register("skyria_portal",
            () -> new BlockItem(SKYRIA_PORTAL.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> ABYSSAL_STONE_ITEM = ITEMS.register("abyssal_stone",
            () -> new BlockItem(ABYSSAL_STONE.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> ABYSSAL_ANDESITE_ITEM = ITEMS.register("abyssal_andesite",
            () -> new BlockItem(ABYSSAL_ANDESITE.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> ABYSSAL_BRICK_ITEM = ITEMS.register("abyssal_brick",
            () -> new BlockItem(ABYSSAL_BRICK.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> ABYSSAL_GLUE_ITEM = ITEMS.register("abyssal_glue",
            () -> new BlockItem(ABYSSAL_GLUE.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> OVERWORLD_PORTAL_ITEM = ITEMS.register("overworld_portal",
            () -> new BlockItem(OVERWORLD_PORTAL.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> CLOUD_PIECE_ITEM = ITEMS.register("cloud_piece",
            () -> new BlockItem(CLOUD_PIECE.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> SKYRIA_BRICK_ITEM = ITEMS.register("skyria_brick",
            () -> new BlockItem(SKYRIA_BRICK.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> REALM_REMNANT_ITEM = ITEMS.register("realm_remnant",
            () -> new BlockItem(REALM_REMNANT.get(), new Item.Properties())
    );

        public static final RegistryObject<Item> POWER_CONTAINER_ITEM = ITEMS.register("power_container",
            () -> new BlockItem(POWER_CONTAINER.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> CLOUPIS_ORE_ITEM = ITEMS.register("cloupis_ore",
            () -> new BlockItem(CLOUPIS_ORE.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> SULFUR_ORE_ITEM = ITEMS.register("sulfur_ore",
            () -> new BlockItem(SULFUR_ORE.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> CLOUPIS_BLOCK_ITEM = ITEMS.register("cloupis_block",
            () -> new BlockItem(CLOUPIS_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> VESSEL_PLACER_ITEM = ITEMS.register("vessel_placer",
            () -> new BlockItem(VESSEL_PLACER.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> VESSEL_PLACER_SKIN0_ITEM = ITEMS.register("vessel_placer_skin0",
            () -> new BlockItem(VESSEL_PLACER_SKIN0.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> POLISHED_SKYRIA_BRICK_ITEM = ITEMS.register("polished_skyria_brick",
            () -> new BlockItem(POLISHED_SKYRIA_BRICK.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> SKYRIA_AIR_ITEM = ITEMS.register("skyria_air",
            () -> new BlockItem(SKYRIA_AIR.get(), new Item.Properties())
    );
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block",
            () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> RESTORATION_STATION_ITEM = ITEMS.register("restoration_station",
            () -> new BlockItem(RESTORATION_STATION.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> INFRANIUM_BLOCK_ITEM = ITEMS.register("infranium_block",
            () -> new BlockItem(INFRANIUM_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> GEFRANIUM_BLOCK_ITEM = ITEMS.register("gefranium_block",
            () -> new BlockItem(GEFRANIUM_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> RADIUM_BLOCK_ITEM = ITEMS.register("radium_block",
            () -> new BlockItem(RADIUM_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> ORONIUM_BLOCK_ITEM = ITEMS.register("oronium_block",
            () -> new BlockItem(ORONIUM_BLOCK.get(), new Item.Properties())
    );

    public static final RegistryObject<Item> INFRANIUM_ORE_ITEM = ITEMS.register("infranium_ore",
            () -> new BlockItem(INFRANIUM_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> GEFRANIUM_ORE_ITEM = ITEMS.register("gefranium_ore",
            () -> new BlockItem(GEFRANIUM_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ORONIUM_ORE_ITEM = ITEMS.register("oronium_ore",
            () -> new BlockItem(ORONIUM_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> RADIUM_ORE_ITEM = ITEMS.register("radium_ore",
            () -> new BlockItem(RADIUM_ORE.get(), new Item.Properties()));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}