<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<openmrs:require privilege="View Administration Functions" otherwise="/login.htm" redirect="dhisDataValues.form" />

<openmrs:htmlInclude file="/moduleResources/systemmonitor/renderjson.js" />

<style type="text/css">
#renderDHISDataValueSets {
	font-size: 150%;
	border-radius: 8px;
	border: 1px solid #15719a;
	margin: 12px 12px 0 12px;
	padding-left: 0.3em;
}

.renderjson a {
	text-decoration: none;
}

.renderjson .disclosure {
	color: crimson;
	font-size: 150%;
}

.renderjson .syntax {
	color: grey;
}

.renderjson .string {
	color: #15719a;
}

.renderjson .number {
	color: cyan;
}

.renderjson .boolean {
	color: plum;
}

.renderjson .key {
	color: lightblue;
}

.renderjson .keyword {
	color: lightgoldenrodyellow;
}

.renderjson .object.syntax {
	color: lightseagreen;
}

.renderjson .array.syntax {
	color: lightsalmon;
}
</style>

<br />
<br />
<h3>
	<spring:message code="systemmonitor.data" />
</h3>
<div id="renderDHISDataValueSets"></div>

<script type="text/javascript">
	var jsonData = ${data};

	document.getElementById("renderDHISDataValueSets").appendChild(
			renderjson(jsonData));
</script>
<%@ include file="/WEB-INF/template/footer.jsp"%>