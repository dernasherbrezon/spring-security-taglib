package com.aerse.security.taglib;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class Authorize extends BodyTagSupport {

	private static final long serialVersionUID = -2469920560494862487L;

	private static final Pattern COMMA = Pattern.compile(",");
	private String access;
	private Set<String> any = new HashSet<>();

	@Override
	public int doStartTag() throws JspException {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext == null) {
			return BodyTag.SKIP_BODY;
		}
		Authentication auth = securityContext.getAuthentication();
		if (auth == null) {
			return BodyTag.SKIP_BODY;
		}
		if ((access != null && access.trim().length() != 0) && !hasRole(auth.getAuthorities(), access)) {
			return BodyTag.SKIP_BODY;
		}
		if (!any.isEmpty()) {
			for (String curRole : any) {
				if (hasRole(auth.getAuthorities(), curRole)) {
					return BodyTag.EVAL_BODY_INCLUDE;
				}
			}
			return BodyTag.SKIP_BODY;
		}
		return BodyTag.EVAL_BODY_INCLUDE;
	}

	private static boolean hasRole(Collection<? extends GrantedAuthority> roles, String role) {
		for (GrantedAuthority cur : roles) {
			if (cur.getAuthority().equals(role)) {
				return true;
			}
		}
		return false;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public void setAny(String access) {
		String[] parts = COMMA.split(access);
		for (String cur : parts) {
			any.add(cur);
		}
	}

}
