package fr.acth2.ror.utils.subscribers.gen.utils;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;

public class MobSpawnData {
    private final RegistryObject<? extends EntityType<?>> entityType;
    private final int spawnChance;
    private final Block requiredBlock;
    // 0 = broken-moon / 1 = blood-sun
    private final int eventInteger;

    public MobSpawnData(RegistryObject<? extends EntityType<?>> entityTypeRegistryObject, int spawnChance, Block requiredBlock) {
        this.entityType = entityTypeRegistryObject;
        this.spawnChance = spawnChance;
        this.requiredBlock = requiredBlock;
        this.eventInteger = -1;
    }

    public MobSpawnData(RegistryObject<? extends EntityType<?>> entityTypeRegistryObject, int spawnChance, Block requiredBlock, int eventEntityID) {
        this.entityType = entityTypeRegistryObject;
        this.spawnChance = spawnChance;
        this.requiredBlock = requiredBlock;
        this.eventInteger = eventEntityID;
    }

    public EntityType<?> getEntityType() {
        return entityType.get();
    }

    public Block getRequiredBlock() {
        return requiredBlock;
    }

    public int getEventID () {
        return eventInteger;
    }
}