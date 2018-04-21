# IS4302 EasyPass 

EasyPass is a G2C initiative that incorporates blockchain technology for a swift, secure and reliable visa application process.

## Features
- **Citizen**: 
    a. Submit new visa application 
    b. View visa application status
    c. View e-visa
- **Endorser**: Validate / reject the assets that submitted by citizen
    - Bank -> Bank statement
    - TransportationProvider -> Transportation Reference
    - AccommodationProvider -> Accommodation Reference 
    - InsuranceCompany -> Insurance Reference
    - LocalContactPerson -> Local Contact Infomation
    - ICA -> BasicInfo
    - SPF -> Criminal Record (Only asset that filled by endoser)
- **Embassy**: Issue visa based on the validated visa application

## Pre-Requisites
* Netbeans v8.x 
* Glassfish v4.1.x 
* MySQL 
* Docker
* NodeJS 

# Loading initial data
1. Make sure MySQL is installed and Hyperledger is running at localhost:3000
2. Unzip 'initial data.zip'
3. Change MySQL credential in 'config.json'
```shell
# To install dependencies 
$ npm install

# To load the intial data into MySQL and Hyperledger
$ npm start
```


## Local Development

Block chain: use Vagrant or Docker image to run hyperledger faric and expose the composer-rest-api to localhost:3000

Frontend + backend application: 
1. Open netbeans and import project 
2. Create JDBC Resources -> please refer to https://docs.oracle.com/cd/E19798-01/821-1751/gharo/index.html to create a jdbc resource and connection pool (e.g. jdbc/easyPass) for glassfish server
2. Right click the proejct -> clean and build
3. Right click the proejct -> depoly 
4. Right click the proejct -> run 
5. The project should be running at localhost:8080

## Login credential 
User ID: 
1. Citizen: citizen001
2. Embassy: embassy001
3. Bank: bank001
4. TransportationProvider: transport001
5. AccommodationProvider: hotel001
6. InsuranceCompany: insurance001
7. ICA: ica001
8. SPF: spf001
9. LocalContactPerson: localcontact001

Password:
* Password: 123
* All passpords are the same