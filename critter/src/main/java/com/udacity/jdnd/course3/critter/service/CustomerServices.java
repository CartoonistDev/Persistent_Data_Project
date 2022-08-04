package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServices {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PetRepository petRepository;

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    public Customer getCustomer(long id){
        return customerRepository.getOne(id);
    }

    public Customer getCustomerByPetId(long id){
        return petRepository.getOne(id).getCustomer();
    }

    public Customer saveCustomer(Customer customer, List<Long> customerPetIds){
        List<Pet> petList = new ArrayList<>();
        if (customerPetIds!= null && !customerPetIds.isEmpty()){
            petList = customerPetIds.stream().map((petId) -> petRepository.getOne(petId)).collect(Collectors.toList());
        }
        customer.setPets(petList);
        return customerRepository.save(customer);
    }
}
