package com.example.demo.controller;

import com.example.demo.domain.Subscription;
import com.example.demo.service.AddBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by cw on 2017/9/30.
 */
@Controller
public class SubscriptionController {

    @Autowired
    private AddBookService addBookService;
    @RequestMapping("/")
    public String index() {
        return "redirect:/book";
    }

    @RequestMapping("/book")
    public String toEdit(Model model) {
        Subscription sub = new Subscription();
        model.addAttribute("sub" , sub);
        return "AddBook";
    }

    @RequestMapping(value = "/edit", method= RequestMethod.POST)
    public String edit(@ModelAttribute("sub")Subscription subscription, RedirectAttributes model) {
        System.out.println(subscription.getUrl());
        if (addBookService.addBook(subscription)) {
            return "redirect:/add_success";
        } else {
            return "redirect:/add_error";
        }
    }

    @RequestMapping("/add_success")
    public String add_success(Model model) {
        model.addAttribute("msg", "成功添加书籍");
        return "AddBookStatus";

    }

    @RequestMapping("/add_error")
    public String add_error(Model model) {
        model.addAttribute("msg", "添加书籍失败");
        return "AddBookStatus";

    }
}
