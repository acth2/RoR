package fr.acth2.ror.impl;

import fr.acth2.ror.api.registry.IRegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class RegistryHelper116 implements IRegistryHelper {

    @Override
    public DeferredRegister<Block> makeBlockRegistry(String modId) {
        return DeferredRegister.create(ForgeRegistries.BLOCKS, modId);
    }

    @Override
    public DeferredRegister<Item> makeItemRegistry(String modId) {
        return DeferredRegister.create(ForgeRegistries.ITEMS, modId);
    }

    @Override
    public DeferredRegister<EntityType<?>> makeEntityRegistry(String modId) {
        return DeferredRegister.create(ForgeRegistries.ENTITIES, modId);
    }

    @Override
    public DeferredRegister<TileEntityType<?>> makeBlockEntityRegistry(String modId) {
        return DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, modId);
    }

    @Override
    public <T extends IForgeRegistryEntry<T>> DeferredRegister<T> makeRegistry(String modId, String registryName, Class<T> type) {
        return DeferredRegister.create(type, modId);
    }

    @Override
    public <T extends IForgeRegistryEntry<T>> DeferredRegister<T> makeRegistry(String modId, Class<T> type) {
        return makeRegistry(modId, type.getSimpleName().toLowerCase(), type);
    }
}