package fr.acth2.ror.utils.subscribers.mod;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.acth2.ror.entities.constructors.ExampleInvaderEntity;
import fr.acth2.ror.events.ServerEventManager;
import fr.acth2.ror.gui.MainMenuGui;
import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.ModItems;
import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.init.constructors.items.Glider;
import fr.acth2.ror.init.constructors.items.RadiumScimtar;
import fr.acth2.ror.init.constructors.items.RadiumSword;
import fr.acth2.ror.network.skills.SyncPlayerStatsPacket;
import fr.acth2.ror.network.skills.dexterity.DodgePacket;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import fr.acth2.ror.utils.subscribers.client.PlayerStatsCapability;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.lwjgl.opengl.GL11;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber
public class PlayerEvents {

    private static final AtomicBoolean atomicBrokenMoonWarning = new AtomicBoolean(true);
    private static final AtomicBoolean atomicEvent1Warning = new AtomicBoolean(true);
    private static int saveTimer = 0;

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
    public static void onPlayerGliding(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Glider.handleGliding(event.player);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent event) {
        loadDexterityModifier(event.getPlayer());
        loadStrengthModifier(event.getPlayer());
        PlayerStats.get(event.getPlayer()).setDexterity(MainMenuGui.calculateDexterityFromModifiers(event.getPlayer()));
        PlayerStats.get(event.getPlayer()).setStrengthModifierValue(MainMenuGui.calculateStrengthFromModifiers(event.getPlayer()));
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            PlayerStats stats = PlayerStats.get(player);
            ModNetworkHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), new SyncPlayerStatsPacket(stats));

            ServerEventManager.syncEventsToPlayer(player);
        }
    }

    @SubscribeEvent
    public static void onPlayerChangedDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.getPlayer() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getPlayer();
            player.getServer().execute(() -> {
                ServerEventManager.syncEventsToPlayer(player);
            });
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
            ItemStack heldItem = player.getMainHandItem();
            if (heldItem.getItem() instanceof RadiumSword) {
                PlayerStats stats = PlayerStats.get(player);
                float baseDamage = 6.0f;
                float dynamicDamage = 8 + (stats.getStrength() / 4.0f);
                float ratio = dynamicDamage / baseDamage;
                event.setAmount(event.getAmount() * ratio);
            }

            if (heldItem.getItem() instanceof RadiumScimtar) {
                PlayerStats stats = PlayerStats.get(player);
                float baseDamage = 6.0f;
                float dynamicDamage = 5 + (stats.getDexterity() / 5.0f);
                float ratio = dynamicDamage / baseDamage;
                event.setAmount(event.getAmount() * ratio);
            }
        }
    }

    @SubscribeEvent
    public static void playerDexterityConsequence(LivingHurtEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            PlayerStats playerStats = PlayerStats.get(player);

            int randomGoal = (int) (Math.random() * 170);
            for (int i = 0; i < playerStats.getDexterity(); i++) {
                int randomTry = (int) (Math.random() * 150);
                if (randomTry == randomGoal && !player.isSwimming() &&
                        !event.getSource().isExplosion() &&
                        !event.getSource().isFire() &&
                        event.getSource().getEntity() != null &&
                        !player.isDeadOrDying()) {

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
        PlayerEntity player = event.player;
        if (event.phase == TickEvent.Phase.END && event.player != null && !event.player.isDeadOrDying()) {
            PlayerStats playerStats = PlayerStats.get(player);

            if (player.isOnGround()) {
                playerStats.setHasDoubleJumped(false);
            }
        }

        if (event.player.level.isClientSide) return;

        if (References.brokenMoonWarning && References.brokenMoonPicked == 0 && atomicBrokenMoonWarning.get()) {
            player.playSound(ModSoundEvents.BROKEN_MOON_AMBIENT.get(), 1.0F, 1.0F);
        }

        if (References.brokenMoonWarning && References.brokenMoonPicked == 0 && atomicBrokenMoonWarning.getAndSet(false)) {
            broadcastMessage(new StringTextComponent(TextFormatting.GOLD + "BROKEN MOON ENTRY MESSAGE"));
        } else if (!References.brokenMoonWarning) {
            atomicBrokenMoonWarning.set(true);
        }


        //event1
        if (References.event1Warning && References.event1Picked == 0 && atomicEvent1Warning.get()) {
            player.playSound(ModSoundEvents.BROKEN_MOON_AMBIENT.get(), 1.0F, 1.0F);
        }

        if (References.event1Warning && References.event1Picked == 0 && atomicEvent1Warning.getAndSet(false)) {
            broadcastMessage(new StringTextComponent(TextFormatting.DARK_RED + "EVENT1 ENTRY MESSAGE"));
        } else if (!References.event1Warning) {
            atomicEvent1Warning.set(true);
        }
    }

    private static void broadcastMessage(ITextComponent message) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            server.getPlayerList().broadcastMessage(message, ChatType.SYSTEM, Util.NIL_UUID);
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        saveDexterityModifier(event.getPlayer());
        saveStrengthModifier(event.getPlayer());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft minecraft = Minecraft.getInstance();
            PlayerEntity player = minecraft.player;

            if (player != null) {
                if (minecraft.screen instanceof MainMenuGui) {
                    saveDexterityModifier(player);
                    saveStrengthModifier(player);
                }

                saveTimer++;
                if (saveTimer >= 6000) {
                    saveDexterityModifier(player);
                    saveStrengthModifier(player);
                    saveTimer = 0;
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

    private static void saveStrengthModifier(PlayerEntity player) {
        PlayerStats stats = PlayerStats.get(player);
        if (stats != null) {
            ModifiableAttributeInstance maxStrAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
            if (maxStrAttribute != null) {
                AttributeModifier strModifier = maxStrAttribute.getModifier(References.STRENGTH_MODIFIER_UUID);
                if (strModifier != null) {
                    stats.setStrengthModifierValue(strModifier.getAmount());
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

    private static void loadStrengthModifier(PlayerEntity player) {
        PlayerStats stats = PlayerStats.get(player);
        if (stats != null) {
            ModifiableAttributeInstance maxStrAttribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
            if (maxStrAttribute != null) {
                maxStrAttribute.removeModifier(References.STRENGTH_MODIFIER_UUID);
                maxStrAttribute.addPermanentModifier(new AttributeModifier(
                        References.STRENGTH_MODIFIER_UUID,
                        "player_str_modifier",
                        stats.getStrengthModifierValue(),
                        AttributeModifier.Operation.ADDITION
                ));
            }
        }
    }

    private static float redEffectIntensity = 0.5f;

    @SubscribeEvent
    public static void onClientInvadingTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level != null && mc.player != null) {
                boolean bossNearby = !mc.level.getEntitiesOfClass(
                        ExampleInvaderEntity.class,
                        mc.player.getBoundingBox().inflate(50)
                ).isEmpty();

                float targetIntensity = bossNearby ? 0.5f : 0f;

                if (bossNearby) {
                    mc.options.renderDistance = 2;
                }
                redEffectIntensity += (targetIntensity - redEffectIntensity) * 0.05f;
            }
        }
    }

    @SubscribeEvent
    public static void onRenderFog(EntityViewRenderEvent.FogColors event) {
        if (redEffectIntensity > 0.01f) {
            event.setRed(event.getRed() * (1f + redEffectIntensity));
            event.setGreen(event.getGreen() * (1f - redEffectIntensity * 0.7f));
            event.setBlue(event.getBlue() * (1f - redEffectIntensity * 0.7f));
        }
    }

    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event) {
        if (redEffectIntensity > 0.01f) {
            Minecraft mc = Minecraft.getInstance();
            drawRedOverlay(mc, event.getMatrixStack(), redEffectIntensity);
        }
    }

    private static void drawRedOverlay(Minecraft mc, MatrixStack matrixStack, float intensity) {
        MainWindow window = mc.getWindow();
        int width = window.getGuiScaledWidth();
        int height = window.getGuiScaledHeight();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableTexture();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        float alpha = intensity * 0.3f;
        RenderSystem.color4f(1.0f, 0.1f, 0.1f, alpha);

        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuilder();
        bufferbuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        bufferbuilder.vertex(0, height, -90).endVertex();
        bufferbuilder.vertex(width, height, -90).endVertex();
        bufferbuilder.vertex(width, 0, -90).endVertex();
        bufferbuilder.vertex(0, 0, -90).endVertex();
        bufferbuilder.end();
        WorldVertexBufferUploader.end(bufferbuilder);

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableTexture();
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
