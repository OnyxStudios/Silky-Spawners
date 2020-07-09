package io.github.onyxstudios.silkyspawners.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpawnerBlock.class)
public abstract class SpawnerBlockMixin {

    @Inject(method = "onStacksDropped", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockWithEntity;onStacksDropped(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;)V", shift = At.Shift.AFTER), cancellable = true)
    private void silkyspawners_onStacksDropped(BlockState state, World world, BlockPos pos, ItemStack stack, CallbackInfo ci) {
        if(EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) > 0) {
            ci.cancel();
        }
    }
}
