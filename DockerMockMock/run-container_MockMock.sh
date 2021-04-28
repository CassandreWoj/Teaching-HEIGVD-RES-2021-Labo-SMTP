#!/bin/bash

#Run le docker avec des ports exposés et l'ip localhost
docker run -p 25:25 -p 8282:8282 --ip 127.0.0.1 gdcw/mockmock
