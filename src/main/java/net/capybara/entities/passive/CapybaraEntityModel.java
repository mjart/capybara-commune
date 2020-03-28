package net.capybara.entities.passive;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.entity.Entity;

@Environment(EnvType.CLIENT)
public class CapybaraEntityModel<T extends Entity> extends QuadrupedEntityModel<T> {
    public CapybaraEntityModel() { this(1.0F);}

    public CapybaraEntityModel(float f) {
        super(2,f,true,4F,3F,2F, 2F, 24);
        this.head.setTextureOffset(16, 16)
                .addCuboid(-2.0F, 0.0F, -9.0F, 4.0F, 3.0F, 1.0F, f);
        this.head.addCuboid(2.0F, 3.0F, 9.0F, 0.3F, 1.5F, 0.3F, f); //ears
        this.head.addCuboid(-2.0F, 3.0F, 9.0F, 0.3F, 1.5F, 0.3F, f); //ears

    }
}
