package net.capybara.fluids;

import jdk.internal.jline.internal.Nullable;
import net.capybara.CapybaraMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Random;

public abstract class HotWaterFluid extends BaseFluid {

    public HotWaterFluid() {
    }

    public Fluid getFlowing() {
        return CapybaraMain.FLOWING_HOTWATER;
    }

    public Fluid getStill() {
        return CapybaraMain.STILL_HOTWATER;
    }

    public Item getBucketItem() {
        return Items.WATER_BUCKET;
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(World world, BlockPos pos, FluidState state, Random random) {
        if (!state.isStill() && !(Boolean)state.get(FALLING)) {
            if (random.nextInt(64) == 0) {
                world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, random.nextFloat() * 0.25F + 0.75F, random.nextFloat() + 0.5F, false);
            }
        } else if (random.nextInt(10) == 0) {
            world.addParticle(ParticleTypes.UNDERWATER, (double)pos.getX() + (double)random.nextFloat(), (double)pos.getY() + (double)random.nextFloat(), (double)pos.getZ() + (double)random.nextFloat(), 0.0D, 0.0D, 0.0D);
        }

    }

    @Nullable
    @Environment(EnvType.CLIENT)
    public ParticleEffect getParticle() {
        return ParticleTypes.DRIPPING_WATER;
    }

    protected boolean isInfinite() {
        return true;
    }

    protected void beforeBreakingBlock(IWorld world, BlockPos pos, BlockState state) {
        BlockEntity blockEntity = state.getBlock().hasBlockEntity() ? world.getBlockEntity(pos) : null;
        Block.dropStacks(state, world.getWorld(), pos, blockEntity);
    }

    public int method_15733(WorldView worldView) {
        return 4;
    }

    public BlockState toBlockState(FluidState state) {
        return (BlockState)CapybaraMain.HOTWATER.getDefaultState().with(FluidBlock.LEVEL, method_15741(state));
       // return CapybaraMain.HOTWATER.getDefaultState().with(Properties.LEVEL_15, method_15741(state));
    }

    public boolean matchesType(Fluid fluid) {
        return fluid == CapybaraMain.STILL_HOTWATER || fluid == CapybaraMain.FLOWING_HOTWATER;
    }

    public int getLevelDecreasePerBlock(WorldView world) {
        return 1;
    }

    public int getTickRate(WorldView world) {
        return 5;
    }

    public boolean canBeReplacedWith(FluidState state, BlockView world, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.matches(FluidTags.WATER);
    }

    protected float getBlastResistance() {
        return 100.0F;
    }

    public static class Flowing extends HotWaterFluid {
        public Flowing() {
        }

        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(new Property[]{LEVEL});
        }

        public int getLevel(FluidState state) {
            return (Integer)state.get(LEVEL);
        }

        public boolean isStill(FluidState state) {
            return false;
        }
    }

    public static class Still extends HotWaterFluid {
        public Still() {
        }

        public int getLevel(FluidState state) {
            return 8;
        }

        public boolean isStill(FluidState state) {
            return true;
        }
    }


    /*@Override
    public Fluid getStill()
    {
        return CapybaraMain.STILL_HOTWATER;
    }

    @Override
    public Fluid getFlowing()
    {
        return CapybaraMain.FLOWING_HOTWATER;
    }

    @Override
    public Item getBucketItem()
    {
        return CapybaraMain.HOTWATER_BUCKET;
    }

    @Override
    public BlockState toBlockState(FluidState fluidState)
    {
         //method_15741 converts the LEVEL_1_8 of the fluid state to the LEVEL_15 the fluid block uses
        //return (BlockState) Blocks.WATER.getDefaultState().with(FluidBlock.LEVEL, method_15741(fluidState));
        return CapybaraMain.HOTWATER.getDefaultState().with(Properties.LEVEL_15, method_15741(fluidState));
    }

    public boolean matchesType(Fluid fluid) {
        return fluid == CapybaraMain.STILL_HOTWATER || fluid == CapybaraMain.FLOWING_HOTWATER;
    }

    public static class Flowing extends HotWaterFluid
    {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder)
        {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getLevel(FluidState fluidState)
        {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState)
        {
            return false;
        }
    }

    public static class Still extends HotWaterFluid
    {
        @Override
        public int getLevel(FluidState fluidState)
        {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState)
        {
            return true;
        }
    }*/
}
