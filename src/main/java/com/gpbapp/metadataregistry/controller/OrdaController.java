package com.gpbapp.metadataregistry.controller;

import com.gpbapp.metadataregistry.dto.orda.*;
import com.gpbapp.metadataregistry.service.OrdaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orda")
public class OrdaController {
    public OrdaController(OrdaService ordaService) {
        this.ordaService = ordaService;
    }

    private final OrdaService ordaService;

    @PostMapping("/databases")
    public OrdaDbDto createDatabase(@RequestBody OrdaBaseCreateDto request) {
        return ordaService.createDatabase(request);
    }

    @PostMapping("/schemas")
    public OrdaDatabaseSchemaDto createSchema(@RequestBody OrdaSchemaCreateDTO request) {
        return ordaService.createSchema(request);
    }

    @PostMapping("/tables")
    public OrdaTableDto createTable(@RequestBody OrdaTableCreateDTO request) {
        return ordaService.createTable(request);
    }

    @PutMapping("/tables")
    public OrdaTableDto updateTable(@RequestBody OrdaTableCreateDTO request) {
        return ordaService.createOrUpdateTable(request);
    }

    @PostMapping("/orda-services")
    public  OrdaServiceDto createServices(OrdaServiceCreateDto dto) {
        return ordaService.createService(dto);
    }


    @GetMapping("/orda-services")
    public  List<OrdaServiceDto> getAllOrdaServices() {
        return ordaService.getServices();
    }

    @GetMapping("/databases")
    public  List<OrdaDbDto> getAllDatabases() {
        List<OrdaDbDto> database = ordaService.getDatabases();
        return database;
    }
    @GetMapping("/schemas")
    public  List<OrdaDatabaseSchemaDto> getAllSchemas() {
        return ordaService.getSchemas();
    }

    @GetMapping("/tables")
    public  List<OrdaTableDto> getAllTables() {
        return ordaService.getTables();
    }


}
