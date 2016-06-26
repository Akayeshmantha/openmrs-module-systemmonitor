<spring:htmlEscape defaultHtmlEscape="true" />
<ul id="menu">
	<li class="first"><a
		href="${pageContext.request.contextPath}/admin"><spring:message
				code="admin.title.short" /></a></li>

	<li
		<c:if test='<%= request.getRequestURI().contains("/dhisDataValues") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/systemmonitor/dhisDataValues.form"><spring:message
				code="systemmonitor.dhisDataValues" /></a>
	</li>
	
	<!-- Add further links here -->
</ul>
<h2>
	<spring:message code="systemmonitor.title" />
</h2>
