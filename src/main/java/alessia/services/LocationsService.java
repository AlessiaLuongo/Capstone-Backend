package alessia.services;

import alessia.entities.Activity;
import alessia.entities.Location;
import alessia.entities.User;
import alessia.exceptions.NotFoundException;
import alessia.payloads.NewLocationDTO;
import alessia.repositories.LocationsDAO;
import alessia.repositories.UsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service

public class LocationsService {

    @Autowired
    private LocationsDAO locationsDAO;

    @Autowired
    private UsersDAO usersDAO;

    @Autowired
    private UsersService usersService;

    public Page<Location> getAllLocations(int page, int size, String sortBy){
        if(size > 100) size = 100;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.locationsDAO.findAll(pageable);
    }

    public Location saveLocation(User currentUser, NewLocationDTO body){

        Location newLocation = new Location(
                body.title(),
                body.description(),
                LocalDate.now(),
                body.outdoor(),
                0,
                1,
                body.price(),
                new ArrayList<>(),
                currentUser,
                body.locationType(),
                body.influxOfPeople());

                return locationsDAO.save(newLocation);

    }

    public Location findLocationById(UUID locationId){
        return locationsDAO.findLocationById(locationId).orElseThrow(()-> new NotFoundException("Activity not found"));
    }

    public Location findLocationByIdAndUpdate(UUID locationId, Location updatedLocation) {
        Optional<Location> optionalLocation = locationsDAO.findLocationById(locationId);
        if (optionalLocation.isPresent()){
            Location found = optionalLocation.get();

            found.setTitle(updatedLocation.getTitle());
            found.setDescription(updatedLocation.getDescription());
            found.setOutdoor(updatedLocation.isOutdoor());
            found.setNumberOfVisits(updatedLocation.getNumberOfVisits());
            found.setRate(updatedLocation.getRate());
            found.setPrice(updatedLocation.getPrice());
            found.setListOfPictures(updatedLocation.getListOfPictures());
            found.setLocationType(updatedLocation.getLocationType());
            found.setInfluxOfPeople(updatedLocation.getInfluxOfPeople());

            return locationsDAO.save(found);

        }else {
            throw new NotFoundException("Location not found");
        }
    }

    public void deleteLocationById(UUID locationId) {
        Optional<Location> optionalLocation = locationsDAO.findLocationById(locationId);
        if (optionalLocation.isPresent()) {
            Location found = optionalLocation.get();
            locationsDAO.delete(found);
        } else {
            throw new NotFoundException("Location not found");
        }
    }
}
