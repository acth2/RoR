package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.gui.MainMenuGui;
import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.network.skills.SyncPlayerStatsPacket;
import fr.acth2.ror.network.skills.dexterity.DodgePacket;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.client.PlayerStatsCapability;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;

@Mod.EventBusSubscriber
public class PlayerEvents {

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof PlayerEntity) {
            System.out.println("Attaching PlayerStats capability to player");
            event.addCapability(
                    new ResourceLocation(References.MODID, "player_stats"),
                    new PlayerStatsCapability()
            );
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {
        loadDexterityModifier(event.getPlayer());
        PlayerStats.get(event.getPlayer()).setDexterity(MainMenuGui.calculateDexterityFromModifiers(event.getPlayer()));
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            PlayerStats stats = PlayerStats.get(player);
            ModNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncPlayerStatsPacket(stats));
        }
    }

    @SubscribeEvent
    public static void playerDexterityConsequence(LivingHurtEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            PlayerStats playerStats = PlayerStats.get(player);

            int randomGoal = (int) (Math.random() * 45);
            for (int i = 0; i < playerStats.getDexterity(); i++) {
                int randomTry = (int) (Math.random() * 50);
                if (randomTry == randomGoal) {
                    event.setCanceled(true);
                    player.sendMessage(ITextComponent.nullToEmpty("You dodged the attack thanks to your dexterity"), player.getUUID());

                    if (player instanceof ServerPlayerEntity) {
                        ModNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new DodgePacket());
                    }
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.player != null && !event.player.isDeadOrDying()) {
            PlayerEntity player = event.player;
            PlayerStats playerStats = PlayerStats.get(player);

            if (player.isOnGround()) {
                playerStats.setHasDoubleJumped(false);
            }
        }
    }







    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        saveDexterityModifier(event.getPlayer());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft minecraft = Minecraft.getInstance();
            if (minecraft.screen instanceof MainMenuGui) {
                PlayerEntity player = minecraft.player;
                if (player != null) {
                    saveDexterityModifier(player);
                }
            }
        }
    }

    private static void saveDexterityModifier(PlayerEntity player) {
        PlayerStats stats = PlayerStats.get(player);
        if (stats != null) {
            ModifiableAttributeInstance maxDexAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (maxDexAttribute != null) {
                AttributeModifier dexModifier = maxDexAttribute.getModifier(References.DEXTERITY_MODIFIER_UUID);
                if (dexModifier != null) {
                    stats.setDexterityModifierValue(dexModifier.getAmount());
                }
            }
        }
    }

    private static void loadDexterityModifier(PlayerEntity player) {
        PlayerStats stats = PlayerStats.get(player);
        if (stats != null) {
            ModifiableAttributeInstance maxDexAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
            if (maxDexAttribute != null) {
                maxDexAttribute.removeModifier(References.DEXTERITY_MODIFIER_UUID);
                maxDexAttribute.addPermanentModifier(new AttributeModifier(
                        References.DEXTERITY_MODIFIER_UUID,
                        "player_dex_modifier",
                        stats.getDexterityModifierValue(),
                        AttributeModifier.Operation.ADDITION
                ));
            }
        }
    }
}