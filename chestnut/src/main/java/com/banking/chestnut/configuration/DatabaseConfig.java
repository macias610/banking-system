package com.banking.chestnut.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

@Component
public class DatabaseConfig {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseConfig(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        try {
            File initFile = new ClassPathResource("data.sql").getFile();
            for(String line : Files.readAllLines(initFile.toPath(), Charset.defaultCharset())){
                jdbcTemplate.execute(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}