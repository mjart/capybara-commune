package net.capybara;

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class CapybaraMain implements ModInitializer {

	public static final Item CAPYBARA_MEAT = new Item(new Item.Settings().group(ItemGroup.MISC));
	public static final Item CAPYBARA_PELT = new Item(new Item.Settings().group(ItemGroup.MISC));
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Registry.register(Registry.ITEM, new Identifier("capybarameat", "capybara-meat"), CAPYBARA_MEAT);
		Registry.register(Registry.ITEM, new Identifier("capybarapelt", "capybara-pelt"), CAPYBARA_PELT);

		System.out.println("Hello Fabric world!");
	}
}
