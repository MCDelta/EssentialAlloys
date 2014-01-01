package mcdelta.essentialalloys.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mcdelta.core.assets.world.Position;
import mcdelta.core.network.PacketDelta;
import mcdelta.essentialalloys.block.tileentity.TileEntityCrusher;
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

    public PacketCrusherExtend()
    {
        super(1);
    }

    public PacketCrusherExtend(int i1, int i2, int i3, int i4)
    {
        super(1);
        extend = i1;
        x = i2;
        y = i3;
        z = i4;
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        data.writeInt(extend);
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
    }

    @Override
    public void readData(DataInputStream data) throws IOException
    {
        extend = data.readInt();
        x = data.readInt();
        y = data.readInt();
        z = data.readInt();
    }

    @Override
    public void execute(INetworkManager manager, Player playerParam)
    {
        EntityPlayer player = (EntityPlayer) playerParam;

        World world = player.worldObj;

        Position pos = new Position(world, x, y, z);

        if (pos.getTile() instanceof TileEntityCrusher)
        {
            TileEntityCrusher tile = (TileEntityCrusher) pos.getTile();

            tile.extend = extend;
        }
    }
}
