package com.eshop.dto;

import java.util.List;

public class PageContainerDTO {
    private long recordCount;
    private List<ProductDTO> records;
    public PageContainerDTO(long recordCount, List<ProductDTO> records){
        this.recordCount = recordCount;
        this.records = records;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public List<ProductDTO> getRecords() {
        return records;
    }

    public void setRecords(List<ProductDTO> records) {
        this.records = records;
    }
}
