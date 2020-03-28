package net.capybara;

import net.capybara.items.OakBark;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class CapybaraMain implements ModInitializer {
	public static final String CAPYBARA_NAMESPACE = "capybara-commune";

	// Setup the creative item group
	public static ItemGroup CAPYBARA = FabricItemGroupBuilder.create(
			new Identifier("capybara", "general"))
			.icon(() -> new ItemStack(Items.BOWL))
			.build();


	public static final Item OAK_BARK = new OakBark(new Item.Settings().group(CapybaraMain.CAPYBARA));
	public static final Item CAPYBARA_MEAT = new Item(new Item.Settings().group(CapybaraMain.CAPYBARA));
	public static final Item CAPYBARA_PELT = new Item(new Item.Settings().group(CapybaraMain.CAPYBARA));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		Registry.register(Registry.ITEM, new Identifier(CAPYBARA_NAMESPACE, "capybara-meat"), CAPYBARA_MEAT);
		Registry.register(Registry.ITEM, new Identifier(CAPYBARA_NAMESPACE, "capybara-pelt"), CAPYBARA_PELT);
		Registry.register(Registry.ITEM, new Identifier(CAPYBARA_NAMESPACE, "oak_bark"), OAK_BARK);
		System.out.println("Hello Fabric world!");
	}
}
