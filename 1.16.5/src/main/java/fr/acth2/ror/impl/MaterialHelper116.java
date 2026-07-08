package fr.acth2.ror.impl;

import fr.acth2.ror.api.block.IMaterialHelper;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;

@SuppressWarnings({"unchecked", "rawtypes"})
public class MaterialHelper116 implements IMaterialHelper {

    @Override
    public Object wood() {
        return AbstractBlock.Properties.of(Material.WOOD);
    }

    @Override
    public Object stone() {
        return AbstractBlock.Properties.of(Material.STONE);
    }

    @Override
    public Object metal() {
        return AbstractBlock.Properties.of(Material.METAL);
    }

    @Override
    public Object dirt() {
        return AbstractBlock.Properties.of(Material.DIRT);
    }

    @Override
    public Object sand() {
        return AbstractBlock.Properties.of(Material.SAND);
    }

    @Override
    public Object glass() {
        return AbstractBlock.Properties.of(Material.GLASS);
    }

    @Override
    public Object wool() {
        return AbstractBlock.Properties.of(Material.WOOL);
    }

    @Override
    public Object leaves() {
        return AbstractBlock.Properties.of(Material.LEAVES);
    }

    @Override
    public Object ice() {
        return AbstractBlock.Properties.of(Material.ICE);
    }

    @Override
    public Object water() {
        return AbstractBlock.Properties.of(Material.WATER);
    }

    @Override
    public Object of(float hardness, float resistance) {
        return AbstractBlock.Properties.of(Material.STONE)
                .strength(hardness, resistance);
    }
}