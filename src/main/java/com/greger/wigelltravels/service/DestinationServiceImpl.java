package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.DestinationRepository;
import com.greger.wigelltravels.entity.Address;
import com.greger.wigelltravels.entity.Destination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DestinationServiceImpl implements DestinationService{

    private DestinationRepository destinationRepository;

    @Autowired
    public DestinationServiceImpl(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }

    @Override
    public List<Destination> findAll() {
        return destinationRepository.findAll();
    }

    @Override
    public Destination findById(int id) {
        Optional<Destination> d = destinationRepository.findById(id);
        Destination destination;
        if (d.isPresent()){
            destination = d.get();
        }
        else {
            throw new RuntimeException("Destination with id: " + id + " could not be found!");
        }
        return destination;
    }

    @Override
    @Transactional
    public Destination save(Destination destination) {
        destination = checkIfExistsInDatabaseIfNotSave(destination, false);
        return destinationRepository.save(destination);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        destinationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Destination checkIfExistsInDatabaseIfNotSave(Destination destination, boolean autoSave) {
        String city = destination.getCity();
        String country = destination.getCountry();
        String hotellName = destination.getHotellName();
        System.out.println("###############################################################################");
        System.out.println("INKOMMANDE: " + destination);

        Destination destinationFromDatabase = destinationRepository.findDestinationByHotellNameAndCityAndCountry(hotellName, city, country);
        if (destinationFromDatabase != null){
            System.out.println("FRÅN DB: " + destinationFromDatabase);
            return destinationFromDatabase;
        }
        destination.setId(0);
        if(autoSave){
            destinationFromDatabase = save(destination);
            System.out.println("SPARAD: " + destinationFromDatabase);
            return destinationFromDatabase;
        }


        System.out.println("###############################################################################");
      return destination;
    }
}
