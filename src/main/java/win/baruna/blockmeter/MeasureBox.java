//   // Decompiled by Procyon v0.5.30
// 
package win.baruna.blockmeter;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;

public class MeasureBox
{

    BlockPos blockStart;
    BlockPos blockEnd;
    String dimension;
    DyeColor color;
    boolean finished;
    
    MeasureBox() {};

    public void writePacketBuf(PacketByteBuf buf) {
        buf.writeBlockPos(this.blockStart);
        buf.writeBlockPos(this.blockEnd);
        buf.writeString(dimension);
        buf.writeInt(color.getId());
        buf.writeBoolean(finished);
    }

    public static MeasureBox fromPacketByteBuf(PacketByteBuf attachedData) {
        MeasureBox result = new MeasureBox();
        result.fillFromPacketByteBuf(attachedData);
        return result;
    }
    
    void fillFromPacketByteBuf(PacketByteBuf attachedData) {
        blockStart = attachedData.readBlockPos();
        blockEnd = attachedData.readBlockPos();
        dimension = attachedData.readString();
        color = DyeColor.byId(attachedData.readInt());
        finished = attachedData.readBoolean();
        
        if (Math.abs(blockStart.getX()-blockEnd.getX()) > 1024
        ||  Math.abs(blockStart.getZ()-blockEnd.getZ()) > 1024
        ||  blockStart.getY() < 0 || blockStart.getY() > 256
        ||  blockEnd.getY() < 0   || blockEnd.getY() > 256
        ||  dimension  == null) 
        {
            throw new IllegalArgumentException("invalid buffer");
        }
    }
}