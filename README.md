# Admin Course App

# Add to application.properties:
spring.profiles.active=local

# Create application-local.properties and add this for local database connection:
spring.datasource.url=jdbc:postgresql://localhost:5432/#Database name#
spring.datasource.username=#your database username (usually postgres)#
spring.datasource.password=#Your database password#
spring.datasource.driver-class-name=org.postgresql.Driver


spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Hibernate properties
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
