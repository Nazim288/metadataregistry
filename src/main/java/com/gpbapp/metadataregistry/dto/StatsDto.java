package com.gpbapp.metadataregistry.dto;

public class StatsDto {
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPercentileRank() {
        return percentileRank;
    }

    public void setPercentileRank(int percentileRank) {
        this.percentileRank = percentileRank;
    }

    private int count;
    private int percentileRank;

    public StatsDto() {
    }

    public StatsDto(int count, int percentileRank) {
        this.count = count;
        this.percentileRank = percentileRank;
    }
}
