package com.gpbapp.metadataregistry.dto.orda;

public class OrdaPagingDto {
    /**
     * Курсор на следующую страницу
     */
    private String after;

    /**
     * Курсор на предыдущую страницу
     */
    private String before;

    /**
     * Смещение (offset) для текущей страницы
     */
    private int offset;

    /**
     * Общее количество элементов
     */
    private int total;

    public OrdaPagingDto() {
    }

    public OrdaPagingDto(String after, String before, int offset, int total) {
        this.after = after;
        this.before = before;
        this.offset = offset;
        this.total = total;
    }

    public String getAfter() {
        return after;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public String getBefore() {
        return before;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
