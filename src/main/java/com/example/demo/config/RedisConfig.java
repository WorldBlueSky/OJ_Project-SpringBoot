package com.example.demo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

@Configuration
public class RedisConfig {
    //配置我们自己的redisTemplate  固定模板

    @Bean
    @SuppressWarnings("all") //告诉编译器忽略全部的警告，不用在编译完成后出现警告信息
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory)
            throws UnknownHostException {

        //我们为了自己开发方便，一般直接使用<String, Object>类型
        RedisTemplate<String, Object> template = new RedisTemplate<String,Object>();
        //连接工厂
        template.setConnectionFactory(factory);

        //Json的序列化配置
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper(); //JackSon对象
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        //String类型的序列化配置
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();


        //Key采用String的序列化操作
        template.setKeySerializer(stringRedisSerializer);
        //Hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //value序列化采用jackson
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //Hash的value序列化也采用jackson
        template.setHashValueSerializer(jackson2JsonRedisSerializer);

        //配置完之后将所有的properties设置进去
        template.afterPropertiesSet();
        return template;
    }
}