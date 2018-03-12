package org.fran.cameldemo.camelspringboot;

import org.apache.camel.builder.RouteBuilder;
import org.fran.cameldemo.camelspringboot.anno.FtlMethod;
import org.fran.cameldemo.camelspringboot.anno.FtlService;
import org.fran.cameldemo.camelspringboot.vo.ServiceFtlResultVO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Map;

@EnableAutoConfiguration
@SpringBootApplication(scanBasePackageClasses = {
        Application.class
})
public class Application extends RouteBuilder {

    @Resource
    private ApplicationContext applicationContext;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void configure() throws Exception {
        String[] consumers = applicationContext.getBeanNamesForAnnotation(FtlService.class);
        if(consumers!= null) {
            for (String cs : consumers) {
                Object csObj = applicationContext.getBean(cs);
                FtlService ftlService = csObj.getClass().getAnnotation(FtlService.class);

                if(ftlService != null){
                    Method[] methods = csObj.getClass().getMethods();
                    for(Method m : methods){
                        FtlMethod ftlMethod = m.getAnnotation(FtlMethod.class);
                        if(ftlMethod!= null){
                            Class<?> type = ftlMethod.parameterType();
                            String classPath = ftlService.getPath();
                            String methodPath = ftlMethod.path();
                            if(type == null || classPath == null || methodPath == null
                                    || "".equals(classPath) || "".equals(methodPath)){
                                throw new IllegalArgumentException("FtlService & FtlMethod param null!");
                            }
                            String endPoint = "direct:" + classPath + "/" + methodPath;

                            BeanMap toDto = BeanMap.create(type.newInstance());
                            from(endPoint)
                                    .transform()
                                    .body((a,b)->{
                                        Object dto = null;
                                        try {
                                            dto = type.newInstance();
                                            BeanMap r = toDto.newInstance(dto);
                                            ServiceFtlResultVO request = (ServiceFtlResultVO) a;
                                            r.putAll((Map)request.getRequest());
                                            request.setRequest(dto);
                                            return request;
                                        } catch (Exception e) {
                                            System.out.println("error:[" + endPoint + "]");
                                            e.printStackTrace();
                                        }
                                        return null;

                                    })
                                    .bean(csObj, m.getName())
                                    .end();
                        }
                    }
                }
            }
        }
    }
}
