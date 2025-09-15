package com.gpbapp.metadataregistry.config;


import org.springframework.context.annotation.Configuration;

@Configuration
public class IgniteConfig {

//    @Bean
//    public Ignite igniteInstance() {
//        IgniteConfiguration cfg = new IgniteConfiguration();
//        cfg.setClientMode(true);
//
//        CacheConfiguration<MetadataKey, MetaData> cacheCfg =
//                new CacheConfiguration<>("metadataCache");
//
//        cacheCfg.setIndexedTypes(MetadataKey.class, MetaData.class);
//        cfg.setCacheConfiguration(cacheCfg);
//
//        return Ignition.start(cfg);
//    }
}
