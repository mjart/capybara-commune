

package net.capybara.entities.ai;

import java.util.Iterator;
import java.util.Random;

import net.capybara.CapybaraMain;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class EatBarkGoal extends Goal {
    private double searchRadius = 5.0D;
    private final MobEntityWithAi mob;
    private BlockPos pos = null;
    private int timer = 0;

    public EatBarkGoal(MobEntityWithAi mob) {
        this.mob = mob;
    }

    @Override
    public boolean shouldContinue() {
        return pos != null ;
    }

    @Override
    public boolean canStop() {
        return pos == null;
    }

    public boolean canStart() {
        return this.mob.onGround && pos == null ;
          }

    @Override
    public void tick() {
        timer--;
        if(timer < 0) timer = 0;
        if (pos != null&&timer == 0) {
            Random random = new Random();
            this.mob.getMoveControl().moveTo(
                    (double)pos.getX(),
                    (double)pos.getY(),
                    (double)pos.getZ(),
                    1.0D);
            if(mob.getBlockPos().isWithinDistance(pos,3)) {
                if(this.mob.world.getBlockState(pos).matches(BlockTags.LOGS))
                {
                    this.mob.world.setBlockState(pos, CapybaraMain.OAK_WITHOUT_BARK.getDefaultState());
                }
                timer = 500;
                pos = null;
            }
        }
    }

    public void start() {

        Iterable<BlockPos> iterable = BlockPos.iterate(
                MathHelper.floor(this.mob.getX() - searchRadius),   //Min X
                MathHelper.floor(this.mob.getY() - searchRadius),   //Min Y
                MathHelper.floor(this.mob.getZ() - searchRadius),   //Min Z
                MathHelper.floor(this.mob.getX() + searchRadius),   //Max X
                MathHelper.floor(this.mob.getY()),                      //Max Y
                MathHelper.floor(this.mob.getZ() + searchRadius));  //Max Z
        Iterator iter = iterable.iterator();


        while(iter.hasNext()) {
            BlockPos blockPos2 = (BlockPos)iter.next();
            if (this.mob.world.getBlockState(blockPos2).matches(BlockTags.LOGS) && !this.mob.world.getBlockState(blockPos2).getBlock().getTranslationKey().equalsIgnoreCase("block.capybara.oak_without_bark")) {
                pos = blockPos2;
                break;
            }
        }



    }
}
