package net.capybara;

import net.capybara.entities.passive.CapybaraRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class CapybaraMainClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        System.out.println("Initializing Capybara-Commune Client...");
        EntityRendererRegistry.INSTANCE.register(CapybaraMain.CAPYBARA_MOB,
                (entityRenderDispatcher, context) -> new CapybaraRenderer(entityRenderDispatcher));
        System.out.println("Completed initialization of Capybara-Commune!");
    }
}
