package com.technokratos.kirillakhmetov.controller;

import com.technokratos.kirillakhmetov.entity.Owner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("main")
public class MainController {

    @GetMapping
    public String main(Model model) {
        Owner owner = new Owner();
        owner.setId(1L);
        owner.setFirstName("Тестовый");
        owner.setLastName("Пользователь");
        owner.setEmail("test@example.com");
        owner.setBusinessName("Тестовая компания");

        // Добавляем данные в контекст шаблона
        model.addAttribute("currentPage", "main");
        model.addAttribute("owner", owner);

        return "main";
    }
}
