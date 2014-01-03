package mcdelta.essentialalloys.block.tileentity;

import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.Position;
import mcdelta.core.block.tileentity.TileEntityDelta;
import mcdelta.essentialalloys.EAContent;
import mcdelta.essentialalloys.data.NBTTags;
import mcdelta.essentialalloys.network.PacketCrusherPower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TileEntityCrusher extends TileEntityDelta
{
<<<<<<< HEAD
     public int     extend        = 0;
     public int     extendTotal   = 0;
     public int     cooldown      = 0;
     public boolean checkForPower = false;
     public int     power;
     
     
     
     
     @Override
     public void updateEntity ()
     {
          super.updateEntity();
          
          if (getBlockType() == null)
          {
               return;
          }
          this.updateExtensions();
          this.updatePower();
     }
     
     
     
     
     private void updatePower ()
     {
          if (this.power != 0)
          {
               this.checkForPower = true;
          }
          if (this.power == 98)
          {
               Assets.updateBlock(getPosition());
          }
          if (this.power >= 100)
          {
               this.power = 100;
               this.checkForPower = false;
               
               EAContent.crusher.doThings(getPosition());
          }
          if (this.checkForPower)
          {
               final Position pos = getPosition();
               
               final List<Position> idleFurnaces = Assets.checkAdjacentBlocks(Block.furnaceIdle, pos);
               final List<Position> burningFurnaces = Assets.checkAdjacentBlocks(Block.furnaceBurning, pos);
               
               if (this.power == 0)
               {
                    if (!idleFurnaces.isEmpty())
                    {
                         for (final Position furnace : idleFurnaces)
                         {
                              final TileEntityFurnace tile = (TileEntityFurnace) furnace.getTile();
                              
                              final ItemStack fuel = tile.getStackInSlot(1);
                              
                              if (fuel != null && TileEntityFurnace.isItemFuel(fuel))
                              {
                                   tile.decrStackSize(1, 1);
                                   tile.furnaceBurnTime = TileEntityFurnace.getItemBurnTime(fuel);
                                   BlockFurnace.updateFurnaceBlockState(true, worldObj, furnace.x, furnace.y, furnace.z);
                                   tile.onInventoryChanged();
                                   
                                   this.power++;
                              }
                         }
                    }
               }
               if (!burningFurnaces.isEmpty())
               {
                    for (final Position furnace : burningFurnaces)
                    {
                         this.power++;
                         
                         final TileEntityFurnace tile = (TileEntityFurnace) furnace.getTile();
                         tile.furnaceCookTime = 0;
                    }
               }
               EAContent.crusher.doThings(getPosition());
               
               if (Assets.isServer())
               {
                    PacketDispatcher.sendPacketToAllAround(pos.x, pos.y, pos.z, 20, worldObj.provider.dimensionId, Assets.populatePacket(new PacketCrusherPower(this.power, pos.x, pos.y, pos.z)));
               }
          }
          this.checkForPower = false;
     }
     
     
     
     
     private void updateExtensions ()
     {
          if (this.cooldown != 0)
          {
               this.cooldown--;
          }
          if (this.extend == 0 && this.extendTotal != 0)
          {
               this.extendTotal = 0;
          }
          if (this.extend != 0)
          {
               if (this.extendTotal == 0)
               {
                    this.extendTotal = this.extend;
               }
               final Position pos = new Position(worldObj, xCoord, yCoord, zCoord);
               
               Assets.updateBlock(pos.move(Assets.getFacing(pos.getMeta())));
               
               if (this.extend < 0)
               {
                    this.extend++;
                    
                    if (this.extend == 0)
                    {
                         EAContent.crusher.finishRetraction(new Position(worldObj, xCoord, yCoord, zCoord));
                         this.cooldown = 5;
                         return;
                    }
               }
               else
               {
                    this.extend--;
                    
                    if (this.extend == 0)
                    {
                         EAContent.crusher.finishExtension(new Position(worldObj, xCoord, yCoord, zCoord));
                         this.cooldown = 5;
                         return;
=======
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
            final Position pos = getPosition();

            final List<Position> idleFurnaces = Assets.checkAdjacentBlocks(Block.furnaceIdle, pos);
            final List<Position> burningFurnaces = Assets.checkAdjacentBlocks(Block.furnaceBurning, pos);

            if (power == 0)
            {
                if (!idleFurnaces.isEmpty())
                {
                    for (final Position furnace : idleFurnaces)
                    {
                        final TileEntityFurnace tile = (TileEntityFurnace) furnace.getTile();

                        final ItemStack fuel = tile.getStackInSlot(1);

                        if ((fuel != null) && TileEntityFurnace.isItemFuel(fuel))
                        {
                            tile.decrStackSize(1, 1);
                            tile.furnaceBurnTime = TileEntityFurnace.getItemBurnTime(fuel);
                            BlockFurnace.updateFurnaceBlockState(true, worldObj, furnace.x, furnace.y, furnace.z);
                            tile.onInventoryChanged();

                            power++;
                        }
>>>>>>> 5ad34493d08f0ba6f35302fdcfc63d2da091fe08
                    }
                }
            }
            if (!burningFurnaces.isEmpty())
            {
                for (final Position furnace : burningFurnaces)
                {
                    power++;

                    final TileEntityFurnace tile = (TileEntityFurnace) furnace.getTile();
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
            final Position pos = new Position(worldObj, xCoord, yCoord, zCoord);

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
            } else
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
    public void writeToNBT(final NBTTagCompound nbtTag)
    {
        super.writeToNBT(nbtTag);

        nbtTag.setInteger(NBTTags.CRUSHER_STATUS, extend);
        nbtTag.setInteger(NBTTags.CRUSHER_STATUS_TOTAL, extendTotal);
        nbtTag.setInteger(NBTTags.CRUSHER_COOLDOWN, cooldown);
        nbtTag.setInteger(NBTTags.CRUSHER_POWER, power);
        nbtTag.setBoolean(NBTTags.CRUSHER_CHECK, checkForPower);
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbtTag)
    {
        super.readFromNBT(nbtTag);

        extend = nbtTag.getInteger(NBTTags.CRUSHER_STATUS);
        extendTotal = nbtTag.getInteger(NBTTags.CRUSHER_STATUS_TOTAL);
        cooldown = nbtTag.getInteger(NBTTags.CRUSHER_COOLDOWN);
        power = nbtTag.getInteger(NBTTags.CRUSHER_POWER);
        checkForPower = nbtTag.getBoolean(NBTTags.CRUSHER_CHECK);
    }
}
