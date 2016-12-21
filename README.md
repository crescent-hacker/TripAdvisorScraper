# TripAdvisorScraper

##Setup

* Install maven and setup corresponding environment.
* Install mysql server and setup corresponding environment.
* I toggled the config of hibernate 'hibernate.hbm2ddl.auto=update', if you want to create tables by yourself, just annotate this line in 'hibernate.properties'.

* Modify 'TripAdvisorScraper/src/main/resources/META-INF/properties/hibernate.properties'.

		url=jdbc:mysql://127.0.0.1:3306/scraper?useUnicode=true&characterEncoding=UTF-8
		username=your database username
		password=your database password.

* Modify 'TripAdvisorScraper/src/main/resources/log4j.xml'.

		<param name="File" value="/path/to/logs/scraper.log" />

##Run
'cd TripAdvisorScraper/'
'mvn jetty:run'

* Visit http://localhost:8080/



