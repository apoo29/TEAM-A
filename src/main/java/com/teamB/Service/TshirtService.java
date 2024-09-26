package com.teamB.Service;

import com.teamB.Entity.TshirtOrder;
import com.teamB.Repository.TshirtOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TshirtService {

    @Autowired
    private TshirtOrderRepository tshirtOrderRepository;

    @HystrixCommand(fallbackMethod = "defaultOrderTshirt")
    public TshirtOrder orderTshirt(TshirtOrder order) {
        order.setStatus("Processing");
        return tshirtOrderRepository.save(order);
    }

    public TshirtOrder defaultOrderTshirt(TshirtOrder order) {
        order.setStatus("Failed");
        return order;
    }
}
