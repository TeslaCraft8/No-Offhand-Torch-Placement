package net.teslacraft8.notp.mixins;

// import config class
import net.teslacraft8.notp.Config;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockPlacementMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void blockOffhandTorchPlacement(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir){
        // get player
        Player player = context.getPlayer();
        // validate player and off hand interaction
        if (player == null || context.getHand() != InteractionHand.OFF_HAND) return;

        // allow when crouching if enabled
        if (Config.allowCrouched && player.isCrouching()) return;

        // get resourcelocation id of item in hand
        ItemStack item = context.getItemInHand();
        ResourceLocation itemId = BuiltInRegistries.ITEM.getKey(item.getItem());

        // check if the item is part of the blocked items set
        if (Config.blockedItems.contains(itemId)) {
            //block interaction
            cir.setReturnValue(InteractionResult.FAIL);
        }
    }
}
