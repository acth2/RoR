package fr.acth2.ror.impl;

import fr.acth2.ror.api.block.IMaterialHelper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;

public class MaterialHelper118 implements IMaterialHelper {

    @Override
    public Object wood() {
        return BlockBehaviour.Properties.of(Material.WOOD);
    }

    @Override
    public Object stone() {
        return BlockBehaviour.Properties.of(Material.STONE);
    }

    @Override
    public Object metal() {
        return BlockBehaviour.Properties.of(Material.METAL);
    }

    @Override
    public Object dirt() {
        return BlockBehaviour.Properties.of(Material.DIRT);
    }

    @Override
    public Object sand() {
        return BlockBehaviour.Properties.of(Material.SAND);
    }

    @Override
    public Object glass() {
        return BlockBehaviour.Properties.of(Material.GLASS);
    }

    @Override
    public Object wool() {
        return BlockBehaviour.Properties.of(Material.WOOL);
    }

    @Override
    public Object leaves() {
        return BlockBehaviour.Properties.of(Material.LEAVES);
    }

    @Override
    public Object ice() {
        return BlockBehaviour.Properties.of(Material.ICE);
    }

    @Override
    public Object water() {
        return BlockBehaviour.Properties.of(Material.WATER);
    }

    @Override
    public Object of(float hardness, float resistance) {
        return BlockBehaviour.Properties.of(Material.STONE)
                .strength(hardness, resistance);
    }
}