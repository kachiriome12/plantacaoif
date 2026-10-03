package com.terrainfinita;

import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(TerraInfinita.MODID)
public class TerraInfinita {
    public static final String MODID = "terrainfinita";

    public TerraInfinita() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModRegistry.BLOCKS.register(bus);
        ModRegistry.ITEMS.register(bus);
        ModRegistry.BLOCK_ENTITIES.register(bus);
        ModRegistry.TABS.register(bus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
}
