package io.sporkpgm.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInfo {
	
    public String name();
    
    public Class<? extends Module> module();
    
    public String desc() default "";
    
    public Class<? extends Module>[] requires() default {};

}
