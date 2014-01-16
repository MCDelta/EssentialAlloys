package mcdelta.essentialalloys.block.tileentity;

import java.util.List;

import mcdelta.core.DeltaCore;
import mcdelta.core.assets.Assets;
import mcdelta.core.block.tileentity.TileEntityDelta;
import mcdelta.essentialalloys.EAContent;
import mcdelta.essentialalloys.data.EANBTTags;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;

public class TileHotPlate extends TileEntityDelta implements IInventory, ISidedInventory
{
     public ItemStack[]   smeltingStacks     = new ItemStack[10];
     public int[]         smeltingTimes      = new int[5];
     public int[]         smeltingTotalTimes = new int[5];
     private int          tick               = 0;
     public AxisAlignedBB blastFurnaceBB;
     public int[]         dimensions;
     
     
     
     
     @Override
     public void updateEntity ()
     {
          super.updateEntity();
          tick++;
          
          int fuel = 0;
          
          if (dimensions != null)
          {
               fuel = EAContent.hotPlate.getFuel(getPosition(), dimensions);
               
               for (int i = 0; i < 5; i++)
               {
                    if (smeltingTimes[i] > 0 && fuel >= 1)
                    {
                         smeltingTimes[i]--;
                    }
                    
                    if (smeltingTimes[i] == 1)
                    {
                         smeltingStacks[i] = null;
                         ItemStack stack = new ItemStack(EAContent.nuggetSteel, smeltingStacks[i + 5] == null ? 0 : smeltingStacks[i + 5].stackSize);
                         stack.stackSize += 3;
                         
                         if (!(stack.stackSize > 64) && Assets.isServer())
                         {
                              setInventorySlotContents(i + 5, stack);
                         }
                         
                         smeltingTotalTimes[i] = 0;
                         
                         if (DeltaCore.rand.nextFloat() > 0.75F)
                         {
                              EAContent.hotPlate.removeRandomFuel(getPosition(), dimensions);
                         }
                         
                         smeltingTimes[i] = 0;
                    }
               }
          }
          
          boolean updated = false;
          
          if (dimensions == null || dimensions.length != 3 || tick % 50 == 0)
          {
               updated = refreshDimensions();
          }
          
          if (updated)
          {
               blastFurnaceBB = getFurnaceBB();
          }
          
          final List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, blastFurnaceBB);
          
          if (!items.isEmpty())
          {
               for (final EntityItem item : items)
               {
                    if (item.getEntityItem().getItem() == Item.ingotIron && item.isBurning())
                    {
                         if (smeltingStacks[0] == null || smeltingStacks[1] == null || smeltingStacks[2] == null || smeltingStacks[3] == null || smeltingStacks[4] == null)
                         {
                              ItemStack stack = item.getEntityItem();
                              
                              for (int i = 0; i < 5 && stack != null; i++)
                              {
                                   ItemStack stack1 = getStackInSlot(i);
                                   
                                   boolean flag = false;
                                   
                                   int max = 1;
                                   if (stack1 == null)
                                   {
                                        if (max >= stack.stackSize)
                                        {
                                             setInventorySlotContents(i, stack);
                                             stack = null;
                                        }
                                        else
                                        {
                                             setInventorySlotContents(i, stack.splitStack(max));
                                        }
                                        flag = true;
                                   }
                                   
                                   else if (areItemStacksEqualItem(stack1, stack))
                                   {
                                        if (max > stack1.stackSize)
                                        {
                                             int l = Math.min(stack.stackSize, max - stack1.stackSize);
                                             stack.stackSize -= l;
                                             stack1.stackSize += l;
                                             flag = l > 0;
                                        }
                                   }
                                   
                                   if (flag && fuel >= 1)
                                   {
                                        smeltingTotalTimes[i] = 2000 - (50 * fuel);
                                        
                                        if (smeltingTotalTimes[i] < 500)
                                        {
                                             smeltingTotalTimes[i] = 500;
                                        }
                                        
                                        smeltingTimes[i] = smeltingTotalTimes[i];
                                        onInventoryChanged();
                                        
                                        for (int i2 = 0; i2 < 50; i2++)
                                        {
                                             final float x = DeltaCore.rand.nextFloat() - 0.5F;
                                             final float y = DeltaCore.rand.nextFloat() - 0.5F;
                                             final float z = DeltaCore.rand.nextFloat() - 0.5F;
                                             worldObj.spawnParticle("smoke", item.posX + x, item.posY + y, item.posZ + z, 0, 0, 0);
                                        }
                                        
                                        for (int i2 = 0; i2 < 5; i2++)
                                        {
                                             final float x = DeltaCore.rand.nextFloat() - 0.5F;
                                             final float y = DeltaCore.rand.nextFloat() - 0.5F;
                                             final float z = DeltaCore.rand.nextFloat() - 0.5F;
                                             worldObj.spawnParticle("flame", item.posX + x, item.posY + y, item.posZ + z, 0, 0, 0);
                                        }
                                   }
                              }
                              
                              item.extinguish();
                              item.setDead();
                         }
                    }
               }
          }
     }
     
     
     
     
     private static boolean areItemStacksEqualItem (ItemStack stack, ItemStack stack1)
     {
          return stack.itemID != stack1.itemID ? false : (stack.getItemDamage() != stack1.getItemDamage() ? false : (stack.stackSize > stack.getMaxStackSize() ? false : ItemStack.areItemStackTagsEqual(stack, stack1)));
     }
     
     
     
     
     public AxisAlignedBB getFurnaceBB ()
     {
          final AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(xCoord - dimensions[0] / 2, yCoord, zCoord - dimensions[2] / 2, xCoord + dimensions[0] / 2 + 1, yCoord + dimensions[1] + 1, zCoord + dimensions[2] / 2 + 1);
          return axis;
     }
     
     
     
     
     public boolean refreshDimensions ()
     {
          final int[] newDim = EAContent.hotPlate.getDimensions(getPosition());
          
          if (dimensions == null || dimensions.length != 3 || dimensions[0] != newDim[0] || dimensions[1] != newDim[1] || dimensions[2] != newDim[2])
          {
               dimensions = newDim;
               return true;
          }
          
          return false;
     }
     
     
     
     
     @Override
     public void writeToNBT (final NBTTagCompound nbtTag)
     {
          super.writeToNBT(nbtTag);
          nbtTag.setInteger(EANBTTags.HOTPLATE_TICK, tick);
          nbtTag.setIntArray(EANBTTags.SMELTING_TIMES, smeltingTimes);
          nbtTag.setIntArray(EANBTTags.SMELTING_TIMES_TOTAL, smeltingTotalTimes);
          
          NBTTagList items = new NBTTagList();
          for (int i = 0; i < smeltingStacks.length; ++i)
          {
               if (smeltingStacks[i] != null)
               {
                    NBTTagCompound stack = new NBTTagCompound();
                    stack.setByte("Slot", (byte) i);
                    smeltingStacks[i].writeToNBT(stack);
                    items.appendTag(stack);
               }
          }
          nbtTag.setTag("Items", items);
          
     }
     
     
     
     
     @Override
     public void readFromNBT (final NBTTagCompound nbtTag)
     {
          super.readFromNBT(nbtTag);
          tick = nbtTag.getInteger(EANBTTags.HOTPLATE_TICK);
          smeltingTimes = nbtTag.getIntArray(EANBTTags.SMELTING_TIMES);
          smeltingTotalTimes = nbtTag.getIntArray(EANBTTags.SMELTING_TIMES_TOTAL);
          
          NBTTagList items = nbtTag.getTagList("Items");
          smeltingStacks = new ItemStack[getSizeInventory()];
          for (int i = 0; i < items.tagCount(); ++i)
          {
               NBTTagCompound nbttagcompound1 = (NBTTagCompound) items.tagAt(i);
               int j = nbttagcompound1.getByte("Slot") & 255;
               
               if (j >= 0 && j < smeltingStacks.length)
               {
                    smeltingStacks[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
               }
          }
     }
     
     
     
     
     @Override
     public int getSizeInventory ()
     {
          return 10;
     }
     
     
     
     
     @Override
     public ItemStack getStackInSlot (final int i)
     {
          return smeltingStacks[i];
     }
     
     
     
     
     @Override
     public ItemStack decrStackSize (int i, int j)
     {
          if (smeltingStacks[i] != null)
          {
               ItemStack itemstack;
               
               if (smeltingStacks[i].stackSize <= j)
               {
                    itemstack = smeltingStacks[i];
                    smeltingStacks[i] = null;
                    onInventoryChanged();
                    return itemstack;
               }
               else
               {
                    itemstack = smeltingStacks[i].splitStack(j);
                    
                    if (smeltingStacks[i].stackSize == 0)
                    {
                         smeltingStacks[i] = null;
                    }
                    
                    onInventoryChanged();
                    return itemstack;
               }
          }
          else
          {
               return null;
          }
     }
     
     
     
     
     @Override
     public ItemStack getStackInSlotOnClosing (int i)
     {
          if (smeltingStacks[i] != null)
          {
               ItemStack itemstack = smeltingStacks[i];
               smeltingStacks[i] = null;
               return itemstack;
          }
          else
          {
               return null;
          }
     }
     
     
     
     
     @Override
     public void setInventorySlotContents (int i, ItemStack stack)
     {
          this.smeltingStacks[i] = stack;
          
          if (stack != null && stack.stackSize > this.getInventoryStackLimit())
          {
               stack.stackSize = this.getInventoryStackLimit();
          }
          
          this.onInventoryChanged();
     }
     
     
     
     
     @Override
     public String getInvName ()
     {
          return "container.hotPlate";
     }
     
     
     
     
     @Override
     public boolean isInvNameLocalized ()
     {
          return false;
     }
     
     
     
     
     @Override
     public int getInventoryStackLimit ()
     {
          return 64;
     }
     
     
     
     
     @Override
     public boolean isUseableByPlayer (EntityPlayer player)
     {
          return this.worldObj.getBlockTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.zCoord + 0.5D) <= 64.0D;
     }
     
     
     
     
     @Override
     public void openChest ()
     {
     }
     
     
     
     
     @Override
     public void closeChest ()
     {
     }
     
     
     
     
     @Override
     public boolean isItemValidForSlot (int i, ItemStack stack)
     {
          return isItemValidForSlot(i, stack, false);
     }
     
     
     
     
     public boolean isItemValidForSlot (int i, ItemStack stack, boolean fromFurnace)
     {
          return fromFurnace && (i <= 4) && stack.getItem() == Item.ingotIron;
     }
     
     
     
     
     @Override
     public int[] getAccessibleSlotsFromSide (int side)
     {
          return new int[]
          { 5, 6, 7, 8, 9 };
     }
     
     
     
     
     @Override
     public boolean canInsertItem (int slot, ItemStack stack, int side)
     {
          return false;
     }
     
     
     
     
     @Override
     public boolean canExtractItem (int slot, ItemStack stack, int side)
     {
          return slot >= 5;
     }
}
