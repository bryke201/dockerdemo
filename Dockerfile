FROM postgres:9.6.22

COPY demo_schema.sql /docker-entrypoint-initdb.d/
