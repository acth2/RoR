package fr.acth2.ror.utils.subscribers.mod;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import fr.acth2.ror.entities.constructors.ExampleInvaderEntity;
import fr.acth2.ror.gui.MainMenuGui;
import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.init.ModNetworkHandler;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import org.lwjgl.opengl.GL11;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Mod.EventBusSubscriber
public class PlayerEvents {

    private static final AtomicBoolean atomicBrokenMoonWarning = new AtomicBoolean(true);

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
                int randomTry = (int) (Math.random() * 75);
                if (randomTry == randomGoal && !player.isSwimming() && !player.isFallFlying() && !player.isDeadOrDying() && !player.isInLava()) {
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

        if (References.brokenMoonWarning && References.brokenMoonPicked == 0 && atomicBrokenMoonWarning.get()) {
            player.playSound(ModSoundEvents.BROKEN_MOON_AMBIENT.get(), 1.0F, 1.0F);
        }

        if (References.brokenMoonWarning && References.brokenMoonPicked == 0 && atomicBrokenMoonWarning.getAndSet(false)) {
            player.sendMessage(ITextComponent.nullToEmpty(TextFormatting.GOLD + "BROKEN MOON ENTRY MESSAGE"), player.getUUID());
        } else if (!References.brokenMoonWarning) {
            atomicBrokenMoonWarning.set(true);
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