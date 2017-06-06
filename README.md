# Entando Migration Tool
   
The Entando migration tool is a standalone Java application used to migrate the database from a given version of Entando to the next one.

## Usage
Usage is rather simple:
 ```
 $ git clone git remote add origin https://github.com/entando/entando-migration-tool.git
 $ cd entando-migration-tool
 $ mvn package
 $ (optional) mv target/entando-db-migration-tool-jar-with-dependencies.jar ../
 $ java -jar ./entando-db-migration-tool-jar-with-dependencies.jar --src=<source_db_addr> --user=<username> --password=<password> --dst=<dest_db_addr> --jbdc=[postgres|mysql]
 ```
 
### Options
 
 The following are available:
 
| switch | mandatory | description |
| ------ | ------ | ------ |
| src | yes | source database address |
| dst | yes | destination database address |
| user | yes | database username  |
| password | yes | database user passaword |
| jdbc | yes | database selection can be '_postgresql_' or '_mysql_' |
| min-idle | no | the MIN number of idle connection admitted (default: 5) |
| max-idle | no | the MAX number of idle connection admitted (default: 10) |
| max-prepared-statement | no | MAX number of open prepared statement (default: 100) |
 
### Example

java -jar entando-db-migration-tool-jar-with-dependencies.jar --src=http://127.0.0.1:5432/ent-4.2Port  
--user=agile --password=agile --dst=http://127.0.0.1:5432/ent-4.3Port --jbdc=postgres 

where:

 * Entando 4.2 source db is **ent-4.2Port** available at the http://127.0.0.1:5432/ent-4.2Port
 * destination db of the Entando 4.3 is **ent-4.3Port** at http://127.0.0.1:5432/ent-4.3Port
 * DBMS is Postgres

## Current limitations

The following software presents the following limitations:

* migrates DB from Entando 4.2 -> Entando 4.3 (no version selection switch available)
* source db and destination db must share the same RDBMS
* source db and destination db must share the same login credentials

## License

GNU Lesser General Public License