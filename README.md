# Landmark-View
Website dedicated to viewing Bucharest landmarks

The application is a web platform for exploring the landmarks of Bucharest through an interactive map. When a visitor lands on the homepage, they see a map of the city dotted with icons marking notable sites — churches, palaces, museums, parks, monuments. Clicking any icon opens a side panel with information about that landmark: a description, photos, location details, and a discussion thread where registered users share their thoughts.
The platform has two types of users. Regular visitors can browse the map freely and read about any landmark, but they need to register to post comments or reply to others. Each registered user has a profile page that displays their account info and the comments they have made. Admins have an additional area of the site where they can add new landmarks (name, description, photos, coordinates), edit existing entries, and moderate comments. Newly added landmarks become visible on the public map as soon as the admin saves them.
On the technical side, the backend is built with Spring Boot and Spring Security (encrypted passwords, session-based auth), the data sits in a relational database, the frontend is responsive with Bootstrap, and the whole stack ships in Docker containers that come up with a single script.

# User stories:

1. As a visitor curious about Bucharest, I want to click on any icon on the map and immediately see a photo, description, and location for that landmark in a side panel, so that I can quickly learn about interesting places without hunting through menus or pages.

2. As an admin, I want to add a new landmark by dropping a pin on the map and filling in its name, description, and photos, so that the places I know about become discoverable for everyone else on the site.

3. As a registered user, I want to leave comments and reply to other people on a landmark's page, so that I can share my own experience visiting it or ask questions and build a small discussion around each place.

## Figma Wireframe

https://www.figma.com/design/UpcF7HtdAHaKhUFdyBhnF0/Bucharest-Landmarks-%E2%80%94-Wireframes?node-id=0-1&t=ruRbOyH54jEa3Oqi-1

## Trello

https://trello.com/invite/b/6a0b3a722627d85275efb513/ATTI56a1cb55647edd37984254d058c49ce5F96696E2/bucharest-landmarks
