package fr.acth2.ror.utils.subscribers.client;

import fr.acth2.ror.entities.renderer.EntityExampleRenderer;
import fr.acth2.ror.entities.renderer.clucker.CluckerRenderer;
import fr.acth2.ror.entities.renderer.curser.CurserRenderer;
import fr.acth2.ror.entities.renderer.hopper.HopperRenderer;
import fr.acth2.ror.entities.renderer.lc.LostCaverRenderer;
import fr.acth2.ror.entities.renderer.rc.RustedCoreRenderer;
import fr.acth2.ror.entities.renderer.wicked.WickedRenderer;
import fr.acth2.ror.entities.renderer.woodfall.WoodFallRenderer;
import fr.acth2.ror.init.ModEntities;
import fr.acth2.ror.utils.References;
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
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.ENTITY_EXAMPLE.get(), EntityExampleRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.LOST_CAVER.get(), LostCaverRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.HOPPER.get(), HopperRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.RUSTED_CORE.get(), RustedCoreRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WICKED.get(), WickedRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CLUCKER.get(), CluckerRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.CURSER.get(), CurserRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.WOODFALL.get(), WoodFallRenderer::new);
    }
}
