package com.gpbapp.metadataregistry.controller;

import com.gpbapp.metadataregistry.dto.orda.*;
import com.gpbapp.metadataregistry.service.OrdaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/orda")
public class OrdaController {
    public OrdaController(OrdaService ordaService) {
        this.ordaService = ordaService;
    }

    private final OrdaService ordaService;

    //create
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
    @PostMapping("/orda-services")
    public  OrdaServiceDto createServices(@RequestBody OrdaServiceCreateDto dto) {
        return ordaService.createService(dto);
    }

    // put
    @PutMapping("/tables")
    public OrdaTableDto updateTable(@RequestBody OrdaTableCreateDTO request) {
        return ordaService.updateTable(request);
    }

    @PutMapping("/databases")
    public OrdaDbDto updateDatabase(@RequestBody OrdaBaseCreateDto request) {
        return ordaService.updateDatabase(request);
    }

    @PutMapping("/schemas")
    public OrdaDatabaseSchemaDto updateSchema(@RequestBody OrdaSchemaCreateDTO request) {
        return ordaService.updateSchema(request);
    }

    @PutMapping("/orda-services")
    public  OrdaServiceDto updateServices(@RequestBody OrdaServiceCreateDto dto) {
        return ordaService.updateService(dto);
    }

    // get
    @GetMapping("/orda-services")
    public  OrdaServicesResponseDto getAllOrdaServices() {
        return ordaService.getServices();
    }

    @GetMapping("/databases")
    public  OrdaDatabaseResponseDto getAllDatabases() {
        return ordaService.getDatabases();
    }
    @GetMapping("/schemas")
    public  OrdaSchemasResponseDto getAllSchemas() {
        return ordaService.getSchemas();
    }

    @GetMapping("/tables")
    public  OrdaTablesResponseDto getAllTables() {
        return ordaService.getTables();
    }

    // --- DELETE SERVICES ---
    @DeleteMapping("/orda-services/{name}/soft")
    public ResponseEntity<String> deleteServiceSoft(@PathVariable String name) {
        ordaService.deleteServiceSoft(name);
        return ResponseEntity.ok("Service (soft delete) удалён: " + name);
    }

    @DeleteMapping("/orda-services/{name}/soft-recursive")
    public ResponseEntity<String> deleteServiceSoftRecursive(@PathVariable String name) {
        ordaService.deleteServiceSoftRecursive(name);
        return ResponseEntity.ok("Service (soft recursive delete) удалён: " + name);
    }

    @DeleteMapping("/orda-services/{name}/hard-recursive")
    public ResponseEntity<String> deleteServiceHardRecursive(@PathVariable String name) {
        ordaService.deleteServiceHardRecursive(name);
        return ResponseEntity.ok("Service (hard recursive delete) удалён: " + name);
    }

    // --- DELETE DATABASES ---
    @DeleteMapping("/databases/{fqn}/soft")
    public ResponseEntity<String> deleteDatabaseSoft(@PathVariable String fqn) {
        ordaService.deleteDatabaseSoft(fqn);
        return ResponseEntity.ok("Database (soft delete) удалена: " + fqn);
    }

    @DeleteMapping("/databases/{fqn}/soft-recursive")
    public ResponseEntity<String> deleteDatabaseSoftRecursive(@PathVariable String fqn) {
        ordaService.deleteDatabaseSoftRecursive(fqn);
        return ResponseEntity.ok("Database (soft recursive delete) удалена: " + fqn);
    }

    @DeleteMapping("/databases/{fqn}/hard-recursive")
    public ResponseEntity<String> deleteDatabaseHardRecursive(@PathVariable String fqn) {
        ordaService.deleteDatabaseHardRecursive(fqn);
        return ResponseEntity.ok("Database (hard recursive delete) удалена: " + fqn);
    }

    // --- DELETE SCHEMAS ---
    @DeleteMapping("/schemas/{fqn}/soft")
    public ResponseEntity<String> deleteSchemaSoft(@PathVariable String fqn) {
        ordaService.deleteSchemaSoft(fqn);
        return ResponseEntity.ok("Schema (soft delete) удалена: " + fqn);
    }

    @DeleteMapping("/schemas/{fqn}/soft-recursive")
    public ResponseEntity<String> deleteSchemaSoftRecursive(@PathVariable String fqn) {
        ordaService.deleteSchemaSoftRecursive(fqn);
        return ResponseEntity.ok("Schema (soft recursive delete) удалена: " + fqn);
    }

    @DeleteMapping("/schemas/{fqn}/hard-recursive")
    public ResponseEntity<String> deleteSchemaHardRecursive(@PathVariable String fqn) {
        ordaService.deleteSchemaHardRecursive(fqn);
        return ResponseEntity.ok("Schema (hard recursive delete) удалена: " + fqn);
    }

    // --- DELETE TABLES ---
    @DeleteMapping("/tables/{fqn}/soft")
    public ResponseEntity<String> deleteTableSoft(@PathVariable String fqn) {
        ordaService.deleteTableSoft(fqn);
        return ResponseEntity.ok("Table (soft delete) удалена: " + fqn);
    }

    @DeleteMapping("/tables/{fqn}/soft-recursive")
    public ResponseEntity<String> deleteTableSoftRecursive(@PathVariable String fqn) {
        ordaService.deleteTableSoftRecursive(fqn);
        return ResponseEntity.ok("Table (soft recursive delete) удалена: " + fqn);
    }

    @DeleteMapping("/tables/{fqn}/hard")
    public ResponseEntity<String> deleteTableHard(@PathVariable String fqn) {
        ordaService.deleteTableHard(fqn);
        return ResponseEntity.ok("Table (hard delete) удалена: " + fqn);
    }
}

