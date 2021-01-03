package corgiaoc.aloneandtogether.common.world.feature.tree.util;

import corgiaoc.aloneandtogether.common.world.feature.config.ATTreeConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public abstract class HugeTreeSpawner extends TreeSpawner {
    public static boolean canBigTreeSpawnAt(BlockState blockUnder, IBlockReader worldIn, BlockPos pos, int xOffset, int zOffset) {
        Block block = blockUnder.getBlock();
        return block == worldIn.getBlockState(pos.add(xOffset, 0, zOffset)).getBlock() && block == worldIn.getBlockState(pos.add(xOffset + 1, 0, zOffset)).getBlock() && block == worldIn.getBlockState(pos.add(xOffset, 0, zOffset + 1)).getBlock() && block == worldIn.getBlockState(pos.add(xOffset + 1, 0, zOffset + 1)).getBlock();
    }

    public boolean spawn(ISeedReader worldIn, ChunkGenerator chunkGenerator, BlockPos pos, BlockState blockUnder, Random random) {
        for (int i = 0; i >= -1; --i) {
            for (int j = 0; j >= -1; --j) {
                if (canBigTreeSpawnAt(blockUnder, worldIn, pos, i, j)) {
                    return this.bigTree(worldIn, chunkGenerator, pos, blockUnder, random, i, j);
                }
            }
        }

        return super.spawn(worldIn, chunkGenerator, pos, blockUnder, random);
    }

    @Nullable
    protected abstract ConfiguredFeature<ATTreeConfig, ?> getBigTreeFeature(Random random);

    public boolean bigTree(ISeedReader worldIn, ChunkGenerator chunkGenerator, BlockPos pos, BlockState blockUnder, Random random, int xOffset, int zOffset) {
        ConfiguredFeature<ATTreeConfig, ?> configuredTreeFeature = this.getBigTreeFeature(random);
        if (configuredTreeFeature == null) {
            return false;
        } else {
            BlockState blockstate = Blocks.AIR.getDefaultState();
            worldIn.setBlockState(pos.add(xOffset, 0, zOffset), blockstate, 4);
            worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset), blockstate, 4);
            worldIn.setBlockState(pos.add(xOffset, 0, zOffset + 1), blockstate, 4);
            worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset + 1), blockstate, 4);

            configuredTreeFeature.config.forcePlacement();
            if (configuredTreeFeature.generate(worldIn, chunkGenerator, random, pos.add(xOffset, 0, zOffset))) {
                return true;
            } else {
                worldIn.setBlockState(pos.add(xOffset, 0, zOffset), blockUnder, 4);
                worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset), blockUnder, 4);
                worldIn.setBlockState(pos.add(xOffset, 0, zOffset + 1), blockUnder, 4);
                worldIn.setBlockState(pos.add(xOffset + 1, 0, zOffset + 1), blockUnder, 4);
                return false;
            }
        }
    }
}