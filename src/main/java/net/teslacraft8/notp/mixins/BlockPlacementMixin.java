package net.teslacraft8.notp.mixins;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public class BlockPlacementMixin {
    @Inject(method = "useOn", at = @At("HEAD"), cancellable = true)
    private void blockOffhandTorchPlacement(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir){
        ItemStack item = context.getItemInHand();

        Player player = context.getPlayer();
        if (player == null) return;
        if (context.getHand() == InteractionHand.OFF_HAND &&
                (item.is(Items.TORCH) || item.is(Items.SOUL_TORCH))) {
            if (!player.isCrouching()){
                cir.setReturnValue(InteractionResult.FAIL);
            }
        }
    }
}
