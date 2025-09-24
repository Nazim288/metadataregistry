package com.gpbapp.metadataregistry.controller;

import com.gpbapp.metadataregistry.enums.OrdaBaseType;
import com.gpbapp.metadataregistry.service.MetaSyncExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sync")
public class SyncController {
    public SyncController(MetaSyncExecutor metaSyncService) {
        this.metaSyncService = metaSyncService;
    }

    private final MetaSyncExecutor metaSyncService;

    @PostMapping("/postgres/start")
    public ResponseEntity<String> syncPostgresData(@RequestParam String source) {
        return runSync(source, OrdaBaseType.POSTGRES);
    }

    @PostMapping("/mssql/start")
    public ResponseEntity<String> syncMssqlData(@RequestParam String source) {
        return runSync(source, OrdaBaseType.MSSQL);
    }

    @PostMapping("/oracle/start")
    public ResponseEntity<String> syncOracleData(@RequestParam String source) {
        return runSync(source, OrdaBaseType.ORACLE);
    }

    private ResponseEntity<String> runSync(String source, OrdaBaseType type) {
        try {
            return metaSyncService.sync(source, type);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ошибка при синхронизации данных с ордой: " + e.getMessage() + e.getCause());
        }
    }

}
