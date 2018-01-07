# ForexBrowser

Project for "Object Oriented Programming" course on "AGH - University of Science and Technology" in Cracow.

## How it works




### Concept diagram

Diagram shows relations between abstract concepts. It isn't made in any UML standard. 

![Diagram](https://image.ibb.co/hWiFPw/NBP_API.jpg)



TableBuilder as builder design pattern. 

![Diagram](https://image.ibb.co/g5svrb/Forex_Browser_DFD.jpg)


### Install autocomplete:

```sh

alias forex-browser='java -jar ForexBrowser/out/artifacts/ForexBrowser_jar/ForexBrowser.jar'

java -cp "ForexBrowser.jar" picocli.AutoComplete -n forex-browser com.oop.browser.Main

. forex-browser_completion

source ~/.bashrc

```
