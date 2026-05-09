package fr.acth2.ror.api.registry;

import net.minecraft.block.Block;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IRegistryHelper {

    DeferredRegister<Block> makeBlockRegistry(String modId);

    DeferredRegister<Item> makeItemRegistry(String modId);

    DeferredRegister<EntityType<?>> makeEntityRegistry(String modId);

    DeferredRegister<TileEntityType<?>> makeBlockEntityRegistry(String modId);

    <T extends IForgeRegistryEntry<T>> DeferredRegister<T> makeRegistry(String modId, Class<T> type);
    <T extends IForgeRegistryEntry<T>> DeferredRegister<T> makeRegistry(String modId, String registryName, Class<T> type);
}