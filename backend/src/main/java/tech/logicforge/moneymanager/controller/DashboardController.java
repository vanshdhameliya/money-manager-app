package tech.logicforge.moneymanager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.logicforge.moneymanager.service.impl.DashBoardService;

import java.util.Map;

@RestController
@RequestMapping("dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashBoardService dashBoardService;


//  Retrieves aggregated current balance, total statistics, and a joint history feed.
    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboard() {
        return ResponseEntity.ok(dashBoardService.getDashBoardData());
    }
}
