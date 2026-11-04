package net.teslacraft8.notp;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod(Notp.MODID)
public class Notp {
    public static final String MODID = "notp";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Notp(IEventBus modEventBus, ModContainer modContainer) {

        // Register event listeners
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(Config::onLoad); // instead of deprecated event bus

        // Register common configuration
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Notp mod initialized - Mixin ready");
    }

    // -- deprecated and not needed
    // @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    // public static class ClientModEvents {
    //     @SubscribeEvent
    //     public static void onClientSetup(FMLClientSetupEvent event) {
    //         LOGGER.info("Notp client setup complete");
    //     }
    // }

}