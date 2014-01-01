package mcdelta.essentialalloys.block.tileentity;

import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.NBTTags;
import mcdelta.core.assets.world.Position;
import mcdelta.core.block.tileentity.TileEntityDelta;
import mcdelta.essentialalloys.block.BlockEA;
import mcdelta.essentialalloys.network.PacketCrusherPower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityCrusher extends TileEntityDelta
{
    public int extend = 0;
    public int extendTotal = 0;
    public int cooldown = 0;
    public boolean checkForPower = false;
    public int power;

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (getBlockType() == null)
        {
            return;
        }

        updateExtensions();
        updatePower();
    }

    private void updatePower()
    {
        if (power != 0)
        {
            checkForPower = true;
        }

        if (power == 98)
        {
            Assets.updateBlock(getPosition());
        }

        if (power >= 100)
        {
            power = 100;
            checkForPower = false;

            BlockEA.crusher.doThings(getPosition());
        }

        if (checkForPower)
        {
            Position pos = getPosition();

            List<Position> idleFurnaces = Assets.checkAdjacentBlocks(Block.furnaceIdle, pos);
            List<Position> burningFurnaces = Assets.checkAdjacentBlocks(Block.furnaceBurning, pos);

            if (power == 0)
            {
                if (!idleFurnaces.isEmpty())
                {
                    for (Position furnace : idleFurnaces)
                    {
                        TileEntityFurnace tile = (TileEntityFurnace) furnace.getTile();

                        ItemStack fuel = tile.getStackInSlot(1);

                        if ((fuel != null) && TileEntityFurnace.isItemFuel(fuel))
                        {
                            tile.decrStackSize(1, 1);
                            tile.furnaceBurnTime = TileEntityFurnace.getItemBurnTime(fuel);
                            BlockFurnace.updateFurnaceBlockState(true, worldObj, furnace.x, furnace.y, furnace.z);
                            tile.onInventoryChanged();

                            power++;
                        }
                    }
                }
            }

            if (!burningFurnaces.isEmpty())
            {
                for (Position furnace : burningFurnaces)
                {
                    power++;

                    TileEntityFurnace tile = (TileEntityFurnace) furnace.getTile();
                    tile.furnaceCookTime = 0;
                }
            }

            BlockEA.crusher.doThings(getPosition());

            if (Assets.isServer())
            {
                PacketDispatcher.sendPacketToAllAround(pos.x, pos.y, pos.z, 20, worldObj.provider.dimensionId,
                        Assets.populatePacket(new PacketCrusherPower(power, pos.x, pos.y, pos.z)));
            }
        }

        checkForPower = false;
    }

    private void updateExtensions()
    {
        if (cooldown != 0)
        {
            cooldown--;
        }

        if ((extend == 0) && (extendTotal != 0))
        {
            extendTotal = 0;
        }

        if (extend != 0)
        {
            if (extendTotal == 0)
            {
                extendTotal = extend;
            }

            Position pos = new Position(worldObj, xCoord, yCoord, zCoord);

            Assets.updateBlock(pos.move(Assets.getFacing(pos.getMeta())));

            if (extend < 0)
            {
                extend++;

                if (extend == 0)
                {
                    BlockEA.crusher.finishRetraction(new Position(worldObj, xCoord, yCoord, zCoord));
                    cooldown = 5;
                    return;
                }
            }

            else
            {
                extend--;

                if (extend == 0)
                {
                    BlockEA.crusher.finishExtension(new Position(worldObj, xCoord, yCoord, zCoord));
                    cooldown = 5;
                    return;
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTag)
    {
        super.writeToNBT(nbtTag);

        nbtTag.setInteger(NBTTags.CRUSHER_STATUS, extend);
        nbtTag.setInteger(NBTTags.CRUSHER_STATUS_TOTAL, extendTotal);
        nbtTag.setInteger(NBTTags.CRUSHER_COOLDOWN, cooldown);
        nbtTag.setInteger(NBTTags.CRUSHER_POWER, power);
        nbtTag.setBoolean(NBTTags.CRUSHER_CHECK, checkForPower);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTag)
    {
        super.readFromNBT(nbtTag);

        extend = nbtTag.getInteger(NBTTags.CRUSHER_STATUS);
        extendTotal = nbtTag.getInteger(NBTTags.CRUSHER_STATUS_TOTAL);
        cooldown = nbtTag.getInteger(NBTTags.CRUSHER_COOLDOWN);
        power = nbtTag.getInteger(NBTTags.CRUSHER_POWER);
        checkForPower = nbtTag.getBoolean(NBTTags.CRUSHER_CHECK);
    }
}
