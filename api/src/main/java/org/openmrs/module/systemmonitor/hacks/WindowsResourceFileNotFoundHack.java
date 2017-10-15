package org.openmrs.module.systemmonitor.hacks;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.SystemUtils;
import org.openmrs.module.systemmonitor.SystemMonitorConstants;

import java.io.IOException;

public class WindowsResourceFileNotFoundHack {
	/**
	 * This must be updated every time
	 * src/main/resources/dhis-mappings.properties is updated
	 */
	private static String mappings = "## For this tool to report to DHIS/HMIS; DHIS data from the actual DHIS server must be first mapped correctly to much the tool's data"
			+ "\n## This is done through two steps;" + "\n##\tCorrecting mappings for DHIS Data Elements"
			+ "\n##\tCorrecting mappings for Organization Units"
			+ "\n## NOTE: Please only edit the lines in between the -----------------... lines, and only text after the '=' character"
			+ "\n\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
			+ "\n## MAPPING DHIS DATA ELEMENTS"
			+ "\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
			+ "\n## 'emtdataelementcode=dhis-dataelement-id' is the data element format to follow while mapping"
			+ "\n------------------------------------------------------------------------------------"
			+ "\nDATA-ELEMENT_systemId=yBHJmoeteNR" + "\nDATA-ELEMENT_openmrsAppName=ec9fC1xmg8R"
			+ "\nDATA-ELEMENT_primaryCareDays=rb9ef1D53Fv" + "\nDATA-ELEMENT_primaryCareHours=VDEnb2bEQH3"
			+ "\nDATA-ELEMENT_totalEncounters_rwanda=RYe2tuO9njZ" + "\nDATA-ELEMENT_totalObservations_rwanda=NorJph8rRjt"
			+ "\nDATA-ELEMENT_totalUsers_rwanda=GKi8zBGuC3p" + "\nDATA-ELEMENT_totalVisits_rwanda=nqGCy0uyzm8"
			+ "\nDATA-ELEMENT_totalPatientsNew=aGdN2xl9nUj"
			+ "\nDATA-ELEMENT_totalPatientsActive=hk0HYxaBPtz"
			+ "\nDATA-ELEMENT_openmrsUptime=OBJQIpvppBt" + "\nDATA-ELEMENT_systemUptime-thisWeek=CrZDptrDUqA"
			+ "\nDATA-ELEMENT_systemUptime-lastWeek=h08FIw8cVUD"
			+ "\nDATA-ELEMENT_systemUptime-lastMonth=q9MRIo5DX4I"
			+ "\nDATA-ELEMENT_totalMemory=FRANuyR9bKI" + "\nDATA-ELEMENT_usedMemory=QZMqiNLOZNH"
			+ "\nDATA-ELEMENT_freeMemory=ZPrLSHvWDm8" + "\nDATA-ELEMENT_systemStartupCounts=q8LwlSrBOSj"
			+ "\nDATA-ELEMENT_patientsViralLoadTestResults_ever=AB7Nj3FNvR6"
			+ "\nDATA-ELEMENT_patientsViralLoadTestResults_last6Months=TIdLXyneWYd"
			+ "\nDATA-ELEMENT_patientsViralLoadTestResults_LastYear=D30SnV0TGws"
			+ "\nDATA-ELEMENT_systemInfo_operatingSystemName=CRThksbOPAd"
			+ "\nDATA-ELEMENT_systemInfo_operatingSystemVersion=l76OuWQxBYa"
			+ "\nDATA-ELEMENT_systemInfo_operatingSystemArch=EFfJ4CVvac2"
			+ "\nDATA-ELEMENT_systemInfo_javaVersion=FmjhhJQyLoy"
			+ "\nDATA-ELEMENT_systemInfo_javaVendor=TuBY7HgyJ7J"
			+ "\nDATA-ELEMENT_systemInfo_jvmVersion=RgJN8fbRRuW"
			+ "\nDATA-ELEMENT_systemInfo_jvmVendor=yIMQP0rsprI"
			+ "\nDATA-ELEMENT_systemInfo_javaRuntimeName=SrKrLKmigUg"
			+ "\nDATA-ELEMENT_systemInfo_javaRuntimeVersion=ixpB1MGxTOt"
			+ "\nDATA-ELEMENT_systemInfo_userName=KiOmWTYmc4F"
			+ "\nDATA-ELEMENT_systemInfo_systemLanguage=RHNugiFzCnz"
			+ "\nDATA-ELEMENT_systemInfo_systemTimezone=GldPX7AtrxQ"
			+ "\nDATA-ELEMENT_systemInfo_systemDateTime=vXlzpA8wi6K"
			+ "\nDATA-ELEMENT_systemInfo_fileSystemEncoding=RlDJSS2SRXL"
			+ "\nDATA-ELEMENT_systemInfo_userDirectory=Fy0lelr1WAB"
			+ "\nDATA-ELEMENT_systemInfo_tempDirectory=FveZHUNsC1o"
			+ "\nDATA-ELEMENT_systemInfo_openMRSVersion=Gnpcar514A3"
			+ "\nDATA-ELEMENT_systemInfo_installedModules=ICTGPRAVZVZ"
			+ "\nDATA-ELEMENT_systemRealLocation=hKJXqpumYZ6"
			+ "\nDATA-ELEMENT_patientsCD4CountTestResults_ever=UEfoD5rcZbC"
			+ "\nDATA-ELEMENT_patientsCD4CountTestResults_last6Months=d3XqMIFIoz6"
			+ "\nDATA-ELEMENT_patientsCD4CountTestResults_LastYear=GHq0YsrAsaf"
			+ "\nDATA-ELEMENT_serverUptimeSecs=RiyIySUEl1W" + "\nDATA-ELEMENT_openmrsUptimeSecs=WdPwtuNKhcd"
			+ "\nDATA-ELEMENT_processor=xr3kif2Vuzu" + "\nDATA-ELEMENT_newEncounters=oUoKdxhelRG"
			+ "\nDATA-ELEMENT_newObservations=BHXAiiCvX5K" + "\nDATA-ELEMENT_newUsers=luKfl7hZZek"
			+ "\nDATA-ELEMENT_newEncounters_adultinitial=HkctO4QQgte"
			+ "\nDATA-ELEMENT_newEncounters_adultreturn=aHYdS1cNuam"
			+ "\nDATA-ELEMENT_newEncounters_predsinitial=UidOuL65vMw"
			+ "\nDATA-ELEMENT_newEncounters_pedsreturn=CdlLkU6tqSX"
			+ "\nDATA-ELEMENT_patientsCD4CountTestResults_new=QoIyYyc7Z36"
			+ "\nDATA-ELEMENT_patientsViralLoadTestResults_new=uaUc1zJbaEC"
			+ "\nDATA-ELEMENT_dataForLastBackup=v3Pww13i2r1"
			+ "\nDATA-ELEMENT_newTotalPatientsNew=JNsXijeCdIx"
			+ "\nDATA-ELEMENT_newTotalPatientsActive=GCC9L1LGFBH"
			+ "\nDATA-ELEMENT_activePatient20=SWpmUA95Gtv"
			+ "\nDATA-ELEMENT_activePatient8=m21uNOTO7Tp"
			+ "\nDATA-ELEMENT_activePatient20_CD4_lastYear=SVwRZ9dSTdb"
			+ "\nDATA-ELEMENT_activePatient20_Vl_lastYear=HMCX3fD2CkG" 
			+ "\nDATA-ELEMENT_activePatient8_Vl_EMR=wHr69AiiuZU"
			+ "\nDATA-ELEMENT_activePatient8_CD4_EMR=nD2alAworkZ"
			+ "\nDATA-ELEMENT_initialVL=fEgFipkmtNq"
			+ "\nDATA-ELEMENT_initialCD4=RQTlblrf6eR"
			+ "\nDATA-ELEMENT_followUpVL=oRuD83Dnoqd"
			+ "\nDATA-ELEMENT_followUpCD4=lj3Wh3XwgVu"
			+ "\nDATA-ELEMENT_downTimePercentage=e9VBv3quDim"
			+ "\nDATA-ELEMENT_downTimeMinutes=q5K2raiuqQh"
			+ "\nDATA-ELEMENT_downTimeIntervals=uzD2FZ52RFK"
			+ "\nDATA-ELEMENT_upTimeIntervals=YjIxaOR1Vtj"
			+ "\nDATA-ELEMENT_previousWeekEncounters=BBB69AiiuZU"
			+ "\nDATA-ELEMENT_previousMonthEncounters=BBB69AiiuAA"
			+ "\nDATA-ELEMENT_previousWeekObservations=BBB69AiiuCC"
			+ "\nDATA-ELEMENT_previousMonthObservations=BBB69AiiuDD"
			+ "\nDATA-ELEMENT_previousWeekPatients=BBB69AiiuEE"
			+ "\nDATA-ELEMENT_previousMonthPatients=BBB69AiiuFF"
			+ "\nDATA-ELEMENT_executionDateTime=BAB69AiiuZZ"
			+ "\nDATA-ELEMENT_totalEncounters=ZZB69AiiuAA"
			+ "\nDATA-ELEMENT_totalObservations=ZZB69AiiuBV"
			+ "\nDATA-ELEMENT_totalUsers=ZZB69AiiuRT"
			+ "\nDATA-ELEMENT_totalVisits=ZZB69AiiuOI"
			+ "\nDATA-ELEMENT_totalPatients=ZZB69AiiuUU"
			+ "\nDATA-ELEMENT_totalEncounters_new=ZZB69AiiuMM"
			+ "\nDATA-ELEMENT_totalObservations_new=ZZB69AiiuPO"
			+ "\nDATA-ELEMENT_totalUsers_new=ZZB69AiiuWE"
			+ "\nDATA-ELEMENT_totalVisits_new=ZZB69AiiuEE"
			+ "\nDATA-ELEMENT_totalPatients_new=ZZB69AiiuTT"
			+ "\nDATA-ELEMENT_totalOrders=PPB69AiiuPP"
			+ "\nDATA-ELEMENT_totalOrders_new=PPB69AiiuAA"
			+ "\nDATA-ELEMENT_totalConcepts=PPB69AiiuPW"
			+ "\nDATA-ELEMENT_totalConcepts_new=PPB69AiiuAW"
			+ "\nDATA-ELEMENT_totalLocations=PPB69AiiuPW"
			+ "\nDATA-ELEMENT_totalLocations_new=PPB69AiiuAF"
			+ "\nDATA-ELEMENT_totalForms=PPB69AiiuPR"
			+ "\nDATA-ELEMENT_totalForms_new=PPB69AiiuAR"
			+ "\n------------------------------------------------------------------------------------" + "\n\n"
			+ "\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
			+ "\n## MAPPING DHIS SUPPORTED ORGANISATION UNITS"
			+ "\n++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"
			+ "\n## fosaid=dhis2uID is the format each mapping must follow (fosaid is the unique server Id that needs to be mapped to an existing DHIS UID)"
			+ "\n## This list is provided as part of the Study Sites/servers by the Rwanda SPU study team"
			+ "\n## The last mapping whose fosid is JEMBI is for the Jembi test environment"
			+ "\n------------------------------------------------------------------------------------"
			+ "\n107=BQ6Kvt02NYY" + "\n1103=ugY1922BR55" + "\n113=CeUh4uCoQ3A" + "\n118=oyCnY8ItZ63"
			+ "\n120=j2qbe5TmxKy" + "\n121=bFxvEvBCL0T" + "\n122=kFdoH8Wp4HN" + "\n125=gFxLFth8Aw4"
			+ "\n125=gFxLFth8Aw4" + "\n142=hFG3GjDpSz7" + "\n143=gCwAtxRPHGq" + "\n147=NKQShbA52bc"
			+ "\n149=WskKjxqBH5P" + "\n150=Hkp8eisYz6j" + "\n151=CeVJXduK0fn" + "\n155=lBxkzBuDuyp"
			+ "\n158=ueEKzJwO9kf" + "\n159=I61dQKlZ8G0" + "\n16=efJxdTem7M4" + "\n160=hls3zLeUGFu" + "\n163=XtvkcthcT44"
			+ "\n166=ikqbiLMmE6i" + "\n167=MyQyCgynZ37" + "\n170=q1FwOQo9waZ" + "\n171=AR4WkJpbDcc"
			+ "\n177=RzuhrNxCAvJ" + "\n178=uWAq817TIeO" + "\n179=HkPQKvi8EqL" + "\n181=xfXOundWGw5"
			+ "\n183=jaQ4DmKUghP" + "\n186=hV87OCHgO4v" + "\n187=Oy3YrOJMyWk" + "\n188=tO9CpvxsTx1"
			+ "\n192=v5QtLCZMN61" + "\n193=xJ1Y6sH5yoD" + "\n194=fmkyYI7d0Jy" + "\n195=ryHi2WBeo4a"
			+ "\n196=WW8hkYaxybD" + "\n197=rnM981srks2" + "\n2=fGha3KRGmC8" + "\n201=cbQ7xglzZf1" + "\n203=XPVy8lRPJpd"
			+ "\n207=H20elb9Y79s" + "\n212=Z9AtH7XJ1Vg" + "\n217=rZG239wwQkF" + "\n218=o13EyR5whj1"
			+ "\n228=MNBjpiNQ9hi" + "\n230=zOF7aDynhoB" + "\n231=vKwJccfuhYc" + "\n232=uHz1c1Y9xQr"
			+ "\n233=YBXdkoXuynx" + "\n234=MxS4FEL6GSR" + "\n255=lKWdtos0ojO" + "\n256=J0j1xb37YYJ"
			+ "\n258=rcxHfloUczM" + "\n259=NJDkBCeYx9s" + "\n26=k0x0QmE6wah" + "\n260=UatEHyBfTkd" + "\n261=TlW7l2DqCoe"
			+ "\n263=LYUCoyycN16" + "\n264=Km4dwozvoi1" + "\n265=QnxmgSaTMh9" + "\n266=F6yOxGNU4hF"
			+ "\n267=bAACJeoNHYv" + "\n269=NXUwncads4R" + "\n270=GXbNYRE8ZzT" + "\n274=o1d0BbsPhcF"
			+ "\n276=jeiM0yEesl9" + "\n277=ykXMNOjkYG7" + "\n278=mkeeAHOynYc" + "\n279=UhhATkOva0k" + "\n28=Od4EuZl2bbZ"
			+ "\n280=Dfr3u48nJzc" + "\n284=RvcQ2BCQZTM" + "\n285=pU54lCB6csX" + "\n286=FIeQuZdhz5d"
			+ "\n287=uIzwKRz1083" + "\n288=mgrAIR9zEGP" + "\n289=bQvQTtF2qUy" + "\n290=d6luyFEuGqV"
			+ "\n291=bJU4zRNz3ki" + "\n292=cwTLPnF8SgI" + "\n293=eoNeJzh8Gb5" + "\n294=fWkyPlh63iC"
			+ "\n295=PfhIPt8QQIF" + "\n30=DegiMX7LbtQ" + "\n303=KEEVAaerMNe" + "\n304=PsPeDqz42PE" + "\n305=N3irGruOxY7"
			+ "\n307=HPLByDDQ217" + "\n308=FFRc4CJGgBx" + "\n31=eTN5MvbEJFh" + "\n311=DfgrSUXLVPy" + "\n313=eU4vFNj8hVc"
			+ "\n315=xj3nxWJBlfj" + "\n316=X3VDRgqWC8F" + "\n318=H26oBWDLtpe" + "\n319=IPKVjJMPUFU"
			+ "\n320=imxPGApQhH3" + "\n346=ngBAM6yLmd7" + "\n371=I7GoAFV3VXF" + "\n373=Z5vW78TmZqa"
			+ "\n378=qh5wwPStFji" + "\n38=an9nNfwqi5P" + "\n381=s26sIV6A8jW" + "\n383=X9TV6fJEaTU" + "\n385=Fuc8ULAaSyE"
			+ "\n387=zeGIzxjPn63" + "\n389=Nmuo57zTJ" + "\n39=XNpPZhiJsXZ" + "\n393=NcOuLT16U6k" + "\n393=NcOuLT16U6k"
			+ "\n396=djGdJzEZZpL" + "\n399=vxgHAA1aFro" + "\n40=oGMUMam2KTm" + "\n401=tNt9fEFe9tY" + "\n403=fI8jAwkvB93"
			+ "\n41=uho7GYtsuMr" + "\n411=MILfSPTBw8s" + "\n413=RO54JuENSJG" + "\n418=MzSEK3pAhwj" + "\n42=cI9mw2lF62C"
			+ "\n43=B8tFWAvFlQi" + "\n433=L1ejzhqcmOo" + "\n434=UcTsuqZmZEe" + "\n440=lYG6uqBc1p8" + "\n441=O0erNC90LpM"
			+ "\n442=e594asJLvCr" + "\n443=iqEN9xf6ctf" + "\n445=OZwJLEEFr5N" + "\n446=PJ1NM3rTaeZ"
			+ "\n447=BPZcHDS6OO0" + "\n448=i95svNqPM7N" + "\n450=qyyUCb6ULPt" + "\n452=eP4yz4T6tzX"
			+ "\n455=yafuG4eCHLP" + "\n458=q5b1Nm2fo5l" + "\n513=Ko31kixQW69" + "\n52=a7uoL2YrBPl" + "\n545=WKL5mJUTH0u"
			+ "\n551=J6ATf0qwyOP" + "\n56=f0h0G1cDc9P" + "\n57=lJZh1yVIxcS" + "\n58=GcMWyAgltlT" + "\n591=qO8SPTG8SU5"
			+ "\n592=jMVudx1i8rD" + "\n594=PTONcFtEvuM" + "\n62=NUfKga3Otrc" + "\n63=Ge9JH0pz07L" + "\n64=nTUZO3VFS9M"
			+ "\n642=eSHZKaW3za9" + "\n66=R8EQIcE1Dax" + "\n68=dThqqqyqcYY" + "\n69=MTmFtwBz8" + "\n72=ZtTXsxuFh2S"
			+ "\n76=PxVTxBRyI2B" + "\n79=zwQEjVnk7ie" + "\n80=hTpfGsBvZOu" + "\n86=rtDQKWYjVZc" + "\n87=uAIewYJZEq2"
			+ "\n88=tGC8GzJw1WK" + "\n89=T3DlBaAEL6K" + "\n9=Z0mivz38syU" + "\n91=sJGSKHvEPS9" + "\n92=VtHqSAme8CZ"
			+ "\n93=XrYSh1ygwyf" + "\n94=Qp6EM25AmgR" + "\n96=UNpU4eA39Nv" + "\n97=aOFSTk9Ri7k" + "\nJEMBI=vjFcsoL24z5"
			+ "\n------------------------------------------------------------------------------------";

	/**
	 * This must be updated every time
	 * src/main/resources/dhis-dataelementsMetadata.json is updated
	 */
	public static String dataElementsMetadata = "{ \"pager\": { \"page\": 1, \"pageCount\": 1, \"total\": 58, \"pageSize\": 100 }, \"dataElements\": [ { \"lastUpdated\": \"2016-07-13T07:05:17.318+0000\", \"created\": \"2016-07-13T06:45:38.841+0000\", \"name\": \"Date for last backup\", \"id\": \"v3Pww13i2r1\", \"href\": \"http://82.196.9.250:8080/api/dataElements/v3Pww13i2r1\" }, { \"lastUpdated\": \"2016-05-16T08:31:01.138+0000\", \"created\": \"2016-05-16T08:31:01.125+0000\", \"name\": \"File System Encoding\", \"id\": \"RlDJSS2SRXL\", \"href\": \"http://82.196.9.250:8080/api/dataElements/RlDJSS2SRXL\" }, { \"lastUpdated\": \"2016-02-22T21:25:12.085+0000\", \"created\": \"2016-02-21T11:27:03.298+0000\", \"name\": \"Free Memory (MB)\", \"id\": \"ZPrLSHvWDm8\", \"href\": \"http://82.196.9.250:8080/api/dataElements/ZPrLSHvWDm8\" }, { \"lastUpdated\": \"2016-05-16T08:48:46.331+0000\", \"created\": \"2016-05-16T08:48:46.325+0000\", \"name\": \"Installed Modules\", \"id\": \"ICTGPRAVZVZ\", \"href\": \"http://82.196.9.250:8080/api/dataElements/ICTGPRAVZVZ\" }, { \"lastUpdated\": \"2016-05-16T08:13:48.734+0000\", \"created\": \"2016-05-16T08:13:48.730+0000\", \"name\": \"Java Runtime Name\", \"id\": \"SrKrLKmigUg\", \"href\": \"http://82.196.9.250:8080/api/dataElements/SrKrLKmigUg\" }, { \"lastUpdated\": \"2016-05-16T08:14:28.986+0000\", \"created\": \"2016-05-16T08:14:28.979+0000\", \"name\": \"Java Runtime Version\", \"id\": \"ixpB1MGxTOt\", \"href\": \"http://82.196.9.250:8080/api/dataElements/ixpB1MGxTOt\" }, { \"lastUpdated\": \"2016-05-16T08:11:15.936+0000\", \"created\": \"2016-05-16T08:11:15.932+0000\", \"name\": \"Java Vendor\", \"id\": \"TuBY7HgyJ7J\", \"href\": \"http://82.196.9.250:8080/api/dataElements/TuBY7HgyJ7J\" }, { \"lastUpdated\": \"2016-05-16T08:09:56.165+0000\", \"created\": \"2016-05-16T08:09:19.753+0000\", \"name\": \"Java Version\", \"id\": \"FmjhhJQyLoy\", \"href\": \"http://82.196.9.250:8080/api/dataElements/FmjhhJQyLoy\" }, { \"lastUpdated\": \"2016-05-16T08:12:31.371+0000\", \"created\": \"2016-05-16T08:12:31.363+0000\", \"name\": \"JVM Vendor\", \"id\": \"yIMQP0rsprI\", \"href\": \"http://82.196.9.250:8080/api/dataElements/yIMQP0rsprI\" }, { \"lastUpdated\": \"2016-05-16T08:11:50.278+0000\", \"created\": \"2016-05-16T08:11:50.273+0000\", \"name\": \"JVM Version\", \"id\": \"RgJN8fbRRuW\", \"href\": \"http://82.196.9.250:8080/api/dataElements/RgJN8fbRRuW\" }, { \"lastUpdated\": \"2016-02-21T11:59:54.669+0000\", \"created\": \"2016-02-21T07:54:24.177+0000\", \"name\": \"Number Of System Starts\", \"id\": \"q8LwlSrBOSj\", \"href\": \"http://82.196.9.250:8080/api/dataElements/q8LwlSrBOSj\" }, { \"lastUpdated\": \"2016-02-22T09:58:12.284+0000\", \"created\": \"2016-02-22T09:58:12.277+0000\", \"name\": \"OpenMRS App Name\", \"id\": \"ec9fC1xmg8R\", \"href\": \"http://82.196.9.250:8080/api/dataElements/ec9fC1xmg8R\" }, { \"lastUpdated\": \"2016-02-22T21:37:52.612+0000\", \"created\": \"2016-02-21T11:53:20.730+0000\", \"name\": \"OpenMRS Uptime (%)\", \"id\": \"OBJQIpvppBt\", \"href\": \"http://82.196.9.250:8080/api/dataElements/OBJQIpvppBt\" }, { \"lastUpdated\": \"2016-07-03T21:40:53.466+0000\", \"created\": \"2016-07-01T04:52:08.802+0000\", \"name\": \"OpenMRS Uptime (minutes)\", \"id\": \"WdPwtuNKhcd\", \"href\": \"http://82.196.9.250:8080/api/dataElements/WdPwtuNKhcd\" }, { \"lastUpdated\": \"2016-05-16T08:34:04.818+0000\", \"created\": \"2016-05-16T08:34:04.808+0000\", \"name\": \"OpenMRS Version\", \"id\": \"Gnpcar514A3\", \"href\": \"http://82.196.9.250:8080/api/dataElements/Gnpcar514A3\" }, { \"lastUpdated\": \"2016-05-16T08:10:10.315+0000\", \"created\": \"2016-05-16T08:06:28.206+0000\", \"name\": \"Operating System\", \"id\": \"CRThksbOPAd\", \"href\": \"http://82.196.9.250:8080/api/dataElements/CRThksbOPAd\" }, { \"lastUpdated\": \"2016-05-16T08:10:20.972+0000\", \"created\": \"2016-05-16T08:07:30.528+0000\", \"name\": \"Operating System Arch\", \"id\": \"EFfJ4CVvac2\", \"href\": \"http://82.196.9.250:8080/api/dataElements/EFfJ4CVvac2\" }, { \"lastUpdated\": \"2016-05-16T08:10:32.087+0000\", \"created\": \"2016-05-16T08:08:37.421+0000\", \"name\": \"Operating System Version\", \"id\": \"l76OuWQxBYa\", \"href\": \"http://82.196.9.250:8080/api/dataElements/l76OuWQxBYa\" }, { \"lastUpdated\": \"2016-07-01T05:09:58.632+0000\", \"created\": \"2016-06-30T19:50:28.943+0000\", \"name\": \"Patient CD4 Count Test Results - All\", \"id\": \"UEfoD5rcZbC\", \"href\": \"http://82.196.9.250:8080/api/dataElements/UEfoD5rcZbC\" }, { \"lastUpdated\": \"2016-07-01T05:10:20.261+0000\", \"created\": \"2016-06-30T19:50:56.520+0000\", \"name\": \"Patient CD4 Count Test Results - Last 6 Months\", \"id\": \"d3XqMIFIoz6\", \"href\": \"http://82.196.9.250:8080/api/dataElements/d3XqMIFIoz6\" }, { \"lastUpdated\": \"2016-07-01T05:10:10.452+0000\", \"created\": \"2016-06-30T19:51:19.308+0000\", \"name\": \"Patient CD4 Count Test Results - Last Year\", \"id\": \"GHq0YsrAsaf\", \"href\": \"http://82.196.9.250:8080/api/dataElements/GHq0YsrAsaf\" }, { \"lastUpdated\": \"2016-07-14T08:00:52.280+0000\", \"created\": \"2016-07-11T09:06:52.731+0000\", \"name\": \"Patient CD4 Count Test Results - New\", \"id\": \"QoIyYyc7Z36\", \"href\": \"http://82.196.9.250:8080/api/dataElements/QoIyYyc7Z36\" }, { \"lastUpdated\": \"2016-05-01T17:52:05.525+0000\", \"created\": \"2016-05-01T17:52:05.436+0000\", \"name\": \"Patient Viral Load Test Results - All\", \"id\": \"AB7Nj3FNvR6\", \"href\": \"http://82.196.9.250:8080/api/dataElements/AB7Nj3FNvR6\" }, { \"lastUpdated\": \"2016-05-01T17:52:39.755+0000\", \"created\": \"2016-05-01T17:52:39.723+0000\", \"name\": \"Patient Viral Load Test Results - Last 6 Months\", \"id\": \"TIdLXyneWYd\", \"href\": \"http://82.196.9.250:8080/api/dataElements/TIdLXyneWYd\" }, { \"lastUpdated\": \"2016-05-01T17:53:15.989+0000\", \"created\": \"2016-05-01T17:53:15.982+0000\", \"name\": \"Patient Viral Load Test Results - Last Year\", \"id\": \"D30SnV0TGws\", \"href\": \"http://82.196.9.250:8080/api/dataElements/D30SnV0TGws\" }, { \"lastUpdated\": \"2016-07-14T08:01:07.195+0000\", \"created\": \"2016-07-11T09:07:21.967+0000\", \"name\": \"Patient Viral Load Test Results - New\", \"id\": \"uaUc1zJbaEC\", \"href\": \"http://82.196.9.250:8080/api/dataElements/uaUc1zJbaEC\" }, { \"lastUpdated\": \"2016-02-22T10:00:21.933+0000\", \"created\": \"2016-02-22T10:00:21.919+0000\", \"name\": \"Primary Clinic Days\", \"id\": \"rb9ef1D53Fv\", \"href\": \"http://82.196.9.250:8080/api/dataElements/rb9ef1D53Fv\" }, { \"lastUpdated\": \"2016-02-22T10:01:28.305+0000\", \"created\": \"2016-02-22T10:01:28.294+0000\", \"name\": \"Primary Clinic Hours\", \"id\": \"VDEnb2bEQH3\", \"href\": \"http://82.196.9.250:8080/api/dataElements/VDEnb2bEQH3\" }, { \"lastUpdated\": \"2016-07-03T08:22:16.698+0000\", \"created\": \"2016-07-03T08:22:16.679+0000\", \"name\": \"Processor\", \"id\": \"xr3kif2Vuzu\", \"href\": \"http://82.196.9.250:8080/api/dataElements/xr3kif2Vuzu\" }, { \"lastUpdated\": \"2016-02-22T09:57:43.364+0000\", \"created\": \"2016-02-22T09:57:43.346+0000\", \"name\": \"Server Id\", \"id\": \"yBHJmoeteNR\", \"href\": \"http://82.196.9.250:8080/api/dataElements/yBHJmoeteNR\" }, { \"lastUpdated\": \"2016-06-15T23:39:37.239+0000\", \"created\": \"2016-06-15T23:39:36.920+0000\", \"name\": \"Server's Real Location\", \"id\": \"hKJXqpumYZ6\", \"href\": \"http://82.196.9.250:8080/api/dataElements/hKJXqpumYZ6\" }, { \"lastUpdated\": \"2016-07-03T08:00:23.659+0000\", \"created\": \"2016-07-01T04:51:28.393+0000\", \"name\": \"Server Uptime (minutes)\", \"id\": \"RiyIySUEl1W\", \"href\": \"http://82.196.9.250:8080/api/dataElements/RiyIySUEl1W\" }, { \"lastUpdated\": \"2016-05-16T12:26:00.679+0000\", \"created\": \"2016-05-16T08:24:23.282+0000\", \"name\": \"System DateTime\", \"id\": \"vXlzpA8wi6K\", \"href\": \"http://82.196.9.250:8080/api/dataElements/vXlzpA8wi6K\" }, { \"lastUpdated\": \"2016-05-16T08:17:52.006+0000\", \"created\": \"2016-05-16T08:17:51.995+0000\", \"name\": \"System Language\", \"id\": \"RHNugiFzCnz\", \"href\": \"http://82.196.9.250:8080/api/dataElements/RHNugiFzCnz\" }, { \"lastUpdated\": \"2016-05-16T08:19:02.190+0000\", \"created\": \"2016-05-16T08:19:02.180+0000\", \"name\": \"System Timezone\", \"id\": \"GldPX7AtrxQ\", \"href\": \"http://82.196.9.250:8080/api/dataElements/GldPX7AtrxQ\" }, { \"lastUpdated\": \"2016-02-22T21:36:51.246+0000\", \"created\": \"2016-02-21T07:53:47.279+0000\", \"name\": \"System Uptime - LastMonth (%)\", \"id\": \"q9MRIo5DX4I\", \"href\": \"http://82.196.9.250:8080/api/dataElements/q9MRIo5DX4I\" }, { \"lastUpdated\": \"2016-02-22T21:37:01.730+0000\", \"created\": \"2016-02-21T07:53:05.307+0000\", \"name\": \"System Uptime - LastWeek (%)\", \"id\": \"h08FIw8cVUD\", \"href\": \"http://82.196.9.250:8080/api/dataElements/h08FIw8cVUD\" }, { \"lastUpdated\": \"2016-02-22T21:37:10.224+0000\", \"created\": \"2016-02-21T07:52:49.030+0000\", \"name\": \"System Uptime - ThisWeek (%)\", \"id\": \"CrZDptrDUqA\", \"href\": \"http://82.196.9.250:8080/api/dataElements/CrZDptrDUqA\" }, { \"lastUpdated\": \"2016-05-16T08:32:40.970+0000\", \"created\": \"2016-05-16T08:32:40.965+0000\", \"name\": \"Temporary Directory\", \"id\": \"FveZHUNsC1o\", \"href\": \"http://82.196.9.250:8080/api/dataElements/FveZHUNsC1o\" }, { \"lastUpdated\": \"2016-02-21T11:59:23.539+0000\", \"created\": \"2016-02-10T10:19:34.196+0000\", \"name\": \"Total Encounters\", \"id\": \"RYe2tuO9njZ\", \"href\": \"http://82.196.9.250:8080/api/dataElements/RYe2tuO9njZ\" }, { \"lastUpdated\": \"2016-02-22T21:27:59.936+0000\", \"created\": \"2016-02-21T11:26:42.724+0000\", \"name\": \"Total Memory (MB)\", \"id\": \"FRANuyR9bKI\", \"href\": \"http://82.196.9.250:8080/api/dataElements/FRANuyR9bKI\" }, { \"lastUpdated\": \"2016-02-21T12:00:32.336+0000\", \"created\": \"2016-02-10T10:19:47.725+0000\", \"name\": \"Total Observations\", \"id\": \"NorJph8rRjt\", \"href\": \"http://82.196.9.250:8080/api/dataElements/NorJph8rRjt\" }, { \"lastUpdated\": \"2016-07-14T07:47:32.359+0000\", \"created\": \"2016-02-10T10:20:23.671+0000\", \"name\": \"Total Patients - Active\", \"id\": \"hk0HYxaBPtz\", \"href\": \"http://82.196.9.250:8080/api/dataElements/hk0HYxaBPtz\" }, { \"lastUpdated\": \"2016-02-22T21:39:29.961+0000\", \"created\": \"2016-02-10T10:20:35.935+0000\", \"name\": \"Total Patients - New\", \"id\": \"aGdN2xl9nUj\", \"href\": \"http://82.196.9.250:8080/api/dataElements/aGdN2xl9nUj\" }, { \"lastUpdated\": \"2016-02-21T12:01:54.504+0000\", \"created\": \"2016-02-10T10:20:05.289+0000\", \"name\": \"Total Users\", \"id\": \"GKi8zBGuC3p\", \"href\": \"http://82.196.9.250:8080/api/dataElements/GKi8zBGuC3p\" }, { \"lastUpdated\": \"2016-02-21T12:02:10.067+0000\", \"created\": \"2016-02-10T10:21:00.277+0000\", \"name\": \"Total Visits\", \"id\": \"nqGCy0uyzm8\", \"href\": \"http://82.196.9.250:8080/api/dataElements/nqGCy0uyzm8\" }, { \"lastUpdated\": \"2016-02-22T21:27:46.253+0000\", \"created\": \"2016-02-21T15:36:08.574+0000\", \"name\": \"Used Memory (MB)\", \"id\": \"QZMqiNLOZNH\", \"href\": \"http://82.196.9.250:8080/api/dataElements/QZMqiNLOZNH\" }, { \"lastUpdated\": \"2016-05-16T08:31:53.663+0000\", \"created\": \"2016-05-16T08:31:53.659+0000\", \"name\": \"User Directory\", \"id\": \"Fy0lelr1WAB\", \"href\": \"http://82.196.9.250:8080/api/dataElements/Fy0lelr1WAB\" }, { \"lastUpdated\": \"2016-05-16T08:16:32.731+0000\", \"created\": \"2016-05-16T08:16:32.720+0000\", \"name\": \"User Name\", \"id\": \"KiOmWTYmc4F\", \"href\": \"http://82.196.9.250:8080/api/dataElements/KiOmWTYmc4F\" }, { \"lastUpdated\": \"2016-07-14T07:37:18.634+0000\", \"created\": \"2016-07-11T09:01:58.001+0000\", \"name\": \"New Total Adult Initial Encounters\", \"id\": \"HkctO4QQgte\", \"href\": \"http://82.196.9.250:8080/api/dataElements/HkctO4QQgte\" }, { \"lastUpdated\": \"2016-07-14T07:37:31.893+0000\", \"created\": \"2016-07-11T09:03:50.692+0000\", \"name\": \"New Total Adult Return Encounters\", \"id\": \"aHYdS1cNuam\", \"href\": \"http://82.196.9.250:8080/api/dataElements/aHYdS1cNuam\" }, { \"lastUpdated\": \"2016-07-14T07:37:53.031+0000\", \"created\": \"2016-07-06T16:33:19.037+0000\", \"name\": \"New Total Encounters\", \"id\": \"oUoKdxhelRG\", \"href\": \"http://82.196.9.250:8080/api/dataElements/oUoKdxhelRG\" }, { \"lastUpdated\": \"2016-07-14T07:38:03.349+0000\", \"created\": \"2016-07-11T09:00:14.506+0000\", \"name\": \"New Total Observations\", \"id\": \"BHXAiiCvX5K\", \"href\": \"http://82.196.9.250:8080/api/dataElements/BHXAiiCvX5K\" }, { \"lastUpdated\": \"2016-07-14T07:38:17.822+0000\", \"created\": \"2016-07-11T09:05:03.038+0000\", \"name\": \"New Total Paediatrics Initial Encounters\", \"id\": \"UidOuL65vMw\", \"href\": \"http://82.196.9.250:8080/api/dataElements/UidOuL65vMw\" }, { \"lastUpdated\": \"2016-07-14T07:38:30.489+0000\", \"created\": \"2016-07-11T09:05:33.184+0000\", \"name\": \"New Total Paediatrics Return Encounters\", \"id\": \"CdlLkU6tqSX\", \"href\": \"http://82.196.9.250:8080/api/dataElements/CdlLkU6tqSX\" }, { \"lastUpdated\": \"2016-07-14T07:47:53.987+0000\", \"created\": \"2016-07-14T07:47:53.944+0000\", \"name\": \"New Total Patients - Active\", \"id\": \"GCC9L1LGFBH\", \"href\": \"http://82.196.9.250:8080/api/dataElements/GCC9L1LGFBH\" }, { \"lastUpdated\": \"2016-07-14T07:48:16.890+0000\", \"created\": \"2016-07-14T07:48:16.876+0000\", \"name\": \"New Total Patients - New\", \"id\": \"JNsXijeCdIx\", \"href\": \"http://82.196.9.250:8080/api/dataElements/JNsXijeCdIx\" }, { \"lastUpdated\": \"2016-07-14T07:38:40.938+0000\", \"created\": \"2016-07-11T09:00:44.485+0000\", \"name\": \"New Total Users\", \"id\": \"luKfl7hZZek\", \"href\": \"http://82.196.9.250:8080/api/dataElements/luKfl7hZZek\" } ] }";

	public static void addMappingsFileToSystemMonitorDataDirectory() {
		try {
			if (SystemUtils.IS_OS_WINDOWS)
				FileUtils.writeStringToFile(SystemMonitorConstants.SYSTEMMONITOR_FINAL_MAPPINGFILE, mappings);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addDHISDataElementsMetadataToSystemMonitorDataDirectory() {
		try {
			if (SystemUtils.IS_OS_WINDOWS)
				FileUtils.writeStringToFile(SystemMonitorConstants.SYSTEMMONITOR_DATAELEMENTSMETADATA_FILE,
						dataElementsMetadata);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
