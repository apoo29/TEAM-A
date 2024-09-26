package com.teamB.Controller;

import com.teamB.Entity.TshirtOrder;
import com.teamB.Service.TshirtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tshirts")
public class TshirtController {

    @Autowired
    private TshirtService tshirtService;

    @PostMapping("/order")
    public ResponseEntity<?> orderTshirt(@Valid @RequestBody TshirtOrder order, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        TshirtOrder savedOrder = tshirtService.orderTshirt(order);
        return ResponseEntity.ok(savedOrder);
    }
}
