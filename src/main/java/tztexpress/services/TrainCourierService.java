package tztexpress.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tztexpress.models.Traincourier;
import tztexpress.models.User;
import tztexpress.repositories.TrainCourierRepository;
import tztexpress.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainCourierService {
    private TrainCourierRepository trainCourierRepository;

    @Autowired
    public TrainCourierService(TrainCourierRepository trainCourierRepository) {
        this.trainCourierRepository = trainCourierRepository;
    }

    public List<Traincourier> listAll() {
        List<Traincourier> traincouriers = new ArrayList<>();
        trainCourierRepository.findAll().forEach(traincouriers::add);
        return traincouriers;
    }

    public Traincourier getById(long id) {
        return this.trainCourierRepository.findOne(id);
    }
}
