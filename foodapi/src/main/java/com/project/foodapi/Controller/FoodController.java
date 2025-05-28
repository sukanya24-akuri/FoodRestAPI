package com.project.foodapi.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.foodapi.entity.FoodEntity;
import com.project.foodapi.service.IFoodService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@CrossOrigin("*")
public class FoodController
{
    @Autowired
    private IFoodService service;

    @PostMapping
    public ResponseEntity<?> addFoodDetails(@RequestPart("entity") String entity,
                                            @RequestPart("file") MultipartFile file) {


        try {
            ObjectMapper objectMapper = new ObjectMapper();
            FoodEntity resentity= objectMapper.readValue(entity, FoodEntity.class);
            FoodEntity foodEntity = service.addFood(resentity, file);
            return new ResponseEntity<>(foodEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllFoodDetails()
    {
        try {
            List<FoodEntity> list = service.allFoodDetails();
            return new ResponseEntity<>(list, HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


@DeleteMapping("/{id}")
    public ResponseEntity<String> deleteByFoodId(@PathVariable String id)
    {
        try
        {
        String del=  service.deleteByFoodID(id);
            if (del.contains("not found")) {
                return new ResponseEntity<>(del, HttpStatus.NOT_FOUND);
            }
        return  new ResponseEntity<String>(del,HttpStatus.OK);
        }
        catch (Exception e)
        {
            return  new ResponseEntity<String>(e.getMessage(),HttpStatus.OK);
        }
    }

    @GetMapping("/foods/{id}")
    public ResponseEntity<?> findByFoodID(@PathVariable String id)
    {
try
{
    FoodEntity foodEntity=service.getFoodByID(id);
    return  new ResponseEntity<>(foodEntity,HttpStatus.OK);
}
catch (Exception e)
{
    return  new ResponseEntity<String>(e.getMessage(),HttpStatus.OK);
}
    }
}

