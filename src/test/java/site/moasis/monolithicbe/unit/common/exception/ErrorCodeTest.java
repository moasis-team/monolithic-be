package site.moasis.monolithicbe.unit.common.exception;

import org.junit.jupiter.api.Test;
import site.moasis.monolithicbe.common.exception.ErrorCode;
import static org.assertj.core.api.Assertions.assertThat;

public class ErrorCodeTest {

    @Test
    void ErrorCode_getCode_성공() {
        // given
        ErrorCode errorCode = ErrorCode.INTERNAL_SERVER_ERROR;

        // when
        String code = errorCode.getCode();

        // then
        assertThat(code).isEqualTo("internalServerError");
    }

}
