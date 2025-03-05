package fr.acth2.ror.init;

import fr.acth2.ror.init.constructors.armor.InfraniumArmorMaterial;
import fr.acth2.ror.init.constructors.armor.ghost.GhostArmorMaterial;
import fr.acth2.ror.init.constructors.armor.ghost.GhostBoots;
import fr.acth2.ror.init.constructors.items.ItemExample;
import fr.acth2.ror.init.constructors.items.RealmsVessel;
import fr.acth2.ror.init.constructors.items.RustedItem;
import fr.acth2.ror.init.constructors.items.SpiritAxe;
import fr.acth2.ror.init.constructors.tools.HopperItemTier;
import fr.acth2.ror.init.constructors.tools.InfraniumItemTier;
import fr.acth2.ror.init.constructors.tools.SpiritItemTier;
import fr.acth2.ror.utils.References;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.eventbus.api.IEventBus;
import org.lwjgl.system.CallbackI;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
            () -> new ItemExample(new Item.Properties())
    );

    public static final RegistryObject<Item> INFRANIUM_ORE = ITEMS.register("infranium_ore",
            () -> new BlockItem(ModBlocks.INFRANIUM_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> REALMS_VESSEL = ITEMS.register("realms_vessel",
            () -> new RealmsVessel(new Item.Properties())
    );

    public static final RegistryObject<Item> INFRANIUM_INGOT = ITEMS.register("infranium_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TRANSLUCENT_INGOT = ITEMS.register("translucent_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GHOST_DUST = ITEMS.register("ghost_dust",
            () -> new Item(new Item.Properties())
    );

    public static final RegistryObject<Item> INFRANIUM_CORE = ITEMS.register("infranium_core",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RUSTED_INFRANIUM_CORE = ITEMS.register("rusted_infranium_core",
            () -> new RustedItem(new Item.Properties())
    );

    public static final RegistryObject<Item> INFRANIUM_SWORD = ITEMS.register("infranium_sword",
            () -> new SwordItem(InfraniumItemTier.INFRANIUM, 5, -2.4F, new Item.Properties()));

    public static final RegistryObject<Item> HOPPER_SWORD = ITEMS.register("hopper_sword",
            () -> new SwordItem(HopperItemTier.HOPPER, 6, -1.9F, new Item.Properties()));
    public static final RegistryObject<Item> INFRANIUM_PICKAXE = ITEMS.register("infranium_pickaxe",
            () -> new PickaxeItem(InfraniumItemTier.INFRANIUM, 3, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> INFRANIUM_AXE = ITEMS.register("infranium_axe",
            () -> new AxeItem(InfraniumItemTier.INFRANIUM, 7.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> INFRANIUM_SHOVEL = ITEMS.register("infranium_shovel",
            () -> new ShovelItem(InfraniumItemTier.INFRANIUM, 2.5F, -3.0F, new Item.Properties()));

    public static final RegistryObject<Item> INFRANIUM_HELMET = ITEMS.register("infranium_helmet",
            () -> new ArmorItem(InfraniumArmorMaterial.INFRANIUM, EquipmentSlotType.HEAD, new Item.Properties()));
    public static final RegistryObject<Item> INFRANIUM_CHESTPLATE = ITEMS.register("infranium_chestplate",
            () -> new ArmorItem(InfraniumArmorMaterial.INFRANIUM, EquipmentSlotType.CHEST, new Item.Properties()));
    public static final RegistryObject<Item> INFRANIUM_LEGGINGS = ITEMS.register("infranium_leggings",
            () -> new ArmorItem(InfraniumArmorMaterial.INFRANIUM, EquipmentSlotType.LEGS, new Item.Properties()));
    public static final RegistryObject<Item> INFRANIUM_BOOTS = ITEMS.register("infranium_boots",
            () -> new ArmorItem(InfraniumArmorMaterial.INFRANIUM, EquipmentSlotType.FEET, new Item.Properties()));

    public static final RegistryObject<Item> GHOST_BOOTS = ITEMS.register("ghost_boots",
            () -> new GhostBoots(GhostArmorMaterial.GHOST, EquipmentSlotType.FEET, new Item.Properties()));

    public static final RegistryObject<Item> SPIRIT_AXE = ITEMS.register("spirit_axe",
            () -> new SpiritAxe(SpiritItemTier.SPIRIT, 0.0F, -1.0F, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
