package net.capybara;

import nerdhub.foml.obj.OBJLoader;
import net.capybara.features.HotSpring;
import net.capybara.entities.passive.CapybaraEntity;
import net.capybara.entities.passive.CapybaraEntityFactory;
import net.capybara.fluids.HotWaterFluid;
import net.capybara.items.OakBark;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.*;
import net.minecraft.fluid.BaseFluid;
import net.minecraft.item.*;
import net.fabricmc.fabric.api.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityCategory;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.ChanceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.feature.*;


public class CapybaraMain implements ModInitializer {
	public static final String CAPYBARA_NAMESPACE = "capybara";

	// Setup the creative item group
	public static ItemGroup CAPYBARA_ITEM_GROUP = FabricItemGroupBuilder.create(
			new Identifier("capybara", "general"))
			.icon(() -> new ItemStack(Items.BOWL))
			.build();

//Features
	//private static final Feature<DefaultFeatureConfig> HOT_SPRING = Registry.register(
	//		Registry.FEATURE,
	//		new Identifier("capybara", "hotspring"),
	//		new HotSpring(DefaultFeatureConfig::deserialize)
	//);

	public static final Block OAK_WITHOUT_BARK = new LogBlock(MaterialColor.WOOD, FabricBlockSettings.of(Material.WOOD).strength(2.0f, 2.0f).build());
	public static final Block HOTSPRING_BRICK = new Block(FabricBlockSettings.of(Material.STONE).strength(2.0f, 2.0f).build());

	public static BaseFluid STILL_HOTWATER;
	public static BaseFluid FLOWING_HOTWATER;
	public static Block HOTWATER;
	public static HotSpring HOTWATER_LAKE;

	public static final Item OAK_BARK = new OakBark(new Item.Settings().group(CapybaraMain.CAPYBARA_ITEM_GROUP));
	public static final Item CAPYBARA_MEAT = new Item(new Item.Settings().group(CapybaraMain.CAPYBARA_ITEM_GROUP));
	public static final Item CAPYBARA_PELT = new Item(new Item.Settings().group(CapybaraMain.CAPYBARA_ITEM_GROUP));
	public static  Item HOTWATER_BUCKET  = new Item(new Item.Settings().group(CapybaraMain.CAPYBARA_ITEM_GROUP));;

	public static final EntityType<CapybaraEntity> CAPYBARA_MOB = FabricEntityTypeBuilder
			.create(EntityCategory.CREATURE, new CapybaraEntityFactory()).size(EntityDimensions.fixed(2,1))
			.build();

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		System.out.println("Initializing Capybara-Commune...");

		System.out.println("Registering FOML..");
		OBJLoader.INSTANCE.registerDomain("capybara-commune");
		System.out.println("FOML Registered");

		//Fluids
		STILL_HOTWATER = Registry.register(Registry.FLUID, new Identifier(CAPYBARA_NAMESPACE, "hotwater"), new HotWaterFluid.Still());
		FLOWING_HOTWATER = Registry.register(Registry.FLUID, new Identifier(CAPYBARA_NAMESPACE, "flowing_hotwater"), new HotWaterFluid.Flowing());
		HOTWATER_BUCKET = Registry.register(Registry.ITEM, new Identifier(CAPYBARA_NAMESPACE, "hotwater_bucket"), new BucketItem(STILL_HOTWATER, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));

		//Items
		Registry.register(Registry.ITEM, new Identifier(CAPYBARA_NAMESPACE, "capybara-meat"), CAPYBARA_MEAT);
		Registry.register(Registry.ITEM, new Identifier(CAPYBARA_NAMESPACE, "capybara-pelt"), CAPYBARA_PELT);
		Registry.register(Registry.ITEM, new Identifier(CAPYBARA_NAMESPACE, "oak_bark"), OAK_BARK);
		Registry.register(Registry.ITEM, new Identifier(CAPYBARA_NAMESPACE, "oak_without_bark"), new BlockItem(OAK_WITHOUT_BARK, new Item.Settings().group(CAPYBARA_ITEM_GROUP)));
		Registry.register(Registry.ENTITY_TYPE, new Identifier(CAPYBARA_NAMESPACE, "capybara_entity"), CAPYBARA_MOB);
		Registry.register(Registry.BLOCK, new Identifier(CAPYBARA_NAMESPACE, "oak_without_bark"), OAK_WITHOUT_BARK);
		Registry.register(Registry.BLOCK, new Identifier(CAPYBARA_NAMESPACE, "hotspring_brick"), HOTSPRING_BRICK);
		HOTWATER = Registry.register(Registry.BLOCK, new Identifier(CAPYBARA_NAMESPACE, "hotwater"), new FluidBlock(STILL_HOTWATER, FabricBlockSettings.copy(Blocks.WATER).build()){});
		HOTWATER_LAKE = Registry.register(Registry.FEATURE, new Identifier(CAPYBARA_NAMESPACE, "hot_water"), new HotSpring(SingleStateFeatureConfig::deserialize));

		// generate in swamps, similar to water lakes, but with a chance of 40 (the higher the number, the lower the generation chance)
		Biomes.SWAMP.addFeature(
				GenerationStep.Feature.LOCAL_MODIFICATIONS,
				HOTWATER_LAKE.configure(new SingleStateFeatureConfig(HOTWATER.getDefaultState()))
						.createDecoratedFeature(Decorator.WATER_LAKE.configure(new ChanceDecoratorConfig(10)))
		);

		System.out.println("Completed initialization of Capybara-Commune!");
	}
}
