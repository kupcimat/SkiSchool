package controllers;

import controllers.deadbolt.DeadboltHandler;
import controllers.deadbolt.ExternalizedRestrictionsAccessor;
import controllers.deadbolt.RestrictedResourcesHandler;
import models.*;
import models.deadbolt.ExternalizedRestrictions;
import models.deadbolt.RoleHolder;

public class Security extends Secure.Security implements DeadboltHandler {

	static boolean authenticate(String username, String password) {
		return User.connect(username, password) != null;
	}

	static void onDisconnected() {
		Application.index();
	}

	@Override
	public void beforeRoleCheck() {
		if (!Secure.Security.isConnected()) {
			try {
				if (!session.contains("username")) {
					flash.put("url", "GET".equals(request.method) ? request.url : "/");
					Secure.login();
				}
			} catch (Throwable t) {
				// handle this in an app-specific way
			}
		}
	}

	@Override
	public RoleHolder getRoleHolder() {
		String userName = Secure.Security.connected();
		return User.getByEmail(userName);
	}

	@Override
	public void onAccessFailure(String controllerClassName) {
		forbidden();
	}

	@Override
	public ExternalizedRestrictionsAccessor getExternalizedRestrictionsAccessor() {
		return new ExternalizedRestrictionsAccessor() {
			public ExternalizedRestrictions getExternalizedRestrictions(String name) {
				return null;
			}
		};
	}

	@Override
	public RestrictedResourcesHandler getRestrictedResourcesHandler() {
		return null;
	}

}