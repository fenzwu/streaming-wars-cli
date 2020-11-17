# A6 Streaming Wars Team A6-15
**Class**: CS6310 Fall 2020 \
**Team Members**: Kevin Chau, Eduard Levshteyn, Kitsana Sayasy, Kathryn Trant, Fen Wu 

## Overview
Welcome to Team A6-15's submission for the Streaming Wars assignment. It is a command line (no GUI) JAR application built using SQLite and OpenJDK 15. Our selected modifications are:
* Authentication - Added user accounts with username/password login, ability to change passwords, and stored credentials in a database. 
* Authorization - Added role based access control to commands and ability to assign a role to each user.
* Auditability - Added logging and stored logs in a database. 

We have included a Dockerfile for convience. See instructions below for running the application and command documentation. 

**Demonstration Video (mts_video):** https://www.youtube.com/watch?v=sEe6JlJZojo&feature=youtu.be

## Running
From directory with "Dockerfile" and "cs6310_a6_15.jar"
1. Build container `docker build -t a6-15 -f Dockerfile ./`
2. Get shell in container `docker run -ti a6-15 sh`
3. Run program `java -jar cs6310_a6_15.jar`
4. Enter any commands listed below. *NOTE:* You need to be logged in to run most commands. A default admin account is created on initialization (username = `admin`, password = `admin`). See the **Permission** section of each command for what roles are allowed to run a particular command. 


## Commands:

### Authorization and Authentication
This program uses roles assigned to each user to check if they are authorized to run certain commands. It identifies the user's role by doing username/password authentication. All user credentials are stored in a database and persist between program runs. Below is a summary of what each role can do.

Reader
* Display information (e.g. display_demo, display_stream)
* Change own password

Editor
* Create demographics, services, studios, and events
* Update demographics, events, and streams
* Retract movies
* Offer events
* Watch events
* Advance to next month
* Includes Reader permissions

Admin
* Create new user accounts
* Assign roles
* View logs
* Includes Reader and Editor permissions

**Command:** `login,<username>,<password>` \
**Description:** Set the active user where `username` is the account username and `password` is the account password. If there are no users (like during first run) then login with the default admin (admin:admin). \
**Permission:** Everyone

**Command:** `logout` \
**Description:** Logs out the active user. \
**Permission:** Everyone

**Command:** `create_user,<username>,<password>,<role>` \
**Description:** Creates a new user with the given `username`, initial `password`, and `role`. Valid roles are: "reader", "editor", and "admin" (case sensitive). \
**Permission:** Admin

**Command:** `display_user` \
**Description:** Prints out the name and role of the active user. \
**Permission:** Reader, Editor, Admin

**Command:** `change_password,<new_password>` \
**Description:** Changes the active user's password \
**Permission:** Reader, Editor, Admin

**Command:** `change_role,<username>,<new role>` \
**Description:** Changes the user's role identified by `username` to `new role`. Valid roles are: "reader", "editor", and "admin" (case sensitive).  \
**Permission:** Admin

### Auditability
All commands are logged in a database and are stored in a database so they persist between program runs.

**Command:** `display_logs` \
**Description:** Show the audit log. Displays the user, command, and time of each operation. \
**Permission:** Admin

**Command:** `clear_logs` \
**Description:** Clear the audit log \
**Permission:** Admin

### Commands from A5
*Command descriptions reference assignment A5 instructions*

**Command:** `update_demo,<short name>,<long name>,<number of accounts>` \
**Description:** This command updates the attributes of the demographic group with the corresponding short
name. The short name itself cannot be changed; and, if the short name does not exist, then the
command is considered invalid. The number of accounts can be changed only at the
beginning of a time period before that specific demographic group has accessed and viewed any
movies or Pay-Per-View events. \
**Permission:** Editor, Admin

**Command:** `update_event,<name>,<year produced>,<duration>,<license fee>` \
**Description:** This command updates the attributes of the movie or Pay-Per-View event with the corresponding
name and year produced. The name and the year produced cannot be changed; and, if the
combination of the name and year produced does not exist, then the command is considered invalid. The license fee can be changed only at the beginning of a time period before
that specific movie or Pay-Per-View event has been accessed and viewed any demographic groups. \
**Permission:** Editor, Admin

**Command:** `update_stream,<short name>,<long name>,<subscription price>` \
**Description:** This command updates the attributes of the streaming service with the corresponding short
name. The short name itself cannot be changed; and, if the short name does not exist, then the
command is considered invalid. The subscription price can be changed only at the
beginning of a time period before that specific streaming service has not been used to access and
view any movies. \
**Permission:** Editor, Admin

**Command:** `retract_movie,<streaming service>,<movie name>,<movie year>` \
**Description:** This command removes the designated movie from the listing for that specific streaming service.
If either the streaming service or movie name reference is invalid, then this command should be
considered invalid. \
**Permission:** Editor, Admin

### Commands from A4
*Command descriptions reference assignment A4 instructions*

**Command:** `create_demo,<short name>,<long name>,<number of accounts>` \
**Description:** This command creates a demographic group with the corresponding short name, long name, and number of accounts. The short name must be unique for all demographic groups. \
**Permission:** Editor, Admin

**Command:** `create_studio,<short name>,<long name>` \
**Description:** This command creates a studio with the corresponding short and long names. The short name must be unique for all studios. \
**Permission:** Editor, Admin

**Command:** `create_event,<type>,<name>,<year produced>,<duration>,<studio>,<license fee>` \
**Description:** This command creates an event type – movie or Pay-Per-View (ppv) – with the given name, year of production, duration, and licensing fee. The combination of the name and year produced must be unique for all movies. Also, the movie must have been produced by a valid studio. If the studio being referenced is invalid (e.g., hasn't been created yet), then this command should be considered invalid, and the movie should not be created.  \
**Permission:** Editor, Admin

**Command:** `create_stream,<short name>,<long name>,<subscription price>` \
**Description:** This command must creates a streaming service with the given short name, long name and monthly
subscription price. The short name must be unique for all streaming services.  \
**Permission:** Editor, Admin

**Command:** `offer_movie,<streaming service>,<movie name>,<year produced>` \
**Description:** This command creates a listing for the designated streaming service that allows prospective
viewers (i.e., portions of demographic groups) to access and watch the movie. If either the streaming
service or movie name reference is invalid, then this command should be considered invalid, and the
listing should not be created.   \
**Permission:** Editor, Admin

**Command:** `offer_ppv,<streaming service>,<pay-per-view name>,<year produced>,<viewing price>` \
**Description:** This command creates a listing for the designated streaming service that allows prospective
viewers (i.e., portions of demographic groups) to access and watch the Pay-Per-View event for the
designated price. If either the streaming service or pay-per-view name reference is invalid, then this
command should be considered invalid, and the listing should not be created.   \
**Permission:** Editor, Admin

**Command:** `watch_event,<demographic group>,<percentage>,<streaming service>,<event name>,<year produced>` \
**Description:** This command creates a transaction such that some percentage of the accounts in the
designated demographic group have decided to access and watch the movie or Pay-Per-View event
being offered on the streaming service. As with most commands, if the references to the
demographic, streaming service or event are invalid, then this command should also be considered
invalid. Similarly, if the streaming service is not offering the designated event, then the command
should be considered invalid.   \
**Permission:** Editor, Admin

**Command:** `next_month` \
**Description:** This command advances the calendar to the next calendar month. \
**Permission:** Editor, Admin

**Command:** `display_demo,<short name>` \
**Description:** This command displays information about the demographic group that is referenced by the provided
short name. \
**Permission:** Reader, Editor, Admin

**Command:** `display_stream,<short name>` \
**Description:** This command displays information about the streaming service that is referenced by the provided
short name.  \
**Permission:** Reader, Editor, Admin

**Command:** `display_studio,<short name>` \
**Description:** This command displays information about the studio or publishing group that is referenced by the
provided short name.   \
**Permission:** Reader, Editor, Admin

**Command:** `display_events` \
**Description:** This command displays all of the movies and Pay-Per-View events that have been produced by the
studios.   \
**Permission:** Reader, Editor, Admin

**Command:** `display_offers` \
**Description:** This command displays all of the license requests made from the streaming services to the studios for
various movies and Pay-Per-View events.   \
**Permission:** Reader, Editor, Admin

**Command:** `display_time` \
**Description:** This command displays the month and year for the current time period.   \
**Permission:** Reader, Editor, Admin

**Command:** `stop` \
**Description:** This command simply causes the (otherwise infinite) interactive loop to halt.   \
**Permission:** Everyone

## Notes for Developers
Ensure you include /lib/sqlite-jdbc-3.32.3.2.jar (sqlite driver) in your classpath!

