package net.capybara.entities.passive;

import net.capybara.entities.ai.EatBarkGoal;
import net.fabricmc.fabric.api.event.server.ServerStopCallback;
import net.fabricmc.fabric.impl.client.particle.FabricParticleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;





public class CapybaraEntity extends SheepEntity implements ServerStopCallback {
    private EatBarkGoal eatBarkGoal;
    public static final EntityAttribute ORIGINAL_FOLLOW_RANGE = (new ClampedEntityAttribute((EntityAttribute)null, "capybara.originalFollowRange", 32.0D, 0.0D, 2048.0D)).setName("Original Follow Range");
    public static final EntityAttribute PACIFIED_COUNT = (new ClampedEntityAttribute((EntityAttribute)null, "capybara.pacifiedCount", 0, 0.0D, 2048.0D)).setName("Pacified by N mobs");

    public float pacifyRadius = 10f;
    private List<HostileEntity> pacifiedEntities = new ArrayList<>();

    public CapybaraEntity(EntityType<? extends SheepEntity> entityType, World world) {
        super(entityType, world);

        //TODO: I'm 90% sure this will create a memory leak. But hey it's a mod jam :)
        ServerStopCallback.EVENT.register(this);
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
                    if(originalFollowRange > 0.0f) {
                        getOrRegisterAttribute(entity.getAttributes(), ORIGINAL_FOLLOW_RANGE).setBaseValue(originalFollowRange);
                    }
                    double pacifyCount = 1 + getOrRegisterAttribute(entity.getAttributes(), PACIFIED_COUNT).getBaseValue();
                    getOrRegisterAttribute(entity.getAttributes(), PACIFIED_COUNT).setBaseValue(pacifyCount);
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

    private static EntityAttributeInstance getOrRegisterAttribute(AbstractEntityAttributeContainer attributes, EntityAttribute attribute) {
        EntityAttributeInstance instance = attributes.get(attribute);
        if (instance != null) {
            return instance;
        }

        return attributes.register(attribute);
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

    @Override
    public void onStopServer(MinecraftServer minecraftServer) {
        for(HostileEntity entity : pacifiedEntities){
            decreasePacifyCount(entity);
        }
        pacifiedEntities.clear();
    }
}
