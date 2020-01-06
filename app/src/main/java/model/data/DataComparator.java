package model.data;

import java.util.Comparator;

public class DataComparator implements Comparator<Data>
{
    @Override
    public int compare(Data o1, Data o2)
    {
        return Integer.compare(o1.getId(), o2.getId());
    }
}
