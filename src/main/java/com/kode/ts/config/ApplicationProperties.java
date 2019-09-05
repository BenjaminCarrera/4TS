package com.kode.ts.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to 4 TS.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	
	private String userImagePath;
	
	public void setUserImagePath(String userImagePath) {
        this.userImagePath = userImagePath;
    }

    public String getUserImagePath() {
        return this.userImagePath;
    }
    
}
