package com.thevoxelbox.voxelsniper.brush;

import java.util.ArrayList;

import com.thevoxelbox.voxelsniper.Message;
import com.thevoxelbox.voxelsniper.SnipeData;

import org.bukkit.Material;
import org.bukkit.block.Block;

/**
 * @author MikeMatrix
 */
public class BlockResetBrush extends Brush
{
    private static int timesUsed = 0;
    private static final ArrayList<Material> DENIED_UPDATES = new ArrayList<Material>();

    static
    {
        BlockResetBrush.DENIED_UPDATES.add(Material.SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.SIGN_POST);
        BlockResetBrush.DENIED_UPDATES.add(Material.WALL_SIGN);
        BlockResetBrush.DENIED_UPDATES.add(Material.CHEST);
        BlockResetBrush.DENIED_UPDATES.add(Material.FURNACE);
        BlockResetBrush.DENIED_UPDATES.add(Material.BURNING_FURNACE);
        BlockResetBrush.DENIED_UPDATES.add(Material.REDSTONE_TORCH_OFF);
        BlockResetBrush.DENIED_UPDATES.add(Material.REDSTONE_TORCH_ON);
        BlockResetBrush.DENIED_UPDATES.add(Material.REDSTONE_WIRE);
        BlockResetBrush.DENIED_UPDATES.add(Material.DIODE_BLOCK_OFF);
        BlockResetBrush.DENIED_UPDATES.add(Material.DIODE_BLOCK_ON);
        BlockResetBrush.DENIED_UPDATES.add(Material.WOODEN_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(Material.WOOD_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(Material.IRON_DOOR);
        BlockResetBrush.DENIED_UPDATES.add(Material.IRON_DOOR_BLOCK);
        BlockResetBrush.DENIED_UPDATES.add(Material.FENCE_GATE);
    }

    /**
     *
     */
    public BlockResetBrush()
    {
        this.setName("Block Reset Brush");
    }

    private void applyBrush(final SnipeData v)
    {
        for (int z = -v.getBrushSize(); z <= v.getBrushSize(); z++)
        {
            for (int x = -v.getBrushSize(); x <= v.getBrushSize(); x++)
            {
                for (int y = -v.getBrushSize(); y <= v.getBrushSize(); y++)
                {
                    final Block block = this.getWorld().getBlockAt(this.getBlockPositionX() + x, this.getBlockPositionY() + y, this.getBlockPositionZ() + z);
                    if (BlockResetBrush.DENIED_UPDATES.contains(block.getType()))
                    {
                        continue;
                    }

                    final byte oldData = block.getData();
                    block.setTypeIdAndData(block.getTypeId(), (byte) ((block.getData() + 1) & 0xf), true);
                    block.setTypeIdAndData(block.getTypeId(), oldData, true);
                }
            }
        }
    }

    @Override
    protected final void arrow(final SnipeData v)
    {
        applyBrush(v);
    }

    @Override
    protected final void powder(final SnipeData v)
    {
        applyBrush(v);
    }

    @Override
    public final void info(final Message vm)
    {
        vm.brushName(this.getName());
    }

    @Override
    public final int getTimesUsed()
    {
        return BlockResetBrush.timesUsed;
    }

    @Override
    public final void setTimesUsed(final int tUsed)
    {
        BlockResetBrush.timesUsed = tUsed;
    }
}
