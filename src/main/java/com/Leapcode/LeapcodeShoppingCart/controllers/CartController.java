package com.Leapcode.LeapcodeShoppingCart.controllers;

import com.Leapcode.LeapcodeShoppingCart.models.Cart;
import com.Leapcode.LeapcodeShoppingCart.models.ProductRepository;
import com.Leapcode.LeapcodeShoppingCart.models.data.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductRepository productRepo;

    @GetMapping("/add/{id}")
    public String add(@PathVariable int id, HttpSession session, Model model){
        Product product = productRepo.getOne(id);

        if (session.getAttribute("cart") == null){
            Map<Integer, Cart> cart = new HashMap<>();
            cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
            session.setAttribute("cart", cart);
        } else {
            Map<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");
            if (cart.containsKey(id)){
                int quantity = cart.get(id).getQuantity();
                cart.put(id, new Cart(id, product.getName(), product.getPrice(), ++quantity, product.getImage()));
            } else {
                cart.put(id, new Cart(id, product.getName(), product.getPrice(), 1, product.getImage()));
                session.setAttribute("cart", cart);
            }
        }

        Map<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");
        int size = 0;
        double totalPrice = 0;
        for(Cart value : cart.values()){
            size += value.getQuantity();
            totalPrice += value.getQuantity() * Double.parseDouble(value.getPrice());
        }
        model.addAttribute("size", size);
        model.addAttribute("totalPrice", totalPrice);
        return "cart_view";
    }
}
