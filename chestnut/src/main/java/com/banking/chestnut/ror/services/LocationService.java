package com.banking.chestnut.ror.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.models.Location;
import com.banking.chestnut.ror.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class LocationService implements ILocationService {

    private LocationRepository locationRepository;

    private UserRepository userRepository;

    private Environment env;

    private Integer cashierId;

    @Autowired
    public LocationService(LocationRepository locationRepository, UserRepository userRepository, Environment env) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.env = env;
        this.cashierId = Integer.parseInt(env.getProperty("app.cashier.user.id"));
    }

    @Override
    public Location saveLocation(Location location) {
        location.setCreatedAt(new Date());
        location.setCreatedBy(userRepository.findById(cashierId).get());
        this.locationRepository.save(location);
        return location;
    }
}
