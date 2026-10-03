package com.terrainfinita;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRegistry {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TerraInfinita.MODID);
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TerraInfinita.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TerraInfinita.MODID);
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TerraInfinita.MODID);

    public static final RegistryObject<Block> TERRA_INFINITA = BLOCKS.register("terra_infinita",
            () -> new TerraInfinitaBlock(BlockBehaviour.Properties.copy(Blocks.DIRT)
                    .strength(0.6F).sound(SoundType.GRAVEL)));

    public static final RegistryObject<Item> TERRA_INFINITA_ITEM = ITEMS.register("terra_infinita",
            () -> new BlockItem(TERRA_INFINITA.get(), new Item.Properties()));

    public static final RegistryObject<BlockEntityType<TerraInfinitaBlockEntity>> TERRA_BE =
            BLOCK_ENTITIES.register("terra_infinita",
                    () -> BlockEntityType.Builder.of(TerraInfinitaBlockEntity::new, TERRA_INFINITA.get()).build(null));

    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("terrainfinita_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.terrainfinita"))
                    .icon(() -> new ItemStack(TERRA_INFINITA_ITEM.get()))
                    .displayItems((params, out) -> out.accept(TERRA_INFINITA_ITEM.get()))
                    .build());
}
