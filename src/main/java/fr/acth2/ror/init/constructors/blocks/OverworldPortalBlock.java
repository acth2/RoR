package fr.acth2.ror.init.constructors.blocks;

import fr.acth2.ror.init.ModDimensions;
import fr.acth2.ror.utils.subscribers.gen.skyria.SkyriaTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.world.NoteBlockEvent;

import java.util.Random;

public class OverworldPortalBlock extends NetherPortalBlock {
    protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public OverworldPortalBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        ServerWorld overworld = world.getServer().getLevel(World.OVERWORLD);
        if (overworld == null) return;


        BlockPos teleportPos = new BlockPos(
                entity.getX(),
                overworld.getMaxBuildHeight(),
                entity.getZ()
        );

        entity.changeDimension(overworld, new SkyriaTeleporter(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ()));
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            player.addEffect(new EffectInstance(Effects.SLOW_FALLING, 800, 0, false, false));
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void animateTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playLocalSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
                    SoundEvents.PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }

        for (int i = 0; i < 4; ++i) {
            double x = pos.getX() + random.nextDouble();
            double y = pos.getY() + random.nextDouble();
            double z = pos.getZ() + random.nextDouble();
            double xSpeed = (random.nextDouble() - 0.5D) * 2.0D;
            double ySpeed = (random.nextDouble() - 0.5D) * 2.0D;
            double zSpeed = (random.nextDouble() - 0.5D) * 2.0D;

            world.addParticle(ParticleTypes.ENCHANT, x, y, z, xSpeed, ySpeed, zSpeed);
        }
    }
}