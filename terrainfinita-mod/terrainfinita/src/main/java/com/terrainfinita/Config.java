package com.terrainfinita;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    private static final ForgeConfigSpec.Builder B = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue INTERVAL_TICKS = B
            .comment("A cada quantos ticks a Terra Infinita age (20 ticks = 1 segundo)")
            .defineInRange("intervaloTicks", 20, 1, 1200);

    public static final ForgeConfigSpec.BooleanValue INSTANT_GROWTH = B
            .comment("Se true, a planta cresce ate o maximo na hora")
            .define("crescimentoInstantaneo", true);

    public static final ForgeConfigSpec SPEC = B.build();
}
