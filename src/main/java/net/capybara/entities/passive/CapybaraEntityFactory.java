package net.capybara.entities.passive;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;

public class CapybaraEntityFactory implements EntityType.EntityFactory<CapybaraEntity> {
    @Override
    public CapybaraEntity create(EntityType<CapybaraEntity> type, World world) {
        return new CapybaraEntity(type, world);
    }
}
