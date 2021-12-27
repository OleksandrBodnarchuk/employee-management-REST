package com.obodnarchuk.employee;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("csv")
public class CsvDataController {

    private final EmployeeService employeeService;

    public CsvDataController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(path = "/export")
    public void getAllEmployeesInCsv(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition", "attachment; filename=\"employees.csv\"");
        employeeService.writeEmployeesToCsv(servletResponse.getWriter());
    }

    @PostMapping(path = "/import", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<EmployeeResponseDTO> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        return employeeService.saveFromCSV(file);
    }
}

