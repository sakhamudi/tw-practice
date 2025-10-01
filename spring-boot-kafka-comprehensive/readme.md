docker compose up -d

mvn -DskipTests package
mvn spring-boot:run

curl -X POST http://localhost:8080/api/messages/send -H "Content-Type: text/plain" -d "hello world"
curl -X POST http://localhost:8080/api/messages/send/fail -H "Content-Type: text/plain" -d "please fail this message"


# inside kafka container or if kafka tools are installed locally
kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group demo-group

# inside kafka container or if kafka tools are installed locally
kafka-consumer-groups --bootstrap-server localhost:9092 --describe --group demo-group
