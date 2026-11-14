package org.opendcs.decodes.exec;

import java.time.ZonedDateTime;

public interface DataBuilder<T>
{
    void addValue(int sensor, ZonedDateTime dateTime, Object value, Position dataPosition);

    T build();

    public record Position(int start, int end)
    {
    }
}
