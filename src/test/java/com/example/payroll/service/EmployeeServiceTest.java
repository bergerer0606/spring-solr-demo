package com.example.payroll.service;

import com.example.payroll.entity.Employee;
import com.example.payroll.ex.EmployeeNotFoundException;
import com.example.payroll.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService subject;

    @Mock
    private EmployeeRepository repository;

    @Captor ArgumentCaptor<Pageable> pagingCaptor;
    @Captor ArgumentCaptor<Employee> employeeCaptor;
    @Captor ArgumentCaptor<String> idCaptor;

    @Test
    public void testAllCorrect(){
        //given
        Employee emp1 = new Employee("Robert Engle", "P+ SWE Intern");
        Employee emp2 = new Employee("Rahul Vanmali", "Lucid MechE Intern");
        Employee emp3 = new Employee("Alice Bob", "Filler");
        Employee emp4 = new Employee("Bob Alice", "Filler");

        List<Employee> employees = List.of(emp1, emp2, emp3, emp4);
        Page<Employee> employeePage = new PageImpl<>(
                employees,
                PageRequest.of(0, 10),
                employees.size()
        );

        Mockito.when(repository.findAll(pagingCaptor.capture())).thenReturn(employeePage);
        //when
        Page<Employee> result = subject.all();
        //then
        Assertions.assertThat(
                result.stream().filter(
                        emp -> !employees.contains(emp)
                ).count()
        ).isEqualTo(0);
    }

    @Test
    public void testAdd(){
        //given
        Pageable defaultPaging = PageRequest.of(0, 10);

        Employee emp1 = new Employee("Robert Engle", "P+ SWE Intern");
        Employee emp2 = new Employee("Rahul Vanmali", "Lucid MechE Intern");
        Employee emp3 = new Employee("Alice Bob", "Filler");
        Employee emp4 = new Employee("Bob Alice", "Filler");

        List<Employee> actualEmployeeList = new ArrayList<>(List.of(emp1, emp2, emp3, emp4));

        Employee employeeToBeAdded = new Employee("Bob Bob", "Bob");
        List<Employee> expectedEmployeeList = List.of(emp1, emp2, emp3, emp4, employeeToBeAdded);

        Mockito.when(repository.save(employeeCaptor.capture())).thenReturn(employeeToBeAdded);
        Mockito.when(repository.findAll(pagingCaptor.capture())).thenReturn(
                new PageImpl<>(actualEmployeeList, defaultPaging, actualEmployeeList.size())
        );

        //when
        subject.add(employeeToBeAdded);
        actualEmployeeList.add(employeeCaptor.getValue());

        //then
        Assertions.assertThat(
                subject.all()
                        .stream()
                        .filter(emp -> !expectedEmployeeList.contains(emp))
                        .count()
        ).isEqualTo(0);
    }

    @Test
    public void testOneWhenAvailable(){
        //given
        Employee emp1 = new Employee("Robert Engle", "P+ SWE Intern");
        Employee emp2 = new Employee("Rahul Vanmali", "Lucid MechE Intern");
        Employee emp3 = new Employee("Alice Bob", "Filler");
        Employee emp4 = new Employee("Bob Alice", "Filler");
        emp1.setId("1");
        emp2.setId("2");
        emp3.setId("3");
        emp4.setId("4");

        Map<String, Employee> employeeDb = new HashMap<>();
        Stream.of(emp1, emp2, emp3, emp4).forEach(emp ->
            employeeDb.put(emp.getId(), emp)
        );

        Mockito.when(repository.findById(idCaptor.capture())).thenReturn(Optional.of(emp1));

        //when
        Employee resultEmployee = subject.one("1");

        //then
        Assertions.assertThat(employeeDb.get(idCaptor.getValue())).isEqualTo(resultEmployee);
    }

    @Test
    public void testOneWhenNotAvailable(){
        //given
        Employee emp1 = new Employee("Robert Engle", "P+ SWE Intern");
        Employee emp2 = new Employee("Rahul Vanmali", "Lucid MechE Intern");
        Employee emp3 = new Employee("Alice Bob", "Filler");
        Employee emp4 = new Employee("Bob Alice", "Filler");
        emp1.setId("1");
        emp2.setId("2");
        emp3.setId("3");
        emp4.setId("4");

        Map<String, Employee> employeeDb = new HashMap<>();
        Stream.of(emp1, emp2, emp3, emp4).forEach(emp ->
                employeeDb.put(emp.getId(), emp)
        );

        Mockito.when(repository.findById(idCaptor.capture())).thenThrow(new EmployeeNotFoundException("5"));

        //when
        Employee resultEmployee;
        try {
            resultEmployee = subject.one("5");
        }catch (EmployeeNotFoundException e){
            resultEmployee = null;
        }

        //then
        //Hashmap will return null if a key doesn't exist in the map
        Assertions.assertThat(employeeDb.get(idCaptor.getValue())).isEqualTo(resultEmployee);
    }

    @Test
    public void testReplaceWhenAvailable(){
        //given
        Employee employeeInDb = new Employee("Robert Engle", "P+ SWE Intern");
        Employee employeeToReplaceWith = new Employee("Rahul Vanmali", "Lucid MechE Intern");
        employeeInDb.setId("1");

        Mockito.when(repository.findById("1")).thenReturn(Optional.of(employeeInDb));
        Mockito.when(repository.save(employeeCaptor.capture())).thenReturn(employeeToReplaceWith);

        //when
        subject.replace(employeeToReplaceWith, "1");

        //then
        Assertions.assertThat(employeeCaptor.getValue().getName()).isEqualTo(employeeToReplaceWith.getName());
        Assertions.assertThat(employeeCaptor.getValue().getRole()).isEqualTo(employeeToReplaceWith.getRole());
    }

    @Test
    public void testReplaceWhenNotAvailable(){
        Employee employeeToBeUpdated = new Employee("Terry", "Huffman", "Director");
        Mockito.when(repository.findById("123")).thenReturn(Optional.empty());
        Mockito.when(repository.save(employeeToBeUpdated)).thenReturn(employeeToBeUpdated);
        Employee actual = subject.replace(employeeToBeUpdated, "123");
        Assertions.assertThat(actual.getName()).isEqualTo(employeeToBeUpdated.getName());
    }

    @Test
    public void testDeleteWhenAvailable(){
        //given
        Employee employeeInDb = new Employee("Robert Engle", "P+ SWE Intern");
        employeeInDb.setId("1");

        Mockito.when(repository.findById("1")).thenReturn(Optional.of(employeeInDb));
        Mockito.doNothing().when(repository).delete(employeeCaptor.capture());
        //when
        subject.delete("1");

        //then
        Assertions.assertThat(employeeCaptor.getValue().getId()).isEqualTo(employeeInDb.getId());
    }

    @Test
    public void testDeleteWhenNotAvailable(){
        //given

        Mockito.when(repository.findById("1")).thenThrow(new EmployeeNotFoundException("1"));

        //when & then
        Assertions.assertThatThrownBy(() -> {
            subject.delete("1");
        }).isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("1");

    }
}
