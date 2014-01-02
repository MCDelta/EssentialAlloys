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

public class PacketCrusherPower extends PacketDelta
{
    private int power;
    private int x;
    private int y;
    private int z;

    public PacketCrusherPower()
    {
        super(0);
    }

    public PacketCrusherPower(int power, int x, int y, int z)
    {
        super(0);
        this.power = power;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void writeData(DataOutputStream data) throws IOException
    {
        data.writeInt(power);
        data.writeInt(x);
        data.writeInt(y);
        data.writeInt(z);
    }

    @Override
    public void readData(DataInputStream data) throws IOException
    {
        power = data.readInt();
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

            tile.power = power;
        }
    }
}