package fr.acth2.ror.utils.subscribers.client;

import fr.acth2.ror.entities.renderer.EntityExampleRenderer;
import fr.acth2.ror.entities.renderer.ExampleInvaderRenderer;
import fr.acth2.ror.entities.renderer.aquamarin.AquamarinRenderer;
import fr.acth2.ror.entities.renderer.axis.AxisRenderer;
import fr.acth2.ror.entities.renderer.bi.BloodInfectionerRenderer;
import fr.acth2.ror.entities.renderer.bri.BrokenInsurrectionistRenderer;
import fr.acth2.ror.entities.renderer.cavesucker.CaveSuckerRenderer;
import fr.acth2.ror.entities.renderer.cg.CoinGiverRenderer;
import fr.acth2.ror.entities.renderer.clucker.CluckerRenderer;
import fr.acth2.ror.entities.renderer.corrupted.CorruptedRenderer;
import fr.acth2.ror.entities.renderer.curser.CurserRenderer;
import fr.acth2.ror.entities.renderer.bo.BadOmenRenderer;
import fr.acth2.ror.entities.renderer.despiter.DespiterRenderer;
import fr.acth2.ror.entities.renderer.echo.EchoRenderer;
import fr.acth2.ror.entities.renderer.flyer.FlyerRenderer;
import fr.acth2.ror.entities.renderer.fussle.FussleRenderer;
import fr.acth2.ror.entities.renderer.ghost.GhostRenderer;
import fr.acth2.ror.entities.renderer.grasser.GrasserRenderer;
import fr.acth2.ror.entities.renderer.hopper.HopperRenderer;
import fr.acth2.ror.entities.renderer.howler.HowlerRenderer;
import fr.acth2.ror.entities.renderer.lb.LavaBeingRenderer;
import fr.acth2.ror.entities.renderer.lc.LostCaverRenderer;
import fr.acth2.ror.entities.renderer.mimic.MimicRenderer;
import fr.acth2.ror.entities.renderer.mw.MajorWickedRenderer;
import fr.acth2.ror.entities.renderer.ookla.OoklaRenderer;
import fr.acth2.ror.entities.renderer.rc.RustedCoreRenderer;
import fr.acth2.ror.entities.renderer.rift.RiftV2Renderer;
import fr.acth2.ror.entities.renderer.se.SkyEjectorRenderer;
import fr.acth2.ror.entities.renderer.seeker.SeekerRenderer;
import fr.acth2.ror.entities.renderer.silker.SilkerRenderer;
import fr.acth2.ror.entities.renderer.traveler.TravelerRenderer;
import fr.acth2.ror.entities.renderer.rift.RiftRenderer;
import fr.acth2.ror.entities.renderer.wicked.WickedRenderer;
import fr.acth2.ror.entities.renderer.woodfall.WoodFallRenderer;
import fr.acth2.ror.entities.renderer.woodfall.solider.WoodFallSoliderRenderer;
import fr.acth2.ror.entities.renderer.ws.WoodSpiritRenderer;
import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModItems;
import fr.acth2.ror.init.ModEntities;
import fr.acth2.ror.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventSubscriber {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ENTITY_EXAMPLE.get(), EntityExampleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.LOST_CAVER.get(), LostCaverRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.HOPPER.get(), HopperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.RUSTED_CORE.get(), RustedCoreRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WICKED.get(), WickedRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CLUCKER.get(), CluckerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CURSER.get(), CurserRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WOODFALL.get(), WoodFallRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WOOD_SPIRIT.get(), WoodSpiritRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.TRAVELER.get(), TravelerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.COIN_GIVER.get(), CoinGiverRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WOODFALL_SOLIDER.get(), WoodFallSoliderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.GHOST.get(), GhostRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SEEKER.get(), SeekerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.AQUAMARIN.get(), AquamarinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FUSSLE.get(), FussleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ECHO.get(), EchoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SILKER.get(), SilkerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MAJOR_WICKED.get(), MajorWickedRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.OOKLA.get(), OoklaRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BAD_OMEN.get(), BadOmenRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.MIMIC.get(), MimicRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.SKY_EJECTOR.get(), SkyEjectorRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.LAVA_BEING.get(), LavaBeingRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.GRASSER.get(), GrasserRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.EXAMPLE_INVADER.get(), ExampleInvaderRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.FLYER.get(), FlyerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BLOOD_INFECTIONER.get(), BloodInfectionerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BROKEN_INSURRECTIONIST.get(), BrokenInsurrectionistRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CAVE_SUCKER.get(), CaveSuckerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.AXIS.get(), AxisRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.HOWLER.get(), HowlerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.DESPITER.get(), DespiterRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.RIFT.get(), RiftRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.RIFT_V2.get(), RiftV2Renderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CORRUPTED.get(), CorruptedRenderer::new);

        event.enqueueWork(() -> {
            ItemModelsProperties.register(
                    ModItems.REALMS_VESSEL.get(),
                    new ResourceLocation(References.MODID, "is_activated"),
                    (itemStack, world, livingEntity) -> {
                        if (itemStack.hasTag() && itemStack.getTag().getBoolean("isActivated")) {
                            return 1.0F;
                        }
                        return 0.0F;
                    }
            );

            ItemModelsProperties.register(
                    ModItems.REALMS_VESSEL.get(),
                    new ResourceLocation(References.MODID, "is_skyria"),
                    (itemStack, world, livingEntity) -> {
                        if (itemStack.hasTag() && itemStack.getTag().getBoolean("isSkyria")) {
                            return 1.0F;
                        }
                        return 0.0F;
                    }
            );
        });

        event.enqueueWork(() -> {
            RenderTypeLookup.setRenderLayer(ModBlocks.CLOUD_PIECE.get(), RenderType.translucent());
            RenderTypeLookup.setRenderLayer(ModBlocks.SKYRIA_AIR.get(), RenderType.translucent());
        });
    }
}
