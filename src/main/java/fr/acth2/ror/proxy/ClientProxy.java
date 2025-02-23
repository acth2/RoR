package fr.acth2.ror.proxy;

import fr.acth2.ror.entities.renderer.lc.LostCaverRenderer;
import fr.acth2.ror.init.ModEntities;
import fr.acth2.ror.init.ModKeyBindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void setup() {
        super.setup();

        ModKeyBindings.register();
        ClientRegistry.registerKeyBinding(ModKeyBindings.MM);

        System.out.println("Client-side setup complete.");
    }
}