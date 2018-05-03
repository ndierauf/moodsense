# moodsense
Mood Sensing Application

Hello, welcome to Nick Dierauf's Mood Sensing Application. Please review Mood-Sense-Development-Exercise.pdf for a complete explanation of the project.

To run the app, pull the source and compile as a .war file (mvn clean install). Drop the war file into a Java servlet container.

To test the app, navigate to the URL for this web application. 
Note that there are three fields - User Name, Capture Type, and Location.
Enter a valid User Name (hint, only works with 'nick' and 'nalin'). 
Select a Capture Type (HAPPY, SAD, NEUTRAL)
Click a location on the map such that the Location field populates.
Click 'Submit Query'. 
The Results field should populate with the following information: 

Mood Distribution for user '<user>': Happy: 1, Sad: 0, Neutral: 0
Proximity to Locations for capture (HAPPY): 
  Work: 0.2 mi
  In-and-Out Burgers: 3 ft
  Gym: 351 ft 

![Moodsense.PNG](Moodsense.PNG)

Assumptions and Explanations:
This is a very simplified app. The frontend and backend is very rudimentary, however I believe it addresses most of the requirements as outlined in the PDF document. I spent about 6 hours working on this project. It, obviously, does not actually analyze images to determine whether the user is happy, sad, or neutral. I did not research if there are such technologies available as open-source or for licensing.

It uses a very simple authentication mechanism - only two users are allowed to obtain results: nick and nalin. A more sophisticated implementation would run with SSL using a proper certificate, and would have a sign-in form to securely submit credentials. Server-side would validate the credentials using LDAP, or some other mechanism. RBAC would also be used to determine if the user has permissions to work with the application.

Technologies:
This is a Java-based web application, using all open-source technologies. Google Map APIs are used for displaying the map. jQuery APIs are used for making AJAX calls to the server and for populating the results.

The app implements a servlet for handling REST requests. Parameters are validated. All input is only persisted in-memory. A more sophisticated application would need to persist to a database. REST calls are made to Google Map APIs in order to determine distance to set locations, and the resulting JSon is parsed and returned to the user.

It was a fun project.
