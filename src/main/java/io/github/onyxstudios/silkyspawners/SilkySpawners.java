package io.github.onyxstudios.silkyspawners;

import io.github.onyxstudios.silkyspawners.loot.SetSpawnerLoreLootFunction;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplier;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SilkySpawners implements ModInitializer {

    public static final String MODID = "silky_spawners";
    public static final Tag<Item> ALLOWED_TOOLS = TagRegistry.item(new Identifier(MODID, "allowed_tools"));

    public static LootFunctionType SET_SPAWNER_LORE_LOOT_FUNCTION;

    @Override
    public void onInitialize() {
        SET_SPAWNER_LORE_LOOT_FUNCTION = Registry.register(Registry.LOOT_FUNCTION_TYPE, new Identifier(MODID, "set_spawner_lore"), new LootFunctionType(new SetSpawnerLoreLootFunction.Serializer()));
        Identifier spawnerLootTable = new Identifier("blocks/spawner");
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, identifier, fabricLootSupplierBuilder, lootTableSetter) -> {
            if (spawnerLootTable.equals(identifier)) {
                FabricLootSupplier additionalLoot = (FabricLootSupplier) lootManager.getTable(new Identifier(MODID, "blocks/spawner"));
                fabricLootSupplierBuilder.withPools(additionalLoot.getPools());
            }
        });
    }
}
