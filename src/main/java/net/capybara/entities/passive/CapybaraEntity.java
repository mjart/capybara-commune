package net.capybara.entities.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.world.World;

public class CapybaraEntity extends PigEntity {


    public CapybaraEntity(EntityType<? extends PigEntity> entityType, World world) {
        super(entityType, world);
    }
}
