package alessia.controllers;


import alessia.entities.Location;
import alessia.entities.User;
import alessia.exceptions.BadRequestException;
import alessia.payloads.NewLocationDTO;
import alessia.services.LocationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/locations")
@CrossOrigin

public class LocationsController {

    @Autowired
    private LocationsService locationService;

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
    public Location saveLocation(@AuthenticationPrincipal User currentUser, @RequestBody NewLocationDTO body) throws BadRequestException {
        return this.locationService.saveLocation(currentUser, body);
    }

    @GetMapping
    public Page<Location> getAllLocations(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "title")String sortBy) {
        return this.locationService.getAllLocations(page, size, sortBy);
    }

    @GetMapping("/{locationId}")
    public Location findLocationById(@PathVariable UUID locationId) {
        return this.locationService.findLocationById(locationId);
    }

    @PutMapping("/me/{locationId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Location findLocationByIdAndUpdate(@PathVariable UUID locationId, @RequestBody Location location, @AuthenticationPrincipal User currentUser) {
        return this.locationService.findLocationByIdAndUpdate(currentUser, locationId, location);
    }

    @DeleteMapping("/{locationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findLocationByIdAndDelete(@PathVariable UUID locationId) {
        this.locationService.deleteLocationById(locationId);
    }

}
