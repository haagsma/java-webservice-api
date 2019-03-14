package com.thejavageek.jaxrs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.jboss.resteasy.plugins.interceptors.CorsFilter;

@ApplicationPath("/rest")
public class App extends Application {
	
	final public static String JSON = "application/json";
	private Set<Object> singletons = new HashSet<Object>();private HashSet<Class<?>> classes = new HashSet<Class<?>>();
	
	public App() {
		CorsFilter corsFilter = new CorsFilter();
        corsFilter.getAllowedOrigins().add("*");
        corsFilter.setAllowedMethods("OPTIONS, GET, POST, DELETE, PUT, PATCH");
        singletons.add(corsFilter);
        
        classes.add(AlunoWebService.class);
	}
	
	@Override
    public Set<Object> getSingletons() {
        return singletons;
    }
	@Override
    public HashSet<Class<?>> getClasses(){
      return classes;
    }
	

}