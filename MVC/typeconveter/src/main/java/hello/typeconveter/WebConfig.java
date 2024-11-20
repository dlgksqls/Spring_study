package hello.typeconveter;

import hello.typeconveter.converter.IntegerToStringConverter;
import hello.typeconveter.converter.IpPortToStringConverter;
import hello.typeconveter.converter.StringToIntegerConverter;
import hello.typeconveter.converter.StringToIpPortConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToIntegerConverter());
        registry.addConverter(new IntegerToStringConverter());

        // 이것 두개는 커스텀해서 등록해줘야함
        registry.addConverter(new StringToIpPortConverter());
        registry.addConverter(new IpPortToStringConverter());
    }
}
