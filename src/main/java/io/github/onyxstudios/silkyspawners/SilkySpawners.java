package io.github.onyxstudios.silkyspawners;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootSupplier;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SilkySpawners implements ModInitializer {

    public static final String MODID = "silky_spawners";
    private static final Logger logger = LogManager.getLogger(MODID);

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
