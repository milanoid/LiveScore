With Java, Selenium Webdriver use PageObject Pattern automate following test cases:

## Event info
1. Go to [livescore.com](http://livescore.com) webpage
2. Save the informations of second event on page
3. Open the second event on page
4. In the opened window get the info of event
5. Compare if the team names and scores are the same

## Favourites

1. Go to [livescore.com](http://livescore.com) webpage
2. Add event from page to favourites (with star)
3. Open favourites
4. Verify that event was added

---
How to run it
-

- have ready Selenium locally, e.g by running

`docker run -p 4444:4444 -p 5900:5900 --shm-size=2g selenium/standalone-chrome-debug:3.141.59-20200409`

- then run using maven `mvn test` or via IntelliJ IDEA