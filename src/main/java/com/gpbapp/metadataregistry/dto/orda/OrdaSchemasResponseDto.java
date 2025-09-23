package com.gpbapp.metadataregistry.dto.orda;

import java.util.List;

public class OrdaSchemasResponseDto {
    private List<OrdaDatabaseSchemaDto> data;

    public OrdaPagingDto getPaging() {
        return paging;
    }

    public void setPaging(OrdaPagingDto paging) {
        this.paging = paging;
    }

    private OrdaPagingDto paging;



    public OrdaSchemasResponseDto() {
    }

    public List<OrdaDatabaseSchemaDto> getData() {
        return data;
    }

    public void setData(List<OrdaDatabaseSchemaDto> data) {
        this.data = data;
    }
}
