package fr.acth2.ror.impl;

import fr.acth2.ror.api.registry.IRegistryHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings({"unchecked", "rawtypes"})
public class RegistryHelper119 implements IRegistryHelper {

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

    public DeferredRegister makeRegistry(String modId, String registryName, Class type) {
        return DeferredRegister.create(
                net.minecraft.resources.ResourceKey.createRegistryKey(
                        new net.minecraft.resources.ResourceLocation(modId, registryName)
                ),
                modId
        );
    }

    @Override
    public DeferredRegister makeRegistry(String modId, Class type) {
        return makeRegistry(modId, type.getSimpleName().toLowerCase(), type);
    }
}