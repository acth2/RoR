package fr.acth2.ror.utils.subscribers.mod.skills;

import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.client.PlayerStatsCapability;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerStats implements INBTSerializable<CompoundNBT> {

    private int level;
    private int health;
    private int dexterity;
    private int strength;

    public PlayerStats(int level, int health, int dexterity, int strength) {
        this.level = level;
        this.health = health;
        this.dexterity = dexterity;
        this.strength = strength;
    }

    public int getLevel() {
        return level;
    }


    public int getHealth() {
        return health;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getStrength() {
        return strength;
    }

    public boolean canLevelUp(String stat, int coins) {
        return coins >= getLevelUpCost(stat);
    }

    public int getLevelUpCost(String stat) {
        switch (stat) {
            case "health":
                return 100 + (health * 10);
            case "stamina":

            case "dexterity":
                return 100 + (dexterity * 10);
            case "strength":
                return 100 + (strength * 20);
            default:
                throw new IllegalArgumentException("Invalid stat: " + stat);
        }
    }

    public void levelUp(String stat, PlayerEntity player) {
        switch (stat) {
            case "health":
                health += 2;
                System.out.println("Health stat increased to: " + health);
                ModifiableAttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
                if (maxHealthAttribute != null) {
                    maxHealthAttribute.removeModifier(References.HEALTH_MODIFIER_UUID);

                    maxHealthAttribute.addPermanentModifier(new AttributeModifier(
                            References.HEALTH_MODIFIER_UUID,
                            "player_health_modifier",
                            health - References.HEALTH_REDUCER,
                            AttributeModifier.Operation.ADDITION
                    ));
                }
                break;
            case "dexterity":
                dexterity += 1;
                System.out.println("Dexterity stat increased to: " + dexterity);
                ModifiableAttributeInstance maxDexAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
                if (maxDexAttribute != null) {
                    maxDexAttribute.removeModifier(References.DEXTERITY_MODIFIER_UUID);

                    maxDexAttribute.addPermanentModifier(new AttributeModifier(
                            References.DEXTERITY_MODIFIER_UUID,
                            "player_dex_modifier",
                            (double) dexterity / References.DEXTERITY_REDUCER,
                            AttributeModifier.Operation.ADDITION
                    ));
                }
                break;
            case "strength":
                strength += 1;
                break;
            default:
                throw new IllegalArgumentException("Invalid stat: " + stat);
        }

        level += 1;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("level", level);
        nbt.putInt("health", health);
        nbt.putInt("dexterity", dexterity);
        nbt.putInt("strength", strength);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        level = nbt.getInt("level");
        health = nbt.getInt("health");
        dexterity = nbt.getInt("dexterity");
        dexterity = nbt.getInt("strength");
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public static PlayerStats get(PlayerEntity player) {
        LazyOptional<PlayerStats> stats = player.getCapability(PlayerStatsCapability.PLAYER_STATS_CAPABILITY);
        if (!stats.isPresent()) {
            System.err.println("PlayerStats capability not found for player: " + player.getName().getString());
        }
        return stats.orElseThrow(() -> new IllegalStateException("PlayerStats capability not found!"));
    }

}