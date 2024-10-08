package preciousMetals.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import preciousMetals.model.Currency;
import preciousMetals.model.User;
import preciousMetals.service.MetalPriceService;
import preciousMetals.service.UserService;

import java.util.Map;

@Controller
public class CombinedController {

    private final UserService userService;       // To fetch user data
    private final MetalPriceService metalPriceService;  // To fetch metal prices

    public CombinedController(UserService userService, MetalPriceService metalPriceService) {
        this.userService = userService;
        this.metalPriceService = metalPriceService;
    }

    // Combined endpoint for user data and metal prices
    @GetMapping("/user/metal-prices")
    public String getUserAndMetalPrices(Model model) {
        User user = userService.getCurrentUser();

        // Fetch metal prices from the Python API
        Map<String, Double> prices = metalPriceService.getMetalPrices();

        // Add user data and metal prices to the model
        model.addAttribute("user", user);
        model.addAttribute("prices", prices);

        // Return the Thymeleaf template name
        return "user_metal_prices";  // This resolves to 'user_metal_prices.html'
    }

    @PostMapping("/user/buy-metal")
    @ResponseBody
    public ResponseEntity<?> buyMetal(@RequestBody Map<String, String> purchaseData) {
        try {
            var metal = Currency.valueOf(purchaseData.get("metal").toUpperCase());
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

    @PostMapping("/user/sell-metal")
    @ResponseBody
    public ResponseEntity<?> sellMetal(@RequestBody Map<String, String> saleData) {
        try {
            var metal = Currency.valueOf(saleData.get("metal").toUpperCase());
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
