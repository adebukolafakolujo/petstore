package com.petstore.data.repository;

import com.petstore.data.model.Gender;
import com.petstore.data.model.Pet;
import com.petstore.data.model.Store;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j

@Sql(scripts = {"classpath:db/insert.sql"})
@SpringBootTest
class PetRepositoryTest {

    @Autowired
    PetRepository petRepository;

    @Autowired
    StoreRepository storeRepository;

    @BeforeEach
    void setUp() {
    }

    //Test that we can save a pet to the database
    @Test
    public void whenPetIsSaved_thenReturnAPetId() {

        //step 1: Create an instance of a pet

        Pet pet = new Pet();
        pet.setName("Jack");
        pet.setAge(2);
        pet.setBreed("Dog");
        pet.setColor("Black");
        pet.setPetSex(Gender.MALE);


        log.info("Pet instance before saving --> {}", pet);

        //step 2: Call repository save method
        petRepository.save(pet);

        assertThat(pet.getId()).isNotNull();
        log.info("Pet instance");
    }

     @Test
     @Transactional
     @Rollback(value = false)

    public void whenStoreIsMappedToPet_thenForeignKeyIsPresent(){
        //create a pet
         Pet pet = new Pet();
         pet.setName("Jack");
         pet.setAge(2);
         pet.setBreed("Dog");
         pet.setColor("Black");
         pet.setPetSex(Gender.MALE);



        //create a store
        Store store = new Store();
        store.setName("Pet Sellers");
        store.setLocation("Yaba");
        store.setContactNo("08080472478");

        log.info("Store instance before saving --> {}", store);

        //map pet to store
        pet.setStore(store);

        //save pet
        //step 2: Call repository save method
        petRepository.save(pet);



        assertThat(pet.getId()).isNotNull();

        log.info("Pet instance after saving --> {}", pet);
        log.info("Store instance after saving --> {}", store);

        assertThat(pet.getId()).isNotNull();
        assertThat(store.getId()).isNotNull();
        assertThat(pet.getStore()).isNotNull();

    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void whenIAddPetsToAStore_thenICanFetchAListOfPetsFromStore(){

        //create a store
        Store store = new Store();
        store.setName("Pet Sellers");
        store.setLocation("Yaba");
        store.setContactNo("08080472478");


        //create 2 pets
        Pet jack = new Pet();
        jack.setName("Jack");
        jack.setAge(2);
        jack.setBreed("Dog");
        jack.setColor("Black");
        jack.setPetSex(Gender.MALE);
        jack.setStore(store);


        Pet sally = new Pet();
        sally.setName("Sally");
        sally.setAge(2);
        sally.setBreed("Dog");
        sally.setColor("Black");
        sally.setPetSex(Gender.MALE);
        sally.setStore(store);

        log.info("Pet instances before saving --> {}", jack, sally);




        //map pet to store
        //jack.setStore(store);
        //sally.setStore(store);

        store.addPets(jack);
        store.addPets(sally);

        storeRepository.save(store);

        log.info("Store instance before saving --> {}", store);

        //assert for pet Ids
        //assert for store Id

         assertThat(jack.getId()).isNotNull();
         assertThat(sally.getId()).isNotNull();
         assertThat(store.getPetList()).isNotNull();



        //save pet
        //step 2: Call repository save method
       // petRepository.save(pet);



       // assertThat(pet.getId()).isNotNull();

        //log.info("Pet instance after saving --> {}", pet);
       // log.info("Store instance after saving --> {}", store);

       // assertThat(pet.getId()).isNotNull();
        //assertThat(store.getId()).isNotNull();
        //assertThat(pet.getStore()).isNotNull();



    }

    @Test
    public void whenFindAllPetISCalled_thenReturnAllPetsInStore(){

        //find pets from store
        List<Pet> savedPets = petRepository.findAll();

        log.info("Fetched pets list from db --> {}", savedPets);

        //assert that pets exist
        assertThat(savedPets).isNotNull();
        assertThat(savedPets.size()).isEqualTo(7);

    }

    @Test
    public void updateExistingPetDetailsTest(){

        //fetch a pet
        //assert the field
        //update pet field
        //save pet
        //assert that updated field has changed
        Pet sally = petRepository.findById(34).orElse( null);

        log.info("Pet object retrieved from database --> {}", sally);

        assertThat(sally).isNotNull();

        assertThat(sally.getColor()).isEqualTo("brown");

        sally.setColor("purple");

        petRepository.save(sally);

        assertThat(sally.getColor()).isEqualTo("purple");

    }

    @Test
    public void whenIDeletePetFromDatabase_thenPetISDeleted(){

        //check if pet exists
        boolean result = petRepository.existsById(31);
        //assert that pet exists
        assertThat(result).isTrue();
        //delete pet
        petRepository.deleteById(31);
        //check if pet exists
        assertThat(petRepository.existsById(31)).isFalse();
        //assert that pet does not exist





    }
}