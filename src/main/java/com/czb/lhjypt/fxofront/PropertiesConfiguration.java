package com.czb.lhjypt.fxofront;

import com.czb.lhjypt.fxofront.util.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Properties;

@Component
@Slf4j
public class PropertiesConfiguration implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] activeProfiles = SpringContextUtil.getApplicationContext().getEnvironment().getActiveProfiles();
        String evn = null;
        if (activeProfiles != null && activeProfiles.length > 0) {
            evn = activeProfiles[0];
        }
        this.putSystemProperty("application.properties");
        if (evn != null) {
            this.putSystemProperty("config/application-" + evn + ".properties");
        }
        //log.info("PropertiesConfiguration netty.server.port:" + System.getProperty("netty.server.port"));
		/*Properties p = System.getProperties();
		for(Object key:p.keySet()){
			System.out.println(key+":"+p.getProperty((String) key));
		}*/
    }

    private void putSystemProperty(String propertiesFileName) throws Exception {
        InputStream inStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesFileName);
        if (inStream != null) {
            Properties prop = new Properties();
            prop.load(inStream);

            for (Object okey : prop.keySet()) {

                String key = (String) okey;
                System.setProperty(key, prop.getProperty(key));
            }
            log.info("Load system property finished: [{}] ", propertiesFileName);
        } else {
            log.warn("Load system property fail, Can't find file: [{}]", propertiesFileName);
        }
    }

}
