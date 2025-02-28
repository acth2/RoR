package fr.acth2.ror.init;

import fr.acth2.ror.init.constructors.items.ItemExample;
import fr.acth2.ror.init.constructors.items.RealmsVessel;
import fr.acth2.ror.utils.References;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
            () -> new ItemExample(new Item.Properties())
    );

    public static final RegistryObject<Item> REALMS_VESSEL = ITEMS.register("realms_vessel",
            () -> new RealmsVessel(new Item.Properties())
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
