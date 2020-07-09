package io.github.onyxstudios.silkyspawners;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplier;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class SilkySpawners implements ModInitializer {

    public static final String MODID = "silky_spawners";
    public static final Tag<Item> ALLOWED_TOOLS = TagRegistry.item(new Identifier(MODID, "allowed_tools"));

    @Override
    public void onInitialize() {
        Identifier spawnerLootTable = new Identifier("blocks/spawner");
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, identifier, fabricLootSupplierBuilder, lootTableSetter) -> {
            if (spawnerLootTable.equals(identifier)) {
                FabricLootSupplier additionalLoot = (FabricLootSupplier) lootManager.getTable(new Identifier(MODID, "blocks/spawner"));
                fabricLootSupplierBuilder.withPools(additionalLoot.getPools());
            }
        });
    }
}
