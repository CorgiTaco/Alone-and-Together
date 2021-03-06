package corgiaoc.aloneandtogether.common.block;

import corgiaoc.aloneandtogether.core.ATBlocks;
import net.minecraft.block.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@SuppressWarnings("deprecation")
public class ATFernBlock extends BushBlock implements IFlammableBlock {
    private static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

    public ATFernBlock(Properties properties) {
        super(properties);
    }

    @ParametersAreNonnullByDefault
    @Override
    public @Nonnull VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        Vector3d offset = state.getOffset(reader, pos);
        return SHAPE.withOffset(offset.x, offset.y, offset.z);
    }

    @ParametersAreNonnullByDefault
    @Override
    protected boolean isValidGround(BlockState state, IBlockReader reader, BlockPos pos) {
        return super.isValidGround(state, reader, pos) ||
                        state.isIn(ATBlocks.OVERGROWN_VOIDSTONE) || state.isIn(ATBlocks.ORVIUM_GRASS) || state.isIn(ATBlocks.PHERN_MOSS) || state.isIn(ATBlocks.RITHLIUM) ||
                        state.isIn(BlockTags.NYLIUM) ||
                        state.isIn(Blocks.MYCELIUM);
    }

    @Override
    public @Nonnull OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XYZ;
    }

    @Override
    public int[] getFlammability() {
        return new int[] {60, 100};
    }
}

