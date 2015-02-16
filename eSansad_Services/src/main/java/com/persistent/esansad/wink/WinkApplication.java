package com.persistent.esansad.wink;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.persistent.esansad.api.resources.ConstituenciesResource;
import com.persistent.esansad.api.resources.FileUploadService;
import com.persistent.esansad.api.resources.UsersResource;
import com.persistent.esansad.api.resources.VotesResource;

public class WinkApplication extends Application {
 
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resourceClasses = new HashSet<Class<?>>();
		
		resourceClasses.add(UsersResource.class);
		resourceClasses.add(ConstituenciesResource.class);
		resourceClasses.add(FileUploadService.class);
		resourceClasses.add(VotesResource.class);
		return resourceClasses;
	}
}
