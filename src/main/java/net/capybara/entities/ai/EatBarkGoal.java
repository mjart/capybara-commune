

package net.capybara.entities.ai;

import java.util.Iterator;
import java.util.Random;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntityWithAi;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class EatBarkGoal extends Goal {
    private double searchRadius = 10.0D;
    private final MobEntityWithAi mob;

    public EatBarkGoal(MobEntityWithAi mob) {
        this.mob = mob;
    }

    public boolean canStart() {
        return this.mob.onGround && !this.mob.world.getBlockState(new BlockPos(this.mob)).matches(BlockTags.LOGS);
          }

    public void start() {
        BlockPos blockPos = null;
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
                blockPos = blockPos2;
                break;
            }
        }

        if (blockPos != null) {
            Random random = new Random();
            this.mob.getMoveControl().moveTo(
                    (double)blockPos.getX(),
                    (double)blockPos.getY(),
                    (double)blockPos.getZ(),
                    10.0D);
        }

    }
}
