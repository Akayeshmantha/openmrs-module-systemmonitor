<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="template/localHeader.jsp"%>

<h3>
	<spring:message code="systemmonitor.configurations" />
</h3>

<form method="post">
	<table>
		<tr>
			<th><spring:message code="systemmonitor.gp" /></th>
			<th><spring:message code="general.value" /></th>
			<th><spring:message code="general.description" /></th>
		</tr>
		<c:if test="${configurations.mustSetScheduler}">
			<tr>
				<td>${configurations.schedulerUsernameGp.property}</td>
				<td><input type="text"
					value="${configurations.schedulerUsernameGp.propertyValue}"
					name="schedulerUsername" /></td>
				<td>${configurations.schedulerUsernameGp.description}</td>
			</tr>
			<tr>
				<td>${configurations.schedulerPasswordGp.property}</td>
				<td><input type="password"
					value="${configurations.schedulerPasswordGp.propertyValue}"
					name="schedulerPassword" /></td>
				<td>${configurations.schedulerPasswordGp.description}</td>
			</tr>
		</c:if>
		<tr>
			<td>${configurations.viralLoadConceptIdGp.property}</td>
			<td><input type="text"
				value="${configurations.viralLoadConceptIdGp.propertyValue}"
				name="viralLoadConceptId" /></td>
			<td>${configurations.viralLoadConceptIdGp.description}</td>
		</tr>
		<tr>
			<td>${configurations.cd4CountConceptIdGp.property}</td>
			<td><input type="text"
				value="${configurations.cd4CountConceptIdGp.propertyValue}"
				name="cd4CountConceptId" /></td>
			<td>${configurations.cd4CountConceptIdGp.description}</td>
		</tr>
		<tr>
			<td>${configurations.reasonForExistingCareConceptIdGp.property}</td>
			<td><input type="text"
				value="${configurations.reasonForExistingCareConceptIdGp.propertyValue}"
				name="reasonForExistingCareConceptId" /></td>
			<td>${configurations.reasonForExistingCareConceptIdGp.description}</td>
		</tr>	
		<tr>
			<td>${configurations.arvDrugsConceptSetGp.property}</td>
			<td><input type="text"
				value="${configurations.arvDrugsConceptSetGp.propertyValue}"
				name="arvDrugsConceptSetId" /></td>
			<td>${configurations.arvDrugsConceptSetGp.description}</td>
		</tr>
		<tr>
			<td>${configurations.numberOfVisitsMetricEncounterTypeIdsGp.property}</td>
			<td><input type="text"
				value="${configurations.numberOfVisitsMetricEncounterTypeIdsGp.propertyValue}"
				name="numberOfVisitsMetricEncounterTypeIds" /></td>
			<td>${configurations.numberOfVisitsMetricEncounterTypeIdsGp.description}</td>
		</tr>
		<tr>
			<td>${configurations.numberOfTotalNewPatientsMetricEncounterTypeIdsGp.property}</td>
			<td><input type="text"
				value="${configurations.numberOfTotalNewPatientsMetricEncounterTypeIdsGp.propertyValue}"
				name="numberOfTotalNewPatientsMetricEncounterTypeIds" /></td>
			<td>${configurations.numberOfTotalNewPatientsMetricEncounterTypeIdsGp.description}</td>
		</tr>
		<tr>
			<td>${configurations.hivProgramIdGp.property}</td>
			<td><input type="text"
				value="${configurations.hivProgramIdGp.propertyValue}"
				name="hivProgramId" /></td>
			<td>${configurations.hivProgramIdGp.description}</td>
		</tr>
		<tr>
			<td>${configurations.siteIdGp.property}</td>
			<td><input type="text"
				value="${configurations.siteIdGp.propertyValue}" name="siteId" /></td>
			<td>${configurations.siteIdGp.description}</td>
		</tr>
		<tr>
			<td>${configurations.dhisAPIRootUrlGp.property}</td>
			<td><input type="text"
				value="${configurations.dhisAPIRootUrlGp.propertyValue}"
				name="dhisAPIRootUrl" /></td>
			<td>${configurations.dhisAPIRootUrlGp.description}</td>
		</tr>
		<tr>
			<td>${configurations.dhisUsernameGp.property}</td>
			<td><input type="text"
				value="${configurations.dhisUsernameGp.propertyValue}"
				name="dhisUsername" /></td>
			<td>${configurations.dhisUsernameGp.description}</td>
		</tr>
		<tr>
			<td>${configurations.dhisPasswordGp.property}</td>
			<td><input type="password"
				value="${configurations.dhisPasswordGp.propertyValue}"
				name="dhisPassword" /></td>
			<td>${configurations.dhisPasswordGp.description}</td>
		</tr>

		<tr>
			<td><spring:message code="systemmonitor.opening.days" /></td>
			<td><input type="text" name="openingDays"
				value="${configurations.openingDaysGp.propertyValue}" /></td>
			<td><spring:message code="systemmonitor.opening.days.desc" /></td>
		</tr>
		<tr>
			<td><spring:message code="systemmonitor.opening.hour" /></td>
			<td><input type="text" name="openingHour"
				value="${configurations.openingHourGp.propertyValue}" /></td>
			<td><spring:message code="systemmonitor.opening.hour.desc" /></td>
		</tr>
		<tr>
			<td><spring:message code="systemmonitor.closing.hour" /></td>
			<td><input type="text" id="clinicEnd" name="closingHour"
				value="${configurations.closingHourGp.propertyValue}" /></td>
			<td><spring:message code="systemmonitor.closing.hour.desc" /></td>
		</tr>

		<tr>
			<td><input type="submit"
				value='<spring:message code="systemmonitor.configurations.save"/>' /></td>
		</tr>
	</table>
</form>
<%@ include file="/WEB-INF/template/footer.jsp"%>