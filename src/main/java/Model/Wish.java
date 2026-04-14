package Model;

import org.springframework.core.metrics.StartupStep;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Wish {
    private int wishId;
    private String title;
    private String description;
    private double price;
    private String url;
    private LocalDate date;
    private List<WishTags> tags = new ArrayList<>();

}

//Constructor

//setters

//getters

//toString?
