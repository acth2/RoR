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

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDispatcher;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = References.MODID)
public class PlayerDataHandler {

    public static int coins;
    public static int pv;
    public static int sv;
    public static int stv;
    public static int lv;

    public static final List<String> deathCounter = new ArrayList<>();

    @SubscribeEvent
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            PlayerStats playerStats = PlayerStats.get(player);

            coins = player.level.isClientSide ?
                    CoinsManager.getClientCoins() :
                    CoinsManager.getCoins((ServerPlayerEntity) player);

            pv = playerStats.getHealth();
            sv = playerStats.getStamina();
            stv = playerStats.getStrength();
            lv = playerStats.getLevel();

            if (!deathCounter.contains(player.getName().getString())) {
                deathCounter.add(player.getName().getString());
            }
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

        playerStats.setHealth(pv);
        playerStats.setStamina(sv);
        playerStats.setStrength(stv);
        playerStats.setLevel(lv);
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
                    playerStats.getHealth() - 20,
                    AttributeModifier.Operation.ADDITION
            ));

            System.out.println("Reapplied health modifier. New max health: " + player.getMaxHealth());

            player.setHealth(player.getMaxHealth());
        }
    }
}