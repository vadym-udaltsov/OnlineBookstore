🏷️ Online Bookstore API Testing Project
This project is designed to test the API endpoints of an online bookstore using the FakeRESTAPI. 
It represents a comprehensive testing framework that verifies the functionality of all endpoints for books and authors, 
utilizing Java, Maven, TestNG, RestAssured, and integrating Allure Reports for generating detailed testing reports.

Before you start, make sure you have the following installed:
* JDK 22: Download JDK 22
* Maven: Install Maven and add it to your PATH variables.
* Git: Ensure Git is installed and configured.

📂 Project Structure
The project is organized as follows:
OnlineBookstore/
├── src
│   ├── main
│   │   ├── java
│   │   │   └── [project source code]
│   │   └── resources
│   │       └── config.properties
│   ├── test
│   │   ├── java
│   │   │   └── [test classes]
│   │   └── resources
│   │       └── testng.xml
├── pom.xml
├── README.md
└── .github

🛠️ Technologies Used
* Java 22: Main programming language
* Maven: Build management tool
* TestNG: Testing framework
* RestAssured: API testing library
* Allure: For generating detailed test reports
* GitHub Actions: CI/CD integration

📥 Installation 
1. Clone the repository:
bash
git clone https://github.com/Vadym-Udaltsov/OnlineBookstore.git

2. Navigate to the project directory:
bash
cd OnlineBookstore

3. Build the project using Maven:
bash
mvn clean install

⚙️ Configuration
Project configurations can be found in the src/main/resources/config.properties file

🚀 Running Tests
To run the tests, execute the following Maven command:

bash
mvn test
The tests will be executed according to the settings in the testng.xml file located in src/test/resources/testng.xml.

📊 Allure Reporting
The project uses Allure to generate detailed test execution reports. After the tests are complete, you can generate the report using:

bash
mvn allure:serve
The report will be available at http://localhost:8080.

You can also view the latest Allure report via this link: https://vadym-udaltsov.github.io/OnlineBookstore/#. 
* [Note that the report updates about 10 minutes after merging a PR.]

🔄 GitHub Actions Integration
This project is integrated with GitHub Actions for continuous integration and deployment. 
Every push to the repository triggers the following steps:

* Project build
* Test execution
* Allure report generation and update
* Publishing the updated Allure report to GitHub Pages

⚙️ GitHub Actions Setup
Make sure your repository is public and has enabled workflow permissions:
1. Go to Settings > Actions > General of your repository.
2. Set "Workflow permissions" to "Read and write permissions."

## Contacts
For any inquiries, feedback, or contributions, feel free to reach out:

Vadym Udaltsov
Email: vadym.udaltsov@avenga.com