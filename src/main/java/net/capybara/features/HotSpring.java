package net.capybara.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class HotSpring extends Feature<DefaultFeatureConfig> {

    public HotSpring(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        BlockPos topPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos);
        Direction offset = Direction.NORTH;

        for (int x = 1; x < 5; x++) {
            for(int z = 1; z < 5; z++) {
                if((x == 1 || x == 4) || ( z ==1 || z == 4))
                {
                    topPos = world.getTopPosition(Heightmap.Type.WORLD_SURFACE, pos.add(x, 0, z));
                    for (int y = 1; y < 4; y++) {
                        world.setBlockState(topPos.up(y).offset(offset), Blocks.STONE.getDefaultState(), 3);
                    }
                }
            }
        }

        return true;
    }
}
