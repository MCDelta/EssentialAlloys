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

public class PacketCrusherExtend extends PacketDelta
{
     private int extend;
     private int x;
     private int y;
     private int z;
     
     
     
     
     public PacketCrusherExtend ()
     {
          super(1);
     }
     
     
     
     
     public PacketCrusherExtend (final int extend, final int x, final int y, final int z)
     {
          super(1);
          this.extend = extend;
          this.x = x;
          this.y = y;
          this.z = z;
     }
     
     
     
     
     @Override
     public void writeData (final DataOutputStream data) throws IOException
     {
          data.writeInt(extend);
          data.writeInt(x);
          data.writeInt(y);
          data.writeInt(z);
     }
     
     
     
     
     @Override
     public void readData (final DataInputStream data) throws IOException
     {
          extend = data.readInt();
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
               
               tile.extend = extend;
          }
     }
}
