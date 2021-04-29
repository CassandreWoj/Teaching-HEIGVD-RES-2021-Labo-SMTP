#!/bin/bash

#Run le docker avec des ports exposés et l'ip localhost
docker run -p 2525:2525 -p 8282:8282 gdcw/mockmock
