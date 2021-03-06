package corgiaoc.aloneandtogether;

import corgiaoc.aloneandtogether.client.entity.renderers.GeckoRenderer;
import corgiaoc.aloneandtogether.client.entity.renderers.PyreFlyRenderer;
import corgiaoc.aloneandtogether.client.renderer.ATChestTileEntityRenderer;
import corgiaoc.aloneandtogether.client.renderer.ATCutOutRenders;
import corgiaoc.aloneandtogether.common.dimension.abyss.entity.GeckoEntity;
import corgiaoc.aloneandtogether.common.dimension.abyss.entity.PyreFlyEntity;
import corgiaoc.aloneandtogether.common.dimension.abyss.world.blockplacer.ATBlockPlacerTypes;
import corgiaoc.aloneandtogether.core.*;
import corgiaoc.aloneandtogether.core.world.*;
import corgiaoc.aloneandtogether.core.world.util.WorldGenRegistrationHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.blockplacer.BlockPlacerType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Comparator;

@Mod(AloneAndTogether.MOD_ID)
public class AloneAndTogether {

    public static final String MOD_ID = "aloneandtogether";

    public static final Logger LOGGER = LogManager.getLogger();

    public AloneAndTogether() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        MinecraftForge.EVENT_BUS.register(new ForgeEvents());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        ATDimensions.registerBiomeSources();
        ATDimensions.registerChunkGenerators();
        Registry.BLOCK.keySet().forEach(block ->{
            if (block.toString().contains(AloneAndTogether.MOD_ID)){
                System.out.println(block.toString());
            }
        });
        GlobalEntityTypeAttributes.put(ATEntities.GECKO, GeckoEntity.setCustomAttributes().create());
        GlobalEntityTypeAttributes.put(ATEntities.PYRE_FLY, PyreFlyEntity.setCustomAttributes().create());

    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ATDimensions.registerDimensionRenderers();
        ATCutOutRenders.renderCutOuts();
        ClientRegistry.bindTileEntityRenderer(TileEntityType.CHEST, ATChestTileEntityRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ATEntities.GECKO, GeckoRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(ATEntities.PYRE_FLY, PyreFlyRenderer::new);


    }

    public static @Nonnull ResourceLocation createResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ATRegistries {
        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            LOGGER.debug("Alone & Together: Registering blocks...");
            ATBlocks.init();
            ATBlocks.blocks.forEach(block -> event.getRegistry().register(block));
            ATBlocks.blocks.clear();
            ATBlocks.blocks = null;
            LOGGER.info("Alone & Together: Blocks registered!");
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            LOGGER.debug("Alone & Together: Registering items...");
            ATItems.init();
            ATItems.items.forEach(item -> event.getRegistry().register(item));
            ATItems.items.clear();
            ATItems.items = null;
            LOGGER.info("Alone & Together: Items registered!");
        }

        @SubscribeEvent
        public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
            LOGGER.debug("Alone & Together: Registering entities...");
            ATEntities.init();
            ATEntities.entities.forEach(entityType -> event.getRegistry().register(entityType));
            ATEntities.entities.clear();
            ATEntities.entities = null;
            LOGGER.info("Alone & Together: Entities registered!");
        }

        @SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
            LOGGER.debug("Alone & Together: Registering sounds...");
            ATSoundEvents.init();
            ATSoundEvents.soundEvents.forEach(soundEvent -> event.getRegistry().register(soundEvent));
            ATSoundEvents.soundEvents.clear();
            ATSoundEvents.soundEvents = null;
            LOGGER.info("Alone & Together: Sounds registered!");
        }
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ATWorldGenRegistries {

        @SubscribeEvent
        public static void registerBiomes(RegistryEvent.Register<Biome> event) {
            LOGGER.debug("Alone & Together: Registering biomes...");
            ATBiomes.init();
            ATBiomes.biomes.sort(Comparator.comparingInt(WorldGenRegistrationHelper.PreserveBiomeOrder::getOrderPosition));
            ATBiomes.biomes.forEach(preserveBiomeOrder -> event.getRegistry().register(preserveBiomeOrder.getBiome()));
            ATBiomes.biomes.clear();
            ATBiomes.biomes = null;
            LOGGER.info("Alone & Together: Biomes registered!");
        }

        @SubscribeEvent
        public static void registerDecorators(RegistryEvent.Register<Placement<?>> event) {
            LOGGER.debug("Alone & Together: Registering decorators...");
            ATDecorators.init();
            ATDecorators.decorators.forEach(decorator -> event.getRegistry().register(decorator));
            ATDecorators.decorators.clear();
            ATDecorators.decorators = null;
            LOGGER.info("Alone & Together: Decorators registered!");
        }

        @SubscribeEvent
        public static void registerStructures(RegistryEvent.Register<Structure<?>> event) {
            LOGGER.debug("Alone & Together: Registering structures...");
            ATStructures.init();
            ATStructures.structures.forEach(structure -> event.getRegistry().register(structure));
            LOGGER.info("Alone & Together: Structures registered!");
        }

        @SubscribeEvent
        public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
            LOGGER.debug("Alone & Together: Registering features...");
            ATFeatures.init();
            ATFeatures.features.forEach(feature -> event.getRegistry().register(feature));
            ATFeatures.features.clear();
            ATFeatures.features = null;
            LOGGER.info("Alone & Together: Features registered!");
        }

        @SubscribeEvent
        public static void registerSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> event) {
            LOGGER.debug("Alone & Together: Registering surface builders...");
            ATSurfaceBuilders.init();
            ATSurfaceBuilders.surfaceBuilders.forEach(surfaceBuilder -> event.getRegistry().register(surfaceBuilder));
            ATSurfaceBuilders.surfaceBuilders.clear();
            ATSurfaceBuilders.surfaceBuilders = null;
            LOGGER.info("Alone & Together: Surface builders Registered!");
        }


        @SubscribeEvent
        public static void registerBlockPlacerType(RegistryEvent.Register<BlockPlacerType<?>> event) {
            LOGGER.debug("Alone & Together: Registering block placer types...");
            ATBlockPlacerTypes.init();
            ATBlockPlacerTypes.types.forEach(type -> event.getRegistry().register(type));
            ATBlockPlacerTypes.types.clear();
            ATBlockPlacerTypes.types = null;
            LOGGER.info("Alone & Together: Registering block placer types!");
        }
    }

    public static class ForgeEvents {
//        @SubscribeEvent
//        public void commandRegisterEvent(FMLServerStartingEvent event) {
//            LOGGER.debug("Alone & Together: \"Server Starting\" Event Starting...");
//            LOGGER.info("Alone & Together: \"Server Starting\" Event Complete!");
//        }

//        @SubscribeEvent
//        public void addDimensionalSpacing(final WorldEvent.Load event) {
//            LOGGER.debug("Alone & Together: \"World Load\" Event Starting...");
//            if(event.getWorld() instanceof ServerWorld){
//                ServerWorld serverWorld = (ServerWorld)event.getWorld();
//
//                // Prevent spawning our structure in Vanilla's superflat world as
//                // people seem to want their superflat worlds free of modded structures.
//                // Also that vanilla superflat is really tricky and buggy to work with in my experience.
//                if(serverWorld.getChunkProvider().getChunkGenerator() instanceof FlatChunkGenerator &&
//                        serverWorld.getDimensionKey().equals(World.OVERWORLD)){
//                    return;
//                }
//
//                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkProvider().generator.func_235957_b_().func_236195_a_());
//                tempMap.put(BYGStructures.VOLCANO_STRUCTURE, DimensionStructuresSettings.field_236191_b_.get(BYGStructures.VOLCANO_STRUCTURE));
//                serverWorld.getChunkProvider().generator.func_235957_b_().field_236193_d_ = tempMap;
//            }
//            LOGGER.info("Alone & Together: \"World Load\" Event Complete!");
//        }
    }
}
