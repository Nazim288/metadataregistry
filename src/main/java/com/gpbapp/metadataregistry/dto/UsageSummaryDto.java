package com.gpbapp.metadataregistry.dto;

public class UsageSummaryDto {
    public StatsDto getDailyStats() {
        return dailyStats;
    }

    public void setDailyStats(StatsDto dailyStats) {
        this.dailyStats = dailyStats;
    }

    public StatsDto getWeeklyStats() {
        return weeklyStats;
    }

    public void setWeeklyStats(StatsDto weeklyStats) {
        this.weeklyStats = weeklyStats;
    }

    public StatsDto getMonthlyStats() {
        return monthlyStats;
    }

    public void setMonthlyStats(StatsDto monthlyStats) {
        this.monthlyStats = monthlyStats;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private StatsDto dailyStats;
    private StatsDto weeklyStats;
    private StatsDto monthlyStats;
    private String date;

    public UsageSummaryDto(StatsDto dailyStats, StatsDto weeklyStats, StatsDto monthlyStats, String date) {
        this.dailyStats = dailyStats;
        this.weeklyStats = weeklyStats;
        this.monthlyStats = monthlyStats;
        this.date = date;
    }

    public UsageSummaryDto() {
    }
}
