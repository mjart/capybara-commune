package net.capybara.fluids;

import net.capybara.CapybaraMain;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class HotWaterFluid extends CapybaraFluid {

    @Override
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
    protected BlockState toBlockState(FluidState fluidState)
    {
         //method_15741 converts the LEVEL_1_8 of the fluid state to the LEVEL_15 the fluid block uses
        return CapybaraMain.HOTWATER.getDefaultState().with(Properties.LEVEL_15, method_15741(fluidState));
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
    }
}
