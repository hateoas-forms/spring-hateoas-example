spring-boot-hateoas-example
=======================

Sample application to test Spring HATEOAS and HAL-FORMS support

How to build the application
============================
Clone this repo and build it (you'll need Git and Maven installed):

    git clone git://github.com/hdiv/spring-hateoas-example.git
    cd spring-boot-hateoas-example
    mvn package
    
Run application packaged as a jar:
    
    java -jar target/spring-boot-hateoas-example.jar

Open [http://localhost:8080/api/](http://localhost:8080/api/) in your favorite browser.


Entry point /api/
============================

	{
		"_links": {
		    "halforms:make-transfer": {
		      "href": "http://127.0.0.1:8080/api/transfer"
		    },
		    "halforms:list-transfers": {
		      "href": "http://127.0.0.1:8080/api/transfer"
		    },
		    "halforms:list-after-date-transfers": {
		      "href": "http://127.0.0.1:8080/api/transfer/filter{?dateFrom,dateTo,status}",
		      "templated": true
		    },
		    "curies": [
		      {
		        "href": "http://127.0.0.1:8080/doc/{rel}",
		        "name": "halforms",
		        "templated": true
		      }
		    ]
		}
	}

Open this URL the application will show the main operations that can be done:

* [make-transfer](http://localhost:8080/api/transfer): URL to POST a new transfer
* [list-transfers](http://127.0.0.1:8080/api/transfer): URL to list all transfers (using GET)
* [list-after-date-transfers](http://127.0.0.1:8080/api/transfer/filter{?dateFrom,dateTo,status}): URL to list transfers matching a filter (using GET)

Additionally documentation links are provided in this URL template http://127.0.0.1:8080/doc/{rel}, for now only HAL-FORMS documents are properly documented, in this first level the following documentation forms are available

* [halforms:make-transfer](http://127.0.0.1:8080/doc/make-transfer)