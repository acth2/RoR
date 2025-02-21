package fr.acth2.mod.proxy;

import fr.acth2.mod.init.ModKeyBindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;

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