TODOs:
______________________________________________
* supporting uploading supported metadata json into the configured DHIS instance
* Read from the local DHIS Data Elements metadata copy to name indicators in the module's pages
* Writing well timed (similar to EMT) scheduling tasks for both data capture and pushing into DHIS2
* Write page pages in the module's copy to display graphs etc
* Support avarages of system indicators such as memory usage and support last week, month, year etc
* Fix server id
* Read Metrics names from metadata locally
* Write metrics display page that graphs staff etc
* Write gp's used configuration page, add emt configurable data like work days and hours
* Support ADX and DXF formats and make the formats configurable for the user to easily switch
* Add Processor indicator, RAM and usage
* Counting indicators should be configurable whether to include retire/voided or not
* Add OrgUnit name to allData json
* Create a scheduled task none daily indicators and data failed to be pushed because of no internet connection
* Create a scheduled task to keep updating both the stored dataelements and organisation units
* Fix System uptime push % datatype conflict
* reloadConfigurations api method, saves gps and reloads dhismetadata if site is changed or dhis account
* if user changes configurations of dhis instance mainly url, after getting metadata from server, confirm that it matches with the embedded mappings file
* Add CD4 Counts indicator that works like for Viral Loads
* Support sending none sent data afterwards
* Again do sever logging of pushing to dhis 
* Add maintanace task that runs to delete all old logs and data depending on a range of time agreed by design team
