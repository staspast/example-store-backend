package com.example.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferSearchDto {
    
    private String title;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String orderBy;
    
}
