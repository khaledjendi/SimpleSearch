# SimpleSearch
Text Search Engine &amp; Suggester

Command line textual search engine and suffester developed as maven application

## Build and Run
This project can be built by any IDE that supports java. All dependencies, such as log4j, are listed in pom.xml. The code also contains java test unit. Jar executable file will be produced in target folder. It is already configured at pom.xml. 

This project uses dataset from: http://mlg.ucd.ie/datasets/bbc.html

## Output:

In terminal, running the jar as the following:

$ java -jar SimpleSearch-1.0-SNAPSHOT.jar /Users/khaled/bbc/files/

[main] INFO  com.compricer.Main  - 15 file(s) read in directory: /Users/khaled/bbc/files/

> :search tech system computer software
  - 014.txt : 100%
  - 006.txt : 100%
  - 002.txt : 75%
  - 012.txt : 75%
  - 007.txt : 75%
  - 009.txt : 75%
  - 004.txt : 50%
> wrong command

[main] WARN  com.compricer.Main  - Wong command!

Command must be in the following format --> :command params

> :search IamNoMatchText

no match found

> :add /Users/khaled/bbc/files2/001.txt /Users/khaled/bbc/files2/016.txt /Users/khaled/bbc/files2/007.txt

3 file(s) added/updated.

> :rm 001.txt 019.txt

file(s) removed.

> :suggest 5 techno
  - techno : tech
  - techno : second
  - techno : tend
  - techno : telco
  - techno : that
> :suggest 5 technology
  - technology : technology
  - technology : Technology
  - technology : technologies
  - technology : technological
  - technology : technologists
> :suggest 5 software
  - software : software
  - software : Software
  - software : spyware
  - software : share
  - software : store


### Screenshots
[!["SimpleSearch Screenshot 1"](https://drive.google.com/uc?id=1A9E0cz4zp1P7jDrex55RLWsXGp_456Bf)]()
[!["SimpleSearch Screenshot 2"](https://drive.google.com/uc?id=1PvNsY1868g7BAFim3zVpOY7lVLPFVi_K)]()
