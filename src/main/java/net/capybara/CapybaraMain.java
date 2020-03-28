package net.capybara;

import net.capybara.items.OakBark;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;


public class CapybaraMain implements ModInitializer {
	public static final String CAPYBARA_NAMESPACE = "capybara-commune";

	// Setup the creative item group
	public static ItemGroup CAPYBARA_ITEM_GROUP = FabricItemGroupBuilder.create(
			new Identifier("capybara", "general"))
			.icon(() -> new ItemStack(Items.BOWL))
			.build();


	public static final Block OAK_WITHOUT_BARK_BLOCK = new Block(FabricBlockSettings.of(Material.WOOD).build());
	public static final Item OAK_WITHOUT_BARK_ITEM = new OakBark(new Item.Settings().group(CapybaraMain.CAPYBARA_ITEM_GROUP));

	public static final Item OAK_BARK = new OakBark(new Item.Settings().group(CapybaraMain.CAPYBARA_ITEM_GROUP));

	public static final Item CAPYBARA_MEAT = new Item(new Item.Settings().group(CapybaraMain.CAPYBARA_ITEM_GROUP));
	public static final Item CAPYBARA_PELT = new Item(new Item.Settings().group(CapybaraMain.CAPYBARA_ITEM_GROUP));

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		System.out.println("Initializing Capybara-Commune...");

		Registry.register(Registry.ITEM, new Identifier(CAPYBARA_NAMESPACE, "capybara-meat"), CAPYBARA_MEAT);
		Registry.register(Registry.ITEM, new Identifier(CAPYBARA_NAMESPACE, "capybara-pelt"), CAPYBARA_PELT);
		Registry.register(Registry.ITEM, new Identifier(CAPYBARA_NAMESPACE, "oak_bark"), OAK_BARK);
		Registry.register(Registry.ITEM, new Identifier("tutorial", "example_block"), new BlockItem(OAK_WITHOUT_BARK_BLOCK, new Item.Settings().group(CAPYBARA_ITEM_GROUP)));


		Registry.register(Registry.BLOCK, new Identifier(CAPYBARA_NAMESPACE, "oak_without_bark"), OAK_WITHOUT_BARK_BLOCK);

		System.out.println("Completed initialization of Capybara-Commune!");
	}
}
