package net.capybara.entities.passive;

import net.capybara.entities.ai.EatBarkGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class CapybaraEntity extends SheepEntity {
    private EatBarkGoal eatBarkGoal;

    public CapybaraEntity(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.eatBarkGoal = new EatBarkGoal(this);
        this.goalSelector.add(5, this.eatBarkGoal);
    }

    @Override
    public void dropItems() {
        return;
    }
}
