package fr.acth2.mod.proxy;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void setup() {
        super.setup();
        System.out.println("Client-side setup complete.");
    }
}