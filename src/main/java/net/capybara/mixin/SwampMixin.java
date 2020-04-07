package net.capybara.mixin;

import net.capybara.CapybaraMain;
import net.capybara.entities.passive.CapybaraEntity;
import net.minecraft.entity.EntityCategory;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SwampBiome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SwampBiome.class)
public abstract class SwampMixin extends Biome {
    protected SwampMixin(Settings settings) {
        super(settings);
    }

    @Inject(at = @At("RETURN"), method = "<init>()V")
    private void init(CallbackInfo info) {
        // EntityType<?> type, int weight, int minGroupSize, int maxGroupSize
        this.addSpawn(EntityCategory.CREATURE, new Biome.SpawnEntry(CapybaraMain.CAPYBARA_MOB, 5, 5, 15 ));

        System.out.println("Mixed in spawning of capybara's in the swamp!");
    }
}
