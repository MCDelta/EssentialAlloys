package mcdelta.essentialalloys.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mcdelta.core.assets.world.Position;
import mcdelta.core.network.PacketDelta;
import mcdelta.essentialalloys.block.tileentity.TileCrusher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.Player;

public class PacketCrusherPower extends PacketDelta
{
     private int power;
     private int x;
     private int y;
     private int z;
     
     
     
     
     public PacketCrusherPower ()
     {
          super(0);
     }
     
     
     
     
     public PacketCrusherPower (final int power, final int x, final int y, final int z)
     {
          super(0);
          this.power = power;
          this.x = x;
          this.y = y;
          this.z = z;
     }
     
     
     
     
     @Override
     public void writeData (final DataOutputStream data) throws IOException
     {
          data.writeInt(power);
          data.writeInt(x);
          data.writeInt(y);
          data.writeInt(z);
     }
     
     
     
     
     @Override
     public void readData (final DataInputStream data) throws IOException
     {
          power = data.readInt();
          x = data.readInt();
          y = data.readInt();
          z = data.readInt();
     }
     
     
     
     
     @Override
     public void execute (final INetworkManager manager, final Player playerParam)
     {
          final EntityPlayer player = (EntityPlayer) playerParam;
          
          final World world = player.worldObj;
          
          final Position pos = new Position(world, x, y, z);
          
          if (pos.getTile() instanceof TileCrusher)
          {
               final TileCrusher tile = (TileCrusher) pos.getTile();
               
               tile.power = power;
          }
     }
}
