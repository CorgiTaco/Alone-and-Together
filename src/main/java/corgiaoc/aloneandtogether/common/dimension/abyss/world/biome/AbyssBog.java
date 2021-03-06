package corgiaoc.aloneandtogether.common.dimension.abyss.world.biome;

import corgiaoc.aloneandtogether.common.dimension.abyss.world.AbyssBiomeBase;
import corgiaoc.aloneandtogether.core.ATBlocks;
import corgiaoc.aloneandtogether.core.world.ATConfiguredFeatures;
import corgiaoc.aloneandtogether.core.world.util.WorldGenRegistrationHelper;
import net.minecraft.client.audio.BackgroundMusicTracks;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class AbyssBog extends AbyssBiomeBase {
    static final ConfiguredSurfaceBuilder<?> SURFACE_BUILDER = WorldGenRegistrationHelper.createConfiguredSurfaceBuilder("abyss_bog", new ConfiguredSurfaceBuilder<>(SurfaceBuilder.DEFAULT, new SurfaceBuilderConfig(ATBlocks.OVERGROWN_VOIDSTONE.getDefaultState(), ATBlocks.VOIDSTONE.getDefaultState(), ATBlocks.VOIDSTONE.getDefaultState())));
    static final Biome.RainType PRECIPATATION = Biome.RainType.RAIN;
    static final Biome.Category CATEGORY = Biome.Category.SWAMP;
    static final float DEPTH = -0.35F;
    static final float SCALE = 0.02F;
    static final float TEMPERATURE = 2.0F;
    static final float DOWNFALL = 0.9F;
    static final int WATER_COLOR = 3158064;
    static final int WATER_FOG_COLOR = 3158064;


    static final Biome.Climate WEATHER = new Biome.Climate(PRECIPATATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder();
    static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(SURFACE_BUILDER);

    public AbyssBog() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(WATER_COLOR).setWaterFogColor(WATER_FOG_COLOR)
                .setWaterColor(WATER_COLOR)
                .setWaterFogColor(WATER_FOG_COLOR)
                .setFogColor(3158064)
                .withSkyColor(0)
                .setParticle(new ParticleEffectAmbience(ParticleTypes.CRIMSON_SPORE, 0.00428F))
                .setAmbientSound(SoundEvents.AMBIENT_CRIMSON_FOREST_LOOP)
                .setMoodSound(new MoodSoundAmbience(SoundEvents.AMBIENT_CRIMSON_FOREST_MOOD, 6000, 8, 2.0D))
                .setAdditionsSound(new SoundAdditionsAmbience(SoundEvents.AMBIENT_CRIMSON_FOREST_ADDITIONS, 0.0010D))
                .setMusic(BackgroundMusicTracks.getDefaultBackgroundMusicSelector(SoundEvents.MUSIC_END)).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.copy());

    }

    static {
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.RANDOM_VOID_PLANT);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.ABYSSAL_GROWTH);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.BOG_BELL_BULBS);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.TALL_BELL_BULBS);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.GLOWTAILS);
        //GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.ABYSS_BOG_TREES);
        /*GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ATConfiguredFeatures.SPECTRAL_VINES);*/
    }
}
