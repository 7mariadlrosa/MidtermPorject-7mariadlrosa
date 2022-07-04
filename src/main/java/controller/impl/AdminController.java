package controller.impl;

import controller.dto.accounts.MoneyDto;
import controller.dto.users.AdminDto;
import controller.dto.users.ThirdPartyDto;
import controller.interfaces.IAdminController;

import model.Account;
import model.Admin;
import model.ThirdParty;
import service.interfaces.IAdminService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AdminController implements IAdminController {

    @Autowired
    private IAdminService adminService;

    @PostMapping("/admin/create-new-admin")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin createAdmin(@RequestBody @Valid AdminDto adminDto) {
        return adminService.createAdmin(adminDto);
    }

    @PostMapping("/admin/create-third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody @Valid ThirdPartyDto thirdPartyDto) {
        return adminService.createThirdParty(thirdPartyDto);
    }

    @PatchMapping("/admin/modify-balance/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account modifyBalance(@PathVariable("id") Long id, @RequestBody @Valid MoneyDto modifiedBalance){
        return adminService.modifyBalance(id, modifiedBalance);
    }

}
