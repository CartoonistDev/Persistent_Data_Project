package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerServices;
import com.udacity.jdnd.course3.critter.service.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerServices customerServices;

    @Autowired
    private EmployeeServices employeeServices;
    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        try {
            Customer customer = new Customer();
            customer.setName(customerDTO.getName());
            customer.setPhoneNumbers(customerDTO.getPhoneNumber());
            customer.setNotes(customer.getNotes());
            List<Long> petIdList = customerDTO.getPetIds();
            return myCustomerDTO(customerServices.saveCustomer(customer, petIdList));
        } catch (UnsupportedOperationException e){
        throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        try {
            List<Customer> customers = customerServices.getAllCustomers();
            return customers.stream().map(this::myCustomerDTO).collect(Collectors.toList());
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        try {
            return myCustomerDTO(customerServices.getCustomerByPetId(petId));
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee employee = new Employee();
            employee.setEmployeeName(employeeDTO.getName());
            employee.setSkillSet(employeeDTO.getSkills());
            employee.setDayOfWeekAvailable(employeeDTO.getDaysAvailable());
            return myEmployeeDTO(employeeServices.createEmployee(employee));
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        try {
            return myEmployeeDTO(employeeServices.getEmployeeById(employeeId));
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        try {
            employeeServices.setAnEmployeeAvailability(daysAvailable, employeeId);
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        try {
            List<Employee> employees = employeeServices.getAnEmployeesForService(employeeDTO.getDate(), employeeDTO.getSkills());
            return employees.stream().map(this::myEmployeeDTO).collect(Collectors.toList());
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    private CustomerDTO myCustomerDTO(Customer myCustomer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(myCustomer.getId());
        customerDTO.setName(myCustomer.getName());
        customerDTO.setPhoneNumber(myCustomer.getPhoneNumbers());
        customerDTO.setNotes(myCustomer.getNotes());
        List<Long> petIds = myCustomer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private EmployeeDTO myEmployeeDTO(Employee myEmployee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(myEmployee.getEmployeeId());
        employeeDTO.setName(myEmployee.getEmployeeName());
        employeeDTO.setSkills(myEmployee.getSkillSet());
        employeeDTO.setDaysAvailable(myEmployee.getDayOfWeekAvailable());
        return employeeDTO;
    }
}
