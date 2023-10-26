package com.greger.wigelltravels.service;

import com.greger.wigelltravels.dao.DestinationRepository;
import com.greger.wigelltravels.entity.Address;
import com.greger.wigelltravels.entity.Destination;
import com.greger.wigelltravels.entity.Trip;
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
    public Destination updateDestination(int id, Destination destination) {
        Destination destinationFromDb = findById(id);
        destinationFromDb.setHotellName(destination.getHotellName());
        destinationFromDb.setPricePerWeek(destination.getPricePerWeek());
        destinationFromDb.setCity(destination.getCity());
        destinationFromDb.setCountry(destination.getCountry());
        return save(destinationFromDb);
    }

    @Override
    @Transactional
    public String deleteById(int id) {
        String message = "";
        List<Trip> tripList = destinationRepository.findAllByDestination(findById(id).getId());
        System.out.println("RESA BASERAT PÅÅ RESA: " + tripList);
        System.out.println(tripList.size());
        if (tripList.size() == 0){
            message = "Destination with id: " + id + " has been deleted!";
            destinationRepository.deleteById(id);
            return message;
        }else {
            message = "Destination with id: " + id + " can not be deleted! Destination is used in " + tripList.size() + " trips!";
            for (Trip t : tripList){
                String tripId = "\n" + "Trip ID: " + String.valueOf(t.getTripId());
                message = message.concat(tripId);
            }
        }
        return message;
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
        if(autoSave){
            destination.setId(0);
            destinationFromDatabase = save(destination);
            System.out.println("SPARAD: " + destinationFromDatabase);
            return destinationFromDatabase;
        }
        System.out.println("###############################################################################");
      return destination;
    }
}
