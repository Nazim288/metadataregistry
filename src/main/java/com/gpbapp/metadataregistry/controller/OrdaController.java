package com.gpbapp.metadataregistry.controller;

import com.gpbapp.metadataregistry.dto.*;
import com.gpbapp.metadataregistry.service.OrdaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orda")
public class OrdaController {
    private final OrdaService ordaService;
    public OrdaController(OrdaService ordaService) {
        this.ordaService = ordaService;
    }

    @PostMapping("/databases")
    public String createDatabase(@RequestBody OrdaBaseCreateDto request) {
        return ordaService.createDatabase(request);
    }

    @PostMapping("/schemas")
    public String createSchema(@RequestBody OrdaSchemaCreateDTO request) {
        return ordaService.createSchema(request);
    }

    @PostMapping("/tables")
    public String createTable(@RequestBody OrdaTableCreateDTO request) {
        return ordaService.createTable(request);
    }

    @PutMapping("/tables")
    public String updateTable(@RequestBody OrdaTableCreateDTO request) {
        return ordaService.createOrUpdateTable(request);
    }

    @PostMapping("/orda-services")
    public  String createServices(OrdaServiceCreateDto dto) {
        return ordaService.createService(dto);
    }


    @GetMapping("/orda-services")
    public  List<DatabaseDto> getAllOrdaServices() {
        return ordaService.getServices();
    }
    @GetMapping("/databases")
    public  List<OrdaTableDto> getAllDatabases() {
        return ordaService.getDatabase();
    }
    @GetMapping("/schemas")
    public  List<OrdaDatabaseSchemaDto> getAllSchemas() {
        return ordaService.getSchema();
    }
    @GetMapping("/tables")
    public  List<OrdaTableDto> getAllTables() {
        return ordaService.getTable();
    }
}
