package site.moasis.monolithicbe.common.exception;

import site.moasis.monolithicbe.common.response.ErrorCode;

public class DuplicatedEmailException extends BaseException{
	public DuplicatedEmailException() {super(ErrorCode.COMMON_DUPLICATED_EMAIL);}
	public DuplicatedEmailException(String message) {super(message,ErrorCode.COMMON_DUPLICATED_EMAIL);}


}
