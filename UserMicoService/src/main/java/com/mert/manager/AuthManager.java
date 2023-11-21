package com.mert.manager;

import com.mert.dto.request.DeleteRequestDto;
import com.mert.dto.request.UpdateEmailAndUsernameRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
@FeignClient(url = "http://localhost:7071/api/v1/user", name = "auth-userprofile")
public interface AuthManager {
    @PutMapping("/update-email-or-username")
    public ResponseEntity<Boolean> update(UpdateEmailAndUsernameRequestDto updateEmailAndUsernameRequestDto);
    @PutMapping("/delete")
    public ResponseEntity<Boolean> delete(DeleteRequestDto dto);
}
