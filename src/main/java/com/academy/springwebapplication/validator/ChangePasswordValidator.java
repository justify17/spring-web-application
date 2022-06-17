package com.academy.springwebapplication.validator;

import com.academy.springwebapplication.dto.ChangedAccountDataDto;
import com.academy.springwebapplication.model.entity.User;
import com.academy.springwebapplication.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class ChangePasswordValidator implements Validator {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean supports(Class<?> clazz) {
        return ChangedAccountDataDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ChangedAccountDataDto changedAccountDataDto = (ChangedAccountDataDto) target;

        if (isOldPasswordNotMatch(changedAccountDataDto)){
            errors.rejectValue("password","error.password","Invalid password");
        }

        if(isNewPasswordsNotEqual(changedAccountDataDto)){
            errors.rejectValue("newPassword","error.newPassword", "New passwords do not match");
        }
    }

    private boolean isOldPasswordNotMatch(ChangedAccountDataDto changedAccountDataDto) {
        String oldPassword = changedAccountDataDto.getPassword();

        User user = userRepository.findByUsername(changedAccountDataDto.getUsername());

        return !passwordEncoder.matches(oldPassword, user.getPassword());
    }

    private boolean isNewPasswordsNotEqual(ChangedAccountDataDto changedAccountDataDto) {
        String newPassword = changedAccountDataDto.getNewPassword();
        String confirmNewPassword = changedAccountDataDto.getConfirmNewPassword();

        return !newPassword.equals(confirmNewPassword);
    }

}
