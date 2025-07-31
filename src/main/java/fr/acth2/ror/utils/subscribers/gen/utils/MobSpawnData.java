package fr.acth2.ror.utils.subscribers.gen.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class MobSpawnData {
    private final RegistryObject<? extends EntityType<?>> entityType;
    private final int spawnChance;
    private final Block requiredBlock;

    public MobSpawnData(RegistryObject<? extends EntityType<?>> entityTypeRegistryObject, int spawnChance, Block requiredBlock) {
        this.entityType = entityTypeRegistryObject;
        this.spawnChance = spawnChance;
        this.requiredBlock = requiredBlock;
    }


    public EntityType<?> getEntityType() {
        return entityType.get();
    }

    public Block getRequiredBlock() {
        return requiredBlock;
    }
}