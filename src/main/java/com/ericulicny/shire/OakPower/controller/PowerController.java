package com.ericulicny.shire.OakPower.controller;

import com.ericulicny.shire.OakPower.domain.PowerUsage;
import com.ericulicny.shire.OakPower.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PowerController {

        @Autowired
        MessageService messageService;

        @CrossOrigin(origins = "http://localhost:3000")
        @GetMapping("/api/v1/currentpower")
        public PowerUsage getCurrentPower() {

                PowerUsage powerUsage = new PowerUsage();
                powerUsage.setCurrentPowerUsageWatts(messageService.getCurrentUsageWatts());
                powerUsage.setUpdateTime(messageService.getUpdatedTime());

                return powerUsage;
        }
}
