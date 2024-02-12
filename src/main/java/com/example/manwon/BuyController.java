package com.example.manwon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

//@Controller
@RestController
@RequiredArgsConstructor
public class BuyController {

    private final WebService webService;

    @GetMapping("/")
    public String Shopping() throws IOException {

        webService.CallPriceApi();
        return "shop";
    }


    public ArrayList<Product> DisplayProduct() {
        ArrayList<Product> arr = new ArrayList<>();

        Product product = new Product();
        product.id = 46;
        product.price = 1110;
        product.name = "서울우유 흰우유(1L)";

        arr.add(product);

        return arr;
    }

}
