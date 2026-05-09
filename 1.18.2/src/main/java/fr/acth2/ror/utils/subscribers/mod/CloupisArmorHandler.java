package fr.acth2.ror.utils.subscribers.mod;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import fr.acth2.ror.init.constructors.armor.cloupis.CloupisArmor;
import fr.acth2.ror.utils.References;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Random;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class CloupisArmorHandler {

    private static final float ALPHA = 0.4f;
    private static boolean isRendering = false;

    private static final java.util.Set<Integer> stealthImmuneEntities = new java.util.HashSet<>();
    private static long stealthBreakUntil = 0;
    private static final long STEALTH_BREAK_DURATION_MS = 15000;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        for (net.minecraft.server.level.ServerLevel world :
                net.minecraftforge.fml.server.ServerLifecycleHooks.getCurrentServer().getAllLevels()) {

            for (PlayerEntity player : world.players()) {
                if (!CloupisArmor.shouldStealth(player)) continue;
                if (!player.isCrouching()) continue;
                if (System.currentTimeMillis() < stealthBreakUntil) continue;

                world.getEntitiesOfClass(
                        MobEntity.class,
                        player.getBoundingBox().inflate(64),
                        mob -> mob.getTarget() == player
                ).forEach(mob -> {
                    mob.setTarget(null);
                    mob.getNavigation().stop();
                });
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if (!(event.getEntityLiving() instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        if (!CloupisArmor.shouldStealth(player)) return;
        if (!player.isCrouching()) return;

        stealthBreakUntil = System.currentTimeMillis() + STEALTH_BREAK_DURATION_MS;
        stealthImmuneEntities.clear();

        player.level.getEntitiesOfClass(
                MobEntity.class,
                player.getBoundingBox().inflate(32),
                mob -> true
        ).forEach(mob -> mob.setTarget(player));
    }

    @SubscribeEvent
    public static void onEntityDeath(net.minecraftforge.event.entity.living.LivingDeathEvent event) {
        stealthImmuneEntities.remove(event.getEntityLiving().getId());
    }

    @SubscribeEvent
    public static void onPlayerRenderPre(RenderPlayerEvent.Pre event) {
        if (isRendering) return;

        PlayerEntity player = event.getPlayer();
        if (!CloupisArmor.shouldStealth(player)) return;
        if (!(player instanceof AbstractClientPlayerEntity)) return;
        AbstractClientPlayerEntity clientPlayer = (AbstractClientPlayerEntity) player;

        event.setCanceled(true);

        PlayerRenderer renderer = event.getRenderer();
        MatrixStack matrixStack = event.getMatrixStack();
        IRenderTypeBuffer bufferSource = event.getBuffers();
        float partialTicks = event.getPartialRenderTick();
        int packedLight = renderer.getPackedLightCoords(clientPlayer, partialTicks);
        ResourceLocation skin = renderer.getTextureLocation(clientPlayer);

        TranslucentBuffer wrappedBuffer = new TranslucentBuffer(bufferSource, skin, ALPHA);

        isRendering = true;
        try {
            matrixStack.pushPose();
            ((LivingRenderer) renderer).render(
                    clientPlayer,
                    clientPlayer.yBodyRot,
                    partialTicks,
                    matrixStack,
                    wrappedBuffer,
                    packedLight
            );
            matrixStack.popPose();
        } finally {
            isRendering = false;
        }
    }

    @SubscribeEvent
    public static void onPlayerRenderPost(RenderPlayerEvent.Post event) {
        if (isRendering) return;
        if (!CloupisArmor.shouldStealth(event.getPlayer())) return;
        event.setCanceled(true);
    }

    public static class TranslucentBuffer implements IRenderTypeBuffer {

        private final IRenderTypeBuffer source;
        private final ResourceLocation skin;
        private final int alphaInt;

        public TranslucentBuffer(IRenderTypeBuffer source, ResourceLocation skin, float alpha) {
            this.source = source;
            this.skin = skin;
            this.alphaInt = Math.round(alpha * 255);
        }

        @Override
        @Nonnull
        public IVertexBuilder getBuffer(@Nonnull RenderType type) {
            ResourceLocation texture = extractTexture(type, skin);

            RenderType remapped = RenderType.entityTranslucent(texture);

            return new AlphaVertex(source.getBuffer(remapped), alphaInt);
        }

        private static ResourceLocation extractTexture(RenderType type, ResourceLocation fallback) {
            try {
                Field stateField = type.getClass().getDeclaredField("state");
                stateField.setAccessible(true);
                Object compositeState = stateField.get(type);

                if (compositeState == null) return fallback;

                for (Field field : compositeState.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    Object val = field.get(compositeState);
                    if (val instanceof net.minecraft.client.renderer.RenderState.TextureState) {
                        Field texField = net.minecraft.client.renderer.RenderState.TextureState.class
                                .getDeclaredField("texture");
                        texField.setAccessible(true);
                        Object optTex = texField.get(val);
                        if (optTex instanceof java.util.Optional) {
                            java.util.Optional<?> opt = (java.util.Optional<?>) optTex;
                            if (opt.isPresent() && opt.get() instanceof ResourceLocation) {
                                return (ResourceLocation) opt.get();
                            }
                        }
                    }
                }
            } catch (Exception e) {}
            return fallback;
        }
    }

    public static class AlphaVertex implements IVertexBuilder {

        private final IVertexBuilder d;
        private final int a;

        public AlphaVertex(IVertexBuilder delegate, int alphaInt) {
            this.d = delegate;
            this.a = alphaInt;
        }

        @Override @Nonnull public IVertexBuilder vertex(double x, double y, double z) { d.vertex(x, y, z); return this; }
        @Override @Nonnull public IVertexBuilder color(int r, int g, int b, int a)    { d.color(r, g, b, this.a); return this; }
        @Override @Nonnull public IVertexBuilder uv(float u, float v)                 { d.uv(u, v); return this; }
        @Override @Nonnull public IVertexBuilder overlayCoords(int u, int v)          { d.overlayCoords(u, v); return this; }
        @Override @Nonnull public IVertexBuilder uv2(int u, int v)                    { d.uv2(u, v); return this; }
        @Override @Nonnull public IVertexBuilder normal(float x, float y, float z)    { d.normal(x, y, z); return this; }
        @Override              public void endVertex()                                { d.endVertex(); }
    }
}