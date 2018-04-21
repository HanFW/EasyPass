# IS4302 EasyPass 

EasyPass is a G2C initiative that incorporates blockchain technology for a swift, secure and reliable visa application process.

## Features
- **Visa Application**: Citizen submit new visa application 
- **Endorser Validate**: Different endorser can validate corresponding asset
- **Issue Visa**: Embassy can issue visa based on the validated visa application

## Pre-Requisites
* Netbeans v8.x 
* Glassfish v4.1.x 
* MySQL 
* Docker
* NodeJS 

# Loading initial data
0. Make sure MySQL is installed and Hyperledger is running at localhost:3000
1. Unzip 'initial data.zip'
2. Change MySQL credential in 'config.json'
```shell
# To install dependencies 
$ npm intall

# To load the intial data into MySQL and Hyperledger
$ npm start
```


## Local Development

Block chain: use Vagrant or Docker image to run hyperledger faric and expose the composer-rest-api to localhost:3000

Frontend + backend application: 
1. Open netbeans and import project 
2. Right click the proejct -> clean and build
3. Right click the proejct -> depoly 
4. The project should be running at localhost:8080

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