package nerdhub.silkyspawners.mixins;

import net.minecraft.block.BlockState;
import net.minecraft.block.MobSpawnerBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobSpawnerBlock.class)
public class MixinMobSpawnerBlock {

    @Inject(method = "onStacksDropped", at = @At(value = "HEAD", target = "Lnet/minecraft/block/Block;dropExperience(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;I)V"), cancellable = true)
    private void onStacksDropper(BlockState blockState_1, World world_1, BlockPos blockPos_1, ItemStack itemStack_1, CallbackInfo ci) {
        if (EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, itemStack_1) > 0) {
            ci.cancel();
        }
    }
}
