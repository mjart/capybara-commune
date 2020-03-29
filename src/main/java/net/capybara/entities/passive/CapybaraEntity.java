package net.capybara.entities.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CapybaraEntity extends PigEntity {
    public static final EntityAttribute ORIGINAL_FOLLOW_RANGE = (new ClampedEntityAttribute((EntityAttribute)null, "capybara.originalFollowRange", 32.0D, 0.0D, 2048.0D)).setName("Original Follow Range");
    public static final EntityAttribute PACIFIED_COUNT = (new ClampedEntityAttribute((EntityAttribute)null, "capybara.pacifiedCount", 0, 0.0D, 2048.0D)).setName("Pacified by N mobs");

    public float pacifyRadius = 10f;
    private List<HostileEntity> pacifiedEntities = new ArrayList<>();

    public CapybaraEntity(EntityType<? extends PigEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected void mobTick() {
        super.mobTick();

        if(!isAlive())
            return;

        Box aroundEntity = new Box(this.getPos(), this.getPos());
        aroundEntity = aroundEntity.expand(pacifyRadius);
        List<HostileEntity> entities = this.world.getEntities(HostileEntity.class, aroundEntity, (mobEntity) -> true);

        Iterator<HostileEntity> pacifiedEntitiesIterator = pacifiedEntities.iterator();
        while(pacifiedEntitiesIterator.hasNext()){
            HostileEntity pacifiedEntity = pacifiedEntitiesIterator.next();

            if(!entities.contains(pacifiedEntity)){
                decreasePacifyCount(pacifiedEntity);
                pacifiedEntitiesIterator.remove();
            }
        }

        for(HostileEntity entity : entities) {
            if (!pacifiedEntities.contains(entity)) {
                EntityAttributeInstance followRangeAttribute = entity.getAttributes().get(EntityAttributes.FOLLOW_RANGE);
                if (followRangeAttribute != null) {
                    double originalFollowRange = followRangeAttribute.getBaseValue();
                    getOrRegisterAttribute(entity.getAttributes(), ORIGINAL_FOLLOW_RANGE).setBaseValue(originalFollowRange);
                    getOrRegisterAttribute(entity.getAttributes(), PACIFIED_COUNT).setBaseValue(1+getOrRegisterAttribute(entity.getAttributes(), PACIFIED_COUNT).getBaseValue());
                    followRangeAttribute.setBaseValue(0.0f);
                    pacifiedEntities.add(entity);
                }
            }
        }
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);

        for(HostileEntity entity : pacifiedEntities){
            decreasePacifyCount(entity);
        }
        pacifiedEntities.clear();
    }

    private void decreasePacifyCount(HostileEntity entity){
        double pacifiedCount = entity.getAttributes().get(PACIFIED_COUNT).getBaseValue();
        entity.getAttributes().get(PACIFIED_COUNT).setBaseValue(--pacifiedCount);
        if(pacifiedCount <= 0){
            double originalFollowRange = entity.getAttributes().get(ORIGINAL_FOLLOW_RANGE).getBaseValue();
            entity.getAttributes().get(EntityAttributes.FOLLOW_RANGE).setBaseValue(originalFollowRange);
        }
    }

    private EntityAttributeInstance getOrRegisterAttribute(AbstractEntityAttributeContainer attributes, EntityAttribute attribute){
        EntityAttributeInstance instance = attributes.get(attribute);
        if(instance != null){
            return instance;
        }

        return attributes.register(attribute);
    }
}
