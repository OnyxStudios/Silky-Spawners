package io.github.onyxstudios.silkyspawners.mixins;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.item.BlockItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    @Redirect(method = "writeTagToBlockEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/BlockEntity;copyItemDataRequiresOperator()Z"))
    private static boolean silkyspawners_writeTagToBlockEntity(BlockEntity blockEntity) {
        return blockEntity.copyItemDataRequiresOperator() && !(blockEntity instanceof MobSpawnerBlockEntity);
    }
}
