package org.opendcs.decodes.exec;

import java.nio.ByteBuffer;
import java.time.ZonedDateTime;

import org.opendcs.decodes.exec.DataBuilder.Position;

/**
 * Storage for the overal statemachine that is an executing decodes script.
 * DecodesExecutionContext
 */
public class DecodesExecutionContext
{
    private String currentLabel;
    private final ByteBuffer data;
    private final DataBuilder<?> emitter;

    public DecodesExecutionContext(ByteBuffer data, DataBuilder<?> emitter)
    {
        this.data = data;
        this.emitter = emitter;
    }

    /**
     * Advance the current position by delta amount.
     * 
     * @param delta number of bytes to move, can be negative to go backwards.
     * @return the new absolute position
     */
    public int advancePosition(int delta)
    {
        int current = data.position();
        data.position(current + delta);
        return data.position();
    }

    public int dataRemaining()
    {
        return data.remaining();
    }

    public boolean moreData()
    {
        return data.hasRemaining();
    }

    /**
     * Retrieve the current position in the byte buffer.
     * @return
     */
    public  int currentPosition()
    {
        return getMessageData().position();
    }

    public String currentLabel()
    {
        return currentLabel;
    }

    public void currentLabel(String label)
    {
        this.currentLabel = label;
    }

    public void emitData(int sensor, ZonedDateTime time, Object value, Position fieldPosition)
    {
        emitter.addValue(sensor, time, value, fieldPosition);
    }
    
    /**
     * Retrive the byte buffer view to retrieve data.
     * @return
     */
    public ByteBuffer getMessageData()
    {
        return this.data;
    }
}
