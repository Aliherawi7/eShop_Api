package com.eshop.resources;

import com.eshop.dto.SummeryDTO;
import com.eshop.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsResources {
    private final StatisticsService statisticsService;


    public StatisticsResources(StatisticsService statisticsService){
        this.statisticsService = statisticsService;
    }

    @GetMapping("/models")
    public ResponseEntity<SummeryDTO> getSummery(){
        return ResponseEntity.ok().body(statisticsService.getModelSummery());
    }
}
