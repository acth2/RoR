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
    private int stamina;
    private int strength;

    public PlayerStats(int level, int health, int stamina, int strength) {
        this.level = level;
        this.health = health;
        this.stamina = stamina;
        this.strength = strength;
    }

    public int getLevel() {
        return level;
    }


    public int getHealth() {
        return health;
    }

    public int getStamina() {
        return stamina;
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
                return 100 + (stamina * 10);
            case "strength":
                return 100 + (strength * 20);
            default:
                throw new IllegalArgumentException("Invalid stat: " + stat);
        }
    }

    public void levelUp(String stat, PlayerEntity player) {
        switch (stat) {
            case "health":
                health += 5;
                System.out.println("Health stat increased to: " + health);
                ModifiableAttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
                if (maxHealthAttribute != null) {
                    maxHealthAttribute.removeModifier(References.HEALTH_MODIFIER_UUID);

                    maxHealthAttribute.addPermanentModifier(new AttributeModifier(
                            References.HEALTH_MODIFIER_UUID,
                            "player_health_modifier",
                            health - 20,
                            AttributeModifier.Operation.ADDITION
                    ));
                    player.setHealth(player.getMaxHealth());
                }
                break;
            case "stamina":
                stamina += 5;
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
        nbt.putInt("stamina", stamina);
        nbt.putInt("strength", strength);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        level = nbt.getInt("level");
        health = nbt.getInt("health");
        stamina = nbt.getInt("stamina");
        strength = nbt.getInt("strength");
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setStamina(int stamina) {
        this.stamina = stamina;
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