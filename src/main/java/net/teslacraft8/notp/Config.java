package net.teslacraft8.notp;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// deprecated! @EventBusSubscriber(modid = Notp.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {

    // config builder init
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    // blocked items list - with support for realistic torches ;P
    private static final ModConfigSpec.ConfigValue<List<? extends String>> BLOCKED_ITEMS = BUILDER.comment("A list of items to block offhand placement for.").defineListAllowEmpty("blockedItems", List.of("minecraft:torch", "minecraft:soul_torch", "minecraft:lantern", "minecraft:soul_lantern", "realistictorches:lit_torch", "realistictorches:unlit_torch"), Config::validateItemName);
    // allow when crouched boolean
    private static final ModConfigSpec.BooleanValue ALLOW_CROUCHED = BUILDER.comment("Whether to allow placement when crouched").define("allowCrouched", true);
    // final config spec
    static final ModConfigSpec SPEC = BUILDER.build();

    // initialized public variables...
    public static volatile boolean allowCrouched = true;
    public static volatile Set<ResourceLocation> blockedItems = Set.of();

    // updated to support non existing items - needed for optionaly supporting items from other mods
    // validates strings follow resourcelocation syntax namespace:path
    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName
                && ResourceLocation.tryParse(itemName) != null;
    }

    // @SubscribeEvent part of deprecated event bus - loaded in notp.java now!
    static void onLoad(final ModConfigEvent event) {
        if (event.getConfig().getSpec() != SPEC) {
            return;
        }

        // Converts item strings into resourcelocation objects
        blockedItems = BLOCKED_ITEMS.get()
                .stream()
                .map(ResourceLocation::parse)
                .collect(Collectors.toUnmodifiableSet());

        // Loads boolean value
        allowCrouched = ALLOW_CROUCHED.get();
    }
}
