package fr.acth2.ror.init;

import fr.acth2.ror.init.constructors.blocks.InfraniumOre;
import fr.acth2.ror.utils.References;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, References.MODID);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);

    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block",
            () -> new Block(Block.Properties.of(Material.WOOD).strength(1.5f, 6.0f))

    );

    public static final RegistryObject<Block> INFRANIUM_ORE = BLOCKS.register("infranium_ore", InfraniumOre::new);

    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block",
            () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties())
    );


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}