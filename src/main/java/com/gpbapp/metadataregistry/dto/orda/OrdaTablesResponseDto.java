package com.gpbapp.metadataregistry.dto.orda;

import java.util.List;

public class OrdaTablesResponseDto {

    private OrdaPagingDto paging;
    private List<OrdaTableDto> data;


    public List<OrdaTableDto> getData() {
        return data;
    }

    public void setData(List<OrdaTableDto> data) {
        this.data = data;
    }

    public OrdaPagingDto getPaging() {
        return paging;
    }

    public void setPaging(OrdaPagingDto paging) {
        this.paging = paging;
    }

    public OrdaTablesResponseDto(List<OrdaTableDto> data, OrdaPagingDto paging) {
        this.data = data;
        this.paging = paging;
    }



    public OrdaTablesResponseDto() {
    }
}
