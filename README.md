# spring-security-taglib

Spring security taglib as it should be. Internally original spring security taglib calls spring expression to evaluate:

```
		Expression accessExpression;
		try {
			accessExpression = handler.getExpressionParser().parseExpression(getAccess());
		}
```

This could be made much easier: compare current user roles with requested role.

## Usage

Include dependency:

```
		<dependency>
			<groupId>com.aerse</groupId>
			<artifactId>spring-security-taglib</artifactId>
		</dependency>
```

Configure taglib:

```
<%@ taglib prefix="cfn" uri="https://github.com/dernasherbrezon/spring-security-taglib" %>
```

And use:

```
<cfn:authorize access="ROLE_ANONYMOUS">
	<a href="/login">Login</a>
</cfn:authorize>
<cfn:authorize access="ROLE_USER">
	<a href="/logout">Logout</a>
</cfn:authorize>
```

Supported configuration parameters:
* access. Single role to check
* any. Comma separated roles to check. At least one role required to evaluate body
