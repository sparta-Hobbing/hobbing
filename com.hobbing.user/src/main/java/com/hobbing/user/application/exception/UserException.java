package com.hobbing.user.application.exception;

<<<<<<< HEAD
import org.springframework.http.HttpStatus;

public class UserException extends CustomException {
    public UserException(UserErrorCode errorCode) {
        super(errorCode);
=======
import com.hobbing.common.application.exception.CustomException;

public class UserException extends CustomException {
    public UserException(UserErrorCode userErrorCode) {
        super.code = userErrorCode.getCode();
        super.message = userErrorCode.getMessage();
        super.httpStatus = userErrorCode.getHttpStatus();
>>>>>>> dev
    }
}
