package fr.acth2.ror.init;

import fr.acth2.ror.init.constructors.armor.gefranium.GefraniumArmor;
import fr.acth2.ror.init.constructors.armor.gefranium.GefraniumArmorMaterial;
import fr.acth2.ror.init.constructors.armor.infranium.InfraniumArmor;
import fr.acth2.ror.init.constructors.armor.infranium.InfraniumArmorMaterial;
import fr.acth2.ror.init.constructors.armor.ghost.GhostArmorMaterial;
import fr.acth2.ror.init.constructors.armor.ghost.GhostBoots;
import fr.acth2.ror.init.constructors.armor.seeker.SeekerArmorMaterial;
import fr.acth2.ror.init.constructors.armor.seeker.SeekerHelmet;
import fr.acth2.ror.init.constructors.items.ItemExample;
import fr.acth2.ror.init.constructors.items.RealmsVessel;
import fr.acth2.ror.init.constructors.items.RustedItem;
import fr.acth2.ror.init.constructors.items.SpiritAxe;
import fr.acth2.ror.init.constructors.tools.GefraniumItemTier;
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

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, References.MODID);

    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item",
            () -> new ItemExample(new Item.Properties())
    );

    public static final RegistryObject<Item> INFRANIUM_ORE = ITEMS.register("infranium_ore",
            () -> new BlockItem(ModBlocks.INFRANIUM_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> GEFRANIUM_ORE = ITEMS.register("gefranium_ore",
            () -> new BlockItem(ModBlocks.GEFRANIUM_ORE.get(), new Item.Properties()));

    public static final RegistryObject<Item> REALMS_VESSEL = ITEMS.register("realms_vessel",
            () -> new RealmsVessel(new Item.Properties())
    );

    public static final RegistryObject<Item> INFRANIUM_INGOT = ITEMS.register("infranium_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> GEFRANIUM_INGOT = ITEMS.register("gefranium_ingot",
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
            () -> new InfraniumArmor(InfraniumArmorMaterial.INFRANIUM, EquipmentSlotType.HEAD, new Item.Properties()));
    public static final RegistryObject<Item> INFRANIUM_CHESTPLATE = ITEMS.register("infranium_chestplate",
            () -> new InfraniumArmor(InfraniumArmorMaterial.INFRANIUM, EquipmentSlotType.CHEST, new Item.Properties()));
    public static final RegistryObject<Item> INFRANIUM_LEGGINGS = ITEMS.register("infranium_leggings",
            () -> new InfraniumArmor(InfraniumArmorMaterial.INFRANIUM, EquipmentSlotType.LEGS, new Item.Properties()));
    public static final RegistryObject<Item> INFRANIUM_BOOTS = ITEMS.register("infranium_boots",
            () -> new InfraniumArmor(InfraniumArmorMaterial.INFRANIUM, EquipmentSlotType.FEET, new Item.Properties()));


    public static final RegistryObject<Item> GEFRANIUM_SWORD = ITEMS.register("gefranium_sword",
            () -> new SwordItem(GefraniumItemTier.GEFRANIUM, 5, -2.0F, new Item.Properties()));

    public static final RegistryObject<Item> GEFRANIUM_PICKAXE = ITEMS.register("gefranium_pickaxe",
            () -> new PickaxeItem(GefraniumItemTier.GEFRANIUM, 3, -2.8F, new Item.Properties()));
    public static final RegistryObject<Item> GEFRANIUM_AXE = ITEMS.register("gefranium_axe",
            () -> new AxeItem(GefraniumItemTier.GEFRANIUM, 7.0F, -3.0F, new Item.Properties()));
    public static final RegistryObject<Item> GEFRANIUM_SHOVEL = ITEMS.register("gefranium_shovel",
            () -> new ShovelItem(GefraniumItemTier.GEFRANIUM, 2.5F, -3.0F, new Item.Properties()));

    public static final RegistryObject<Item> GEFRANIUM_HELMET = ITEMS.register("gefranium_helmet",
            () -> new GefraniumArmor(GefraniumArmorMaterial.GEFRANIUM, EquipmentSlotType.HEAD, new Item.Properties()));
    public static final RegistryObject<Item> GEFRANIUM_CHESTPLATE = ITEMS.register("gefranium_chestplate",
            () -> new GefraniumArmor(GefraniumArmorMaterial.GEFRANIUM, EquipmentSlotType.CHEST, new Item.Properties()));
    public static final RegistryObject<Item> GEFRANIUM_LEGGINGS = ITEMS.register("gefranium_leggings",
            () -> new GefraniumArmor(GefraniumArmorMaterial.GEFRANIUM, EquipmentSlotType.LEGS, new Item.Properties()));
    public static final RegistryObject<Item> GEFRANIUM_BOOTS = ITEMS.register("gefranium_boots",
            () -> new GefraniumArmor(GefraniumArmorMaterial.GEFRANIUM, EquipmentSlotType.FEET, new Item.Properties()));

    public static final RegistryObject<Item> GHOST_BOOTS = ITEMS.register("ghost_boots",
            () -> new GhostBoots(GhostArmorMaterial.GHOST, EquipmentSlotType.FEET, new Item.Properties()));

    public static final RegistryObject<Item> SEEKER_HELMET = ITEMS.register("seeker_helmet",
            () -> new SeekerHelmet(SeekerArmorMaterial.SEEKER, EquipmentSlotType.HEAD, new Item.Properties()));


    public static final RegistryObject<Item> SPIRIT_AXE = ITEMS.register("spirit_axe",
            () -> new SpiritAxe(SpiritItemTier.SPIRIT, 0.0F, -1.0F, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
