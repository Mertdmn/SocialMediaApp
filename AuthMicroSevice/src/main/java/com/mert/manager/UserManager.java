package com.mert.manager;

import com.mert.dto.request.UserCreateRequestDto;
import com.mert.dto.request.ActivateStatusRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@FeignClient(url = "http://localhost:7071/api/v1/user", name = "auth-userprofile")
public interface UserManager {

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody UserCreateRequestDto dto);

    @PostMapping("active-status")
    public ResponseEntity<Boolean> activateStatus2(@RequestBody ActivateStatusRequestDto dto);

}
