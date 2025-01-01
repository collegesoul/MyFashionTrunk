### My Fashion Trunk

The Application uses a React frontend and Springboot backend. 
It integrates Google Cloud Vision API for object detection and
Google Cloud Store for storage of images, and it's connected to a PostgreSQL database .


## How the Application looks like on the frontend

Home view
![homeView](screenshots/homeView.png)

categories for editing categories
![categories1](screenshots/categories.png)

add listing page for adding items
![add_listing](screenshots/add_listing.png)

Drop down menu for profile
![profileDropdown](screenshots/profileDropdown.png)

Profile settings for account update and delete
![profileSettings](screenshots/profileSettings.png)

login page
![login](screenshots/login.png)

register page for new users
![register](screenshots/register.png)


### Environment variables needed on the backend:<br>
SPRING_DATASOURCE_USERNAME<br>
SPRING_DATASOURCE_PASSWORD<br>
GOOGLE_APPLICATION_CREDENTIALS<br>
GOOGLE_CLOUD_PROJECT

### Environment variable needed on the frontend: <br>
VITE_SERVER_APP_URL
