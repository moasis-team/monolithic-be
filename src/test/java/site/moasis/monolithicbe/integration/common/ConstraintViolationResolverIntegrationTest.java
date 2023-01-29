package site.moasis.monolithicbe.integration.common;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import site.moasis.monolithicbe.common.exception.ConstraintViolationResolver;
import site.moasis.monolithicbe.common.exception.FieldErrorDetail;
import site.moasis.monolithicbe.common.exception.SelfValidating;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Transactional
@SpringBootTest
public class ConstraintViolationResolverIntegrationTest {

    @Autowired
    ConstraintViolationResolver sut;

    @Builder
    private static class TestCommand extends SelfValidating<TestCommand> {
        @Size(min = 1, max = 5)
        String string;

        public TestCommand(String string) {
            this.string = string;
            validateSelf();
        }
    }

    @Test
    void 오류_코드로_메시지를_조회하면_메시지의_플레이스홀더를_값으로_치환한다() {
        // given
        String string = "666666";
        List<FieldErrorDetail> fieldErrorDetails = null;

        // when
        try {
            TestCommand.builder()
                    .string(string)
                    .build();
        } catch (ConstraintViolationException constraintViolationException) {
            fieldErrorDetails = constraintViolationException.getConstraintViolations()
                    .stream()
                    .map(sut::toFiledErrorDetail)
                    .collect(Collectors.toList());
        }

        // then
        assertThat(fieldErrorDetails.get(0).getCode()).isEqualTo("Size.testCommand.string");
        assertThat(fieldErrorDetails.get(0).getField()).isEqualTo("string");
        assertThat(fieldErrorDetails.get(0).getMessage()).isEqualTo("문자의 길이는 1 ~ 5 사이 입니다. 현재 문자열 값 [666666]");
    }

    @Builder
    private static class TestCommand2 extends SelfValidating<TestCommand> {
        @Max(100)
        private Integer integer;

        public TestCommand2(Integer integer) {
            this.integer = integer;
            validateSelf();
        }
    }

    @Test
    void 오류_코드로_메시지를_조회하지_못하면_기본값을_사용한다() {
        // given
        Locale.setDefault(new Locale(Locale.US.getLanguage(), Locale.US.getCountry()));
        Integer integer = 1000;
        List<FieldErrorDetail> fieldErrorDetails = null;

        // when
        try {
            TestCommand2.builder()
                    .integer(integer)
                    .build();
        } catch (ConstraintViolationException constraintViolationException) {
            fieldErrorDetails = constraintViolationException.getConstraintViolations()
                    .stream()
                    .map(sut::toFiledErrorDetail)
                    .collect(Collectors.toList());
        }

        // then
        assertThat(fieldErrorDetails.get(0).getCode()).isNull();
        assertThat(fieldErrorDetails.get(0).getField()).isEqualTo("integer");
        assertThat(fieldErrorDetails.get(0).getMessage()).isEqualTo("must be less than or equal to 100");
    }

}
