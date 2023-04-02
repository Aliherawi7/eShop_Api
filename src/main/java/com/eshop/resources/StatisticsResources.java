package com.eshop.resources;

import com.eshop.dto.MonthlyDataDTO;
import com.eshop.dto.SummeryDTO;
import com.eshop.service.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/statistics")
public class StatisticsResources {
    private final StatisticsService statisticsService;


    public StatisticsResources(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/models")
    public ResponseEntity<SummeryDTO> getSummery() {
        return ResponseEntity.ok().body(statisticsService.getModelSummery());
    }

    @GetMapping(value = "/summaryByMonth", params = {"model"})
    public ResponseEntity<?> getSummaryByMonth(@RequestParam String model) {
        model = model.trim().toLowerCase();
        MonthlyDataDTO monthlyDataDTO = null;

        switch (model) {
            case "orders": {
                monthlyDataDTO = statisticsService.totalOrdersMonthly();
                break;
            }
            case "users": {
                monthlyDataDTO = statisticsService.totalJoinedUserMonthly();
                break;
            }
            case "products": {
                monthlyDataDTO = statisticsService.totalAddedProductsMonthly();
                break;
            }
            default: {
                return new ResponseEntity<>("not content!", HttpStatus.NO_CONTENT);
            }
        }
        return ResponseEntity.ok().body(monthlyDataDTO);
    }
}
