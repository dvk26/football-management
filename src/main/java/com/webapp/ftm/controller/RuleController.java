package com.webapp.ftm.controller;


import com.webapp.ftm.dto.response.ApiResponseDTO;
import com.webapp.ftm.dto.RuleDTO;
import com.webapp.ftm.repository.PlayerRepository;
import com.webapp.ftm.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rules")
public class RuleController {
    @Autowired
    private RuleService ruleService;


    @PostMapping("")
    public ResponseEntity<ApiResponseDTO> saveNewRule(@RequestBody RuleDTO ruleDTO){

        ruleService.saveNewRule(ruleDTO);
        return new ResponseEntity<>(new ApiResponseDTO<>("200","Lưu quy định thành công!",ruleDTO), HttpStatusCode.valueOf(200));
    }
    @GetMapping("")
    public ResponseEntity<ApiResponseDTO> getNewestRule(){
        return new ResponseEntity<>(new ApiResponseDTO<>("200","success",ruleService.getNewestRule()), HttpStatusCode.valueOf(200));
    }
}
