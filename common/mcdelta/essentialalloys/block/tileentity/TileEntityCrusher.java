package mcdelta.essentialalloys.block.tileentity;

import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.NBTTags;
import mcdelta.core.assets.world.Position;
import mcdelta.core.block.tileentity.TileEntityDelta;
import mcdelta.essentialalloys.block.BlockCrusher;
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
     public int     extend        = 0;
     public int     extendTotal   = 0;
     public int     cooldown      = 0;
     public boolean checkForPower = false;
     public int     power;
     
     
     
     
     public void updateEntity ()
     {
          super.updateEntity();
          
          if (getBlockType() == null)
          {
               return;
          }
          
          updateExtensions();
          updatePower();
     }
     
     
     
     
     private void updatePower ()
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
                              
                              if (fuel != null && tile.isItemFuel(fuel))
                              {
                                   tile.decrStackSize(1, 1);
                                   tile.furnaceBurnTime = tile.getItemBurnTime(fuel);
                                   BlockFurnace.updateFurnaceBlockState(true, this.worldObj, furnace.x, furnace.y, furnace.z);
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
                    PacketDispatcher.sendPacketToAllAround(pos.x, pos.y, pos.z, 20, worldObj.provider.dimensionId, Assets.populatePacket(new PacketCrusherPower(power, pos.x, pos.y, pos.z)));
               }
          }
          
          checkForPower = false;
     }
     
     
     
     
     private void updateExtensions ()
     {
          if (cooldown != 0)
          {
               cooldown--;
          }
          
          if (extend == 0 && extendTotal != 0)
          {
               extendTotal = 0;
          }
          
          if (extend != 0)
          {
               if (extendTotal == 0)
               {
                    extendTotal = extend;
               }
               
               Position pos = new Position(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
               
               Assets.updateBlock(pos.move(Assets.getFacing(pos.getMeta())));
               
               if (extend < 0)
               {
                    extend++;
                    
                    if (extend == 0)
                    {
                         ((BlockCrusher) BlockEA.crusher).finishRetraction(new Position(this.worldObj, this.xCoord, this.yCoord, this.zCoord));
                         cooldown = 5;
                         return;
                    }
               }
               
               else
               {
                    extend--;
                    
                    if (extend == 0)
                    {
                         ((BlockCrusher) BlockEA.crusher).finishExtension(new Position(this.worldObj, this.xCoord, this.yCoord, this.zCoord));
                         cooldown = 5;
                         return;
                    }
               }
          }
     }
     
     
     
     
     public void writeToNBT (NBTTagCompound nbtTag)
     {
          super.writeToNBT(nbtTag);
          
          nbtTag.setInteger(NBTTags.CRUSHER_STATUS, this.extend);
          nbtTag.setInteger(NBTTags.CRUSHER_STATUS_TOTAL, this.extendTotal);
          nbtTag.setInteger(NBTTags.CRUSHER_COOLDOWN, this.cooldown);
          nbtTag.setInteger(NBTTags.CRUSHER_POWER, this.power);
          nbtTag.setBoolean(NBTTags.CRUSHER_CHECK, this.checkForPower);
     }
     
     
     
     
     public void readFromNBT (NBTTagCompound nbtTag)
     {
          super.readFromNBT(nbtTag);
          
          this.extend = nbtTag.getInteger(NBTTags.CRUSHER_STATUS);
          this.extendTotal = nbtTag.getInteger(NBTTags.CRUSHER_STATUS_TOTAL);
          this.cooldown = nbtTag.getInteger(NBTTags.CRUSHER_COOLDOWN);
          this.power = nbtTag.getInteger(NBTTags.CRUSHER_POWER);
          this.checkForPower = nbtTag.getBoolean(NBTTags.CRUSHER_CHECK);
     }
}
