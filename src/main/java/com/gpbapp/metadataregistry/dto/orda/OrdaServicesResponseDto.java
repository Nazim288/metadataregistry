package com.gpbapp.metadataregistry.dto.orda;


import java.util.List;

public class OrdaServicesResponseDto {
    private List<OrdaServiceDto> data;

    public OrdaPagingDto getPaging() {
        return paging;
    }

    public void setPaging(OrdaPagingDto paging) {
        this.paging = paging;
    }

    private OrdaPagingDto paging;


    public OrdaServicesResponseDto(List<OrdaServiceDto> data) {
        this.data = data;
    }

    public OrdaServicesResponseDto() {
    }

    public List<OrdaServiceDto> getData() {
        return data;
    }

    public void setData(List<OrdaServiceDto> data) {
        this.data = data;
    }
}
