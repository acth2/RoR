package fr.acth2.ror.impl;

import fr.acth2.ror.api.registry.IRegistryHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RegistryHelper118 implements IRegistryHelper {

    @Override
    public DeferredRegister makeBlockRegistry(String modId) {
        return DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
    }

    @Override
    public DeferredRegister makeItemRegistry(String modId) {
        return DeferredRegister.create(ForgeRegistries.ITEMS, modId);
    }

    @Override
    public DeferredRegister makeEntityRegistry(String modId) {
        return DeferredRegister.create(ForgeRegistries.Keys.ENTITY_TYPES, modId);
    }

    @Override
    public DeferredRegister makeBlockEntityRegistry(String modId) {
        return DeferredRegister.create(ForgeRegistries.Keys.BLOCK_ENTITY_TYPES, modId);
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public DeferredRegister makeRegistry(String modId, String registryName, Class type) {
        return DeferredRegister.create(
                net.minecraft.resources.ResourceKey.createRegistryKey(
                        new net.minecraft.resources.ResourceLocation(modId, registryName)
                ),
                modId
        );
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public DeferredRegister makeRegistry(String modId, Class type) {
        return makeRegistry(modId, type.getSimpleName().toLowerCase(), type);
    }
}