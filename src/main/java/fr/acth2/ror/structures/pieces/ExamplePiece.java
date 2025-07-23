package fr.acth2.ror.structures.pieces;

import fr.acth2.ror.Main;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class ExamplePiece extends StructurePiece {
    private final MutableBoundingBox boundingBox;

    public ExamplePiece(TemplateManager templateManager, BlockPos pos, MutableBoundingBox boundingBox) {
        super(Main.EXAMPLE_PILLAR, 0);
        this.boundingBox = boundingBox;
        this.boundingBox.move(pos.getX(), pos.getY(), pos.getZ());
    }

    public ExamplePiece(TemplateManager templateManager, CompoundNBT nbt) {
        super(Main.EXAMPLE_PILLAR, nbt);
        this.boundingBox = new MutableBoundingBox(nbt.getIntArray("Bounds"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT tagCompound) {
        tagCompound.putIntArray("Bounds", new int[]{
                boundingBox.x0, boundingBox.y0, boundingBox.z0,
                boundingBox.x1, boundingBox.y1, boundingBox.z1
        });
    }

    @Override
    public boolean postProcess(ISeedReader world, StructureManager structureManager,
                               ChunkGenerator generator, Random random,
                               MutableBoundingBox boundingBox, ChunkPos chunkPos,
                               BlockPos pos) {
        BlockPos.Mutable mutablePos = new BlockPos.Mutable();

        for (int y = this.boundingBox.y0; y <= this.boundingBox.y1; y++) {
            for (int x = this.boundingBox.x0; x <= this.boundingBox.x1; x++) {
                for (int z = this.boundingBox.z0; z <= this.boundingBox.z1; z++) {
                    mutablePos.set(x, y, z);
                    if (boundingBox.isInside(mutablePos)) {
                        world.setBlock(mutablePos, Blocks.DIRT.defaultBlockState(), 2);
                    }
                }
            }
        }
        return true;
    }
}