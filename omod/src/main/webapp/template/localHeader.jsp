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
	<li
		<c:if test='<%= request.getRequestURI().contains("/pushToDHIS") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/systemmonitor/pushToDHIS.form"><spring:message
				code="systemmonitor.pushToDHIS" /></a>
	</li>
	<li
		<c:if test='<%= request.getRequestURI().contains("/configurations") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/systemmonitor/configurations.form"><spring:message
				code="systemmonitor.configurations" /></a>
	</li>
	<li
		<c:if test='<%= request.getRequestURI().contains("/localReport") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/systemmonitor/localReport.form"><spring:message
				code="systemmonitor.localReport" /></a>
	</li>
	<li
		<c:if test='<%= request.getRequestURI().contains("/runAsSoonAsStarted") %>'>class="active"</c:if>>
		<a
		href="${pageContext.request.contextPath}/module/systemmonitor/runAsSoonAsStarted.form"><spring:message
				code="systemmonitor.runAsSoonAsStarted" /></a>
	</li>
	<!-- Add further links here -->
</ul>
<h2>
	<spring:message code="systemmonitor.title" />
</h2>
