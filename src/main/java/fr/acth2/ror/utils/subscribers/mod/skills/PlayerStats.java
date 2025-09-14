package fr.acth2.ror.utils.subscribers.mod.skills;

import fr.acth2.ror.gui.MainMenuGui;
import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.skills.SyncPlayerStatsPacket;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.client.PlayerStatsCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;

public class PlayerStats implements INBTSerializable<CompoundNBT> {

    private int level;
    private int health;
    private int dexterity;
    private int strength;
    private boolean hasDoubleJumped;
    private double dexterityModifierValue;
    private double strengthModifierValue;
    private boolean isFirstUpgrade = true;

    public PlayerStats(int level, int health, int dexterity, int strength) {
        this.level = level;
        this.health = health;
        this.dexterity = dexterity;
        this.strength = strength;
        this.hasDoubleJumped = false;
        this.dexterityModifierValue = 0.0;
        this.strengthModifierValue = 0.0;
    }


    public boolean hasDoubleJumped() {
        return hasDoubleJumped;
    }

    public void setHasDoubleJumped(boolean hasDoubleJumped) {
        this.hasDoubleJumped = hasDoubleJumped;
    }

    public int getLevel() {
        return level;
    }

    public double getDexterityModifierValue() {
        return dexterityModifierValue;
    }

    public double getStrengthModifierValue() {
        return strengthModifierValue;
    }

    public void setDexterityModifierValue(double dexterityModifierValue) {
        this.dexterityModifierValue = dexterityModifierValue;
    }

    public void setStrengthModifierValue(double strengthModifierValue) {
        this.strengthModifierValue = strengthModifierValue;
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
        PlayerStats stats = PlayerStats.get(player);
        int coins = CoinsManager.getCoins((ServerPlayerEntity) player);

        if (stats.canLevelUp(stat, coins)) {
            switch (stat) {
                case "health":
                    stats.setHealth(stats.getHealth() + 2);
                    System.out.println("Health stat increased to: " + stats.getHealth());
                    ModifiableAttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
                    if (maxHealthAttribute != null) {
                        maxHealthAttribute.removeModifier(References.HEALTH_MODIFIER_UUID);
                        maxHealthAttribute.addPermanentModifier(new AttributeModifier(
                                References.HEALTH_MODIFIER_UUID,
                                "player_health_modifier",
                                stats.getHealth() - References.HEALTH_REDUCER,
                                AttributeModifier.Operation.ADDITION
                        ));
                    }
                    break;
                case "dexterity":
                    stats.setDexterity(stats.getDexterity() + 1);
                    System.out.println("Dexterity stat increased to: " + stats.getDexterity());
                    ModifiableAttributeInstance maxDexAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
                    if (maxDexAttribute != null) {
                        maxDexAttribute.removeModifier(References.DEXTERITY_MODIFIER_UUID);
                        maxDexAttribute.addPermanentModifier(new AttributeModifier(
                                References.DEXTERITY_MODIFIER_UUID,
                                "player_dex_modifier",
                                (double) stats.getDexterity() / References.DEXTERITY_REDUCER,
                                AttributeModifier.Operation.ADDITION
                        ));
                    }
                    break;
                case "strength":
                    stats.setStrength(stats.getStrength() + 1);
                    System.out.println("Dexterity stat increased to: " + stats.getDexterity());
                    ModifiableAttributeInstance maxStrengthAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
                    if (maxStrengthAttribute != null) {
                        maxStrengthAttribute.removeModifier(References.STRENGTH_MODIFIER_UUID);
                        maxStrengthAttribute.addPermanentModifier(new AttributeModifier(
                                References.STRENGTH_MODIFIER_UUID,
                                "player_dex_modifier",
                                (double) stats.getDexterity() / References.STRENGTH_REDUCER,
                                AttributeModifier.Operation.ADDITION
                        ));
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Invalid stat: " + stat);
            }

            stats.setLevel(stats.getLevel() + 1);
            CoinsManager.removeCoins((ServerPlayerEntity) player, stats.getLevelUpCost(stat));

            if (isFirstUpgrade) {
                isFirstUpgrade = false;
                if (player.level.isClientSide) {
                    Minecraft minecraft = Minecraft.getInstance();
                    if (minecraft.screen instanceof MainMenuGui) {
                        MainMenuGui gui = (MainMenuGui) minecraft.screen;
                        gui.updateStats(stats.getLevel(), stats.getHealth(), stats.getDexterity(), stats.getStrength());
                    }
                }
            }

            if (player instanceof ServerPlayerEntity) {
                ModNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SyncPlayerStatsPacket(stats));
            }
        }
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt("level", level);
        nbt.putInt("health", health);
        nbt.putInt("dexterity", dexterity);
        nbt.putInt("strength", strength);
        nbt.putDouble("dexterityModifierValue", dexterityModifierValue);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        level = nbt.getInt("level");
        health = nbt.getInt("health");
        dexterity = nbt.getInt("dexterity");
        strength = nbt.getInt("strength");
        dexterityModifierValue = nbt.getDouble("dexterityModifierValue");
        strengthModifierValue = nbt.getDouble("strengthModifierValue");
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
        return stats.orElse(new PlayerStats(1, 20, 10, 1));
    }

}