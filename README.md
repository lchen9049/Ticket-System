# Ticket-System

This program is a ticket system. First, the client has to login in. If the credential is wrong for three times, the program
will exit. If the user is an admin then he/she will have access to all the features including: open ticket, update ticket,
view ticket, close ticket, and delete ticket. On the other hand, regular users can only open, view, and update tickets. When
the user logs in, he/she will have to choose from the menu tabs what he/she would like to do. To open a ticket, the user will 
have to enter his/her name, the title of the ticket, and what the ticket is for; the date will be automatically set to the 
current time. To update, the user have to enter the ticket that he/she want to update; then enter the description that he/she 
wants to update. Each time the ticket is updated, there will be a timestamp followed. To close a ticket, the admin has to 
enter the id of the ticket, then enter 1 if he/she is sure he/she wants to close it; when close, the status will change to 
‘close’ and the ‘End Date’ will change to the time that the ticket is closed. To delete a ticket, the admin have to enter the 
ticket ID, enter 1 for yes, and then the ticket will be deleted; if the admin enter the wrong ticket ID, he/she can enter 0 to 
cancel. When the user wants to view the tickets, a table will pop out with all the tickets that has been issued. 
