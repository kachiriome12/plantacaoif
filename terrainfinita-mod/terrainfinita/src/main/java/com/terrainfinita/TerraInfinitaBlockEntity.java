package com.terrainfinita;

import java.util.Collections;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class TerraInfinitaBlockEntity extends BlockEntity {
    private int timer = 0;

    public TerraInfinitaBlockEntity(BlockPos pos, BlockState state) {
        super(ModRegistry.TERRA_BE.get(), pos, state);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, TerraInfinitaBlockEntity be) {
        if (!(level instanceof ServerLevel server)) return;
        if (++be.timer < Config.INTERVAL_TICKS.get()) return;
        be.timer = 0;

        BlockPos cropPos = pos.above();
        BlockState crop = server.getBlockState(cropPos);
        Block cropBlock = crop.getBlock();

        // so age em plantas (trigo, cenoura, Mystical Agriculture, nether wart, cana...)
        if (!(cropBlock instanceof BushBlock || cropBlock instanceof SugarCaneBlock || cropBlock instanceof CactusBlock)) {
            return;
        }
        IntegerProperty age = findAge(crop);
        if (age == null) return;

        int max = Collections.max(age.getPossibleValues());
        BlockState mature = crop.setValue(age, max);

        // 1) crescimento instantaneo
        if (Config.INSTANT_GROWTH.get() && crop.getValue(age) < max) {
            server.setBlock(cropPos, mature, 2);
        }

        // 2) com inventario (hopper/bau) embaixo: gera os drops da planta madura, infinitamente
        IItemHandler inv = findInventory(server, pos.below());
        if (inv == null) return;

        List<ItemStack> drops = Block.getDrops(mature, server, cropPos, null);
        if (drops.isEmpty()) return;

        // so produz se tudo couber (nada se perde)
        for (ItemStack s : drops) {
            if (!ItemHandlerHelper.insertItem(inv, s.copy(), true).isEmpty()) return;
        }
        for (ItemStack s : drops) {
            ItemHandlerHelper.insertItem(inv, s.copy(), false);
        }
    }

    private static IntegerProperty findAge(BlockState state) {
        for (Property<?> p : state.getProperties()) {
            if (p instanceof IntegerProperty ip && ip.getName().equals("age")) {
                return ip;
            }
        }
        return null;
    }

    private static IItemHandler findInventory(ServerLevel level, BlockPos below) {
        BlockEntity be = level.getBlockEntity(below);
        if (be == null) return null;
        return be.getCapability(ForgeCapabilities.ITEM_HANDLER, Direction.UP).orElse(null);
    }
}
