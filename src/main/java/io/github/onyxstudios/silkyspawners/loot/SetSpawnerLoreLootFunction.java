package io.github.onyxstudios.silkyspawners.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import io.github.onyxstudios.silkyspawners.SilkySpawners;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class SetSpawnerLoreLootFunction extends ConditionalLootFunction {

    protected SetSpawnerLoreLootFunction(LootCondition[] conditions) {
        super(conditions);
    }

    @Override
    protected ItemStack process(ItemStack stack, LootContext context) {
        CompoundTag beData = stack.getSubTag("BlockEntityTag");
        if (beData != null) {

            if (beData.contains("SpawnData", NbtType.COMPOUND)) {
                CompoundTag spawnData = beData.getCompound("SpawnData");
                if (spawnData.contains("id", NbtType.STRING)) {
                    ListTag lore = getLoreForMerge(stack);
                    Text entityID = new LiteralText(spawnData.getString("id")).formatted(Formatting.DARK_GRAY);
                    lore.add(StringTag.of(Text.Serializer.toJson(entityID)));
                }
            }

            // this should only ever be enabled if a config option is set
            /*
            if(beData.contains("SpawnPotentials", NbtType.LIST)) {
                ListTag spawnPotentials = beData.getList("SpawnPotentials", NbtType.COMPOUND);
                if (!spawnPotentials.isEmpty()) {
                    Style textStyle = Style.EMPTY.withColor(Formatting.DARK_GRAY);
                    ListTag lore = getLoreForMerge(stack);
                    lore.add(StringTag.of(Text.Serializer.toJson(new LiteralText("Potentials:").setStyle(textStyle))));
                    for (int i = 0; i < spawnPotentials.size(); ++i) {
                        MobSpawnerEntry entry = new MobSpawnerEntry(spawnPotentials.getCompound(i));
                        Text loreEntry = new LiteralText("  ").setStyle(textStyle).append(entry.getEntityTag().getString("id"));
                        lore.add(StringTag.of(Text.Serializer.toJson(loreEntry)));
                    }
                }
            }
            */
        }
        return stack;
    }

    @Override
    public LootFunctionType getType() {
        return SilkySpawners.SET_SPAWNER_LORE_LOOT_FUNCTION;
    }

    private ListTag getLoreForMerge(ItemStack stack) {
        CompoundTag display = stack.getOrCreateSubTag("display");
        if (display.contains("Lore", NbtType.LIST)) {
            return display.getList("Lore", NbtType.STRING);
        } else {
            ListTag listTag = new ListTag();
            display.put("Lore", listTag);
            return listTag;
        }
    }

    public static class Serializer extends ConditionalLootFunction.Serializer<SetSpawnerLoreLootFunction> {

        @Override
        public SetSpawnerLoreLootFunction fromJson(JsonObject json, JsonDeserializationContext context, LootCondition[] conditions) {
            return new SetSpawnerLoreLootFunction(conditions);
        }
    }
}
