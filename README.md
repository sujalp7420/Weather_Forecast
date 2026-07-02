# 🌦️ Weather Forecast

A modern and responsive **Weather Forecast Web Application** built using **Spring Boot, Spring MVC, Thymeleaf, Spring Security, MySQL, HTML, CSS, and JavaScript**. The application allows users to create an account, securely log in, search weather information, and manage their profile through a clean and user-friendly interface.

---

## 📌 Features

* 🔐 User Registration & Login
* 🛡️ Spring Security Authentication
* 🌤️ Search weather by city
* 🌍 Real-time weather information
* 📊 Displays:

  * Temperature
  * Weather Condition
  * Humidity
  * Wind Speed
  * Pressure
  * Feels Like Temperature
* 👤 User Dashboard
* 🔑 Change Password
* 📱 Responsive UI
* 🎨 Modern Design
* ⚠️ Error Handling
* 🗄️ MySQL Database Integration

---

## 🛠️ Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring MVC
* Spring Security
* Spring Data JPA
* Hibernate

### Frontend

* HTML5
* CSS3
* JavaScript
* Thymeleaf
* Bootstrap

### Database

* MySQL

### Tools

* Maven
* Git
* GitHub
* IntelliJ IDEA / Eclipse

---

## 📂 Project Structure

```text
Weather_Forecast/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   │   ├── templates/
│   │   │   ├── static/
│   │   │   └── application.properties
│   │   └── ...
│   └── test/
│
├── pom.xml
├── README.md
└── mvnw
```

---

## ⚙️ Installation

### 1. Clone the Repository

```bash
git clone https://github.com/sujalp7420/Weather_Forecast.git
```

### 2. Open the Project

Open the project in:

* IntelliJ IDEA
* Eclipse
* Spring Tool Suite (STS)

---

### 3. Configure Database

Create a MySQL database.

Example:

```sql
CREATE DATABASE weather_forecast;
```

Update your `application.properties` file.

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/weather_forecast
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

### 4. Run the Project

```bash
mvn spring-boot:run
```

or run the main Spring Boot class directly.

---

## 🌐 Access Application

```
http://localhost:8080
```

---

## 🔒 Authentication

The application uses **Spring Security** to provide:

* Secure Login
* User Registration
* Password Encryption
* Session Management
* Protected Routes

---

## 📷 Main Pages

* Home
* Login
* Register
* Dashboard
* Weather Search
* Change Password
* Error Page

---

## 🚀 Future Enhancements

* 📍 Current Location Weather
* ⭐ Favorite Cities
* 🌙 Dark Mode
* 📈 Weather History
* 📄 PDF Weather Report
* 🔔 Weather Alerts
* 📱 Progressive Web App (PWA)
* 📧 Email Notifications

---

## 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature-name
```

3. Commit changes

```bash
git commit -m "Add new feature"
```

4. Push the branch

```bash
git push origin feature-name
```

5. Create a Pull Request

---

## 📄 License

This project is developed for educational and learning purposes.

---

## 👨‍💻 Author

**Sujal Patel**

* GitHub: https://github.com/sujalp7420

---

## ⭐ Support

If you found this project helpful, please give it a ⭐ on GitHub.

It motivates future improvements and open-source contributions.

Happy Coding! 🚀
