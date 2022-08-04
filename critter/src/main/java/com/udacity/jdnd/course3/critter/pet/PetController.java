package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.CustomerServices;
import com.udacity.jdnd.course3.critter.service.PetServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetServices petServices;
    @Autowired
    private CustomerServices customerServices;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        try {
            Pet myPet = new Pet();
            myPet.setType(petDTO.getType());
            myPet.setName(petDTO.getName());
            myPet.setBirthDate(petDTO.getBirthDate());
            myPet.setNotes(petDTO.getNotes());
            return myPetDTO(petServices.savePet(myPet, petDTO.getOwnerId()));
        } catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        try {
            return myPetDTO(petServices.getPetById(petId));
        }catch (UnsupportedOperationException e){
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping
    public List<PetDTO> getPets(){
        try {
            List<Pet> petList = petServices.getAllPets();
            return petList.stream().map(this::myPetDTO).collect(Collectors.toList());
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        try {
            List<Pet> petList = petServices.getAllCustomerPets(ownerId);
            return petList.stream().map(this::myPetDTO).collect(Collectors.toList());

        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException();
        }
    }


    //Set DTO
    private PetDTO myPetDTO(Pet myPet){
        PetDTO petDTO = new PetDTO();
        petDTO.setId(myPet.getId());
        petDTO.setName(myPet.getName());
        petDTO.setType(myPet.getType());
        petDTO.setOwnerId(myPet.getCustomer().getId());
        petDTO.setBirthDate(myPet.getBirthDate());
        petDTO.setNotes(myPet.getNotes());
        return petDTO;
    }
}
