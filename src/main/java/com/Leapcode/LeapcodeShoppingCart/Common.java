package com.Leapcode.LeapcodeShoppingCart;

import com.Leapcode.LeapcodeShoppingCart.models.Cart;
import com.Leapcode.LeapcodeShoppingCart.models.CategoryRepository;
import com.Leapcode.LeapcodeShoppingCart.models.PageRepository;
import com.Leapcode.LeapcodeShoppingCart.models.data.Category;
import com.Leapcode.LeapcodeShoppingCart.models.data.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class Common {

    @Autowired
    private PageRepository pageRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @ModelAttribute
    public void sharedData(Model model, HttpSession session){
        List<Page> pages = pageRepo.findAllByOrderBySortingAsc();
        List<Category> categories = categoryRepo.findAll();
        boolean cartActive = false;
        if (session.getAttribute("cart") != null){
            Map<Integer, Cart> cart = (HashMap<Integer, Cart>)session.getAttribute("cart");
            int size = 0;
            double totalPrice = 0;
            for(Cart value : cart.values()){
                size += value.getQuantity();
                totalPrice += value.getQuantity() * (Double.parseDouble(value.getPrice()));
            }
            model.addAttribute("csize", size);
            model.addAttribute("ctotalPrice", totalPrice);
            cartActive = true;
        }
        model.addAttribute("cartActive", cartActive);
        model.addAttribute("ccategories", categories);
        model.addAttribute("cpages", pages);
    }
}
