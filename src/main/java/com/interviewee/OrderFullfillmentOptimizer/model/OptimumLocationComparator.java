package com.interviewee.OrderFullfillmentOptimizer.model;

import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.HashMap;

@AllArgsConstructor
public class OptimumLocationComparator implements Comparator<Location>
{
    private HashMap<Location, Long> locationFrequency;
    // Used for sorting in ascending order of
    // roll number
    public int compare(Location a, Location b)
    {
        int frequencyCompare = locationFrequency.get(a).compareTo(locationFrequency.get(b));
        if (frequencyCompare == 0)
            return a.getAllStocksSum().compareTo(b.getAllStocksSum());
        else
            return frequencyCompare;
    }
}