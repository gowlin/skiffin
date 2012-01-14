# Skiffin

Provides who's in and who's out information for The Skiff.

## Running

This is a Lift web app.  

$ ./sbt
sbt> container:start

Then http://127.0.0.1:8080/

If you're hacking, 


## API v1

All urls prefixed with /api/v1

### Get a list of everyone

    curl -v -X GET -H 'Content-type: text/json' http://127.0.0.1:8080/api/v1/people

Returns:

    {
      "count":2,
      "people":
      [ { "email":"richard@dallaway.com",
          "in":true,
          "when":1326562010157}, 
        { … }
      ]
    }


## Get a specific person

    curl -v -X GET -H 'Content-type: text/json' http://127.0.0.1:8080/api/v1/richard@dallaway.com

Returns the person representation shown above.

## Post a status change

    curl -v -X POST -d '{ "in" : true }' -H 'Content-type: text/json' http://127.0.0.1:8080/api/v1/richard@dallaway.com

Returns the person representation or a 404 if the person is not known


## Put a new person

    curl -v -X PUT -d '{ "in":false }' -H 'Content-type: text/json' http://127.0.0.1:8080/api/v1/you@example.com

Returns the person representation



