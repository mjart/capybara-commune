package net.capybara.entities.passive;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.Entity;

@Environment(EnvType.CLIENT)
public class CapybaraEntityModel<T extends Entity> extends CustomQuadruped<T> {
    public CapybaraEntityModel() { this(1.0F);}

    public CapybaraEntityModel(float f) {
        super(2,f,true,4F,3F,2F, 2F, 24);
        this.head.setTextureOffset(16, 16);
        this.head.addCuboid(4.0F, -6.0F, -5.0F, 0.3F, 1.5F, 0.3F, f); //ears
        this.head.addCuboid(-4.0F, -6.0F, -5.0F, 0.3F, 1.5F, 0.3F, f); //ears

    }
}
