package com.example.hw_2_13;


import com.example.hw_2_13.exception.EmployeeNotFoundException;
import com.example.hw_2_13.model.Employee;
import com.example.hw_2_13.service.DepartmentService;
import com.example.hw_2_13.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceTest {

    @Mock
    private EmployeeService employeeService;


    @InjectMocks
    private DepartmentService departmentService;

    @BeforeEach
    public void beforeEach() {
        List<Employee> employees = List.of(
                new Employee("Василий", "Васильев", "1", 55_000),
                new Employee("Андрей", "Андреев", 1, 65_000),
                new Employee("Иван", "Иванов", 2, 45_000),
                new Employee("Мария", "Иванова", 2, 50_000),
                new Employee("Ирина", "Андреева", 2, 47_000)
        );
        when(employeeService.getAll()).thenReturn(employees);
    }


    @ParameterizedTest
    @MethodSource("employeeWithMaxSalaryParams")
    public  void employeeWithMaxSalaryPositiveTest(int departmentId, Employee expected) {
        assertThat(departmentService.employeeWithMaxSalary(departmentId)).isEqualTo(expected);
    }

    @Test
    public void employeeWithMaxSalaryNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.employeeWithMaxSalary(3));
    }

    @ParameterizedTest
    @MethodSource("employeeWithMinSalaryParams")
    public  void employeeWithMinSalaryPositiveTest(int departmentId, Employee expected) {
        assertThat(departmentService.employeeWithMinSalary(departmentId)).isEqualTo(expected);
    }
    @Test
    public void employeeWithMinSalaryNegativeTest() {
        assertThatExceptionOfType(EmployeeNotFoundException.class)
                .isThrownBy(() -> departmentService.employeeWithMinSalary(3));
    }
    @ParameterizedTest
    @MethodSource("employeesFromDepartmentParams")
    public  void employeesFromDepartmentPositiveTest(int departmentId, List<Employee> expected) {
        assertThat(departmentService.employeesFromDepartment(departmentId)).containsExactlyElements(expected);
    }
    @Test
    public void employeesGroupedByDepartmentTest() {
        assertThat(departmentService.employeesGroupedByDepartment()).containsAllEntrinsOf(
                        Map.of(
                                1, List.of(new Employee("Василий", "Васильев", 1, 55_0000), new Employee("Андрей", "Андреев", 1, 65_000)),
                                2, List.of(new Employee("Иван", "Иванов", 2, 45_000), new Employee("Мария", "Иванова", 2, 50_000))
                        )
                );

    }

    public static Stream<Arguments> employeeWithMaxSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Андрей", "Андреев", 1, 65_000)),
                Arguments.of(2, new Employee("Мария", "Иванова", 2, 50_000))
        );
    }
    public static Stream<Arguments> employeeWithMinSalaryParams() {
        return Stream.of(
                Arguments.of(1, new Employee("Василий", "Васильев", 1, 55_000)),
                Arguments.of(2, new Employee("Иван", "Иванова", 2, 45_000))
        );
    }

    public static Stream<Arguments> tmployeesFromDepartamentParams() {
        return Stream.of(
                Arguments.of(1, List.of(new Employee("Василий", "Васильев", 1, 55_000), new Employee("Андрей", "Андреев", 1, 65_000)),
                Arguments.of(2, List.of(new Employee("Иван", "Иванова", 2, 45_000), new Employee("Мария", "Иванова", 2, 50_000)),
                        Arguments.of(3, Collections.emptyList()));


    }




}
