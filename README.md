Entando migration tool
The Entando migration tool is used to migrate the database from a given version of Entando to the next one
java -jar entando-db-migration-tool --src=http://127.0.0.1:5432/ent-4.2Port --user=agile --password=agile --dst=http://127.0.0.1:5432/ent-4.3Port --jbdc=postgres 

The values of jbdc paramter are : postgresql, mysql

