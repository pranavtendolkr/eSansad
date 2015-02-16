package com.persistent.esansad.wink;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.persistent.esansad.api.resources.UsersResource;

public class WinkApplication extends Application {
 
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resourceClasses = new HashSet<>();
		
		resourceClasses.add(UsersResource.class);
		return resourceClasses;
	}
}
