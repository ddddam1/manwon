package com.example.manwon;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.ToString;

@Entity
public class Product {
    @Id
    public int id;
    public int price;
    public String name;
}