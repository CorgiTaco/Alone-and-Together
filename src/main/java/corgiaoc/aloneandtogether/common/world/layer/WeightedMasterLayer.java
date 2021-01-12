package corgiaoc.aloneandtogether.common.world.layer;

import corgiaoc.aloneandtogether.util.BiomeUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class WeightedMasterLayer implements IAreaTransformer0 {

    private final Registry<Biome> biomeRegistry;
    private final WeightedList<ResourceLocation> weightedBiomesList;

    public WeightedMasterLayer(Registry<Biome> biomeRegistry, WeightedList<ResourceLocation> weightedBiomesList) {
        this.biomeRegistry = biomeRegistry;
        this.weightedBiomesList = weightedBiomesList;
    }

    @Override
    public int apply(INoiseRandom rand, int x, int y) {
        return getRandomBiome(this.biomeRegistry, rand);
    }

    private int getRandomBiome(Registry<Biome> biomeRegistry, INoiseRandom rand) {
        return biomeRegistry.getId(biomeRegistry.getOptional(BiomeUtils.getBiomeFromID(this.weightedBiomesList, rand)).orElseThrow(RuntimeException::new));
    }
}