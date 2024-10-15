package com.katinuka.preciousMetals.controller;

import com.katinuka.preciousMetals.model.Currency;
import com.katinuka.preciousMetals.model.User;
import com.katinuka.preciousMetals.service.MetalPriceService;
import com.katinuka.preciousMetals.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class HomeController {

    private final UserService userService;
    private final MetalPriceService metalPriceService;

    public HomeController(UserService userService, MetalPriceService metalPriceService) {
        this.userService = userService;
        this.metalPriceService = metalPriceService;
    }

    @GetMapping("/home")
    public String getUserAndMetalPrices(Model model) {
        User user = userService.getCurrentUser();

        // Fetch metal prices from the Python API
        Map<String, Double> prices = metalPriceService.getMetalPrices();

        // Add user data and metal prices to the model
        model.addAttribute("user", user);
        model.addAttribute("prices", prices);

        // Return the Thymeleaf template name
        return "home_page";  // This resolves to 'home_page.html'
    }

    @PostMapping("/buy-metal")
    @ResponseBody
    public ResponseEntity<?> buyMetal(@RequestBody Map<String, String> purchaseData) {
        try {
            var metal = Currency.valueOf(purchaseData.get("metal"));
            var ounces = Double.parseDouble(purchaseData.get("ounces"));
            var pricePerOunce = Double.parseDouble(purchaseData.get("pricePerOunce"));
            var user = userService.getCurrentUser();

            userService.buyMetal(user, metal, ounces, pricePerOunce);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "balance", user.getBalance()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }

    @PostMapping("/sell-metal")
    @ResponseBody
    public ResponseEntity<?> sellMetal(@RequestBody Map<String, String> saleData) {
        try {
            var metal = Currency.valueOf(saleData.get("metal"));
            var ounces = Double.parseDouble(saleData.get("ounces"));
            var pricePerOunce = Double.parseDouble(saleData.get("pricePerOunce"));
            var user = userService.getCurrentUser();

            userService.sellMetal(user, metal, ounces, pricePerOunce);
            return ResponseEntity.ok(Map.of(
                    "status", "success",
                    "balance", user.getBalance()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status", "error",
                    "message", e.getMessage()
            ));
        }
    }
}
