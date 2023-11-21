package com.mert.controller;

import com.mert.dto.request.ActivateStatusRequestDto;
import com.mert.dto.request.DeleteRequestDto;
import com.mert.dto.request.UserCreateRequestDto;
import com.mert.dto.request.UserProfileUpdateRequestDto;
import com.mert.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/USER")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody UserCreateRequestDto dto){
        return ResponseEntity.ok(userProfileService.createUser(dto));
    }
    @PostMapping("/update")
    public ResponseEntity<Boolean> update(@RequestBody UserProfileUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.update(dto));
    }
    @PostMapping("/active-status")
    public ResponseEntity<Boolean> activateStatus2(@RequestBody ActivateStatusRequestDto dto){
        return ResponseEntity.ok(userProfileService.activateStatus(dto));
    }
    @PutMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestBody DeleteRequestDto dto){
        return ResponseEntity.ok(userProfileService.delete(dto));
    }
}
