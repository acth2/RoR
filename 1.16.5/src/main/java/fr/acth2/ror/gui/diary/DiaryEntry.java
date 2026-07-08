package fr.acth2.ror.gui.diary;

import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;

public class DiaryEntry {
    private final String monsterName;
    private final EntityType<?> monsterType;
    private final String description;

    public DiaryEntry(String monsterName, EntityType<?> monsterType, String description) {
        this.monsterName = monsterName;
        this.monsterType = monsterType;
        this.description = description;
    }

    public String getMonsterName() {
        return monsterName;
    }

    public EntityType<?> getMonsterType() {
        return monsterType;
    }

    public String getDescription() {
        return description;
    }

    public CompoundNBT serializeNBT() {
        CompoundNBT tag = new CompoundNBT();
        tag.putString("monsterName", monsterName);
        tag.putString("monsterType", monsterType.getRegistryName().toString());
        tag.putString("description", description);
        return tag;
    }

    public static DiaryEntry deserializeNBT(CompoundNBT tag) {
        String name = tag.getString("monsterName");
        String typeRL = tag.getString("monsterType");
        EntityType<?> type = EntityType.byString(typeRL).orElse(null);
        String desc = tag.getString("description");
        return new DiaryEntry(name, type, desc);
    }
}
