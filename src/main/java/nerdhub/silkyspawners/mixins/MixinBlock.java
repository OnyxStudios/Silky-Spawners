package nerdhub.silkyspawners.mixins;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class MixinBlock {

    @Inject(method = "onBreak", at = @At("HEAD"))
    private void onBroken(World world_1, BlockPos blockPos_1, BlockState blockState_1, PlayerEntity playerEntity_1, CallbackInfo ci) {
        if (blockState_1.getBlock() == Blocks.SPAWNER && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, playerEntity_1.getMainHandStack()) > 0) {
            BlockEntity blockEntity = world_1.getBlockEntity(blockPos_1);
            if (blockEntity instanceof MobSpawnerBlockEntity) {
                ItemStack stack = new ItemStack(Blocks.SPAWNER);
                stack.setTag(new CompoundTag());
                CompoundTag tag = new CompoundTag();
                blockEntity.toTag(tag);
                stack.getTag().put("spawnerData", tag);
                if (!world_1.isClient) {
                    world_1.spawnEntity(new ItemEntity(world_1, blockPos_1.getX(), blockPos_1.getY(), blockPos_1.getZ(), stack));
                }
            }
        }
    }

    @Inject(method = "onPlaced", at = @At("HEAD"))
    private void onPlaced(World world_1, BlockPos blockPos_1, BlockState blockState_1, LivingEntity livingEntity_1, ItemStack itemStack_1, CallbackInfo ci) {
        BlockEntity blockEntity = world_1.getBlockEntity(blockPos_1);
        if (blockEntity instanceof MobSpawnerBlockEntity && itemStack_1.getTag() != null && itemStack_1.getTag().containsKey("spawnerData")) {
            blockEntity.fromTag(itemStack_1.getTag().getCompound("spawnerData"));
            blockEntity.setPos(blockPos_1);
            world_1.updateListeners(blockPos_1, world_1.getBlockState(blockPos_1), world_1.getBlockState(blockPos_1), 3);
        }
    }
}
