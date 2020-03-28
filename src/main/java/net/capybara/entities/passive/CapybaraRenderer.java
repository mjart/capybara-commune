package net.capybara.entities.passive;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class CapybaraRenderer extends MobEntityRenderer<CapybaraEntity, CapybaraEntityModel<CapybaraEntity>> {
    public CapybaraRenderer(EntityRenderDispatcher renderManager) {
        super(renderManager, new CapybaraEntityModel<>(), 1);
    }

    @Override
    public Identifier getTexture(CapybaraEntity CapybaraEntity) {
        return new Identifier("capybara:textures/item/capybara_pelt");
    }
}
