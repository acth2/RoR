package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.coins.SyncCoinsPacket;
import fr.acth2.ror.network.skills.SyncPlayerStatsPacket;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.gen.utils.MobSpawnData;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = References.MODID)
public class PlayerDataHandler {

    public static int coins;
    public static int hp;
    public static int dp;
    public static int sp;
    public static int lp;

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            PlayerStats playerStats = PlayerStats.get(player);

            coins = player.level.isClientSide ?
                    CoinsManager.getClientCoins() :
                    CoinsManager.getCoins((ServerPlayerEntity) player);

            hp = playerStats.getHealth();
            dp = playerStats.getDexterity();
            sp = playerStats.getStrength();
            lp = playerStats.getLevel();
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        PlayerEntity player = event.getPlayer();
        PlayerStats playerStats = PlayerStats.get(player);

        if (player.level.isClientSide) {
            CoinsManager.setClientCoins(coins);
        } else {
            CoinsManager.setCoins((ServerPlayerEntity) event.getPlayer(), coins);
        }

        playerStats.setHealth(hp);
        playerStats.setDexterity(dp);
        playerStats.setStrength(sp);
        playerStats.setLevel(lp);
        reapplyAttributes(player, playerStats);

        if (!player.level.isClientSide) {
            ModNetworkHandler.sendToClient(new SyncPlayerStatsPacket(playerStats), (ServerPlayerEntity) player);
        }
    }

    private static void reapplyAttributes(PlayerEntity player, PlayerStats playerStats) {
        ModifiableAttributeInstance maxHealthAttribute = player.getAttribute(Attributes.MAX_HEALTH);
        if (maxHealthAttribute != null) {
            maxHealthAttribute.removeModifier(References.HEALTH_MODIFIER_UUID);
            maxHealthAttribute.addPermanentModifier(new AttributeModifier(
                    References.HEALTH_MODIFIER_UUID,
                    "player_health_modifier",
                    playerStats.getHealth() - References.HEALTH_REDUCER,
                    AttributeModifier.Operation.ADDITION
            ));

            player.setHealth(player.getMaxHealth());
        }

        ModifiableAttributeInstance maxDexAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (maxDexAttribute != null) {
            maxDexAttribute.removeModifier(References.DEXTERITY_MODIFIER_UUID);

            maxDexAttribute.addPermanentModifier(new AttributeModifier(
                    References.DEXTERITY_MODIFIER_UUID,
                    "player_dex_modifier",
                    (double) playerStats.getDexterity() / References.DEXTERITY_REDUCER,
                    AttributeModifier.Operation.ADDITION
            ));
        }
    }
}