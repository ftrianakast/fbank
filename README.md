[1]: https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo

# PayULatam banco

## Table of contents

* [Resumen](#resumen)
* [Herramientas y versiones](#herramientas-y-versiones)
* [Diagrama entidad-relación](#diagrama-de-entidad-relacion)
* [Despliegue](#despliegue)
    - [Parametrización de la base de datos](#parametrizacion-de-la-base-de-datos) 
    - [DDL Script](#ddl-script)
    - [Probar el proyecto en eclipse](#probar-el-proyecto-en-eclipse)
    - [Probar el proyecto como un jar ejecutable](#probar-el-proyecto-como-un-jar-ejecutable)
    - [Probar el proyecto con Docker](probar-el-proyecto-con-docker)
* [Manual de usuario](#manual-de-usuario)
    - [Client Resource](#client-resource)
        + [POST /clients](#crear-cliente)
        + [GET /clients/{clientId}](#obtener-cliente)
        + [DELETE /clients/{clientId}](#borrar-cliente)
        + [PUT /clients/{clientId}](#actualizar-cliente)    
    - [Account Resource](#account-resource)
        + [POST /clients/{clientId}/accounts](#crear-cuenta)
        + [GET /accounts/{accountId}](#obtener-cuenta)
        + [DELETE /accounts/{accountId}](#borrar-cuenta)
    - [Movement Resource](#movement-resource)
        + [POST /accounts/{accountNumber}/movements](#crear-movimiento)
    - [Report Resource](#report-resource)
        + [POST /clients/{clientId}/reports](#generar-reporte)
             

## Resumen
Los requerimientos del banco están construidos sobre una Arquitectura __REST__. Los servicios REST de dicha arquitectura están implementados utilizando Spring MVC. Se utilizan los verbos HTTP: __GET__, __POST__, __PUT__ y __DELETE__. Las modificaciones parciales de recursos con __PATCH__ no están contempladas. Se deja la implementación de __Hipermedia__ sobre dichos servicios para una iteración posterior.

## Herramientas y versiones

Las siguientes son las herramientas utilizadas en las diferenctes actividades de desarrollo:

- __JDK__: Se utiliza el __JDK 1.8__. Esto es importante por que el proyecto utiliza características únicas de la versión 8 de Java: expresiones lambda, Optionals y Streams.
- __Maven__: El proyecto Maven está descrito con __POM 4.O.O XSD__
- __Spring Boot__: Se utiliza para embeber un servidor Tomcat y no necesitar los contenedores de aplicaciones tradicionales. Esto favorece la portabilidad del proyecto, la configuración automática, entre otras.
- __Spring MVC__: Se utiliza para la construcción de los servicios REST.
- __Spring Data JPA__: Se utiliza para el manejo de la persistencia. Facilita las operaciones y configuraciones de __repositorios__.
- __PostgreSQL__: Se utiliza la versión __9.3.5__ de este motor de base datos relacionales.

## Diagrama Entidad Relación
 
![Diagrama de entidad relacion](https://raw.githubusercontent.com/ftrianakast/fbank/master/src/main/resources/other/fbank.png)


## Despliegue

### Parametrizacion de la base de datos
La base de datos debe ser configurada siguiendo el archivo sobre __fbank/src/main/resources/application.properties__. El archivo define la siguiente parametrización para la base de datos:

    spring.datasource.url = jdbc:postgresql://localhost:5432/fbank
    spring.datasource.username = postgres
    spring.datasource.password = postgres
    spring.datasource.driverClassName = org.postgresql.Driver
    
    # Specify the DBMS
    spring.jpa.database = POSTGRESQL
    
    # Show or not log for each sql query
    spring.jpa.show-sql = true
    
    # Hibernate settings are prefixed with spring.jpa.hibernate.*
    spring.jpa.hibernate.ddl-auto = update
    spring.jpa.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
    spring.jpa.hibernate.naming_strategy = org.hibernate.cfg.ImprovedNamingStrategy

### DDL Script
Aunque no es necesario porque Spring Data JPA viene configurado para la autogeneración, a continuación se provee dicho script

### Probar el proyecto en Eclipse
Clone el proyecto, e importelo como un Maven Project. Corra la clase __co/fbank/Application.java__ como una aplicación Standalone. De inmediato se levanta el servidor embebido Tomcat y ya puede probar los servicios REST. Esta ejecución simple es gracias a Spring Boot

### Probar el proyecto como un jar ejecutable
En __/src/main/resources__ usted puede encontrar un jar ejecutable llamado __fbank.jar__. Ejecute el jar con el siguiente comando:

### Probar el proyecto con docker
Esta es la opción más viable. SIn embargo queda para iteraciones posteriores.


## Manual de Usuario
A continuación se exponen cada una de las entradas y salidas de cada uno de los requerimientos solicitados. Para consumir los servicios puede usar:

- __Curl__
- __Cliente REST De Chrome__: Es una manera gráfica y fácil de enviar peticiones a servicios REST. Usted lo puede encontrar [acá][1].

### Client Resource

#### Crear cliente

#####__POST /clients__

Permite crear clientes. Recibe por input un json con una representación válidad de un cliente como se muestra a continuación:

__Request__:
    
    POST /clients
    
    {
       "name": "Felipe Triana",
       "address": "Calle 6B No 9-26, Madrid Cundinamarca",
       "phone": "3108187426"
    }

__Response__:

    Http Status: 201 Created
    User succesfully added with id: 2

#### Obtener cliente
##### __GET /clients/{clientId}__

Retorna el cliente indicado con variable Path clientId. Ejemplo:

__Request__:

    GET /clients/2

__Response__:
    
    Http Status: 200 OK
    {
        id: 2
        name: "Felipe Triana"
        address: "Calle 6B No 9-26, Madrid Cundinamarca"
        phone: "3108187426"
        accounts: [0]
    }

#### Actualizar cliente
##### __PUT /clients/{clientId}__

Actualiza el cliente con el idea dado como variable path en la url. Por ejemplo a continuación se quiere cambiar la dirección del cliente anterior:

__Request__:

    PUT clients/2
    
    {
        name: "Felipe Triana"
        address: "Rua Santa Clara 86, Río de Janeiro"
        phone: "3108187426"
    }

__Response__:

    Http Status: 202 Accepted
    The client was updated with new information


#### Borrar cliente
##### DELETE /clients/{clientId}

Borra un cliente con el id indicando. El borrado es en cascada, es decir que cuando se borra un cliente automáticamente se borran sus cuentas y por ende los movimientos de esas cuentas.


### 4.2. Account Resource:

#### Crear cuenta
##### POST /clients/{clientId}/accounts
Le crea una cuenta a un cliente. La cuenta se crea automáticamente con un saldo de 0. Ocurre un poco distinto que con los bancos normales, en dónde para abrir una cuenta usted necesita de un saldo inicial.

__Request__ (Sin cuerpo)

    POST /clients/2/accounts

__Response__

    Http Status: 201 Created
    The account was added correctly with a number: 35

#### Obtener cuenta
##### GET /accounts/{accountId}
Obtiene la cuenta cuyo id es dado por parámetro

__Request__
    
    GET /accounts/35

__Response__
    
    Http Status: 200 OK
    {
        number: 35
        balance: 0
        movements: [0]
    }

#### Borrar cuenta
##### DELETE /accounts/{accountId}
Borra la cuenta indicada por parámetro

__Request__

    DELETE /accounts/35
    
__Response__
    
    Http Status: 202 Accepted
    The account was successfully removed

__Nota__: La modificación de una cuenta directamente no tiene mucho sentido desde el punto de vista del negocio. El balance de una cuenta cambia por medio de sus movimientos. Por eso el método __PUT__ para este recurso no fue implementado.
    
### Movement Resource:

#### Crear movimiento
##### POST /accounts/{accountNumber}/movements
Registra un movimiento sobre la cuenta cuyo número es indicado por parámetro. Por simplificación se muestra el path de éxito. Sin embargo también está contemplado el caso en que se quieran hacer créditos sobre una cuenta negativa. En este caso el servidor response con 500 y un mensaje indicando que no se puede hacer la transacción.

__Request__

    POST /accounts/36/movements
    {
      "type": "DEBIT",
      "value": "350000"
    }

__Response__

    Http Status: 201 Created
    Your movement was performed and you have a new balance of: 350000

### Report Resource
#### Generar reporte
##### POST /clients/{clientId}/reports
Se genera el reporte requerido sobre el cliente indicado. Este reporte muestra un resumen de los movimientos de un cliente sobre cada una de sus cuentas.

__Request__

Tener en cuenta que las  fechas siempre tienen el formato __dd-MM-yy HH:mm:ss__

    POST /clients/2/reports
    
    {
      "initDate": "19-12-2014 12:00:00",
      "endDate": "19-12-2014 20:00:00"
    }
    
__Response__

    Http Status: 200 OK
    {
        "accounts": [
            {
                "accountNumber": 31,
                "totalDebitMovements": 0,
                "totalCreditMovements": 0,
                "debitMovements": [],
                "creditMovements": [],
                "balance": 0
            },
            {
                "accountNumber": 32,
                "totalDebitMovements": 0,
                "totalCreditMovements": 0,
                "debitMovements": [],
                "creditMovements": [],
                "balance": 0
            },
            {
                "accountNumber": 33,
                "totalDebitMovements": 3,
                "totalCreditMovements": 0,
                "debitMovements": [
                    {
                        "value": 20000,
                        "date": "19-12-2014 14:18:24"
                    },
                    {
                        "value": 20000.5,
                        "date": "19-12-2014 14:18:33"
                    },
                    {
                        "value": 20000.50006,
                        "date": "19-12-2014 14:18:42"
                    }
                ],
                "creditMovements": [],
                "balance": 60001.00006
            }
        ],
        "initDate": "19-12-2014 12:00:00",
        "endDate": "19-12-2014 20:00:00"
    }