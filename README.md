# 🚀 Mission Control CRUD System

A Spring Boot CRUD application for managing space station resources on Mars. This project is part of the Mission: Mars Deployment challenge, demonstrating CI/CD practices and containerization.

## 🌟 Features

- RESTful API for managing space station resources
- MongoDB integration for data persistence
- Docker containerization
- GitHub Actions CI/CD pipeline
- Comprehensive test coverage
- Docker Compose for local development

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.2.3
- **Database**: MongoDB
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose
- **CI/CD**: GitHub Actions
- **Language**: Java 17

## 🚀 Getting Started

### Prerequisites

- Java 17 or later
- Maven
- Docker and Docker Compose
- MongoDB (if running locally without Docker)

### Running Locally

1. Clone the repository:
   ```bash
   git clone <your-repo-url>
   cd mission-control
   ```

2. Build the application:
   ```bash
   mvn clean package
   ```

3. Run the application:
   ```bash
   java -jar target/mission-control-0.0.1-SNAPSHOT.jar
   ```

### Using Docker Compose

1. Build and start the containers:
   ```bash
   docker-compose up --build
   ```

2. The application will be available at `http://localhost:8080`

## 📚 API Endpoints

### Resources

- `GET /api/resources` - Get all resources
- `GET /api/resources/{id}` - Get a specific resource
- `POST /api/resources` - Create a new resource
- `PUT /api/resources/{id}` - Update a resource
- `DELETE /api/resources/{id}` - Delete a resource

### Example Resource JSON

```json
{
  "name": "Water Supply",
  "description": "Potable water storage",
  "quantity": 1000,
  "unit": "liters",
  "category": "Life Support",
  "weight": 1000.0,
  "location": "Storage Bay A"
}
```

## 🧪 Testing

Run the tests using Maven:
```bash
mvn test
```

## 🔄 CI/CD Pipeline

The project includes a GitHub Actions workflow that:
1. Builds the application
2. Runs tests
3. Builds the Docker image
4. Pushes the image to Docker Hub

### Required Secrets

The following secrets need to be configured in your GitHub repository:
- `DOCKERHUB_USERNAME`: Your Docker Hub username
- `DOCKERHUB_TOKEN`: Your Docker Hub access token

## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 🙏 Acknowledgments

- Created for the Mission: Mars Deployment challenge
- Special thanks to the instructors and mentors