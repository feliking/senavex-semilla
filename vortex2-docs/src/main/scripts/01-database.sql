--==============================================================================
-- create user u_vortex/u_vortex$password

CREATE user u_vortex2 LOGIN  NOSUPERUSER CREATEDB NOCREATEROLE NOREPLICATION CONNECTION LIMIT -1 PASSWORD 'u_vortex2$password'

--==============================================================================
-- create database vortex2_r01    Release 01

﻿CREATE DATABASE vortex2_r01 WITH OWNER u_vortex2 CONNECTION LIMIT = -1



