package corgiaoc.aloneandtogether.common.world.feature.tree.util;

import corgiaoc.aloneandtogether.common.world.feature.config.ATTreeConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class TreeSpawner {
    @Nullable
    protected abstract ConfiguredFeature<ATTreeConfig, ?> getTreeFeature(Random random);

    public boolean spawn(ISeedReader worldIn, ChunkGenerator chunkGenerator, BlockPos pos, BlockState blockUnder, Random random) {
        ConfiguredFeature<ATTreeConfig, ?> configuredTreeFeature = this.getTreeFeature(random);
        if (configuredTreeFeature == null) {
            return false;
        } else {
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 4);
            configuredTreeFeature.config.forcePlacement();
            if (configuredTreeFeature.generate(worldIn, chunkGenerator, random, pos)) {
                return true;
            } else {
                worldIn.setBlockState(pos, blockUnder, 4);
                return false;
            }
        }
    }
}