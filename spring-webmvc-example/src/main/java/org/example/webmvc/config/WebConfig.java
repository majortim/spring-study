package org.example.webmvc.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
@Import({ThymeleafConfig.class})
@ComponentScan(basePackages = "org.example.webmvc.web", includeFilters = @ComponentScan.Filter({Controller.class, ControllerAdvice.class}))
@EnableWebMvc
@EnableSpringDataWebSupport
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/static/");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
        /*
        registry.addFormatter(new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) throws ParseException {
                return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(locale));

            }

            @Override
            public String print(LocalDate localDate, Locale locale) {
                return DateTimeFormatter.ofPattern("yyyy-MM-dd").withLocale(locale).format(localDate);
            }
        });
        */
    }


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = Jackson2ObjectMapperBuilder
                .json()
                .failOnEmptyBeans(false)
                .locale(Locale.KOREA)
                .timeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")))
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .serializerByType(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
                .deserializerByType(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE))
                .serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .moduleClassLoader( WebMvcConfigurationSupport.class.getClassLoader());

        converters.remove(6);
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
    }
}