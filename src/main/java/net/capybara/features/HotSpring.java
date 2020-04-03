package net.capybara.features;

import com.mojang.datafixers.Dynamic;
import net.capybara.CapybaraMain;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;

import java.util.Random;
import java.util.function.Function;

public class HotSpring extends Feature<SingleStateFeatureConfig> {
    private static final BlockState CAVE_AIR;

    public HotSpring(Function<Dynamic<?>, ? extends SingleStateFeatureConfig> configFactory) {
        super(configFactory);
    }

    public boolean generate(IWorld iWorld, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random random, BlockPos blockPos, SingleStateFeatureConfig singleStateFeatureConfig) {
        while (blockPos.getY() > 5 && iWorld.isAir(blockPos)) {
            blockPos = blockPos.down();
        }

        if (blockPos.getY() <= 4) {
            return false;
        } else {
            blockPos = blockPos.down(4);
            ChunkPos chunkPos = new ChunkPos(blockPos);
            if (!iWorld.getChunk(chunkPos.x, chunkPos.z, ChunkStatus.STRUCTURE_REFERENCES).getStructureReferences(Feature.VILLAGE.getName()).isEmpty()) {
                return false;
            } else {
                boolean[] bls = new boolean[2048];
                int i = random.nextInt(4) + 4;

                int ab;
                for (ab = 0; ab < i; ++ab) {
                    double d = random.nextDouble() * 6.0D + 3.0D;
                    double e = random.nextDouble() * 4.0D + 2.0D;
                    double f = random.nextDouble() * 6.0D + 3.0D;
                    double g = random.nextDouble() * (16.0D - d - 2.0D) + 1.0D + d / 2.0D;
                    double h = random.nextDouble() * (8.0D - e - 4.0D) + 2.0D + e / 2.0D;
                    double k = random.nextDouble() * (16.0D - f - 2.0D) + 1.0D + f / 2.0D;

                    for (int l = 1; l < 15; ++l) {
                        for (int m = 1; m < 15; ++m) {
                            for (int n = 1; n < 7; ++n) {
                                double o = ((double) l - g) / (d / 2.0D);
                                double p = ((double) n - h) / (e / 2.0D);
                                double q = ((double) m - k) / (f / 2.0D);
                                double r = o * o + p * p + q * q;
                                if (r < 1.0D) {
                                    bls[(l * 16 + m) * 8 + n] = true;
                                }
                            }
                        }
                    }
                }

                int ad;
                int ac;
                boolean bl2;
                for (ab = 0; ab < 16; ++ab) {
                    for (ac = 0; ac < 16; ++ac) {
                        for (ad = 0; ad < 8; ++ad) {
                            bl2 = !bls[(ab * 16 + ac) * 8 + ad] && (ab < 15 && bls[((ab + 1) * 16 + ac) * 8 + ad] || ab > 0 && bls[((ab - 1) * 16 + ac) * 8 + ad] || ac < 15 && bls[(ab * 16 + ac + 1) * 8 + ad] || ac > 0 && bls[(ab * 16 + (ac - 1)) * 8 + ad] || ad < 7 && bls[(ab * 16 + ac) * 8 + ad + 1] || ad > 0 && bls[(ab * 16 + ac) * 8 + (ad - 1)]);
                            if (bl2) {
                                Material material = iWorld.getBlockState(blockPos.add(ab, ad, ac)).getMaterial();
                                if (ad >= 4 && material.isLiquid()) {
                                    return false;
                                }

                                if (ad < 4 && !material.isSolid() && iWorld.getBlockState(blockPos.add(ab, ad, ac)) != singleStateFeatureConfig.state) {
                                    return false;
                                }
                            }
                        }
                    }
                }

                for (ab = 0; ab < 16; ++ab) {
                    for (ac = 0; ac < 16; ++ac) {
                        for (ad = 0; ad < 8; ++ad) {
                            if (bls[(ab * 16 + ac) * 8 + ad]) {
                                iWorld.setBlockState(blockPos.add(ab, ad, ac), ad >= 4 ? CAVE_AIR : singleStateFeatureConfig.state, 2);
                               if(iWorld.getBlockState(blockPos.add(ab,ad,ac)) == singleStateFeatureConfig.state)
                               {
                                    if(iWorld.getBlockState(blockPos.add(ab + 1, ad, ac)) != singleStateFeatureConfig.state)
                                        iWorld.setBlockState(blockPos.add(ab + 1,ad,ac), CapybaraMain.HOTSPRING_BRICK.getDefaultState(),2);
                                   if(iWorld.getBlockState(blockPos.add(ab - 1, ad, ac)) != singleStateFeatureConfig.state)
                                       iWorld.setBlockState(blockPos.add(ab - 1,ad,ac), CapybaraMain.HOTSPRING_BRICK.getDefaultState(),2);
                                   if(iWorld.getBlockState(blockPos.add(ab , ad, ac +1)) != singleStateFeatureConfig.state)
                                       iWorld.setBlockState(blockPos.add(ab ,ad,ac+1), CapybaraMain.HOTSPRING_BRICK.getDefaultState(),2);
                                   if(iWorld.getBlockState(blockPos.add(ab, ad, ac - 1)) != singleStateFeatureConfig.state)
                                       iWorld.setBlockState(blockPos.add(ab,ad,ac -1), CapybaraMain.HOTSPRING_BRICK.getDefaultState(),2);
                                   if(iWorld.getBlockState(blockPos.add(ab, ad -1 , ac)) != singleStateFeatureConfig.state)
                                       iWorld.setBlockState(blockPos.add(ab,ad - 1,ac ), CapybaraMain.HOTSPRING_BRICK.getDefaultState(),2);


                               }

                            }

                        }
                    }
                }

                BlockPos blockPos3;
                for (ab = 0; ab < 16; ++ab) {
                    for (ac = 0; ac < 16; ++ac) {
                        for (ad = 4; ad < 8; ++ad) {
                            if (bls[(ab * 16 + ac) * 8 + ad]) {
                                blockPos3 = blockPos.add(ab, ad - 1, ac);
                                if (isDirt(iWorld.getBlockState(blockPos3).getBlock()) && iWorld.getLightLevel(LightType.SKY, blockPos.add(ab, ad, ac)) > 0) {
                                    Biome biome = iWorld.getBiome(blockPos3);
                                    if (biome.getSurfaceConfig().getTopMaterial().getBlock() == Blocks.MYCELIUM) {
                                        iWorld.setBlockState(blockPos3, Blocks.MYCELIUM.getDefaultState(), 2);
                                    } else {
                                        iWorld.setBlockState(blockPos3, Blocks.GRASS_BLOCK.getDefaultState(), 2);
                                    }
                                }


                            }
                        }
                    }
                }

                return true;
            }
        }
    }
        static {
            CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
        }
}
