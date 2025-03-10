package hello.typeconveter.formatter;

import hello.typeconveter.converter.IpPortToStringConverter;
import hello.typeconveter.converter.StringToIpPortConverter;
import hello.typeconveter.type.IpPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.format.support.DefaultFormattingConversionService;

import static org.assertj.core.api.Assertions.assertThat;

public class FormattingConversionServiceTest {

    @Test
    public void formattingConversionService(){
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

        // 컨버터 등록
        conversionService.addConverter(new StringToIpPortConverter());
        conversionService.addConverter(new IpPortToStringConverter());

        // 포맷터 등록
        conversionService.addFormatter(new MyNumberFormatter());

        // 컨버터 사용
        IpPort ipPort = conversionService.convert("127.0.0.1:8080", IpPort.class);
        assertThat(ipPort).isEqualTo(new IpPort("127.0.0.1", 8080));

        // 포맷터 사용
        String convert_1 = conversionService.convert(1000, String.class);
        assertThat(convert_1).isEqualTo("1,000");

        Long convert_2 = conversionService.convert("1,000", Long.class);
        assertThat(convert_2).isEqualTo(1000L);
    }
}
