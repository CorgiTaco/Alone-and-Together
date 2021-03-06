package corgiaoc.aloneandtogether.common.dimension.abyss.entity;

import corgiaoc.aloneandtogether.core.ATEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.FlyingMovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.IFlyingAnimal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class PyreFlyEntity extends AnimalEntity implements IFlyingAnimal {

    public PyreFlyEntity(EntityType<? extends PyreFlyEntity> type, World worldIn) {
        super(type, worldIn);
       this.moveController = new FlyingMovementController(this, 20, true);
       this.setPathPriority(PathNodeType.DANGER_FIRE, -1.0F);
       this.setPathPriority(PathNodeType.WATER, -1.0F);
       this.setPathPriority(PathNodeType.WATER_BORDER, 16.0F);
       this.setPathPriority(PathNodeType.COCOA, -1.0F);
       this.setPathPriority(PathNodeType.FENCE, -1.0F);
    }

    public static @Nonnull AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 2.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.18F)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 48.0D)
                .createMutableAttribute(Attributes.FLYING_SPEED, 0.6F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (world.isRemote) {
            double x = MathHelper.lerp(rand.nextDouble(), getPosX() - 0.3D, getPosX() + 0.3D);
            double z = MathHelper.lerp(rand.nextDouble(), getPosZ() - 0.4D, getPosZ() + 0.4D);
            world.addOptionalParticle(ParticleTypes.FLAME, true, x, getPosYHeight(0.4D), z, 0.0D, 0.0D, 0.D);
        }
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @ParametersAreNonnullByDefault
    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    protected void registerData() {
        super.registerData();
    }

    @ParametersAreNonnullByDefault
    @Override
    public @Nullable
    ILivingEntityData onInitialSpawn(IServerWorld world, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnData, @Nullable CompoundNBT tag) {
        return super.onInitialSpawn(world, difficultyIn, reason, spawnData == null ? new AgeableEntity.AgeableData(1.0F) : spawnData, tag);
    }

    @Override
    public void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, GeckoEntity.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromTag(ItemTags.FLOWERS), false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));

    }

    @ParametersAreNonnullByDefault
    @Override
    public @Nullable AgeableEntity func_241840_a(ServerWorld world, AgeableEntity mate) {
        return ATEntities.PYRE_FLY.create(world);
    }

    @Override
    public boolean isBreedingItem(@Nonnull ItemStack stack) {
        return stack.getItem().isIn(ItemTags.FLOWERS);
    }

    protected @Nonnull PathNavigator createNavigator(@Nonnull World worldIn) {
        FlyingPathNavigator navigator = new FlyingPathNavigator(this, worldIn) {
            public boolean canEntityStandOnPos(@Nonnull BlockPos pos) {
                return !this.world.isAirBlock(pos.down());
            }
        };
        navigator.setCanOpenDoors(false);
        navigator.setCanSwim(false);
        navigator.setCanEnterDoors(true);
        return navigator;
    }

    protected boolean makeFlySound() {
        return true;
    }
}

